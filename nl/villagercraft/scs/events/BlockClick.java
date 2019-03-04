package nl.villagercraft.scs.events;

import nl.villagercraft.scs.utils.PresentsData;
import nl.villagercraft.scs.utils.SCEvent;
import nl.villagercraft.scs.utils.SCInventory;
import nl.villagercraft.scs.utils.SCLang;
import nl.villagercraft.scs.utils.SCPlayerData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class BlockClick implements Listener {

   @EventHandler
   public void onClick(PlayerInteractEvent e) {
      try {
         if(e.getClickedBlock() == null) {
            return;
         }

         if(!PresentsData.existsHere(e.getClickedBlock().getLocation())) {
            return;
         }

         if(e.getHand() == EquipmentSlot.OFF_HAND) {
            return;
         }

         if(e.getPlayer().isSneaking() && e.getPlayer().hasPermission("secretpresents.access")) {
            SCInventory.openInventory(e.getPlayer(), e.getClickedBlock().getLocation());
            return;
         }

         if(!SCEvent.isStarted()) {
            e.getPlayer().sendMessage(SCLang.getString("EventNotStarted"));
            return;
         }

         SCPlayerData.add(e.getClickedBlock().getLocation(), e.getPlayer());
      } catch (Exception var3) {
         ;
      }

   }
}
