package org.powernukkitx.item;

public class ItemNetheriteShovel extends ItemTieredTool {
    public ItemNetheriteShovel() {
        this(0, 1);
    }

    public ItemNetheriteShovel(Integer meta) {
        this(meta, 1);
    }

    public ItemNetheriteShovel(Integer meta, int count) {
        super(NETHERITE_SHOVEL, meta, count, "Netherite Shovel", ToolTier.TOOL_TIER_NETHERITE);
    }

    @Override
    public boolean isShovel() {
        return true;
    }

    @Override
    public boolean isLavaResistant() {
        return true;
    }
}
