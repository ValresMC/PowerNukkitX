package org.powernukkitx.item;

public class ItemStoneAxe extends ItemTieredTool {

    public ItemStoneAxe() {
        this(0, 1);
    }

    public ItemStoneAxe(Integer meta) {
        this(meta, 1);
    }

    public ItemStoneAxe(Integer meta, int count) {
        super(STONE_AXE, meta, count, "Stone Axe", ToolTier.TOOL_TIER_STONE);
    }

    @Override
    public boolean isAxe() {
        return true;
    }

    @Override
    public boolean canBreakShield() {
        return true;
    }
}
