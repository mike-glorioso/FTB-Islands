package com.cricketcraft.ftbisland;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.GameRegistry;

import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockLiquid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

import java.io.EOFException;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;

public class IslandCreator {
    private static final Item chickenStick = GameRegistry.findItem("excompressum", "chickenStick");
    public static HashMap<String, IslandPos> islandLocations = new HashMap<>();
    public final String playerName;
    public final IslandPos pos;

    public IslandCreator() {
        playerName = null;
        pos = null;
    }

    public IslandCreator(String playerName, IslandPos pos) {
        this.playerName = playerName;
        this.pos = pos;
    }

    public static boolean spawnIslandAt(World world, BlockPos pos, String playerName, EntityPlayer player) {
        reloadIslands();
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        if (!islandLocations.containsKey(playerName)) {
            if (FTBIslands.islandType.equalsIgnoreCase("tree")) {
                world.setBlockState(pos, Blocks.GRASS.getDefaultState());
                for (int c = -3; c < 2; c++) {
                    for (int d = -3; d < 2; d++) {
                        for (int e = 3; e < 5; e++) {
                            world.setBlockState(pos.add(c + 1, e, d + 1), Blocks.LEAVES.getDefaultState());
                        }
                    }
                }
                for (int c = -2; c < 1; c++) {
                    for (int d = -2; d < 1; d++) {
                        world.setBlockState(pos.add(c + 1, 5, d + 1), Blocks.LEAVES.getDefaultState());
                    }
                }

                world.setBlockState(pos.up(6), Blocks.LEAVES.getDefaultState());
                world.setBlockState(pos.add(1, 6, 0), Blocks.LEAVES.getDefaultState());
                world.setBlockState(pos.add(0, 6, 1), Blocks.LEAVES.getDefaultState());
                world.setBlockState(new BlockPos(x - 1, y + 6, z), Blocks.LEAVES.getDefaultState());
                world.setBlockState(new BlockPos(x - 1, y + 6, z), Blocks.LEAVES.getDefaultState());
                world.setBlockToAir(pos.add(2, 4, 2));

                for (int c = 0; c < 5; c++) {
                    world.setBlockState(pos.add(0, c + 1, 0), Blocks.LOG.getDefaultState());
                }
            } else if (FTBIslands.islandType.equalsIgnoreCase("grass")) {
                world.setBlockState(pos, Blocks.GRASS.getDefaultState());
                world.setBlockState(pos.up(), Blocks.STANDING_SIGN.getDefaultState());
                ((TileEntitySign) world.getTileEntity(pos.up())).signText[0] = new TextComponentString("You get it yet?");
            } else if (FTBIslands.islandType.equalsIgnoreCase("GoG")) {
                // This is similar to how Botania itself generates an island in GoG. This is being done to avoid a soft dependency.
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 4; j++) {
                        for (int k = 0; k < 3; k++) {
                            world.setBlockState(new BlockPos(x - 1 + i, y - j, z - 1 + k), j == 0 ? Blocks.GRASS.getDefaultState() : Blocks.DIRT.getDefaultState());
                        }
                    }
                }
                world.setBlockState(pos.add(-1, -1, 0), Blocks.FLOWING_WATER.getDefaultState());
                int[][] roots = new int[][] {
                        {-1, -2, -1},
                        {-1, -4, -2},
                        {-2, -3, -1},
                        {-2, -3, -2},
                        {1, -3, -1},
                        {1, -4, -1},
                        {2, -4, -1},
                        {2, -4, 0},
                        {3, -5, 0},
                        {0, -2, 1},
                        {0, -3, 2},
                        {0, -4, 3},
                        {1, -4, 3},
                        {1, -5, 2},
                        {1, -2, 0},
                };
                if (Loader.isModLoaded("Botania")) {
                    world.setBlockState(pos.add(1, 3, 1), GameRegistry.findBlock("Botania", "manaFlame").getDefaultState());
                    world.setBlockState(pos.down(3), Blocks.BEDROCK.getDefaultState());
                    for (int[] pos1 : roots) {
                        world.setBlockState(pos.add(pos1[0], pos1[1], pos1[3]), GameRegistry.findBlock("Botania", "root").getDefaultState());
                    }
                } else {
                    for (int[] pos1 : roots) {
                        world.setBlockState(pos.add(pos1[0], pos1[1], pos1[3]), Blocks.LOG.getDefaultState());
                    }
                }
            } else {
                for (int c = 0; c < 3; c++) {
                    for (int d = 0; d < 3; d++) {
                        world.setBlockState(pos.add(c, 0, d), Blocks.DIRT.getDefaultState());
                    }
                }

                world.setBlockState(pos.add(2, 1, 1), Blocks.CHEST.getDefaultState().withProperty(BlockChest.FACING, EnumFacing.WEST));
                TileEntityChest chest = (TileEntityChest) world.getTileEntity(pos.add(2, 1, 1));

                chest.setInventorySlotContents(0, new ItemStack(Items.WATER_BUCKET, 1));
                chest.setInventorySlotContents(1, new ItemStack(Items.LAVA_BUCKET, 1));
                chest.setInventorySlotContents(2, new ItemStack(Items.DYE, 64, 15));
                chest.setInventorySlotContents(3, new ItemStack(Items.DYE, 64, 15));
                chest.setInventorySlotContents(4, new ItemStack(Items.APPLE, 16));
                chest.setInventorySlotContents(5, new ItemStack(Blocks.SAPLING, 8, 0));
                chest.setInventorySlotContents(6, new ItemStack(Items.SPAWN_EGG, 2, 90));
                chest.setInventorySlotContents(7, new ItemStack(Items.SPAWN_EGG, 2, 91));
                chest.setInventorySlotContents(8, new ItemStack(Items.SPAWN_EGG, 2, 92));
                chest.setInventorySlotContents(9, new ItemStack(Items.SPAWN_EGG, 2, 93));
                if (chickenStick != null) {
                    chest.setInventorySlotContents(10, new ItemStack(chickenStick, 1));
                }
            }

            if (islandLocations.size() != 0) {
                islandLocations.put(playerName, FTBIslands.islandLoc.get(islandLocations.size() + 1));
            } else {
                islandLocations.put(playerName, FTBIslands.islandLoc.get(1));
            }

            islandLocations.put(playerName, new IslandPos(pos));
            try {
                FTBIslands.saveIslands(islandLocations);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (player != null) {
                player.addChatMessage(new TextComponentString(String.format("Created island named %s at %d, %d, %d", playerName, pos.getX(), pos.getY(), pos.getZ())));
            } else {
                FTBIslands.logger.info(String.format("Created island named %s at %d %d %d", playerName, pos.getX(), pos.getY(), pos.getZ()));
            }
            return true;
        } else {
            return false;
        }
    }

    protected static void reloadIslands() {
        try {
            islandLocations = FTBIslands.getIslands();
        } catch (EOFException e) {
            // silent catch
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected static void save() {
        try {
            FTBIslands.saveIslands(islandLocations);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class IslandPos implements Serializable {
        private BlockPos pos;

        public IslandPos(BlockPos pos) {
            this.pos = pos;
        }

        public int getX() {
            return pos.getX();
        }

        public int getY() {
            return pos.getY();
        }

        public int getZ() {
            return pos.getZ();
        }
    }
}
