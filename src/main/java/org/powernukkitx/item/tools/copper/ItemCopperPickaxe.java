package org.powernukkitx.item.tools.copper;

import org.powernukkitx.item.ItemTieredTool;
import org.powernukkitx.item.ToolTier;

public class ItemCopperPickaxe extends ItemTieredTool {
    public ItemCopperPickaxe() {
        super(COPPER_PICKAXE, ToolTier.TOOL_TIER_COPPER);
    }

    @Override
    public boolean isPickaxe() {
        return true;
    }

}
