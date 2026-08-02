package org.powernukkitx.item;

/**
 * @author Buddelbubi
 * @since 2025/12/15
 */
public class ItemDiamondSpear extends ItemSpear {

    public ItemDiamondSpear() {
        this(0, 1);
    }

    public ItemDiamondSpear(Integer meta) {
        this(meta, 1);
    }

    public ItemDiamondSpear(Integer meta, int count) {
        super(DIAMOND_SPEAR, meta, count, "Diamond Spear", ToolTier.TOOL_TIER_DIAMOND);
    }
}
