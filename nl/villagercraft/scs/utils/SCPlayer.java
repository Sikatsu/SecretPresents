package nl.villagercraft.scs.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import nl.villagercraft.scs.utils.PresentsData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class SCPlayer {

   public UUID uuid;
   public List presents = new ArrayList();
   public long start = 0L;
   public long finish = 0L;


   public SCPlayer(Player p) {
      this.uuid = p.getUniqueId();
   }

   public SCPlayer(UUID uuid) {
      this.uuid = uuid;
   }

   public void statred() {
      this.start = System.currentTimeMillis();
   }

   public void finished() {
      this.finish = System.currentTimeMillis();
   }

   public Player toPlayer() {
      return Bukkit.getPlayer(this.uuid);
   }

   public void clearRemoved() {
      Iterator var2 = this.presents.iterator();

      while(var2.hasNext()) {
         String str = (String)var2.next();
         if(PresentsData.fromID(str) == null) {
            this.presents.remove(str);
         }
      }

   }
}
