package org.powernukkitx.item;

public class ItemGoldenSword extends ItemTieredTool {
    public ItemGoldenSword() {
        this(0, 1);
    }

    public ItemGoldenSword(Integer meta) {
        this(meta, 1);
    }

    public ItemGoldenSword(Integer meta, int count) {
        super(GOLDEN_SWORD, meta, count, "Golden Sword", ToolTier.TOOL_TIER_GOLD);
    }

    @Override
    public boolean isSword() {
        return true;
    }

}
