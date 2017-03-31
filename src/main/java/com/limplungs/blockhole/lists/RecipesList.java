package com.limplungs.blockhole.lists;

import com.limplungs.blockhole.RecipeCopyBlockhole;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.oredict.RecipeSorter.Category;

public class RecipesList 
{
	public static void registerIRecipes()
	{
		RecipeSorter.register("copyNBT", RecipeCopyBlockhole.class, Category.SHAPELESS, "after:minecraft:shapeless");
	}
	
	
	
	public static void addShapeless()
	{
		// Ender Diamond
		GameRegistry.addShapelessRecipe(new ItemStack(ItemList.ENDER_DIAMOND), Items.ENDER_PEARL, Items.DIAMOND, Items.GHAST_TEAR);
		GameRegistry.addShapelessRecipe(new ItemStack(ItemList.ENDER_DIAMOND, 9), BlockList.ENDER_DIAMOND_BLOCK);
		
		// Blockhole NBT Copy
		GameRegistry.addRecipe(new RecipeCopyBlockhole());
	}
	
	
	
	public static void addShaped()
	{
		// Ender Diamond Block
		GameRegistry.addRecipe(new ItemStack(BlockList.ENDER_DIAMOND_BLOCK), "EEE", "EEE", "EEE", 'E', ItemList.ENDER_DIAMOND);

		// Singularity
		GameRegistry.addRecipe(new ItemStack(BlockList.SINGULARITY), "OEO", "EDE", "OEO", 'O', Blocks.OBSIDIAN, 'E', ItemList.ENDER_DIAMOND, 'D', BlockList.ENDER_DIAMOND_BLOCK);
		
		// Singularity Storage
		GameRegistry.addRecipe(new ItemStack(BlockList.STORAGE), "OCO", "OSO", "OOO", 'O', Blocks.OBSIDIAN, 'S', BlockList.SINGULARITY, 'C', Blocks.CHEST);
		
		// Singularity Portal
		GameRegistry.addRecipe(new ItemStack(BlockList.PORTAL), "IEI", "ESE", "IEI", 'I', Blocks.IRON_BLOCK, 'S', BlockList.SINGULARITY, 'E', Items.ENDER_PEARL);

		// Tuner
		GameRegistry.addRecipe(new ItemStack(ItemList.TUNER), "E ", " S", 'E', ItemList.ENDER_DIAMOND, 'S', Items.STICK);
	}
	
	
	
	public static void addExtra()
	{
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
