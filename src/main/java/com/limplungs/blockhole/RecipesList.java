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
		GameRegistry.addRecipe(new ItemStack(BlockList.TELEPORTER), "WWW", "IEI", "SSS", 'W', Blocks.WOODEN_SLAB, 'E', Items.ENDER_PEARL, 'S', Blocks.STONE);

		// Tuner
		GameRegistry.addRecipe(new ItemStack(ItemList.TUNER), " E ", "NSN", " S ", 'E', Items.ENDER_PEARL, 'N', Items.GOLD_INGOT, 'S', Items.STICK);
		 
		
		// Block Powder Keg
		GameRegistry.addRecipe(new ItemStack(BlockList.POWDERKEG), "SGS", "SCS", "SSS", 'S', Items.STICK, 'G', Items.GUNPOWDER, 'C', Items.GUNPOWDER);
		GameRegistry.addRecipe(new ItemStack(BlockList.POWDERKEG), "SGS", "SCS", "SSS", 'S', Items.STICK, 'G', Items.GUNPOWDER, 'C', new ItemStack(Items.COAL, 1, 0));
		GameRegistry.addRecipe(new ItemStack(BlockList.POWDERKEG), "SGS", "SCS", "SSS", 'S', Items.STICK, 'G', Items.GUNPOWDER, 'C', new ItemStack(Items.COAL, 1, 1));

		GameRegistry.addRecipe(new ItemStack(BlockList.POWDERKEG), "SGS", "SCS", "SSS", 'S', Items.STICK, 'G', new ItemStack(Items.COAL, 1, 0), 'C', Items.GUNPOWDER);
		GameRegistry.addRecipe(new ItemStack(BlockList.POWDERKEG), "SGS", "SCS", "SSS", 'S', Items.STICK, 'G', new ItemStack(Items.COAL, 1, 0), 'C', new ItemStack(Items.COAL, 1, 0));
		GameRegistry.addRecipe(new ItemStack(BlockList.POWDERKEG), "SGS", "SCS", "SSS", 'S', Items.STICK, 'G', new ItemStack(Items.COAL, 1, 0), 'C', new ItemStack(Items.COAL, 1, 1));

		GameRegistry.addRecipe(new ItemStack(BlockList.POWDERKEG), "SGS", "SCS", "SSS", 'S', Items.STICK, 'G', new ItemStack(Items.COAL, 1, 1), 'C', Items.GUNPOWDER);
		GameRegistry.addRecipe(new ItemStack(BlockList.POWDERKEG), "SGS", "SCS", "SSS", 'S', Items.STICK, 'G', new ItemStack(Items.COAL, 1, 1), 'C', new ItemStack(Items.COAL, 1, 0));
		GameRegistry.addRecipe(new ItemStack(BlockList.POWDERKEG), "SGS", "SCS", "SSS", 'S', Items.STICK, 'G', new ItemStack(Items.COAL, 1, 1), 'C', new ItemStack(Items.COAL, 1, 1));
	}
}
