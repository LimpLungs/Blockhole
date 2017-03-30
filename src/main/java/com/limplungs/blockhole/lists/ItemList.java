package com.limplungs.blockhole.lists;

import com.limplungs.blockhole.Blockhole;
import com.limplungs.blockhole.items.ItemBasic;
import com.limplungs.blockhole.items.ItemData;
import com.limplungs.blockhole.items.ItemTuner;

import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;

public class ItemList 
{
	/**
	 *  Item Data
	 */
	private static ItemData DATA_ENDER_DIAMOND = new ItemData(64, Blockhole.tabBlockhole, "enderdiamond");
	private static ItemData DATA_TUNER = new ItemData(64, Blockhole.tabBlockhole, "tuner");
	
	
	/**
	 *  Items
	 */
	public static ItemBasic ENDER_DIAMOND;
	public static ItemTuner TUNER;
	
	
	/**
	 *  Items Registry
	 *  Registered during the 'preInit' stage.
	 */
	public static void registerItems()
	{
		ENDER_DIAMOND = new ItemBasic(DATA_ENDER_DIAMOND);
		TUNER = new ItemTuner(DATA_TUNER);
	}

	
	/**
	 *  Items Renderer
	 *  @param renderItem - RenderItem passed in to render items during 'init' stage.
	 */
	public static void renderItems(RenderItem renderItem) 
	{
		renderItem.getItemModelMesher().register(ENDER_DIAMOND, 0, new ModelResourceLocation(Blockhole.MODID + ":" + ENDER_DIAMOND.getName(), "inventory"));
		renderItem.getItemModelMesher().register(TUNER, 0, new ModelResourceLocation(Blockhole.MODID + ":" + TUNER.getName(), "inventory"));	
	}
}
