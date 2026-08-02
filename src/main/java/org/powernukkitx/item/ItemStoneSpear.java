package org.powernukkitx.item;

/**
 * @author Buddelbubi
 * @since 2025/12/15
 */
public class ItemStoneSpear extends ItemSpear {

    public ItemStoneSpear() {
        this(0, 1);
    }

    public ItemStoneSpear(Integer meta) {
        this(meta, 1);
    }

    public ItemStoneSpear(Integer meta, int count) {
        super(STONE_SPEAR, meta, count, "Stone Spear", ToolTier.TOOL_TIER_STONE);
    }
}
