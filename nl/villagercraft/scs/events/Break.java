package nl.villagercraft.scs.events;

import nl.villagercraft.scs.utils.PresentsData;
import nl.villagercraft.scs.utils.SCLang;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class Break implements Listener {

   @EventHandler
   public void onBrk(BlockBreakEvent e) {
      if(PresentsData.existsHere(e.getBlock().getLocation())) {
         e.getPlayer().sendMessage(SCLang.getString("NoBreak"));
         e.setCancelled(true);
      }
   }
}
