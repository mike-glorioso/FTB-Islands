package com.cricketcraft.ftbisland.commands;

import com.cricketcraft.ftbisland.FTBIslands;
import com.cricketcraft.ftbisland.IslandUtils;

import net.minecraftforge.fml.common.FMLCommonHandler;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class CreateIslandsCommand extends CommandBase implements ICommand {
    private List<String> aliases;

    public CreateIslandsCommand() {
        aliases = new ArrayList<>();
        aliases.add("island_create");
        aliases.add("islands_create");
    }

    @Override
    public List getCommandAliases() {
        return aliases;
    }

    @Override
    public String getCommandName() {
        return aliases.get(0);
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "island_create <name> or create <name> <player>";
    }

    @Override
    public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
        return args.length == 1 ? getListOfStringsMatchingLastWord(args, getPlayers())
                : (args.length == 2 ? getListOfStringsMatchingLastWord(args, getPlayers())
                : null);
    }

    protected String[] getPlayers() {
        return FMLCommonHandler.instance().getMinecraftServerInstance().getAllUsernames();
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] input) {
        World world = sender.getEntityWorld();
        EntityPlayerMP player = (EntityPlayerMP) world.getPlayerEntityByName(sender.getName());
        if (!IslandUtils.createIsland(world, input[0], player != null ? player : null)) {
            if (player != null) {
                player.addChatComponentMessage(new TextComponentString("An island has already been created for that player!"));
            } else {
                FTBIslands.logger.info("An island has already been created for that player or something is broken!");
            }
        }
    }
}
