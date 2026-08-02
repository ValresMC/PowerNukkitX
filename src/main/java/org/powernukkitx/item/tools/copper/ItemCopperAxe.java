package org.powernukkitx.item.tools.copper;

import org.powernukkitx.item.ItemTieredTool;
import org.powernukkitx.item.ToolTier;

public class ItemCopperAxe extends ItemTieredTool {
    public ItemCopperAxe() {
        super(COPPER_AXE, ToolTier.TOOL_TIER_COPPER);
    }

    @Override
    public boolean isAxe() {
        return true;
    }

    @Override
    public boolean canBreakShield() {
        return true;
    }
}
