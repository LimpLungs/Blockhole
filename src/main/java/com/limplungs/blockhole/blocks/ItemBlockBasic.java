package com.limplungs.blockhole.blocks;

import net.minecraft.item.ItemBlock;

public class ItemBlockBasic extends ItemBlock
{

	private final String name;

	public ItemBlockBasic(BlockBasic block) 
	{
		super(block);
		
		this.name = block.getName();
		this.setUnlocalizedName(block.getData().unlocalizedName);
		this.setCreativeTab(block.getData().creativeTab);
		this.setRegistryName(block.getName());
		
	}

	public String getName() 
	{
		return name;
	}
}
