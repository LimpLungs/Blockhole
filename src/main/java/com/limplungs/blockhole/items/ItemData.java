package com.limplungs.blockhole.items;

import com.limplungs.blockhole.Blockhole;

import net.minecraft.creativetab.CreativeTabs;

public class ItemData 
{
	protected int stackSize = 64;
	
	protected String name = "NewItem";
	protected String unlocalizedName = (Blockhole.MODID + "_" + name);
	protected CreativeTabs creativeTab = Blockhole.tabBlockhole;
	
	public ItemData(int stackSize, CreativeTabs tab, String name)
	{
		this.stackSize = stackSize;
		this.creativeTab = tab;
		this.name = name;
		this.unlocalizedName = (Blockhole.MODID + "_" + name);
	}
}
