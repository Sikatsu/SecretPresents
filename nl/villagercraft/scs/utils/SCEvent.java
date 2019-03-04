package nl.villagercraft.scs.utils;

import nl.villagercraft.scs.utils.PresentsData;

public class SCEvent {

   public static boolean started = false;


   public static boolean isStarted() {
      return started;
   }

   public static void load() {
      started = PresentsData.data.getBoolean("Started");
   }

   public static void update(boolean started) {
      started = started;
      PresentsData.data.set("Started", Boolean.valueOf(started));
      PresentsData.save();
   }
}
