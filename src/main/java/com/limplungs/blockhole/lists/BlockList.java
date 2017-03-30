package com.limplungs.blockhole.lists;

import com.limplungs.blockhole.Blockhole;
import com.limplungs.blockhole.blocks.BlockSingularityPortal;
import com.limplungs.blockhole.blocks.BlockSingularityDimensionWall;
import com.limplungs.blockhole.blocks.BlockData;
import com.limplungs.blockhole.blocks.BlockPowderKeg;
import com.limplungs.blockhole.blocks.BlockSingularityStorage;
import com.limplungs.blockhole.blocks.ItemBlockBasic;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;

public class BlockList 
{
	/**
	 *  BlockData
	 */
	private static BlockData DATA_STORAGE = new BlockData(5F, 5F, "pickaxe", 2, "storage", Material.ROCK, Blockhole.tabBlockhole);
	private static BlockData DATA_POWDERKEG = new BlockData(1F, 1F, "pickaxe", 0, "powderkeg", Material.CLOTH, Blockhole.tabBlockhole);
	private static BlockData DATA_BLOCKHOLE = new BlockData(8F, 8F, "pickaxe", 0, "portal", Material.GLASS, Blockhole.tabBlockhole);
	private static BlockData DATA_BLOCKHOLEWALL = new BlockData(100F, 100F, "pickaxe", 100, "dimensionwall", Material.GLASS, Blockhole.tabBlockhole);
	
	
	/**
	 *  Blocks
	 */
	public static BlockSingularityStorage STORAGE;
	public static BlockPowderKeg POWDERKEG;
	public static BlockSingularityPortal PORTAL;
	public static BlockSingularityDimensionWall DIMENSION_WALL;
	
	
	/**
	 *  Block Registry
	 *  Registered during the 'preInit' stage.
	 */
	public static void registerBlocks()
	{
		POWDERKEG = new BlockPowderKeg(DATA_POWDERKEG);
		STORAGE = new BlockSingularityStorage(DATA_STORAGE);
		PORTAL = new BlockSingularityPortal(DATA_BLOCKHOLE);
		DIMENSION_WALL = new BlockSingularityDimensionWall(DATA_BLOCKHOLEWALL);
	}
	
	public static ItemBlockBasic ITEMBLOCK_STORAGE;
	public static ItemBlockBasic ITEMBLOCK_POWDERKEG;
	public static ItemBlockBasic ITEMBLOCK_PORTAL;
	public static ItemBlockBasic ITEMBLOCK_DIMENSION_WALL;
	
	public static void registerItemBlocks()
	{
		ITEMBLOCK_STORAGE = new ItemBlockBasic(STORAGE);
		ITEMBLOCK_POWDERKEG = new ItemBlockBasic(POWDERKEG);
		ITEMBLOCK_PORTAL = new ItemBlockBasic(PORTAL);
		ITEMBLOCK_DIMENSION_WALL = new ItemBlockBasic(DIMENSION_WALL);
	}

	
	/**
	 *  Block Renderer
	 *  @param renderItem - RenderItem passed in to render items during 'init' stage.
	 */
	public static void renderBlocks(RenderItem renderItem) 
	{
		renderItem.getItemModelMesher().register(Item.getItemFromBlock(STORAGE), 0, new ModelResourceLocation(Blockhole.MODID + ":" + STORAGE.getName(), "inventory"));
		renderItem.getItemModelMesher().register(Item.getItemFromBlock(POWDERKEG), 0, new ModelResourceLocation(Blockhole.MODID + ":" + POWDERKEG.getName(), "inventory"));
		renderItem.getItemModelMesher().register(Item.getItemFromBlock(PORTAL), 0, new ModelResourceLocation(Blockhole.MODID + ":" + PORTAL.getName(), "inventory"));
		renderItem.getItemModelMesher().register(Item.getItemFromBlock(DIMENSION_WALL), 0, new ModelResourceLocation(Blockhole.MODID + ":" + DIMENSION_WALL.getName(), "inventory"));
	}
}
