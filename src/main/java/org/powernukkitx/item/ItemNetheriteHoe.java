package org.powernukkitx.item;

public class ItemNetheriteHoe extends ItemTieredTool {

    public ItemNetheriteHoe() {
        this(0, 1);
    }

    public ItemNetheriteHoe(Integer meta) {
        this(meta, 1);
    }

    public ItemNetheriteHoe(Integer meta, int count) {
        super(NETHERITE_HOE, meta, count, "Netherite Hoe", ToolTier.TOOL_TIER_NETHERITE);
    }

    @Override
    public boolean isHoe() {
        return true;
    }

    @Override
    public boolean isLavaResistant() {
        return true;
    }
}
