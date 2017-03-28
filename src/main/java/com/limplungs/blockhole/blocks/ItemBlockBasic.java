package com.limplungs.blockhole.blocks;

import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

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
		
		GameRegistry.register(this);
	}

	public String getName() 
	{
		return name;
	}
	
	
}
