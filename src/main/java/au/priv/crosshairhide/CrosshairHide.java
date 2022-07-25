package au.priv.crosshairhide;

import net.minecraft.client.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.common.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;

@Mod(modid = "crosshairhide", version = "1.0.0", useMetadata = true)

public class CrosshairHide {
  @Mod.EventHandler
  public void init(FMLInitializationEvent e) {
    FMLCommonHandler.instance().bus().register(this);
    MinecraftForge.EVENT_BUS.register(this);
  }
  
  @SubscribeEvent
  public void onRenderTick(final TickEvent.RenderTickEvent e) {
    if (Minecraft.getMinecraft().theWorld != null && Minecraft.getMinecraft().currentScreen == null) {
      drawCrosshair();
    }
  }
  
  public void drawCrosshair() {
    final int[] screenSize = getScreenSize();
    final int screenWidth = screenSize[0] / 2;
    final int screenHeight = screenSize[1] / 2;
    if (Minecraft.getMinecraft().gameSettings.thirdPersonView != 0 && !Minecraft.getMinecraft().gameSettings.showDebugInfo) {
      drawDefaultCrosshair(screenWidth, screenHeight);
    }
  }

  public static int[] getScreenSize() {
    final int[] size = new int[2];
    final ScaledResolution resolution = new ScaledResolution(Minecraft.getMinecraft());
    size[0] = resolution.getScaledWidth();
    size[1] = resolution.getScaledHeight();
    return size;
  }

  private void drawDefaultCrosshair(final int screenWidth, final int screenHeight) {
    GlStateManager.tryBlendFuncSeparate(775, 769, 1, 0);
    GlStateManager.enableAlpha();
    GlStateManager.enableBlend();
    Minecraft.getMinecraft().getTextureManager().bindTexture(Gui.icons);
    drawTexturedRectangle(screenWidth - 7, screenHeight - 7, 0, 0, 16, 16);
    GlStateManager.disableAlpha();
    GlStateManager.disableBlend();
  }
  
  public static void drawTexturedRectangle(final int x, final int y, final int textureX, final int textureY, final int width, final int height) {
    final float f = 0.00390625f;
    final float f2 = 0.00390625f;
    Tessellator.getInstance().getWorldRenderer().begin(7, DefaultVertexFormats.POSITION_TEX);
    Tessellator.getInstance().getWorldRenderer().pos((double)(x + 0), (double)(y + height), 0.0).tex((double)((textureX + 0) * f), (double)((textureY + height) * f2)).endVertex();
    Tessellator.getInstance().getWorldRenderer().pos((double)(x + width), (double)(y + height), 0.0).tex((double)((textureX + width) * f), (double)((textureY + height) * f2)).endVertex();
    Tessellator.getInstance().getWorldRenderer().pos((double)(x + width), (double)(y + 0), 0.0).tex((double)((textureX + width) * f), (double)((textureY + 0) * f2)).endVertex();
    Tessellator.getInstance().getWorldRenderer().pos((double)(x + 0), (double)(y + 0), 0.0).tex((double)((textureX + 0) * f), (double)((textureY + 0) * f2)).endVertex();
    Tessellator.getInstance().draw();
  }
}

