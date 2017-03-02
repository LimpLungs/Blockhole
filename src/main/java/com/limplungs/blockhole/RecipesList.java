package com.limplungs.blockhole;

import com.limplungs.blockhole.blocks.BlockList;
import com.limplungs.blockhole.items.ItemList;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class RecipesList 
{
	public static void addShaped()
	{
		// Blockhole Teleportation Device
		//GameRegistry.addRecipe(new ItemStack(BlockList.TELEPORTER), "WWW", "IEI", "SSS", 'W', Blocks.WOODEN_SLAB, 'E', BlockList.BLOCKHOLE, 'S', Blocks.STONE);
		
		// Ender Diamond
		GameRegistry.addShapelessRecipe(new ItemStack(ItemList.ENDER_DIAMOND), Items.ENDER_PEARL, Items.DIAMOND, Items.GHAST_TEAR);
		
		// Blockhole Singularity
		GameRegistry.addRecipe(new ItemStack(BlockList.BLOCKHOLE), "OEO", "EDE", "OEO", 'O', Blocks.OBSIDIAN, 'E', Items.ENDER_PEARL, 'D', Blocks.DIAMOND_BLOCK);

		// Tuner
		GameRegistry.addRecipe(new ItemStack(ItemList.TUNER), "E ", " S", 'E', ItemList.ENDER_DIAMOND, 'S', Items.STICK);
		 
		
		// Block Powder Keg
		GameRegistry.addRecipe(new ItemStack(BlockList.POWDERKEG), "SGS", "SCS", "SSS", 'S', Items.STICK, 'G', Items.GUNPOWDER,                 'C', Items.GUNPOWDER);
		GameRegistry.addRecipe(new ItemStack(BlockList.POWDERKEG), "SGS", "SCS", "SSS", 'S', Items.STICK, 'G', Items.GUNPOWDER,                 'C', new ItemStack(Items.COAL, 1, 0));
		GameRegistry.addRecipe(new ItemStack(BlockList.POWDERKEG), "SGS", "SCS", "SSS", 'S', Items.STICK, 'G', Items.GUNPOWDER,                 'C', new ItemStack(Items.COAL, 1, 1));

		GameRegistry.addRecipe(new ItemStack(BlockList.POWDERKEG), "SGS", "SCS", "SSS", 'S', Items.STICK, 'G', new ItemStack(Items.COAL, 1, 0), 'C', Items.GUNPOWDER);
		GameRegistry.addRecipe(new ItemStack(BlockList.POWDERKEG), "SGS", "SCS", "SSS", 'S', Items.STICK, 'G', new ItemStack(Items.COAL, 1, 0), 'C', new ItemStack(Items.COAL, 1, 0));
		GameRegistry.addRecipe(new ItemStack(BlockList.POWDERKEG), "SGS", "SCS", "SSS", 'S', Items.STICK, 'G', new ItemStack(Items.COAL, 1, 0), 'C', new ItemStack(Items.COAL, 1, 1));

		GameRegistry.addRecipe(new ItemStack(BlockList.POWDERKEG), "SGS", "SCS", "SSS", 'S', Items.STICK, 'G', new ItemStack(Items.COAL, 1, 1), 'C', Items.GUNPOWDER);
		GameRegistry.addRecipe(new ItemStack(BlockList.POWDERKEG), "SGS", "SCS", "SSS", 'S', Items.STICK, 'G', new ItemStack(Items.COAL, 1, 1), 'C', new ItemStack(Items.COAL, 1, 0));
		GameRegistry.addRecipe(new ItemStack(BlockList.POWDERKEG), "SGS", "SCS", "SSS", 'S', Items.STICK, 'G', new ItemStack(Items.COAL, 1, 1), 'C', new ItemStack(Items.COAL, 1, 1));
	}
}
