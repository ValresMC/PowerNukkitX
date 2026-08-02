package org.powernukkitx.item;

public class ItemDiamondShovel extends ItemTieredTool {
    public ItemDiamondShovel() {
        super(DIAMOND_SHOVEL, ToolTier.TOOL_TIER_DIAMOND);
    }

    @Override
    public boolean isShovel() {
        return true;
    }
}
