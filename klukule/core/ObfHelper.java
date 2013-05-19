package klukule.core;

import klukule.core.klukuleUtil;

public class ObfHelper {

   public static boolean obfuscation;
   private static final String obfVersion = "1.5.0";
   public static final String[] equippedProgress = new String[]{"d", "field_78454_c", "equippedProgress"};
   public static final String[] prevEquippedProgress = new String[]{"e", "field_78451_d", "prevEquippedProgress"};
   public static final String[] itemToRender = new String[]{"c", "field_78453_b", "itemToRender"};
   public static final String[] equippedItemSlot = new String[]{"g", "field_78450_g", "equippedItemSlot"};
   public static final String[] texture = new String[]{"aH", "field_70750_az", "texture"};
   public static final String[] lightningState = new String[]{"b", "field_70262_b", "lightningState"};
   public static final String[] explosionRadius = new String[]{"g", "field_82226_g", "explosionRadius"};
   public static final String[] cameraZoom = new String[]{"X", "field_78503_V", "cameraZoom"};
   public static final String[] downloadResourcesThread = new String[]{"X", "field_71430_V", "downloadResourcesThread"};
   public static final String[] closing = new String[]{"c", "field_74578_c", "closing"};
   public static final String[] itemHealth = new String[]{"d", "field_70291_e", "health"};
   public static final String[] arrowInGround = new String[]{"i", "field_70254_i", "inGround"};
   public static final String[] showNameTime = new String[]{"k", "field_92017_k"};
   public static final String jumpObf = "bl";
   public static final String jumpDeobf = "jump";
   public static final String getDeathSoundObf = "bd";
   public static final String getDeathSoundDeobf = "getDeathSound";


   public static void obfWarning() {
      klukuleUtil.console("Forgot to update obfuscation!", true);
   }

   public static void detectObfuscation() {
      try {
         Class.forName("net.minecraft.world.World");
         obfuscation = false;
      } catch (Exception var1) {
         obfuscation = true;
      }

   }

}
