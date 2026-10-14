package org.powernukkitx.blockentity;

import org.powernukkitx.block.Block;
import org.powernukkitx.block.property.CommonBlockProperties;
import org.powernukkitx.item.Item;
import org.powernukkitx.level.format.IChunk;
import org.powernukkitx.math.BlockFace;
import org.powernukkitx.nbt.tag.CompoundTag;
import org.powernukkitx.utils.ItemHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Buddelbubi
 * @since 2026/03/31
 */
public class BlockEntityBrushable extends BlockEntitySpawnable {
    public static final int BRUSH_COUNT_COMPLETE = 10;
    public static final byte BRUSH_DIRECTION_NONE = 6;

    private static final int FIRST_BRUSH_DELAY = 6;
    private static final int BRUSH_INTERVAL = 10;
    private static final int RESET_DELAY = 40;
    private static final int RESET_INTERVAL = 4;
    private static final int RESET_AMOUNT = 2;

    private boolean brushing;
    private int nextBrushTick;
    private int nextResetTick;

    public BlockEntityBrushable(IChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    /**
     * Checks whether the owning position still contains suspicious sand or gravel.
     *
     * @return whether this block entity still belongs to an archaeology block
     */
    @Override
    public boolean isBlockEntityValid() {
        String blockId = getBlock().getId();
        return Block.SUSPICIOUS_GRAVEL.equals(blockId) || Block.SUSPICIOUS_SAND.equals(blockId);
    }

    /**
     * Normalizes persisted progress and direction without changing the stored loot or loot
     * table. Loaded progress starts regressing after forty ticks on the owning level's clock.
     * Called during initialization or synchronous loading, before brushing resumes.
     */
    @Override
    public void loadNBT() {
        super.loadNBT();
        this.nbt.putInt("brush_count", getBrushCount());
        this.nbt.putByte("brush_direction", getBrushCount() == 0 ? BRUSH_DIRECTION_NONE : getBrushDirection());
        this.nbt.putString("type", getBlock().getId());
        this.brushing = false;
        this.nextResetTick = getLevel().getTick() + RESET_DELAY;
        if (getBrushCount() > 0) {
            scheduleUpdate();
        }
    }

    /**
     * Attempts a brush stroke, preserving the first face until progress returns to zero.
     * The first stroke waits six level ticks; subsequent strokes wait at least ten ticks.
     * Repeated calls in the same tick cannot advance the count more than once. This method
     * does not extract the item when the count reaches {@link #BRUSH_COUNT_COMPLETE}.
     * Call only from the level's synchronous interaction context. Callers coordinating
     * completion with this method must hold this block entity's monitor.
     *
     * @param face non-null face being brushed
     * @return whether a stroke advanced the count; false during the delay or after closure
     */
    public synchronized boolean brush(@NotNull BlockFace face) {
        if (this.closed || !isBlockEntityValid()) {
            return false;
        }

        int tick = getLevel().getTick();
        resetProgress(tick);
        if (getBrushCount() >= BRUSH_COUNT_COMPLETE) {
            return false;
        }

        if (!this.brushing) {
            this.brushing = true;
            this.nextBrushTick = tick + FIRST_BRUSH_DELAY;
        }
        if (getBrushDirection() == BRUSH_DIRECTION_NONE) {
            setBrushDirection(face);
        }
        this.nextResetTick = tick + RESET_DELAY;
        scheduleUpdate();

        if (tick < this.nextBrushTick) {
            return false;
        }

        this.nextBrushTick = tick + BRUSH_INTERVAL;
        setBrushCount(getBrushCount() + 1);
        updateProgress();
        return true;
    }

    /**
     * Regresses interrupted brushing using the owning level's tick clock. The loot is
     * retained, and the original face is cleared only when the count reaches zero.
     * Called by the level update loop while holding this block entity's monitor.
     *
     * @return whether progress or a pending first stroke still requires updates
     */
    @Override
    public synchronized boolean onUpdate() {
        if (this.closed) {
            return false;
        }
        if (!isBlockEntityValid()) {
            close();
            return false;
        }
        resetProgress(getLevel().getTick());
        return this.brushing || getBrushCount() > 0;
    }

    private void resetProgress(int tick) {
        if (tick < this.nextResetTick || (!this.brushing && getBrushCount() == 0)) {
            return;
        }

        int steps = Math.min(BRUSH_COUNT_COMPLETE / RESET_AMOUNT,
                1 + (tick - this.nextResetTick) / RESET_INTERVAL);
        this.nextResetTick += steps * RESET_INTERVAL;
        this.brushing = false;
        setBrushCount(getBrushCount() - steps * RESET_AMOUNT);
        if (getBrushCount() == 0) {
            setBrushDirection(null);
        }
        updateProgress();
    }

    private void updateProgress() {
        Block block = getBlock();
        int count = getBrushCount();
        int progress = count == 0 ? 0 : count < 3 ? 1 : count < 6 ? 2 : 3;
        if (block.getPropertyValue(CommonBlockProperties.BRUSHED_PROGRESS) != progress) {
            block.setPropertyValue(CommonBlockProperties.BRUSHED_PROGRESS, progress);
            getLevel().setBlock(this, block, false, false);
        }
        spawnToAll();
    }

    /**
     * Returns the persisted brush count, clamped to the valid range.
     *
     * @return brush count
     */
    public int getBrushCount() {
        return Math.max(0, Math.min(BRUSH_COUNT_COMPLETE, this.nbt.getInt("brush_count")));
    }

    /**
     * Sets the persisted brush count.
     *
     * @param brushCount brush count
     */
    public void setBrushCount(int brushCount) {
        this.nbt.putInt("brush_count", Math.max(0, Math.min(BRUSH_COUNT_COMPLETE, brushCount)));
        this.setDirty();
    }

    /**
     * Returns the persisted face index, or {@link #BRUSH_DIRECTION_NONE} if absent or invalid.
     *
     * @return face index from zero to five, or six for no active direction
     */
    public byte getBrushDirection() {
        if (!this.nbt.containsByte("brush_direction")) return BRUSH_DIRECTION_NONE;
        byte direction = this.nbt.getByte("brush_direction");
        return direction >= 0 && direction < BRUSH_DIRECTION_NONE ? direction : BRUSH_DIRECTION_NONE;
    }

    /**
     * Sets the persisted brush direction.
     *
     * @param face brush direction, or {@code null} for no active direction
     */
    public void setBrushDirection(@Nullable BlockFace face) {
        this.nbt.putByte("brush_direction", face != null ? (byte) face.getIndex() : BRUSH_DIRECTION_NONE);
        this.setDirty();
    }

    /**
     * Returns the persisted brushable block type.
     *
     * @return persisted type
     */
    public String getType() {
        return this.nbt.getString("type");
    }

    /**
     * Returns whether this brushable block entity contains an item.
     *
     * @return whether an item is present
     */
    public boolean hasItem() {
        return this.nbt.containsCompound("item") && !getItem().isNull();
    }

    public Item getItem() {
        if (!this.nbt.containsCompound("item")) return Item.AIR;
        return ItemHelper.read(this.nbt.getCompound("item"));
    }

    public void setItem(Item item) {
        if (item == null || item.isNull()) {
            this.nbt.remove("item");
        } else {
            this.nbt.putCompound("item", ItemHelper.write(item));
        }

        this.setDirty();
    }

    /*
     * TODO: Implement Bedrock loot_tables resolution.
     *
     * LootTable and LootTableSeed are preserved exactly as BDS stores them,
     * but PNX does not yet resolve the referenced Bedrock loot table into
     * the archaeology item.
     */
    /**
     * Returns whether a Bedrock loot table is persisted.
     *
     * @return whether a loot table is present
     */
    public boolean hasLootTable() {
        return this.nbt.containsString("LootTable") && !this.nbt.getString("LootTable").isEmpty();
    }

    /**
     * Returns the persisted Bedrock loot table identifier.
     *
     * @return loot table identifier
     */
    public String getLootTable() {
        return this.nbt.getString("LootTable");
    }

    /**
     * Returns the persisted Bedrock loot table seed.
     *
     * @return loot table seed
     */
    public int getLootTableSeed() {
        return this.nbt.getInt("LootTableSeed");
    }

    /**
     * Removes the persisted Bedrock loot table data.
     */
    public void clearLootTable() {
        this.nbt.remove("LootTable", "LootTableSeed");
        this.setDirty();
    }

    @Override
    public CompoundTag getSpawnCompound() {
        CompoundTag tag = super.getSpawnCompound()
                .putInt("brush_count", getBrushCount())
                .putByte("brush_direction", getBrushDirection())
                .putString("type", getType());

        if (this.nbt.containsCompound("item")) {
            tag.putCompound("item", this.nbt.getCompound("item").copy());
        }

        return tag;
    }
}
