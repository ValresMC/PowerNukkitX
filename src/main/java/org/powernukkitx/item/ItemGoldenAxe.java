package org.powernukkitx.item;

public class ItemGoldenAxe extends ItemTieredTool {
    public ItemGoldenAxe() {
        this(0, 1);
    }

    public ItemGoldenAxe(Integer meta) {
        this(meta, 1);
    }

    public ItemGoldenAxe(Integer meta, int count) {
        super(GOLDEN_AXE, meta, count, "Golden Axe", ToolTier.TOOL_TIER_GOLD);
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
