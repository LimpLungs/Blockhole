package com.limplungs.blockhole.blocks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockSingularity extends BlockBasic
{
	private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0.3125f, 0.3125f, 0.3125f, 0.6875f, 0.6875f, 0.6875f);
	
	public BlockSingularity(BlockData blockdata) 
	{
		super(blockdata);
	}

	
	
	@Override
	public boolean isFullyOpaque(IBlockState state) 
	{
		return false;
	}
	
	
	
	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}
	
	
	
	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}
	
	
	
	@Override
	public BlockRenderLayer getBlockLayer() 
	{
		return BlockRenderLayer.TRANSLUCENT;
	}
	
	
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) 
	{
		return EnumBlockRenderType.MODEL;
	}
	
	
	
	@Override
	public boolean canProvidePower(IBlockState state) 
	{
		return false;
	}
	
	
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess blockAccess, BlockPos pos) 
	{
		return BOUNDING_BOX;
	}
	
	
	
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) 
	{
		return BOUNDING_BOX;
	}
}
