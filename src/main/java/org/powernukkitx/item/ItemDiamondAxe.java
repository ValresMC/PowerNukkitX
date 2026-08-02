package org.powernukkitx.item;

public class ItemDiamondAxe extends ItemTieredTool {
    public ItemDiamondAxe() {
        super(DIAMOND_AXE, ToolTier.TOOL_TIER_DIAMOND);
    }

    @Override
    public boolean isAxe() {
        return true;
    }

    @Override
    public boolean canBreakShield() {
        return true;
    }
}
