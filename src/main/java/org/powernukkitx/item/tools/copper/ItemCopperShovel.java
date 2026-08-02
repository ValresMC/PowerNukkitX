package org.powernukkitx.item.tools.copper;

import org.powernukkitx.item.ItemTieredTool;
import org.powernukkitx.item.ToolTier;

public class ItemCopperShovel extends ItemTieredTool {
    public ItemCopperShovel() {
        super(COPPER_SHOVEL, ToolTier.TOOL_TIER_COPPER);
    }

    @Override
    public boolean isShovel() {
        return true;
    }

}
