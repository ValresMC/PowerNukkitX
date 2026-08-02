package org.powernukkitx.item;

public class ItemIronSword extends ItemTieredTool {
    public ItemIronSword() {
        this(0, 1);
    }

    public ItemIronSword(Integer meta) {
        this(meta, 1);
    }

    public ItemIronSword(Integer meta, int count) {
        super(IRON_SWORD, meta, count, "Iron Sword", ToolTier.TOOL_TIER_IRON);
    }

    @Override
    public boolean isSword() {
        return true;
    }

}
