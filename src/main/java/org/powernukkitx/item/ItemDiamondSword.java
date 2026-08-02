package org.powernukkitx.item;

public class ItemDiamondSword extends ItemTieredTool {
    public ItemDiamondSword() {
        super(DIAMOND_SWORD, ToolTier.TOOL_TIER_DIAMOND);
    }

    @Override
    public boolean isSword() {
        return true;
    }
}
