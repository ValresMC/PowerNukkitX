package org.powernukkitx.item;

public class ItemNetheritePickaxe extends ItemTieredTool {
    public ItemNetheritePickaxe() {
        this(0, 1);
    }

    public ItemNetheritePickaxe(Integer meta) {
        this(meta, 1);
    }

    public ItemNetheritePickaxe(Integer meta, int count) {
        super(NETHERITE_PICKAXE, meta, count, "Netherite Pickaxe", ToolTier.TOOL_TIER_NETHERITE);
    }

    @Override
    public boolean isPickaxe() {
        return true;
    }

    @Override
    public boolean isLavaResistant() {
        return true;
    }
}
