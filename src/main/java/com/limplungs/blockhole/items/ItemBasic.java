package com.limplungs.blockhole.items;

import net.minecraft.item.Item;

public class ItemBasic extends Item
{
	private final String name;
	
	public ItemBasic(ItemData itemdata)
	{
		// Item Data
		this.name = itemdata.name;
		this.setUnlocalizedName(itemdata.unlocalizedName);
		this.maxStackSize = itemdata.stackSize;
		this.setCreativeTab(itemdata.creativeTab);
		
		// Item Register
		this.setRegistryName(itemdata.name);
	}
	
	public String getName()
	{
		return name;
	}
}
