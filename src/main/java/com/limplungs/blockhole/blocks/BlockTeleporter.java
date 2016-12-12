package com.limplungs.blockhole.blocks;

import net.minecraft.block.state.IBlockState;

public class BlockTeleporter extends BlockBasic
{

	public BlockTeleporter(BlockData blockdata) 
	{
		super(blockdata);
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}
}
