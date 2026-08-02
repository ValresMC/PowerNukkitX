package org.powernukkitx.item;

public class ItemWoodenSword extends ItemTieredTool {

    public ItemWoodenSword() {
        this(0, 1);
    }

    public ItemWoodenSword(Integer meta) {
        this(meta, 1);
    }

    public ItemWoodenSword(Integer meta, int count) {
        super(WOODEN_SWORD, meta, count, "Wooden Sword", ToolTier.TOOL_TIER_WOODEN);
    }

    @Override
    public boolean isSword() {
        return true;
    }

}
