package klukule.core;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import klukule.core.LoggerHelper;
import klukule.core.ObfHelper;
import java.util.logging.Level;

@Mod(
   modid = "klukuleUtil",
   name = "klukuleUtil",
   version = "1.0.1",
   dependencies = "required-after:Forge@[7.7.0.582,)"
)
public class klukuleUtil {

   public static final String version = "1.0.1";
   @Instance("klukuleUtil")
   public static klukuleUtil instance;


   @PreInit
   public void preLoad(FMLPreInitializationEvent event) {
      LoggerHelper.init();
      ObfHelper.detectObfuscation();
   }

   public static void console(String s, boolean warning) {
      StringBuilder sb = new StringBuilder();
      LoggerHelper.log(warning?Level.WARNING:Level.INFO, sb.append("[").append("1.0.1").append("] ").append(s).toString());
   }

   public static void console(String s) {
      console(s, false);
   }

   public static void console(int i) {
      console((new Integer(i)).toString());
   }

   public static void console(boolean b) {
      console((new Boolean(b)).toString());
   }

   public static void console(float f) {
      console((new Float(f)).toString());
   }

   public static void console(double d) {
      console((new Double(d)).toString());
   }
}
