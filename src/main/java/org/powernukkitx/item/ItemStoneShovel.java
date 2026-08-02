package org.powernukkitx.item;

public class ItemStoneShovel extends ItemTieredTool {

    public ItemStoneShovel() {
        this(0, 1);
    }

    public ItemStoneShovel(Integer meta) {
        this(meta, 1);
    }

    public ItemStoneShovel(Integer meta, int count) {
        super(STONE_SHOVEL, meta, count, "Stone Shovel", ToolTier.TOOL_TIER_STONE);
    }

    @Override
    public boolean isShovel() {
        return true;
    }

}
