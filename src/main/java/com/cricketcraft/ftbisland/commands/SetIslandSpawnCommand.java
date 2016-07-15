package com.cricketcraft.ftbisland.commands;

import com.cricketcraft.ftbisland.IslandUtils;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

public class SetIslandSpawnCommand extends CommandBase implements ICommand {
    private List<String> aliases;

    public SetIslandSpawnCommand() {
        aliases = new ArrayList<>();
        aliases.add("island_setspawn");
        aliases.add("islands_setspawn");
    }

    @Override
    public String getCommandName() {
        return aliases.get(0);
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "island_setspawn <IslandName> or island_setspawn <IslandName> <X> <Y> <Z>";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] input) throws CommandException {
        IslandUtils.setSpawnForIsland(input[0], sender.getPosition());
    }
}
