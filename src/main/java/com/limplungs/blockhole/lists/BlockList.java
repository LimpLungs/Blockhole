package com.limplungs.blockhole.lists;

import com.limplungs.blockhole.Blockhole;
import com.limplungs.blockhole.blocks.BlockBasic;
import com.limplungs.blockhole.blocks.BlockSingularity;
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
	private static BlockData DATA_ENDER_DIAMOND_BLOCK;
	public static BlockBasic ENDER_DIAMOND_BLOCK;
	public static ItemBlockBasic ITEMBLOCK_ENDER_DIAMOND_BLOCK;
	
	private static BlockData DATA_SINGULARITY;
	public static BlockSingularity SINGULARITY;
	public static ItemBlockBasic ITEMBLOCK_SINGULARITY;
	
	private static BlockData DATA_STORAGE;
	public static BlockSingularityStorage STORAGE;
	public static ItemBlockBasic ITEMBLOCK_STORAGE;
	
	private static BlockData DATA_PORTAL;
	public static BlockSingularityPortal PORTAL;
	public static ItemBlockBasic ITEMBLOCK_PORTAL;
	
	private static BlockData DATA_DIMENSION_WALL;
	public static BlockSingularityDimensionWall DIMENSION_WALL;
	public static ItemBlockBasic ITEMBLOCK_DIMENSION_WALL;
	
	private static BlockData DATA_POWDERKEG;
	public static BlockPowderKeg POWDERKEG;
	public static ItemBlockBasic ITEMBLOCK_POWDERKEG;
	

	
	/**
	 *  BlockData Registry
	 *  Registered during the 'preInit' stage.
	 */
	public static void registerBlockData()
	{
		// Blocks
		DATA_ENDER_DIAMOND_BLOCK = new BlockData(7F, 7F, "pickaxe", 3, "enderdiamondblock", Material.IRON, Blockhole.tabBlockhole);
		
		// Singularity
		DATA_SINGULARITY         = new BlockData(10F, 10F, "pickaxe", 3, "singularity", Material.GLASS, Blockhole.tabBlockhole);
		DATA_STORAGE             = new BlockData(10F, 10F, "pickaxe", 3, "storage", Material.GLASS, Blockhole.tabBlockhole);
		DATA_PORTAL              = new BlockData(10F, 10F, "pickaxe", 3, "portal", Material.GLASS, Blockhole.tabBlockhole);
		DATA_DIMENSION_WALL      = new BlockData(100F, 100F, "pickaxe", 100, "dimensionwall", Material.GLASS, Blockhole.tabBlockhole);
		
		// Extra
		DATA_POWDERKEG           = new BlockData(1F, 1F, "pickaxe", 0, "powderkeg", Material.CLOTH, Blockhole.tabBlockhole);
	}
	
	
	
	/**
	 *  Block Registry
	 *  Registered during the 'preInit' stage.
	 */
	public static void registerBlocks()
	{
		// Blocks
		ENDER_DIAMOND_BLOCK = new BlockBasic(DATA_ENDER_DIAMOND_BLOCK);
		
		// Singularity
		SINGULARITY         = new BlockSingularity(DATA_SINGULARITY);
		STORAGE             = new BlockSingularityStorage(DATA_STORAGE);
		PORTAL              = new BlockSingularityPortal(DATA_PORTAL);
		DIMENSION_WALL      = new BlockSingularityDimensionWall(DATA_DIMENSION_WALL);
		
		// Extra
		POWDERKEG           = new BlockPowderKeg(DATA_POWDERKEG);
	}

	
	
	/**
	 *  ItemBlock Registry
	 *  Registered during the 'preInit' stage.
	 */
	public static void registerItemBlocks()
	{
		// Blocks
		ITEMBLOCK_ENDER_DIAMOND_BLOCK = new ItemBlockBasic(ENDER_DIAMOND_BLOCK);
		
		// Singularity
		ITEMBLOCK_SINGULARITY         = new ItemBlockBasic(SINGULARITY);
		ITEMBLOCK_STORAGE             = new ItemBlockBasic(STORAGE);
		ITEMBLOCK_PORTAL              = new ItemBlockBasic(PORTAL);
		ITEMBLOCK_DIMENSION_WALL      = new ItemBlockBasic(DIMENSION_WALL);
		
		// Extra
		ITEMBLOCK_POWDERKEG           = new ItemBlockBasic(POWDERKEG);
	}

	
	
	/**
	 *  Block Renderer
	 *  @param renderItem - RenderItem passed in to render items during 'init' stage.
	 */
	public static void renderBlocks(RenderItem renderItem) 
	{
		// Blocks
		renderItem.getItemModelMesher().register(Item.getItemFromBlock(ENDER_DIAMOND_BLOCK), 0, new ModelResourceLocation(Blockhole.MODID + ":" + ENDER_DIAMOND_BLOCK.getName(), "inventory"));
		
		// Singularity
		renderItem.getItemModelMesher().register(Item.getItemFromBlock(SINGULARITY), 0, new ModelResourceLocation(Blockhole.MODID + ":" + SINGULARITY.getName(), "inventory"));
		renderItem.getItemModelMesher().register(Item.getItemFromBlock(STORAGE), 0, new ModelResourceLocation(Blockhole.MODID + ":" + STORAGE.getName(), "inventory"));
		renderItem.getItemModelMesher().register(Item.getItemFromBlock(PORTAL), 0, new ModelResourceLocation(Blockhole.MODID + ":" + PORTAL.getName(), "inventory"));
		renderItem.getItemModelMesher().register(Item.getItemFromBlock(DIMENSION_WALL), 0, new ModelResourceLocation(Blockhole.MODID + ":" + DIMENSION_WALL.getName(), "inventory"));
		
		// Extra
		renderItem.getItemModelMesher().register(Item.getItemFromBlock(POWDERKEG), 0, new ModelResourceLocation(Blockhole.MODID + ":" + POWDERKEG.getName(), "inventory"));
	}
}
