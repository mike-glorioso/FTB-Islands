package com.cricketcraft.ftbisland.commands;

import com.cricketcraft.ftbisland.FTBIslands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ListIslandsCommand extends CommandBase implements ICommand {
    private List<String> aliases;

    public ListIslandsCommand() {
        aliases = new ArrayList<>();
        aliases.add("island_list");
        aliases.add("islands_list");
    }

    @Override
    public String getCommandName() {
        return aliases.get(0);
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "island_list";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] input) {
        try {
            final EntityPlayerMP player = getCommandSenderAsPlayer(sender);
            FTBIslands.getIslands().forEach((k, v) -> player.addChatComponentMessage(new TextComponentString(k)));
        } catch (IOException | PlayerNotFoundException e) {
            e.printStackTrace();
        }
    }
}
