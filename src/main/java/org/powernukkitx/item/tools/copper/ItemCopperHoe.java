package org.powernukkitx.item.tools.copper;

import org.powernukkitx.item.ItemTieredTool;
import org.powernukkitx.item.ToolTier;

public class ItemCopperHoe extends ItemTieredTool {
    public ItemCopperHoe() {
        super(COPPER_HOE, ToolTier.TOOL_TIER_COPPER);
    }

    @Override
    public boolean isHoe() {
        return true;
    }
}
