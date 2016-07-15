package com.cricketcraft.ftbisland.commands;

import com.cricketcraft.ftbisland.FTBIslands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

public class SaveIslandsCommand extends CommandBase implements ICommand {
    private List<String> aliases;

    public SaveIslandsCommand() {
        aliases = new ArrayList<String>();
        aliases.add("island_save");
        aliases.add("islands_save");
    }

    @Override
    public String getCommandName() {
        return aliases.get(0);
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "island_save";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        try {
            FTBIslands.saveIslands(FTBIslands.getIslands());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
