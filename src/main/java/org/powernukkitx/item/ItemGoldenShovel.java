package org.powernukkitx.item;

public class ItemGoldenShovel extends ItemTieredTool {
    public ItemGoldenShovel() {
        this(0, 1);
    }

    public ItemGoldenShovel(Integer meta) {
        this(meta, 1);
    }

    public ItemGoldenShovel(Integer meta, int count) {
        super(GOLDEN_SHOVEL, meta, count, "Golden Shovel", ToolTier.TOOL_TIER_GOLD);
    }

    @Override
    public boolean isShovel() {
        return true;
    }

}
