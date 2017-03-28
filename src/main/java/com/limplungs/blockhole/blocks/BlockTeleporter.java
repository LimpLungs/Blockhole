package com.limplungs.blockhole.blocks;

import com.limplungs.blockhole.BlockholeDefinitions;
import com.limplungs.blockhole.items.ItemList;
import com.limplungs.blockhole.tileentities.TileEntityTeleporter;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockTeleporter extends BlockBasic implements ITileEntityProvider
{
	private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0f, .6875f, 0f, 1f, 0f, 1f);
	
	public BlockTeleporter(BlockData blockdata) 
	{
		super(blockdata);
	}

	
	@Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TileEntityTeleporter();
    }
	
	@Override
	public boolean isFullCube(IBlockState state)
	{
		return true;
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

	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) 
	{
		ItemStack heldItem = player.getHeldItem(hand);
		
		TileEntityTeleporter tile = (TileEntityTeleporter)world.getTileEntity(pos);
		
		return false;
	}
	
	
	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos) 
	{
		
		
	}
		
}
