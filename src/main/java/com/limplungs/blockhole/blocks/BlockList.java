package com.limplungs.blockhole.blocks;

import com.limplungs.blockhole.Blockhole;

import net.minecraft.block.BlockTNT;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockList 
{
	/**
	 *  BlockData
	 */
	private static BlockData DATA_TELEPORTER = new BlockData(5F, 5F, "pickaxe", 2, "teleporter", Material.ROCK, Blockhole.tabBlockhole);
	private static BlockData DATA_POWDERKEG = new BlockData(1F, 1F, "pickaxe", 0, "powderkeg", Material.CLOTH, Blockhole.tabBlockhole);
	
	
	/**
	 *  Blocks
	 */
	public static BlockTeleporter TELEPORTER;
	public static BlockPowderKeg POWDERKEG;
	
	
	/**
	 *  Block Registry
	 *  Registered during the 'preInit' stage.
	 */
	public static void registerBlocks()
	{
		TELEPORTER = new BlockTeleporter(DATA_TELEPORTER);
		POWDERKEG = new BlockPowderKeg(DATA_POWDERKEG);
	}

	
	/**
	 *  Block Renderer
	 *  @param renderItem - RenderItem passed in to render items during 'init' stage.
	 */
	public static void renderBlocks(RenderItem renderItem) 
	{
		renderItem.getItemModelMesher().register(Item.getItemFromBlock(TELEPORTER), 0, new ModelResourceLocation(Blockhole.MODID + ":" + TELEPORTER.getName(), "inventory"));
		renderItem.getItemModelMesher().register(Item.getItemFromBlock(POWDERKEG), 0, new ModelResourceLocation(Blockhole.MODID + ":" + POWDERKEG.getName(), "inventory"));
	}
}
