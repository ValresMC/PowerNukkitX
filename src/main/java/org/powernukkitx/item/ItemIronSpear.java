package org.powernukkitx.item;

/**
 * @author Buddelbubi
 * @since 2025/12/15
 */
public class ItemIronSpear extends ItemSpear {

    public ItemIronSpear() {
        this(0, 1);
    }

    public ItemIronSpear(Integer meta) {
        this(meta, 1);
    }

    public ItemIronSpear(Integer meta, int count) {
        super(IRON_SPEAR, meta, count, "Iron Spear", ToolTier.TOOL_TIER_IRON);
    }
}
