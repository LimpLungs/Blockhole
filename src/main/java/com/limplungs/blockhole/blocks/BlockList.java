package com.limplungs.blockhole.blocks;

import com.limplungs.blockhole.Blockhole;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;

public class BlockList 
{
	/**
	 *  BlockData
	 */
	private static BlockData DATA_TELEPORTER = new BlockData(5F, 5F, "pickaxe", 2, "teleporter", Material.ROCK, Blockhole.tabBlockhole);
	
	/**
	 *  Blocks
	 */
	public static BlockTeleporter TELEPORTER;
	
	
	/**
	 *  Block Registry
	 *  Registered during the 'preInit' stage.
	 */
	public static void registerBlocks()
	{
		TELEPORTER = new BlockTeleporter(DATA_TELEPORTER);
	}

	
	/**
	 *  Block Renderer
	 *  @param renderItem - RenderItem passed in to render items during 'init' stage.
	 */
	public static void renderBlocks(RenderItem renderItem) 
	{
		renderItem.getItemModelMesher().register(Item.getItemFromBlock(TELEPORTER), 0, new ModelResourceLocation(Blockhole.MODID + ":" + TELEPORTER.getName(), "inventory"));	
	}
}
