package org.powernukkitx.item;

public class ItemWoodenShovel extends ItemTieredTool {

    public ItemWoodenShovel() {
        this(0, 1);
    }

    public ItemWoodenShovel(Integer meta) {
        this(meta, 1);
    }

    public ItemWoodenShovel(Integer meta, int count) {
        super(WOODEN_SHOVEL, meta, count, "Wooden Shovel", ToolTier.TOOL_TIER_WOODEN);
    }

    @Override
    public boolean isShovel() {
        return true;
    }

}
