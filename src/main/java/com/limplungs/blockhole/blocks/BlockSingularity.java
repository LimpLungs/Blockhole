package com.limplungs.blockhole.blocks;

import com.limplungs.limpcore.blocks.BlockBasic;
import com.limplungs.limpcore.blocks.BlockData;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockSingularity extends BlockBasic
{
	private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0.25f, 0.25f, 0.25f, 0.75f, 0.75f, 0.75f);
	
	public BlockSingularity(BlockData blockdata) 
	{
		super(blockdata);
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
		return BlockRenderLayer.CUTOUT;
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
