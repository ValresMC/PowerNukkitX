package org.powernukkitx.item;

public class ItemGoldenHoe extends ItemTieredTool {
    public ItemGoldenHoe() {
        this(0, 1);
    }

    public ItemGoldenHoe(Integer meta) {
        this(meta, 1);
    }

    public ItemGoldenHoe(Integer meta, int count) {
        super(GOLDEN_HOE, meta, count, "Golden Hoe", ToolTier.TOOL_TIER_GOLD);
    }

    @Override
    public boolean isHoe() {
        return true;
    }
}
