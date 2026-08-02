package org.powernukkitx.item;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.powernukkitx.block.Block;
import org.powernukkitx.item.tools.copper.ItemCopperAxe;
import org.powernukkitx.item.tools.copper.ItemCopperHoe;
import org.powernukkitx.item.tools.copper.ItemCopperPickaxe;
import org.powernukkitx.item.tools.copper.ItemCopperShovel;
import org.powernukkitx.item.tools.copper.ItemCopperSword;

import java.util.stream.Stream;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ItemTieredToolTest {

    @ParameterizedTest(name = "{0}")
    @MethodSource("tieredTools")
    void tierDefinesCommonToolProperties(String name, ItemTieredTool tool, ToolTier tier,
                                         int attackDamage, int toolType, Integer diggerSpeed) {
        Block block = mock(Block.class);
        when(block.getToolType()).thenReturn(toolType);

        Assertions.assertAll(name,
                () -> Assertions.assertSame(tier, tool.getToolTier()),
                () -> Assertions.assertEquals(tier.level(), tool.getTier()),
                () -> Assertions.assertEquals(tier.durability(), tool.getMaxDurability()),
                () -> Assertions.assertEquals(tier.enchantability(), tool.getEnchantAbility()),
                () -> Assertions.assertEquals(attackDamage, tool.getAttackDamage()),
                () -> Assertions.assertEquals(diggerSpeed, tool.getDiggerSpeed(block))
        );
    }

    private static Stream<Arguments> tieredTools() {
        return Stream.of(
                toolSet("wooden", ToolTier.TOOL_TIER_WOODEN,
                        new ItemWoodenSword(), new ItemWoodenAxe(), new ItemWoodenPickaxe(),
                        new ItemWoodenHoe(), new ItemWoodenShovel(), new ItemWoodenSpear()),
                toolSet("golden", ToolTier.TOOL_TIER_GOLD,
                        new ItemGoldenSword(), new ItemGoldenAxe(), new ItemGoldenPickaxe(),
                        new ItemGoldenHoe(), new ItemGoldenShovel(), new ItemGoldenSpear()),
                toolSet("stone", ToolTier.TOOL_TIER_STONE,
                        new ItemStoneSword(), new ItemStoneAxe(), new ItemStonePickaxe(),
                        new ItemStoneHoe(), new ItemStoneShovel(), new ItemStoneSpear()),
                toolSet("copper", ToolTier.TOOL_TIER_COPPER,
                        new ItemCopperSword(), new ItemCopperAxe(), new ItemCopperPickaxe(),
                        new ItemCopperHoe(), new ItemCopperShovel(), new ItemCopperSpear()),
                toolSet("iron", ToolTier.TOOL_TIER_IRON,
                        new ItemIronSword(), new ItemIronAxe(), new ItemIronPickaxe(),
                        new ItemIronHoe(), new ItemIronShovel(), new ItemIronSpear()),
                toolSet("diamond", ToolTier.TOOL_TIER_DIAMOND,
                        new ItemDiamondSword(), new ItemDiamondAxe(), new ItemDiamondPickaxe(),
                        new ItemDiamondHoe(), new ItemDiamondShovel(), new ItemDiamondSpear()),
                toolSet("netherite", ToolTier.TOOL_TIER_NETHERITE,
                        new ItemNetheriteSword(), new ItemNetheriteAxe(), new ItemNetheritePickaxe(),
                        new ItemNetheriteHoe(), new ItemNetheriteShovel(), new ItemNetheriteSpear())
        ).flatMap(stream -> stream);
    }

    private static Stream<Arguments> toolSet(String material, ToolTier tier,
                                             ItemTieredTool sword, ItemTieredTool axe,
                                             ItemTieredTool pickaxe, ItemTieredTool hoe,
                                             ItemTieredTool shovel, ItemTieredTool spear) {
        return Stream.of(
                Arguments.of(material + " sword", sword, tier, tier.baseAttackDamage(),
                        ItemTool.TYPE_SWORD, tier.miningSpeed()),
                Arguments.of(material + " axe", axe, tier, reducedDamage(tier, 1),
                        ItemTool.TYPE_AXE, tier.miningSpeed()),
                Arguments.of(material + " pickaxe", pickaxe, tier, reducedDamage(tier, 2),
                        ItemTool.TYPE_PICKAXE, tier.miningSpeed()),
                Arguments.of(material + " hoe", hoe, tier, reducedDamage(tier, 2),
                        ItemTool.TYPE_HOE, tier.miningSpeed()),
                Arguments.of(material + " shovel", shovel, tier, reducedDamage(tier, 3),
                        ItemTool.TYPE_SHOVEL, tier.miningSpeed()),
                Arguments.of(material + " spear", spear, tier, reducedDamage(tier, 3),
                        ItemTool.TYPE_SPEAR, null)
        );
    }

    private static int reducedDamage(ToolTier tier, int reduction) {
        return Math.max(1, tier.baseAttackDamage() - reduction);
    }
}
