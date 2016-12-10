package com.limplungs.blockhole.items;

import com.limplungs.blockhole.Blockhole;

import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;

public class ItemList 
{
	/**
	 *  Item Data
	 */
	private static ItemData DATA_TUNER = new ItemData(64, Blockhole.tabBlockhole, "tuner");
	
	
	/**
	 *  Items
	 */
	public static ItemBasic TUNER;
	
	
	/**
	 *  Items Registry
	 *  Registered during the 'preInit' stage.
	 */
	public static void registerItems()
	{
		TUNER = new ItemTuner(DATA_TUNER);
	}

	
	/**
	 *  Items Renderer
	 *  @param renderItem - RenderItem passed in to render items during 'init' stage.
	 */
	public static void renderItems(RenderItem renderItem) 
	{
		renderItem.getItemModelMesher().register(TUNER, 0, new ModelResourceLocation(Blockhole.MODID + ":" + TUNER.getName(), "inventory"));	
	}
}
