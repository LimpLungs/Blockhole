package com.limplungs.blockhole.blocks;

import net.minecraft.block.Block;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockBasic extends Block 
{
	private final String name;
	
	@SuppressWarnings("deprecation")
	public BlockBasic(BlockData blockdata) 
	{
		super(blockdata.material);
		
		// Block Data
		this.name = blockdata.name;
		this.setHardness(blockdata.hardness);
		this.setResistance(blockdata.resistance);
		this.setHarvestLevel(blockdata.tool, blockdata.level);
		this.setUnlocalizedName(blockdata.unlocalizedName);
		this.setCreativeTab(blockdata.creativeTab);
		
		// Block Register
		this.setRegistryName(blockdata.name);
		GameRegistry.registerBlock(this); // deprecated
		
		// Research!!!
		// Not sure what to do with this...
		// GameRegistry.register(this); ????
	}
	
	public String getName()
	{
		return name;
	}

}
