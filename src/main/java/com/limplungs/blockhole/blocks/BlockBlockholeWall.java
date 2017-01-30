package com.limplungs.blockhole.blocks;

import com.limplungs.blockhole.tileentities.TileEntityBlockholeWall;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockBlockholeWall extends BlockBasic implements ITileEntityProvider
{

	public BlockBlockholeWall(BlockData blockdata) 
	{
		super(blockdata);
		this.setBlockUnbreakable();
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) 
	{
		if (player.isSneaking())
		{
			
		}
		
		return false;
	}
	
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) 
	{
		return new ItemStack(Blocks.BEDROCK);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) 
	{
		return new TileEntityBlockholeWall();
	}
	
	@Override
	public boolean isFullyOpaque(IBlockState state) 
	{
		return true;
	}
	
	@Override
	public boolean isSideSolid(IBlockState base_state, IBlockAccess world, BlockPos pos, EnumFacing side) 
	{
		return true;
	}
	
	@Override
	public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) 
	{
		return true;
	}
	
	@Override
	public void onBlockDestroyedByExplosion(World world, BlockPos pos, Explosion explosionIn) 
	{
		super.onBlockDestroyedByExplosion(world, pos, explosionIn);
		
		TileEntityBlockholeWall tile = (TileEntityBlockholeWall)world.getTileEntity(pos);
		
		world.setBlockState(pos, BlockList.BLOCKHOLE_WALL.getDefaultState());
		
		world.setTileEntity(pos, tile);
	}
	
	@Override
	public void onBlockDestroyedByPlayer(World world, BlockPos pos, IBlockState state) 
	{
		super.onBlockDestroyedByPlayer(world, pos, state);
		
		TileEntityBlockholeWall tile = (TileEntityBlockholeWall)world.getTileEntity(pos);
		
		world.setBlockState(pos, BlockList.BLOCKHOLE_WALL.getDefaultState());
		
		world.setTileEntity(pos, tile);
	}
	
	@Override
	public void onBlockExploded(World world, BlockPos pos, Explosion explosion) {
		
		super.onBlockExploded(world, pos, explosion);

		TileEntityBlockholeWall tile = (TileEntityBlockholeWall)world.getTileEntity(pos);
		
		world.setBlockState(pos, BlockList.BLOCKHOLE_WALL.getDefaultState());
		
		world.setTileEntity(pos, tile);
	}
	
	@Override
	public void onBlockHarvested(World world, BlockPos pos, IBlockState state, EntityPlayer player) 
	{
		super.onBlockHarvested(world, pos, state, player);

		TileEntityBlockholeWall tile = (TileEntityBlockholeWall)world.getTileEntity(pos);
		
		world.setBlockState(pos, BlockList.BLOCKHOLE_WALL.getDefaultState());
		
		world.setTileEntity(pos, tile);
	}
	
	@Override
	public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) 
	{ 
		super.removedByPlayer(state, world, pos, player, willHarvest);

		TileEntityBlockholeWall tile = (TileEntityBlockholeWall)world.getTileEntity(pos);
		
		world.setBlockState(pos, BlockList.BLOCKHOLE_WALL.getDefaultState());
		
		world.setTileEntity(pos, tile);
		
		return false;
	}
}
