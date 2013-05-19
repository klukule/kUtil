package klukule.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import klukule.client.gui.GuiSlider;

@SideOnly(Side.CLIENT)
public interface ISlider {

   void onChangeSliderValue(GuiSlider var1);
}
