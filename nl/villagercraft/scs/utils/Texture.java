package nl.villagercraft.scs.utils;

import com.deanveloper.skullcreator.SkullCreator;
import java.util.List;
import nl.villagercraft.scs.SecretPresents;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class Texture {

   public static void apply(Block b, Player p) {
      SkullCreator.blockWithBase64(b, getRandom(), p);
   }

   public static void apply(Block b, String s, Player p) {
      SkullCreator.blockWithBase64(b, s, p);
   }

   public static String getRandom() {
      List l = SecretPresents.p.getConfig().getStringList("Textures");
      return (String)l.get(SecretPresents.r(0, l.size() - 1));
   }
}
