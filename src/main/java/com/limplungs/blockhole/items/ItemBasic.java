package com.limplungs.blockhole.items;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemBasic extends Item
{
	private final String name;
	
	@SuppressWarnings("deprecation")
	public ItemBasic(ItemData itemdata)
	{
		// Item Data
		this.name = itemdata.name;
		this.setUnlocalizedName(itemdata.unlocalizedName);
		this.maxStackSize = itemdata.stackSize;
		this.setCreativeTab(itemdata.creativeTab);
		
		// Item Register
		this.setRegistryName(itemdata.name);
		GameRegistry.registerItem(this); // deprecated
	}
	
	public String getName()
	{
		return name;
	}
}
