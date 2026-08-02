package org.powernukkitx.item;

public class ItemIronShovel extends ItemTieredTool {
    public ItemIronShovel() {
        this(0, 1);
    }

    public ItemIronShovel(Integer meta) {
        this(meta, 1);
    }

    public ItemIronShovel(Integer meta, int count) {
        super(IRON_SHOVEL, meta, count, "Iron Shovel", ToolTier.TOOL_TIER_IRON);
    }

    @Override
    public boolean isShovel() {
        return true;
    }

}
