package org.powernukkitx.item;

public class ItemGoldenPickaxe extends ItemTieredTool {
    public ItemGoldenPickaxe() {
        this(0, 1);
    }

    public ItemGoldenPickaxe(Integer meta) {
        this(meta, 1);
    }

    public ItemGoldenPickaxe(Integer meta, int count) {
        super(GOLDEN_PICKAXE, meta, count, "Golden Pickaxe", ToolTier.TOOL_TIER_GOLD);
    }

    @Override
    public boolean isPickaxe() {
        return true;
    }

}
