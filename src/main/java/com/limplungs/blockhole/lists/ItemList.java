package com.limplungs.blockhole.lists;

import java.util.ArrayList;

import com.limplungs.blockhole.Blockhole;
import com.limplungs.blockhole.items.ItemTuner;

import com.limplungs.limpcore.items.ItemBasic;
import com.limplungs.limpcore.items.ItemData;

import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;

public class ItemList 
{
	public static ArrayList<Item> ITEMS = new ArrayList<Item>();
	
	private static ItemData DATA_ENDER_DIAMOND;
	public static ItemBasic ENDER_DIAMOND;
	
	private static ItemData DATA_TUNER;
	public static ItemTuner TUNER;
	
	
	/**
	 *  Items Registry
	 *  Registered during the 'preInit' stage.
	 */
	public static void registerItemData()
	{
		// Items
		DATA_ENDER_DIAMOND = new ItemData("enderdiamond", Blockhole.MODID, 64, Blockhole.tabBlockhole);
		
		// Tools
		DATA_TUNER         = new ItemData("tuner", Blockhole.MODID, 64, Blockhole.tabBlockhole);
	}
	
	
	
	/**
	 *  Items Registry
	 *  Registered during the 'preInit' stage.
	 */
	public static void registerItems()
	{
		// Items
		ITEMS.add(ENDER_DIAMOND = new ItemBasic(DATA_ENDER_DIAMOND));
		
		// Tools
		ITEMS.add(TUNER         = new ItemTuner(DATA_TUNER));
	}

	
	
	/**
	 *  Items Renderer
	 *  @param renderItem - RenderItem passed in to render items during 'init' stage.
	 */
	public static void renderItems(RenderItem renderItem) 
	{
		// Items
		renderItem.getItemModelMesher().register(ENDER_DIAMOND, 0, new ModelResourceLocation(Blockhole.MODID + ":" + ENDER_DIAMOND.getName(), "inventory"));
		
		// Tools
		renderItem.getItemModelMesher().register(TUNER, 0, new ModelResourceLocation(Blockhole.MODID + ":" + TUNER.getName(), "inventory"));	
	}
}
