package org.powernukkitx.item;

public class ItemWoodenAxe extends ItemTieredTool {
    public ItemWoodenAxe() {
        this(0, 1);
    }

    public ItemWoodenAxe(Integer meta) {
        this(meta, 1);
    }

    public ItemWoodenAxe(Integer meta, int count) {
        super(WOODEN_AXE, meta, count, "Wooden Axe", ToolTier.TOOL_TIER_WOODEN);
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
