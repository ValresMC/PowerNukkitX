package org.powernukkitx.blockentity;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.ArgumentCaptor;
import org.powernukkitx.Player;
import org.powernukkitx.ServerMockFixture;
import org.powernukkitx.block.Block;
import org.powernukkitx.block.BlockBrushable;
import org.powernukkitx.block.BlockSuspiciousGravel;
import org.powernukkitx.block.BlockSuspiciousSand;
import org.powernukkitx.block.property.CommonBlockProperties;
import org.powernukkitx.item.Item;
import org.powernukkitx.item.ItemBrush;
import org.powernukkitx.level.Level;
import org.powernukkitx.level.Position;
import org.powernukkitx.level.Sound;
import org.powernukkitx.level.format.IChunk;
import org.powernukkitx.level.format.LevelProvider;
import org.powernukkitx.math.BlockFace;
import org.powernukkitx.math.Vector3;
import org.powernukkitx.nbt.tag.CompoundTag;
import org.powernukkitx.scheduler.ServerScheduler;
import org.powernukkitx.utils.ItemHelper;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class BlockEntityBrushableTest {
    private Level level;
    private IChunk chunk;
    private Player player;
    private BlockEntityBrushable brushable;
    private ItemBrush brush;
    private Position position;
    private AtomicInteger tick;
    private AtomicReference<Block> block;

    @BeforeAll
    static void boot() {
        ServerMockFixture.boot();
    }

    @BeforeEach
    void setUp() {
        level = mock(Level.class);
        chunk = mock(IChunk.class);
        LevelProvider provider = mock(LevelProvider.class);
        player = mock(Player.class);
        tick = new AtomicInteger();
        position = new Position(0, 80, 0, level);
        Block initialBlock = new BlockSuspiciousSand();
        initialBlock.position(position);
        block = new AtomicReference<>(initialBlock);

        doReturn(level).when(provider).getLevel();
        doReturn(provider).when(chunk).getProvider();
        doReturn(ServerMockFixture.server).when(level).getServer();
        doReturn(mock(ServerScheduler.class)).when(level).getScheduler();
        doReturn(Map.of()).when(level).getChunkPlayers(anyInt(), anyInt());
        doReturn(true).when(player).isSurvival();
        doAnswer(invocation -> tick.get()).when(level).getTick();
        doAnswer(invocation -> block.get().clone()).when(level).getBlock(any(Vector3.class), anyBoolean());
        doAnswer(invocation -> block.get().clone()).when(level).getBlock(any(Vector3.class));
        doAnswer(invocation -> setBlock(invocation.getArgument(1)))
                .when(level).setBlock(any(Vector3.class), any(Block.class), anyBoolean(), anyBoolean());
        doAnswer(invocation -> setBlock(invocation.getArgument(1)))
                .when(level).setBlock(any(Vector3.class), any(Block.class));

        CompoundTag nbt = BlockEntity.getDefaultCompound(position, BlockEntityID.BRUSHABLE_BLOCK)
                .putCompound("item", ItemHelper.write(Item.get("minecraft:emerald")));
        brushable = new BlockEntityBrushable(chunk, nbt);
        doReturn(brushable).when(level).getBlockEntity(any(Vector3.class));
        brush = new ItemBrush();
    }

    private boolean setBlock(Block replacement) {
        Block placed = replacement.clone();
        placed.position(position);
        block.set(placed);
        return true;
    }

    private void activate(int currentTick, BlockFace face) {
        tick.set(currentTick);
        ((BlockBrushable) block.get()).onActivate(brush, player, face, 0.5f, 0.5f, 0.5f);
    }

    private void brushToCount(int count, BlockFace face) {
        activate(0, face);
        for (int stroke = 0; stroke < count; stroke++) {
            activate(6 + stroke * 10, face);
        }
    }

    @Test
    void repeatedInteractionsCannotCompleteBeforeNinetySixTicks() {
        for (int currentTick = 0; currentTick < 96; currentTick++) {
            activate(currentTick, BlockFace.NORTH);
            activate(currentTick, BlockFace.SOUTH);
        }
        assertEquals(9, brushable.getBrushCount());
        assertEquals(0, brush.getDamage());
        verify(level, never()).dropItem(any(Vector3.class), any(Item.class), any(Vector3.class));

        activate(96, BlockFace.SOUTH);
        assertEquals(Block.SAND, block.get().getId());
        assertTrue(brushable.closed);
        assertEquals(1, brush.getDamage());
        verify(level, times(1)).dropItem(any(Vector3.class), any(Item.class), any(Vector3.class));
    }

    @ParameterizedTest
    @EnumSource(BlockFace.class)
    void extractionUsesFirstFaceEvenWhenLaterStrokesUseAnotherFace(BlockFace face) {
        activate(0, face);
        for (int stroke = 0; stroke < 10; stroke++) {
            activate(6 + stroke * 10, face.getOpposite());
        }

        ArgumentCaptor<Vector3> source = ArgumentCaptor.forClass(Vector3.class);
        ArgumentCaptor<Item> drop = ArgumentCaptor.forClass(Item.class);
        ArgumentCaptor<Vector3> motion = ArgumentCaptor.forClass(Vector3.class);
        verify(level).dropItem(source.capture(), drop.capture(), motion.capture());
        assertEquals(0.5 + face.getXOffset() * 0.75, source.getValue().x, 0.00001);
        assertEquals(80.5 + face.getYOffset() * 0.75, source.getValue().y, 0.00001);
        assertEquals(0.5 + face.getZOffset() * 0.75, source.getValue().z, 0.00001);
        assertEquals("minecraft:emerald", drop.getValue().getId());
        assertEquals(0, motion.getValue().lengthSquared(), 0.00001);
        assertFalse(brushable.hasItem());
    }

    @Test
    void interruptedBrushingRegressesWithoutLosingLootAndAllowsNewFaceAfterReset() {
        brushToCount(9, BlockFace.NORTH);
        tick.set(125);
        assertTrue(brushable.onUpdate());
        assertEquals(9, brushable.getBrushCount());

        tick.set(126);
        assertTrue(brushable.onUpdate());
        assertEquals(7, brushable.getBrushCount());
        assertEquals((byte) BlockFace.NORTH.getIndex(), brushable.getBrushDirection());
        tick.set(130);
        brushable.onUpdate();
        assertEquals(5, brushable.getBrushCount());
        assertEquals(2, block.get().getPropertyValue(CommonBlockProperties.BRUSHED_PROGRESS));

        tick.set(142);
        assertFalse(brushable.onUpdate());
        assertEquals(0, brushable.getBrushCount());
        assertEquals(BlockEntityBrushable.BRUSH_DIRECTION_NONE, brushable.getBrushDirection());
        assertEquals(0, block.get().getPropertyValue(CommonBlockProperties.BRUSHED_PROGRESS));
        assertEquals("minecraft:emerald", brushable.getItem().getId());
        assertEquals(0, brush.getDamage());
        activate(142, BlockFace.EAST);
        activate(148, BlockFace.EAST);
        assertEquals(1, brushable.getBrushCount());
        assertEquals((byte) BlockFace.EAST.getIndex(), brushable.getBrushDirection());
    }

    @Test
    void resumeAppliesMissedDecayBeforeChoosingNewFace() {
        brushToCount(3, BlockFace.NORTH);
        activate(74, BlockFace.EAST);
        assertEquals(0, brushable.getBrushCount());
        assertEquals((byte) BlockFace.EAST.getIndex(), brushable.getBrushDirection());
        activate(80, BlockFace.EAST);
        assertEquals(1, brushable.getBrushCount());
    }

    @Test
    void storedProgressAndLootSurviveReloadAndResumeDecay() {
        brushToCount(5, BlockFace.WEST);
        CompoundTag stored = brushable.getStorageNBT().copy();
        brushable.close();
        tick.set(100);
        brushable = new BlockEntityBrushable(chunk, stored);
        assertEquals(5, brushable.getBrushCount());
        assertEquals((byte) BlockFace.WEST.getIndex(), brushable.getBrushDirection());
        assertEquals(Block.SUSPICIOUS_SAND, brushable.getType());
        assertEquals("minecraft:emerald", brushable.getSpawnCompound().getCompound("item").getString("Name"));
        tick.set(140);
        brushable.onUpdate();
        assertEquals(3, brushable.getBrushCount());
        assertTrue(brushable.hasItem());
    }

    @Test
    void otherItemsCannotAdvanceBrushing() {
        BlockBrushable target = (BlockBrushable) block.get();
        for (int currentTick = 0; currentTick <= 100; currentTick++) {
            tick.set(currentTick);
            target.onActivate(Item.get("minecraft:stick"), player, BlockFace.UP, 0.5f, 0.5f, 0.5f);
        }
        assertEquals(0, brushable.getBrushCount());
        assertEquals(BlockEntityBrushable.BRUSH_DIRECTION_NONE, brushable.getBrushDirection());
        verify(level, never()).dropItem(any(Vector3.class), any(Item.class), any(Vector3.class));
    }

    @Test
    void gravelUsesItsOwnBrushingSoundsAndBecomesGravel() {
        setBlock(new BlockSuspiciousGravel());
        brushable.loadNBT();
        brushToCount(10, BlockFace.SOUTH);
        assertEquals(Block.GRAVEL, block.get().getId());
        verify(level, times(10)).addSound(any(Vector3.class), eq(Sound.BRUSH_SUSPICIOUS_GRAVEL));
        verify(level).addSound(any(Vector3.class), eq(Sound.BRUSH_COMPLETED_SUSPICIOUS_GRAVEL));
    }

    @Test
    void creativeBrushDoesNotWear() {
        doReturn(false).when(player).isSurvival();
        doReturn(true).when(player).isCreative();
        brushToCount(10, BlockFace.UP);
        assertEquals(0, brush.getDamage());
    }

    @Test
    void unbreakableBrushDoesNotWearInSurvival() {
        brush.setNbt(new CompoundTag().putByte("Unbreakable", 1));
        brushToCount(10, BlockFace.UP);
        assertEquals(0, brush.getDamage());
    }

    @Test
    void emptyBlockCompletesWithoutCreatingLoot() {
        brushable.setItem(Item.AIR);
        brushToCount(10, BlockFace.UP);
        assertEquals(Block.SAND, block.get().getId());
        assertEquals(1, brush.getDamage());
        verify(level, never()).dropItem(any(Vector3.class), any(Item.class), any(Vector3.class));
    }
}
