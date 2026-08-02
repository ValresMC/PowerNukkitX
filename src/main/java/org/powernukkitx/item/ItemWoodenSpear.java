package org.powernukkitx.item;

/**
 * @author Buddelbubi
 * @since 2025/12/15
 */
public class ItemWoodenSpear extends ItemSpear {

    public ItemWoodenSpear() {
        this(0, 1);
    }

    public ItemWoodenSpear(Integer meta) {
        this(meta, 1);
    }

    public ItemWoodenSpear(Integer meta, int count) {
        super(WOODEN_SPEAR, meta, count, "Wooden Spear", ToolTier.TOOL_TIER_WOODEN);
    }
}
