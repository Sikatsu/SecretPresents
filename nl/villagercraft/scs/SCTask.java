package nl.villagercraft.scs;

import java.util.Iterator;
import nl.villagercraft.scs.SecretPresents;
import nl.villagercraft.scs.particlelib.ParticleEffect;
import nl.villagercraft.scs.particlelib.Particles13;
import nl.villagercraft.scs.utils.PresentsData;
import nl.villagercraft.scs.utils.SCEvent;
import nl.villagercraft.scs.utils.SCPlayerData;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class SCTask {

   public static void run() {
      BukkitRunnable run = new BukkitRunnable() {
         public void run() {
            if(SCEvent.isStarted()) {
               Iterator var2 = PresentsData.locs.iterator();

               while(var2.hasNext()) {
                  Location l = (Location)var2.next();
                  Iterator var4 = l.getWorld().getPlayers().iterator();

                  while(var4.hasNext()) {
                     Player p = (Player)var4.next();
                     if(p.getLocation().distance(l) < (double)SecretPresents.p.getConfig().getInt("DisplayParticleRange")) {
                        float rng = (float)SecretPresents.p.getConfig().getDouble("SpawnParticleRange");
                        if(!SCPlayerData.hasPresent(SCPlayerData.get(p), l)) {
                           Particles13.play(ParticleEffect.valueOf(SecretPresents.p.getConfig().getString("particleNotFound")), l.clone().add(0.5D, 0.5D, 0.5D), rng, rng, rng, 0.0F, 1, p);
                        } else {
                           Particles13.play(ParticleEffect.valueOf(SecretPresents.p.getConfig().getString("particleFound")), l.clone().add(0.5D, 0.5D, 0.5D), rng, rng, rng, 0.0F, 1, p);
                        }
                     }
                  }
               }

            }
         }
      };
      run.runTaskTimer(SecretPresents.p, 1L, 5L);
   }
}
