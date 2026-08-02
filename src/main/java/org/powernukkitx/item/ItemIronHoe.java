package org.powernukkitx.item;

public class ItemIronHoe extends ItemTieredTool {
    public ItemIronHoe() {
        this(0, 1);
    }

    public ItemIronHoe(Integer meta) {
        this(meta, 1);
    }

    public ItemIronHoe(Integer meta, int count) {
        super(IRON_HOE, meta, count, "Iron Hoe", ToolTier.TOOL_TIER_IRON);
    }

    @Override
    public boolean isHoe() {
        return true;
    }
}
