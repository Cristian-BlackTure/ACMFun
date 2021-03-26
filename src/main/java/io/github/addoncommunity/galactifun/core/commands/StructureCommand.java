package io.github.addoncommunity.galactifun.core.commands;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.core.structures.BlockVector3;
import io.github.addoncommunity.galactifun.core.structures.GalacticStructure;
import io.github.addoncommunity.galactifun.util.PersistentBlock;
import io.github.mooy1.infinitylib.commands.AbstractCommand;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

public final class StructureCommand extends AbstractCommand {

    private static final File FOLDER = new File(Galactifun.inst().getDataFolder(), "structures");

    private static final NamespacedKey POS1 = Galactifun.inst().getKey("pos1");
    private static final NamespacedKey POS2 = Galactifun.inst().getKey("pos2");

    public StructureCommand() {
        super("structure", "The command for structures", true);
    }

    @Override
    public void onExecute(@Nonnull CommandSender sender, @Nonnull String[] args) {
        if (args.length >= 2 && sender instanceof Player) {
            Player p = (Player) sender;
            PersistentDataContainer container = p.getPersistentDataContainer();
            Location l = p.getLocation();

            switch (args[1]) {
                case "pos1":
                    container.set(POS1, PersistentBlock.BLOCK, p.getLocation().getBlock());
                    p.sendMessage("Set pos1 to " + toString(l));
                    break;
                case "pos2":
                    container.set(POS2, PersistentBlock.BLOCK, p.getLocation().getBlock());
                    p.sendMessage("Set pos2 to " + toString(l));
                    break;
                case "save":
                    Block pos1 = container.get(POS1, PersistentBlock.BLOCK);
                    if (pos1 == null) {
                        p.sendMessage("pos1 not set!");
                        break;
                    }
                    Block pos2 = container.get(POS2, PersistentBlock.BLOCK);
                    if (pos2 == null) {
                        p.sendMessage("pos2 not set!");
                        break;
                    }

                    GalacticStructure format = new GalacticStructure(
                        p.getWorld(),
                        BlockVector3.fromLocation(pos1.getLocation()),
                        BlockVector3.fromLocation(pos2.getLocation())
                    );

                    format.save(new File(FOLDER, args[2] + ".gsf"));
                    p.sendMessage("Saved " + args[2]);
                    break;
                case "load":
                    GalacticStructure loaded;
                    try {
                        loaded = GalacticStructure.load(new File(FOLDER, args[2] + ".gsf"));
                    } catch (FileNotFoundException e) {
                        p.sendMessage(ChatColor.RED + "Unknown structure!");
                        break;
                    }

                    loaded.paste(l);
                    break;
            }
        }
    }
    
    private static String toString(Location l) {
        return l.getBlockX() + 'x' + l.getBlockY() + 'y' + l.getBlockZ() + "z in " + l.getWorld();
    }

    @Override
    public void onTab(@Nonnull CommandSender commandSender, @Nonnull String[] args, @Nonnull List<String> options) {
        if (args.length == 2) {
            options.addAll(Arrays.asList("pos1", "pos2", "save", "load"));
        }
    }
    
    
}
