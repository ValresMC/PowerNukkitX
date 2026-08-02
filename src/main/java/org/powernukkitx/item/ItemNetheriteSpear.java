package org.powernukkitx.item;

/**
 * @author Buddelbubi
 * @since 2025/12/15
 */
public class ItemNetheriteSpear extends ItemSpear {

    public ItemNetheriteSpear() {
        this(0, 1);
    }

    public ItemNetheriteSpear(Integer meta) {
        this(meta, 1);
    }

    public ItemNetheriteSpear(Integer meta, int count) {
        super(NETHERITE_SPEAR, meta, count, "Netherite Spear", ToolTier.TOOL_TIER_NETHERITE);
    }
}
