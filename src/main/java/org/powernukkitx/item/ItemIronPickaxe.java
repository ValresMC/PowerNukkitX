package org.powernukkitx.item;

public class ItemIronPickaxe extends ItemTieredTool {
    public ItemIronPickaxe() {
        this(0, 1);
    }

    public ItemIronPickaxe(Integer meta) {
        this(meta, 1);
    }

    public ItemIronPickaxe(Integer meta, int count) {
        super(IRON_PICKAXE, meta, count, "Iron Pickaxe", ToolTier.TOOL_TIER_IRON);
    }

    @Override
    public boolean isPickaxe() {
        return true;
    }

}
