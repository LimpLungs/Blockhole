package com.limplungs.blockhole;

import org.lwjgl.opengl.GL11;

import com.limplungs.blockhole.items.ItemTuner;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BlockholeBasicEvents 
{
	private double myRender = 0.8;

	//@SubscribeEvent
	public void loadWorld(WorldEvent.Load event)
	{
		for (int i = 0; i < DimensionManager.getWorlds().length; i++)
		{
			System.out.println(DimensionManager.getWorlds()[i].provider.getDimension());
		}
	}
	
	@SubscribeEvent
	public void showTunerMode(RenderGameOverlayEvent event)
	{
		// Set scale of my render
		GL11.glScaled(myRender, myRender, myRender);
		
		if (Minecraft.getMinecraft().player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemTuner)
		{
			ItemStack tuner = Minecraft.getMinecraft().player.getHeldItem(EnumHand.MAIN_HAND);
			
			if (tuner.hasTagCompound())
			{
				if (tuner.getTagCompound().getInteger("mode") == 0)
				{
					Minecraft.getMinecraft().fontRenderer.drawString("X-Coord", 2, 2, 1666666);
					
					if (Minecraft.getMinecraft().player.isSneaking())
					{
						Minecraft.getMinecraft().fontRenderer.drawString("Right Click + 1", 2, 12, 1666666);
						Minecraft.getMinecraft().fontRenderer.drawString("Shift Right Click - 1", 2, 22, 1666666);
					}
				}
				else if (tuner.getTagCompound().getInteger("mode") == 1)
				{
					Minecraft.getMinecraft().fontRenderer.drawString("Y-Coord", 2, 2, 1666666);
					
					if (Minecraft.getMinecraft().player.isSneaking())
					{
						Minecraft.getMinecraft().fontRenderer.drawString("Right Click + 1", 2, 12, 1666666);
						Minecraft.getMinecraft().fontRenderer.drawString("Shift Right Click - 1", 2, 22, 1666666);
					}
				}
				else if (tuner.getTagCompound().getInteger("mode") == 2)
				{
					Minecraft.getMinecraft().fontRenderer.drawString("Z-Coord", 2, 2, 1666666);
					
					if (Minecraft.getMinecraft().player.isSneaking())
					{
						Minecraft.getMinecraft().fontRenderer.drawString("Right Click + 1", 2, 12, 1666666);
						Minecraft.getMinecraft().fontRenderer.drawString("Shift Right Click - 1", 2, 22, 1666666);
					}
				}
				else if (tuner.getTagCompound().getInteger("mode") == 3)
				{
					Minecraft.getMinecraft().fontRenderer.drawString("Manipulate", 2, 2, 1666666);
					
					if (Minecraft.getMinecraft().player.isSneaking())
					{
						Minecraft.getMinecraft().fontRenderer.drawString("Portal: ", 2, 12, 1666666);
						Minecraft.getMinecraft().fontRenderer.drawString("Right Click to next Singularity", 12, 22, 1666666);
						Minecraft.getMinecraft().fontRenderer.drawString("Shift Right to prev Singularity", 12, 32, 1666666);
						
						Minecraft.getMinecraft().fontRenderer.drawString("Dimension Wall: ", 2, 42, 1666666);
						Minecraft.getMinecraft().fontRenderer.drawString("Right Click flips transfer", 12, 52, 1666666);
						Minecraft.getMinecraft().fontRenderer.drawString("Shift Right Click displays transfer.", 12, 62, 1666666);
					}
				}
			}
		}

		// Reset vanilla renderer
		Minecraft.getMinecraft().getTextureManager().bindTexture(Gui.ICONS);
		
		GL11.glColor4f(1,1,1,1);
		
		GL11.glScaled(1 / myRender, 1 / myRender, 1 / myRender);
	}
}
