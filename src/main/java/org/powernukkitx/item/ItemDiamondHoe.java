package org.powernukkitx.item;

public class ItemDiamondHoe extends ItemTieredTool {
    public ItemDiamondHoe() {
        super(DIAMOND_HOE, ToolTier.TOOL_TIER_DIAMOND);
    }

    @Override
    public boolean isHoe() {
        return true;
    }
}
