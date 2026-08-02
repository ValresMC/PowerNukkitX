package org.powernukkitx.item;

public class ItemStonePickaxe extends ItemTieredTool {
    public ItemStonePickaxe() {
        this(0, 1);
    }

    public ItemStonePickaxe(Integer meta) {
        this(meta, 1);
    }

    public ItemStonePickaxe(Integer meta, int count) {
        super(STONE_PICKAXE, meta, count, "Stone Pickaxe", ToolTier.TOOL_TIER_STONE);
    }

    @Override
    public boolean isPickaxe() {
        return true;
    }

}
