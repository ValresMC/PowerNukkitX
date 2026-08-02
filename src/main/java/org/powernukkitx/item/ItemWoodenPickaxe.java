package org.powernukkitx.item;

public class ItemWoodenPickaxe extends ItemTieredTool {
    public ItemWoodenPickaxe() {
        this(0, 1);
    }

    public ItemWoodenPickaxe(Integer meta) {
        this(meta, 1);
    }

    public ItemWoodenPickaxe(Integer meta, int count) {
        super(WOODEN_PICKAXE, meta, count, "Wooden Pickaxe", ToolTier.TOOL_TIER_WOODEN);
    }

    @Override
    public boolean isPickaxe() {
        return true;
    }

}
