package org.powernukkitx.block;

import org.powernukkitx.block.definition.BlockDefinition;

import org.powernukkitx.Player;
import org.powernukkitx.blockentity.BlockEntityBrushable;
import org.powernukkitx.blockentity.BlockEntityID;
import org.powernukkitx.entity.item.EntityFallingBlock;
import org.powernukkitx.item.Item;
import org.powernukkitx.item.ItemBrush;
import org.powernukkitx.item.enchantment.Enchantment;
import org.powernukkitx.level.Level;
import org.powernukkitx.level.Sound;
import org.powernukkitx.math.BlockFace;
import org.powernukkitx.math.Vector3;
import org.powernukkitx.nbt.tag.CompoundTag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Buddelbubi
 * @since 2026/03/31
 */
public abstract class BlockBrushable extends BlockFallable implements BlockEntityHolder<BlockEntityBrushable> {
    public static final BlockDefinition DEFINITION = FALLABLE.toBuilder()
            .canBeActivated(true)
            .build();

    public BlockBrushable(BlockState blockstate) {
        super(blockstate, DEFINITION);
    }

    public BlockBrushable(BlockState blockstate, BlockDefinition definition) {
        super(blockstate, definition);
    }

    /**
     * Handles a player's brush interaction, including held-use interactions. Other items,
     * null players and null faces do not advance extraction. Completion converts the block
     * and drops its stored item outside the first brushed face. Only completion wears the
     * brush in survival or adventure mode. Called from synchronous level interaction code;
     * the block entity monitor coordinates this interaction with level-thread updates.
     *
     * @param item non-null item used on the block
     * @param player interacting player, or null for a non-player interaction
     * @param blockFace brushed face, or null when no face is supplied
     * @param fx relative hit position on the X axis
     * @param fy relative hit position on the Y axis
     * @param fz relative hit position on the Z axis
     * @return whether the brush interaction was handled
     */
    @Override
    public boolean onActivate(@NotNull Item item, @Nullable Player player, @Nullable BlockFace blockFace, float fx, float fy, float fz) {
        if (!(item instanceof ItemBrush) || player == null || blockFace == null) {
            return super.onActivate(item, player, blockFace, fx, fy, fz);
        }

        Level level = getLevel();
        BlockEntityBrushable brushable = getOrCreateBlockEntity();
        synchronized (brushable) {
            if (!brushable.brush(blockFace)) {
                return true;
            }

            level.addSound(this, getHitSound());
            if (brushable.getBrushCount() < BlockEntityBrushable.BRUSH_COUNT_COMPLETE) {
                return true;
            }

            Item drop = brushable.getItem();
            BlockFace dropFace = BlockFace.fromIndex(brushable.getBrushDirection());
            if (!level.setBlock(this, getFinalState())) {
                brushable.setBrushCount(BlockEntityBrushable.BRUSH_COUNT_COMPLETE - 1);
                brushable.spawnToAll();
                return true;
            }

            brushable.setItem(Item.AIR);
            brushable.close();
            if (!drop.isNull()) {
                level.dropItem(add(
                        0.5 + dropFace.getXOffset() * 0.75,
                        0.5 + dropFace.getYOffset() * 0.75,
                        0.5 + dropFace.getZOffset() * 0.75), drop, new Vector3(0, 0, 0));
            }
            level.addSound(this, getBreakSound());
        }

        if ((player.isSurvival() || player.isAdventure()) && !item.isUnbreakable()) {
            Enchantment durability = item.getEnchantment(Enchantment.ID_DURABILITY);
            if (durability == null || durability.getLevel() <= 0
                    || ThreadLocalRandom.current().nextInt(durability.getLevel() + 1) == 0) {
                item.incDamage(1);
            }
        }
        return true;
    }

    @Override
    protected EntityFallingBlock createFallingEntity(CompoundTag customNbt) {
        customNbt = customNbt.copy().putBoolean("BreakOnGround", true);
        return super.createFallingEntity(customNbt);
    }

    @Override
    public Item[] getDrops(Item item) {
        return new Item[]{Item.AIR};
    }

    @Override
    public boolean canBePulled() {
        return false;
    }

    @Override
    public boolean breaksWhenMoved() {
        return true;
    }

    public abstract Block getFinalState();

    protected abstract Sound getHitSound();

    protected abstract Sound getBreakSound();

    @Override
    public @NotNull Class<? extends BlockEntityBrushable> getBlockEntityClass() {
        return BlockEntityBrushable.class;
    }

    @Override
    public @NotNull String getBlockEntityType() {
        return BlockEntityID.BRUSHABLE_BLOCK;
    }
}
