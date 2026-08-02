package org.powernukkitx.item;

public class ItemStoneSword extends ItemTieredTool {

    public ItemStoneSword() {
        this(0, 1);
    }

    public ItemStoneSword(Integer meta) {
        this(meta, 1);
    }

    public ItemStoneSword(Integer meta, int count) {
        super(STONE_SWORD, meta, count, "Stone Sword", ToolTier.TOOL_TIER_STONE);
    }

    @Override
    public boolean isSword() {
        return true;
    }

}
