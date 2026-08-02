package org.powernukkitx.item;

/**
 * @author Buddelbubi
 * @since 2025/12/15
 */
public class ItemCopperSpear extends ItemSpear {

    public ItemCopperSpear() {
        this(0, 1);
    }

    public ItemCopperSpear(Integer meta) {
        this(meta, 1);
    }

    public ItemCopperSpear(Integer meta, int count) {
        super(COPPER_SPEAR, meta, count, "Copper Spear", ToolTier.TOOL_TIER_COPPER);
    }
}
