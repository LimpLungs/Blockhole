package com.limplungs.blockhole.blocks;

import java.util.ArrayList;
import java.util.List;

import com.limplungs.blockhole.dimensions.DimensionList;
import com.limplungs.blockhole.dimensions.TeleporterSingularity;
import com.limplungs.blockhole.tileentities.TileEntityBlockhole;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class BlockBlockhole extends BlockBasic implements ITileEntityProvider
{
	private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(.375f, .625f, .375f, .625f, .375f, .625f);
	
	public BlockBlockhole(BlockData blockdata) 
	{
		super(blockdata);
	}
	
	@Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
		TileEntityBlockhole tile = new TileEntityBlockhole();
		
		if (tile.getDimensionID() == -999)
		{
			tile.setDimensionID(DimensionList.registerNewDimension());
		}
		
        return tile;
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
	public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) 
	{
		return BOUNDING_BOX;
	}
	
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) 
	{
		TileEntityBlockhole tile = (TileEntityBlockhole)world.getTileEntity(pos);
		
		if (player instanceof EntityPlayerMP && world instanceof WorldServer)
		{
			player.getServer().getPlayerList().transferPlayerToDimension((EntityPlayerMP)player, tile.getDimensionID(), new TeleporterSingularity((WorldServer)world));
			
			return true;
		}
		
		return false;
	}
	
	// Forge Trick to drop with NBT
	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) 
	{
		List<ItemStack> list = new ArrayList<ItemStack>();
		
		TileEntityBlockhole tile = (TileEntityBlockhole)world.getTileEntity(pos);
		
		if (tile != null)
		{
			ItemStack stack = new ItemStack(BlockList.BLOCKHOLE, 1);
			
			stack.setTagCompound(new NBTTagCompound());
			
			tile.writeToNBT(stack.getTagCompound());
			
			list.add(stack);
		}
		
		return list;
	}
	
	
	@Override
	public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest)
	{
		if (willHarvest) return true; //If it will harvest, delay deletion of the block until after getDrops
	    return super.removedByPlayer(state, world, pos, player, willHarvest);
	}
	
	
	@Override
	public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te, ItemStack tool)
	{
	    super.harvestBlock(world, player, pos, state, te, tool);
	    world.setBlockToAir(pos);
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
		
		TileEntityBlockhole tile = (TileEntityBlockhole)worldIn.getTileEntity(pos);
		
		if(tile != null) 
		{
			if (stack.getTagCompound() != null)
			{
		      tile.readFromNBT(stack.getTagCompound());
		      tile.markDirty();
			}
		}
	}
}
