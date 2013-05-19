package klukule.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import klukule.client.gui.ISlider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GuiSlider extends GuiButton {

   public double sliderValue = 1.0D;
   public String dispString = "";
   public boolean dragging = false;
   public double minValue = 0.0D;
   public double maxValue = 5.0D;
   public ISlider parent = null;


   public GuiSlider(int id, int xPos, int yPos, String displayStr, double minVal, double maxVal, double currentVal, ISlider par) {
      super(id, xPos, yPos, 150, 20, displayStr);
      this.minValue = minVal;
      this.maxValue = maxVal;
      this.sliderValue = (currentVal - this.minValue) / (this.maxValue - this.minValue);
      this.dispString = displayStr;
      this.parent = par;
      String val = Double.toString(this.sliderValue * (this.maxValue - this.minValue) + this.minValue);
      if(val.length() > 4) {
         val = val.substring(0, 4);
      } else {
         while(val.length() < 4) {
            val = val + "0";
         }
      }

      this.displayString = this.dispString + val;
   }

   protected int getHoverState(boolean par1) {
      return 0;
   }

   protected void func_73739_b(Minecraft par1Minecraft, int par2, int par3) {
      if(this.drawButton) {
         if(this.dragging) {
            this.sliderValue = (double)((float)(par2 - (this.xPosition + 4)) / (float)(this.width - 8));
            if(this.sliderValue < 0.0D) {
               this.sliderValue = 0.0D;
            }

            if(this.sliderValue > 1.0D) {
               this.sliderValue = 1.0D;
            }

            String val = Double.toString(this.sliderValue * (this.maxValue - this.minValue) + this.minValue);
            if(val.length() > 4) {
               val = val.substring(0, 4);
            } else {
               while(val.length() < 4) {
                  val = val + "0";
               }
            }

            this.displayString = this.dispString + val;
            this.parent.onChangeSliderValue(this);
         }

         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         this.drawTexturedModalRect(this.xPosition + (int)(this.sliderValue * (double)((float)(this.width - 8))), this.yPosition, 0, 66, 4, 20);
         this.drawTexturedModalRect(this.xPosition + (int)(this.sliderValue * (double)((float)(this.width - 8))) + 4, this.yPosition, 196, 66, 4, 20);
      }

   }

   public boolean mousePressed(Minecraft par1Minecraft, int par2, int par3) {
      if(!super.mousePressed(par1Minecraft, par2, par3)) {
         return false;
      } else {
         this.sliderValue = (double)((float)(par2 - (this.xPosition + 4)) / (float)(this.width - 8));
         if(this.sliderValue < 0.0D) {
            this.sliderValue = 0.0D;
         }

         if(this.sliderValue > 1.0D) {
            this.sliderValue = 1.0D;
         }

         String val = Double.toString(this.sliderValue * (this.maxValue - this.minValue) + this.minValue);
         if(val.length() > 4) {
            val = val.substring(0, 4);
         } else {
            while(val.length() < 4) {
               val = val + "0";
            }
         }

         this.displayString = this.dispString + val;
         this.parent.onChangeSliderValue(this);
         this.dragging = true;
         return true;
      }
   }

   public void func_73740_a(int par1, int par2) {
      this.dragging = false;
   }
}
