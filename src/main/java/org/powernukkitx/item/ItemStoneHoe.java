package org.powernukkitx.item;

public class ItemStoneHoe extends ItemTieredTool {
    public ItemStoneHoe() {
        this(0, 1);
    }

    public ItemStoneHoe(Integer meta) {
        this(meta, 1);
    }

    public ItemStoneHoe(Integer meta, int count) {
        super(STONE_HOE, meta, count, "Stone Hoe", ToolTier.TOOL_TIER_STONE);
    }

    @Override
    public boolean isHoe() {
        return true;
    }
}
