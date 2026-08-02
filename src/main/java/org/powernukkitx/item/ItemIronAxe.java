package org.powernukkitx.item;

public class ItemIronAxe extends ItemTieredTool {
    public ItemIronAxe() {
        this(0, 1);
    }

    public ItemIronAxe(Integer meta) {
        this(meta, 1);
    }

    public ItemIronAxe(Integer meta, int count) {
        super(IRON_AXE, meta, count, "Iron Axe", ToolTier.TOOL_TIER_IRON);
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
