package com.limplungs.blockhole;

import com.limplungs.blockhole.lists.BlockList;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

public class RecipeCopyBlockhole implements IRecipe
{
	@Override
	public boolean matches(InventoryCrafting inv, World worldIn) 
	{
		int num = 0;
		boolean flag = true;
		
		for (int i = 0; i < inv.getSizeInventory() && flag == true; i++) 
		{
			if (inv.getStackInSlot(i) != ItemStack.EMPTY) 
			{
				num += 1;
				
				if (inv.getStackInSlot(i).getItem() != new ItemStack(BlockList.PORTAL).getItem()) 
				{
					flag = false;
				}
			}
		}
		
		if (num == 2 && flag == true) 
		{
			return true;
		}
		
		return false;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv) 
	{
		ItemStack nbtcopy = ItemStack.EMPTY;
		
		for (int i = 0; i < inv.getSizeInventory(); i++)
		{
			if (inv.getStackInSlot(i).hasTagCompound())
			{
				nbtcopy = inv.getStackInSlot(i).copy();
				nbtcopy.setCount(2);
				nbtcopy.setTagCompound(inv.getStackInSlot(i).getTagCompound());
				
				// Keeps the first itemstack with NBT found, bypassing any others
				i = inv.getSizeInventory();
			}
		}
		
		return nbtcopy;
	}

	@Override
	public int getRecipeSize() 
	{
		return 2;
	}
	
	@Override
	public ItemStack getRecipeOutput() 
	{
		return new ItemStack(BlockList.PORTAL, 2);
	}

	
	/**
	 * Taken from ShapelessRecipes.class
	 */
	@Override
	public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) 
	{ 
		NonNullList<ItemStack> nnlist = NonNullList.<ItemStack>withSize(inv.getSizeInventory(), ItemStack.EMPTY);

		for (int i = 0; i < nnlist.size(); ++i)
		{
			nnlist.set(i, net.minecraftforge.common.ForgeHooks.getContainerItem(inv.getStackInSlot(i)));
		}

		return nnlist;
	}

}
