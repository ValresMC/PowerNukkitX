package org.powernukkitx.item;

public class ItemWoodenHoe extends ItemTieredTool {

    public ItemWoodenHoe() {
        this(0, 1);
    }

    public ItemWoodenHoe(Integer meta) {
        this(meta, 1);
    }

    public ItemWoodenHoe(Integer meta, int count) {
        super(WOODEN_HOE, meta, count, "Wooden Hoe", ToolTier.TOOL_TIER_WOODEN);
    }

    @Override
    public boolean isHoe() {
        return true;
    }
}
