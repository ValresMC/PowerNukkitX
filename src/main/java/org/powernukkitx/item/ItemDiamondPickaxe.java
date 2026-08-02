package org.powernukkitx.item;

public class ItemDiamondPickaxe extends ItemTieredTool {
    public ItemDiamondPickaxe() {
        super(DIAMOND_PICKAXE, ToolTier.TOOL_TIER_DIAMOND);
    }

    @Override
    public boolean isPickaxe() {
        return true;
    }
}
