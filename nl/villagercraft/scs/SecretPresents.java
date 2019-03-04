package nl.villagercraft.scs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import nl.villagercraft.scs.SCTask;
import nl.villagercraft.scs.events.BlockClick;
import nl.villagercraft.scs.events.Break;
import nl.villagercraft.scs.events.SignEvent;
import nl.villagercraft.scs.particlelib.Particles13;
import nl.villagercraft.scs.utils.Leaderboard;
import nl.villagercraft.scs.utils.PresentsData;
import nl.villagercraft.scs.utils.SCEvent;
import nl.villagercraft.scs.utils.SCInventory;
import nl.villagercraft.scs.utils.SCLang;
import nl.villagercraft.scs.utils.SCPlayerData;
import nl.villagercraft.scs.utils.SkullTexture;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class SecretPresents extends JavaPlugin {

   public static SecretPresents p;
   public static SkullTexture sk;


   public void onEnable() {
      p = this;
      sk = new SkullTexture();
      sk.load();
      Particles13.initialize();
      this.saveDefaultConfig();
      SCTask.run();
      SCLang.load();
      PresentsData.load();
      this.registerEvents();
      SCEvent.load();
      Leaderboard.load();
   }

   public void registerEvents() {
      this.getServer().getPluginManager().registerEvents(new SignEvent(), this);
      this.getServer().getPluginManager().registerEvents(new BlockClick(), this);
      this.getServer().getPluginManager().registerEvents(new Break(), this);
      this.getServer().getPluginManager().registerEvents(new SCInventory(), this);
   }

   public void onDisable() {}

   public static int r(int min, int max) {
      Random rand = new Random();
      int randomNum = rand.nextInt(max - min + 1) + min;
      return randomNum;
   }

   public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
      Player p = null;
      boolean admin = false;
      if(sender instanceof Player) {
         p = (Player)sender;
      }

      if(p == null || p.hasPermission("secretpresents.access")) {
         admin = true;
      }

      if(cmd.getName().equalsIgnoreCase("sps")) {
         String pr;
         Iterator b;
         if(args.length == 0) {
            b = (admin?SCLang.getStringList("AdminHelp"):SCLang.getStringList("PlayerHelp")).iterator();

            while(b.hasNext()) {
               pr = (String)b.next();
               sender.sendMessage(pr);
            }
         }

         Location var15;
         if(args.length == 1) {
            if(args[0].equalsIgnoreCase("reload") && admin) {
               this.reloadConfig();
               SCLang.load();
               SCEvent.load();
               PresentsData.load();
               Leaderboard.load();
               SCInventory.clicked.clear();
               sender.sendMessage(SCLang.getString("Reload"));
            } else if(args[0].equalsIgnoreCase("stats")) {
               p.sendMessage(SCLang.getString("HowMuchIhave").replaceAll("!current!", String.valueOf(SCPlayerData.got(p))).replaceAll("!max!", String.valueOf(PresentsData.locs.size())));
            } else if(args[0].equalsIgnoreCase("list") && admin) {
               sendAllPresents(p);
            } else if(args[0].equalsIgnoreCase("start") && admin) {
               SCEvent.update(true);
               sender.sendMessage(SCLang.getString("Started"));
            } else if(args[0].equalsIgnoreCase("stop") && admin) {
               SCEvent.update(false);
               sender.sendMessage(SCLang.getString("Stop"));
            } else if(args[0].equalsIgnoreCase("leaderboard") && p != null) {
               Leaderboard.getBest(p, 0);
            } else if(args[0].equalsIgnoreCase("resetevent") && p != null && admin) {
               b = PresentsData.locs.iterator();

               while(b.hasNext()) {
                  var15 = (Location)b.next();
                  var15.getBlock().setType(Material.AIR);
               }

               this.reloadConfig();
               PresentsData.data.set("Presents", (Object)null);
               PresentsData.save();
               Leaderboard.leaderboard = null;
               Leaderboard.save();
               b = SCPlayerData.data.getKeys(false).iterator();

               while(b.hasNext()) {
                  pr = (String)b.next();
                  SCPlayerData.data.set(pr, (Object)null);
               }

               SCPlayerData.players.clear();
               SCPlayerData.configsave();
               PresentsData.load();
               SCLang.load();
               SCEvent.load();
               Leaderboard.load();
               sender.sendMessage(SCLang.getString("RemovedData"));
            } else if(args[0].equalsIgnoreCase("info") && admin) {
               b = SCLang.getStringList("Info").iterator();

               while(b.hasNext()) {
                  pr = (String)b.next();
                  p.sendMessage(pr.replaceAll("!max!", String.valueOf(PresentsData.locs.size())).replaceAll("!s!", SCEvent.isStarted()?SCLang.getString("Started"):SCLang.getString("Stop")));
               }

               b = p.getConfig().getStringList("Commands").iterator();

               while(b.hasNext()) {
                  pr = (String)b.next();
                  p.sendMessage("§7" + pr);
               }
            }
         }

         if(args.length == 2) {
            if(args[0].equalsIgnoreCase("leaderboard") && p != null) {
               int var16 = 0;

               try {
                  var16 = Integer.parseInt(args[1]);
               } catch (Exception var14) {
                  ;
               }

               if(var16 <= 0 && var16 >= 100) {
                  boolean var18 = false;
               }

               Leaderboard.getBest(p, Integer.parseInt(args[1]));
            }

            if(args[0].equalsIgnoreCase("reset") && admin) {
               Player var19 = Bukkit.getPlayer(args[1]);
               if(var19 == null) {
                  sender.sendMessage(SCLang.getString("Offline"));
                  return false;
               }

               SCPlayerData.reset(var19);
               sender.sendMessage(SCLang.getString("Done"));
            }

            if(args[0].equalsIgnoreCase("teleport") && p != null && admin) {
               var15 = PresentsData.IdToLocation(args[1]);
               var15.add(0.0D, 0.8D, 0.0D);
               p.teleport(var15);
            }
         }

         if(args.length > 1 && args[0].equalsIgnoreCase("setcmd") && p != null) {
            if(!SCInventory.clicked.containsKey(p)) {
               p.sendMessage(SCLang.getString("NoSelectedCmdPresent"));
               return false;
            }

            pr = PresentsData.getPresent((Location)SCInventory.clicked.get(p));
            args[0].replaceAll("reset", "rdm");
            StringBuilder var17 = new StringBuilder();
            int i = 0;
            String[] var13 = args;
            int l = args.length;

            String command;
            for(int q = 0; q < l; ++q) {
               command = var13[q];
               if(i == 0) {
                  ++i;
               } else if(i == 1) {
                  ++i;
                  var17.append(command);
               } else {
                  var17.append(" " + command);
               }
            }

            command = var17.toString();
            String[] var20 = pr.split(",,");
            List var21 = PresentsData.data.getStringList("Presents");
            var21.remove(pr);
            var21.add(var20[0] + ",," + var20[1] + ",," + var20[2] + ",," + var20[3] + ",," + var20[4] + ",," + command.replaceAll(",,", "") + ",," + var20[6]);
            PresentsData.data.set("Presents", var21);
            p.sendMessage(SCLang.getString("Updated"));
         }
      }

      return false;
   }

   public static void sendAllPresents(Player p) {
      Object list = PresentsData.data.getStringList("Presents") == null?new ArrayList():PresentsData.data.getStringList("Presents");
      p.sendMessage(SCLang.getString("ListOfPresents"));
      int i = 0;
      Iterator var4 = ((List)list).iterator();

      while(var4.hasNext()) {
         String str = (String)var4.next();
         ++i;
         String[] s = str.split(",,");
         sendRaw(p, "[\"\",{\"text\":\"§7" + i + " | " + SCLang.getString("Present") + " \"},{\"text\":\"§e" + SCLang.getString("Teleport") + "\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/sps teleport " + s[0] + "\"}}]");
      }

   }

   public static void sendRaw(Player p, String s) {
      String player = p.getName();
      Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "tellraw " + player + " " + s);
   }
}
