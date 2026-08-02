package org.powernukkitx.item.tools.copper;

import org.powernukkitx.item.ItemTieredTool;
import org.powernukkitx.item.ToolTier;

public class ItemCopperSword extends ItemTieredTool {
    public ItemCopperSword() {
        super(COPPER_SWORD, ToolTier.TOOL_TIER_COPPER);
    }

    @Override
    public boolean isSword() {
        return true;
    }

}
