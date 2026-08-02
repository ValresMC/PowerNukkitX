package org.powernukkitx.item;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.powernukkitx.block.Block;

import java.util.Objects;

/**
 * Base implementation for tools whose properties are defined by a {@link ToolTier}.
 */
public abstract class ItemTieredTool extends ItemTool {
    private final @Nullable ToolTier toolTier;

    /**
     * Creates a single tiered tool without metadata or a custom display name.
     *
     * @param identifier the item identifier
     * @param toolTier the tier that defines the tool properties
     */
    protected ItemTieredTool(@NotNull String identifier, @NotNull ToolTier toolTier) {
        super(identifier);
        this.toolTier = Objects.requireNonNull(toolTier, "toolTier");
    }

    /**
     * Creates an item whose legacy subclass continues to provide its own tool properties.
     *
     * @param identifier the item identifier
     * @param meta the item metadata, or {@code null} when unspecified
     * @param count the item count
     * @param name the display name, or {@code null} to use the default name
     */
    protected ItemTieredTool(@NotNull String identifier, @Nullable Integer meta, int count,
                             @Nullable String name) {
        super(identifier, meta, count, name);
        this.toolTier = null;
    }

    /**
     * Creates a tiered tool with the supplied item data.
     *
     * @param identifier the item identifier
     * @param meta the item metadata, or {@code null} when unspecified
     * @param count the item count
     * @param name the display name, or {@code null} to use the default name
     * @param toolTier the tier that defines the tool properties
     */
    protected ItemTieredTool(@NotNull String identifier, @Nullable Integer meta, int count,
                             @Nullable String name, @NotNull ToolTier toolTier) {
        super(identifier, meta, count, name);
        this.toolTier = Objects.requireNonNull(toolTier, "toolTier");
    }

    /**
     * Returns the tier that defines this tool's common properties.
     *
     * @return the tool tier, or {@code null} for a legacy subclass that provides its own properties
     */
    public final @Nullable ToolTier getToolTier() {
        return this.toolTier;
    }

    @Override
    public int getTier() {
        return this.toolTier == null ? super.getTier() : this.toolTier.level();
    }

    @Override
    public int getMaxDurability() {
        return this.toolTier == null ? super.getMaxDurability() : this.toolTier.durability();
    }

    @Override
    public int getEnchantAbility() {
        return this.toolTier == null ? super.getEnchantAbility() : this.toolTier.enchantability();
    }

    @Override
    public int getAttackDamage() {
        return this.toolTier == null ? super.getAttackDamage() : this.toolTier.attackDamageFor(this);
    }

    @Override
    public @Nullable Integer getDiggerSpeed(@Nullable Block block) {
        Integer componentSpeed = super.getDiggerSpeed(block);
        ToolTier toolTier = this.toolTier;
        if (componentSpeed != null || block == null || toolTier == null) {
            return componentSpeed;
        }

        int blockToolType = block.getToolType();
        boolean correctTool = blockToolType == ItemTool.TYPE_PICKAXE && this.isPickaxe()
                || blockToolType == ItemTool.TYPE_AXE && this.isAxe()
                || blockToolType == ItemTool.TYPE_SHOVEL && this.isShovel()
                || blockToolType == ItemTool.TYPE_HOE && this.isHoe()
                || blockToolType == ItemTool.TYPE_SWORD && this.isSword()
                || blockToolType == ItemTool.TYPE_SHEARS && this.isShears();

        return correctTool ? toolTier.miningSpeed() : null;
    }
}
