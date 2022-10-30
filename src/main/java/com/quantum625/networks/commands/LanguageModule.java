package com.quantum625.networks.commands;

import com.quantum625.networks.Network;
import com.quantum625.networks.data.Language;
import com.quantum625.networks.utils.Location;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.io.File;


public class LanguageModule {

    private Language language;

    public LanguageModule(File datafolder, String lang_id) {
        this.language = new Language(datafolder, lang_id);
    }

    public void message(CommandSender sender, String message) {
        if (sender instanceof Player) {
            ((Player) sender).sendMessage(message);
        }

        else {
            Bukkit.getLogger().info("§9[Networks] §f" + message);
        }
    }

    public void returnMessage(CommandSender sender, String id) {
        if (language == null) {
            message(sender, "ERROR: Language module not found, please contact your system administrator");
        }

        if (language.getText(id) == null) {
            message(sender, "ERROR: No language key found for " + id);
        }

        else {
            message(sender, language.getText(id));
        }
    }

    public void returnMessage(CommandSender sender, String id, Player player) {
        if (language == null) {
            message(sender, "ERROR: Language module not found, please contact your system administrator");
        }

        if (language.getText(id) == null) {
            message(sender, "ERROR: No language key found for " + id);
        }

        else {
            message(sender, language.getText(id).replaceAll("%player", player.getDisplayName()));
        }
    }

    public void returnMessage(CommandSender sender, String id, Network network) {
        if (language == null) {
            message(sender, "ERROR: Language module not found, please contact your system administrator");
        }

        if (language.getText(id) == null) {
            message(sender, "ERROR: No language key found for " + id);
        }

        else {
            message(sender, language.getText(id).replaceAll("%network", network.getID()));
        }
    }

    public void returnMessage(CommandSender sender, String id, Network network, double value) {
        if (language == null) {
            message(sender, "ERROR: Language module not found, please contact your system administrator");
        }

        if (language.getText(id) == null) {
            message(sender, "ERROR: No language key found for " + id);
        }

        else {
            message(sender, language.getText(id).replaceAll("%network", network.getID()).replaceAll("%value", String.valueOf(value)));
        }
    }

    public void returnMessage(CommandSender sender, String id, Location location) {
        if (language == null) {
            message(sender, "ERROR: Language module not found, please contact your system administrator");
        }

        if (language.getText(id) == null) {
            message(sender, "ERROR: No language key found for " + id);
        }

        else {
            message(sender, language.getText(id).replaceAll("%position", location.toString()));
        }
    }

    public void returnMessage(CommandSender sender, String id, Network network, Location location) {
        if (language == null) {
            message(sender, "ERROR: Language module not found, please contact your system administrator");
        }

        if (language.getText(id) == null) {
            message(sender, "ERROR: No language key found for " + id);
        }

        else {
            message(sender, language.getText(id).replaceAll("%network", network.getID()).replaceAll("%position", location.toString()));
        }
    }


    public void returnMessage(CommandSender sender, String id, Network network, Location location, String[] items) {
        if (language == null) {
            message(sender, "ERROR: Language module not found, please contact your system administrator");
            return;
        }

        if (language.getText(id) == null) {
            message(sender, "ERROR: No language key found for " + id);
        }

        else {
            String list = "";
            for (String item : items) {
                list += "\n" + item.toUpperCase();
            }
            message(sender, language.getText(id).replaceAll("%network", network.getID()).replaceAll("%position", location.toString()).replaceAll("%items", list));
        }
    }

    public void returnMessage(CommandSender sender, String id, Material material) {
        if (language == null) {
            message(sender, "ERROR: Language module not found, please contact your system administrator");
        }

        if (language.getText(id) == null) {
            message(sender, "ERROR: No language key found for " + id);
        }

        else {
            message(sender, language.getText(id).replaceAll("%material", material.toString().toUpperCase()));
        }
    }

    public void returnMessage(CommandSender sender, String id, Location location,  Material material) {
        if (language == null) {
            message(sender, "ERROR: Language module not found, please contact your system administrator");
        }

        if (language.getText(id) == null) {
            message(sender, "ERROR: No language key found for " + id);
        }

        else {
            message(sender, language.getText(id).replaceAll("%material", material.toString().toUpperCase()).replaceAll("%position", location.toString()));
        }
    }


    public void returnMessage(CommandSender sender, String id, double value) {
        if (language == null) {
            message(sender, "ERROR: Language module not found, please contact your system administrator");
        }

        if (language.getText(id) == null) {
            message(sender, "ERROR: No language key found for " + id);
        }

        else {
            message(sender, language.getText(id).replaceAll("%number", String.valueOf(value)));
        }
    }


}
