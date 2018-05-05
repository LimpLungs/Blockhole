package com.limplungs.blockhole.blocks;

import net.minecraft.block.Block;

public class BlockBasic extends Block 
{
	private final String name;
	private final BlockData data;
	
	public BlockBasic(BlockData blockdata) 
	{
		super(blockdata.material);
		
		// Block Data
		this.name = blockdata.name;
		this.data = blockdata;
		this.setHardness(blockdata.hardness);
		this.setResistance(blockdata.resistance);
		this.setHarvestLevel(blockdata.tool, blockdata.level);
		this.setUnlocalizedName(blockdata.unlocalizedName);
		this.setCreativeTab(blockdata.creativeTab);
		
		// Block Register
		this.setRegistryName(blockdata.name);
	}
	
	public String getName()
	{
		return name;
	}

	public BlockData getData() 
	{
		return data;
	}
}
