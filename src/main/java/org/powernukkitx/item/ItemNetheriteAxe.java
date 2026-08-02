package org.powernukkitx.item;

public class ItemNetheriteAxe extends ItemTieredTool {
    public ItemNetheriteAxe() {
        this(0, 1);
    }

    public ItemNetheriteAxe(Integer meta) {
        this(meta, 1);
    }

    public ItemNetheriteAxe(Integer meta, int count) {
        super(NETHERITE_AXE, meta, count, "Netherite Axe", ToolTier.TOOL_TIER_NETHERITE);
    }

    @Override
    public boolean isAxe() {
        return true;
    }

    @Override
    public boolean isLavaResistant() {
        return true;
    }

    @Override
    public boolean canBreakShield() {
        return true;
    }
}
