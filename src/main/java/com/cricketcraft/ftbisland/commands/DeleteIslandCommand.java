package com.cricketcraft.ftbisland.commands;

import com.cricketcraft.ftbisland.FTBIslands;
import com.cricketcraft.ftbisland.IslandUtils;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class DeleteIslandCommand extends CommandBase implements ICommand {
    private List<String> aliases;

    public DeleteIslandCommand() {
        aliases = new ArrayList<String>();
        aliases.add("island_delete");
        aliases.add("islands_delete");
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
        return "island_delete <IslandName>";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] input) throws CommandException {
        World world = sender.getEntityWorld();
        EntityPlayerMP player = (EntityPlayerMP) world.getPlayerEntityByName(sender.getName());
        boolean exists = player != null;
        IslandUtils.deleteIsland(input[0]);
        if (exists) {
            player.addChatComponentMessage(new TextComponentString(String.format("Successfully deleted island %s", input[0])));
        } else {
            FTBIslands.logger.info(String.format("Successfully deleted island %s", input[0]));
        }
    }
}
