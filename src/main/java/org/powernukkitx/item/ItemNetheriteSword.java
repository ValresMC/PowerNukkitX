package org.powernukkitx.item;

public class ItemNetheriteSword extends ItemTieredTool {
    public ItemNetheriteSword() {
        this(0, 1);
    }

    public ItemNetheriteSword(Integer meta) {
        this(meta, 1);
    }

    public ItemNetheriteSword(Integer meta, int count) {
        super(NETHERITE_SWORD, meta, count, "Netherite Sword", ToolTier.TOOL_TIER_NETHERITE);
    }

    @Override
    public boolean isSword() {
        return true;
    }

    @Override
    public boolean isLavaResistant() {
        return true;
    }
}
