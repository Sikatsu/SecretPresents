package nl.villagercraft.scs.particlelib;

import net.minecraft.server.v1_13_R1.ParticleType;
import nl.villagercraft.scs.particlelib.ParticleEffect;

public enum NewParticles {

   HEART("HEART", 0, ParticleEffect.HEART, "A"),
   FIREWORKS_SPARK("FIREWORKS_SPARK", 1, ParticleEffect.FIREWORKS_SPARK, "w"),
   WATER_SPLASH("WATER_SPLASH", 2, ParticleEffect.WATER_SPLASH, "R"),
   SUSPENDED("SUSPENDED", 3, ParticleEffect.SUSPENDED, "H"),
   CRIT("CRIT", 4, ParticleEffect.CRIT, "h"),
   CRIT_MAGIC("CRIT_MAGIC", 5, ParticleEffect.CRIT_MAGIC, "p"),
   SMOKE_NORMAL("SMOKE_NORMAL", 6, ParticleEffect.SMOKE_NORMAL, "M"),
   SMOKE_LARGE("SMOKE_LARGE", 7, ParticleEffect.SMOKE_LARGE, "F"),
   SPELL("SPELL", 8, ParticleEffect.SPELL, "n"),
   SPELL_INSTANT("SPELL_INSTANT", 9, ParticleEffect.SPELL_INSTANT, "B"),
   SPELL_MOB("SPELL_MOB", 10, ParticleEffect.SPELL_MOB, "s"),
   SPELL_MOB_AMBIENT("SPELL_MOB_AMBIENT", 11, ParticleEffect.SPELL_MOB_AMBIENT, "a"),
   SPELL_WITCH("SPELL_WITCH", 12, ParticleEffect.SPELL_WITCH, "S"),
   DRIP_WATER("DRIP_WATER", 13, ParticleEffect.DRIP_WATER, "l"),
   DRIP_LAVA("DRIP_LAVA", 14, ParticleEffect.DRIP_LAVA, "k"),
   VILLAGER_ANGRY("VILLAGER_ANGRY", 15, ParticleEffect.VILLAGER_ANGRY, "b"),
   VILLAGER_HAPPY("VILLAGER_HAPPY", 16, ParticleEffect.VILLAGER_HAPPY, "z"),
   TOWN_AURA("TOWN_AURA", 17, ParticleEffect.TOWN_AURA, "H"),
   NOTE("NOTE", 18, ParticleEffect.NOTE, "I"),
   PORTAL("PORTAL", 19, ParticleEffect.PORTAL, "K"),
   ENCHANTMENT_TABLE("ENCHANTMENT_TABLE", 20, ParticleEffect.ENCHANTMENT_TABLE, "q"),
   FLAME("FLAME", 21, ParticleEffect.FLAME, "y"),
   LAVA("LAVA", 22, ParticleEffect.LAVA, "G"),
   CLOUD("CLOUD", 23, ParticleEffect.CLOUD, "g"),
   SNOWBALL("SNOWBALL", 24, ParticleEffect.SNOWBALL, "E"),
   SLIME("SLIME", 25, ParticleEffect.SLIME, "D"),
   SNOW_SHOVEL("SNOW_SHOVEL", 26, ParticleEffect.SNOW_SHOVEL, "D"),
   BARRIER("BARRIER", 27, ParticleEffect.BARRIER, "c"),
   DRAGON_BREATH("DRAGON_BREATH", 28, ParticleEffect.DRAGON_BREATH, "j"),
   END_ROD("END_ROD", 29, ParticleEffect.END_ROD, "r"),
   DAMAGE("DAMAGE", 30, ParticleEffect.DAMAGE, "i"),
   SPIT("SPIT", 31, ParticleEffect.Spit, "N"),
   TOTEM("TOTEM", 32, ParticleEffect.Totem, "P");
   public ParticleEffect ef;
   public ParticleType p;
   public String name;
   // $FF: synthetic field
   private static final NewParticles[] ENUM$VALUES = new NewParticles[]{HEART, FIREWORKS_SPARK, WATER_SPLASH, SUSPENDED, CRIT, CRIT_MAGIC, SMOKE_NORMAL, SMOKE_LARGE, SPELL, SPELL_INSTANT, SPELL_MOB, SPELL_MOB_AMBIENT, SPELL_WITCH, DRIP_WATER, DRIP_LAVA, VILLAGER_ANGRY, VILLAGER_HAPPY, TOWN_AURA, NOTE, PORTAL, ENCHANTMENT_TABLE, FLAME, LAVA, CLOUD, SNOWBALL, SLIME, SNOW_SHOVEL, BARRIER, DRAGON_BREATH, END_ROD, DAMAGE, SPIT, TOTEM};


   private NewParticles(String var1, int var2, ParticleEffect ef, String s) {
      this.ef = ef;
      this.name = s;
   }

   public static String convertToID(ParticleEffect e) {
      String name = e.name();

      try {
         return valueOf(name.toUpperCase()).name;
      } catch (Exception var3) {
         return null;
      }
   }
}
