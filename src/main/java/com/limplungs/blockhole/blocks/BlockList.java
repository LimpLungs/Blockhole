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
	private static BlockData DATA_POWDERKEG = new BlockData(1F, 1F, "pickaxe", 0, "powderkeg", Material.CLOTH, Blockhole.tabBlockhole);
	private static BlockData DATA_BLOCKHOLE = new BlockData(8F, 8F, "pickaxe", 0, "blockhole", Material.GLASS, Blockhole.tabBlockhole);
	private static BlockData DATA_BLOCKHOLEWALL = new BlockData(100F, 100F, "pickaxe", 100, "blockholewall", Material.GLASS, Blockhole.tabBlockhole);
	
	
	/**
	 *  Blocks
	 */
	public static BlockTeleporter TELEPORTER;
	public static BlockPowderKeg POWDERKEG;
	public static BlockBlockhole BLOCKHOLE;
	public static BlockBlockholeWall BLOCKHOLE_WALL;
	
	
	/**
	 *  Block Registry
	 *  Registered during the 'preInit' stage.
	 */
	public static void registerBlocks()
	{
		TELEPORTER = new BlockTeleporter(DATA_TELEPORTER);
		POWDERKEG = new BlockPowderKeg(DATA_POWDERKEG);
		BLOCKHOLE = new BlockBlockhole(DATA_BLOCKHOLE);
		BLOCKHOLE_WALL = new BlockBlockholeWall(DATA_BLOCKHOLEWALL);
	}

	
	/**
	 *  Block Renderer
	 *  @param renderItem - RenderItem passed in to render items during 'init' stage.
	 */
	public static void renderBlocks(RenderItem renderItem) 
	{
		renderItem.getItemModelMesher().register(Item.getItemFromBlock(TELEPORTER), 0, new ModelResourceLocation(Blockhole.MODID + ":" + TELEPORTER.getName(), "inventory"));
		renderItem.getItemModelMesher().register(Item.getItemFromBlock(POWDERKEG), 0, new ModelResourceLocation(Blockhole.MODID + ":" + POWDERKEG.getName(), "inventory"));
		renderItem.getItemModelMesher().register(Item.getItemFromBlock(BLOCKHOLE), 0, new ModelResourceLocation(Blockhole.MODID + ":" + BLOCKHOLE.getName(), "inventory"));
	}
}
