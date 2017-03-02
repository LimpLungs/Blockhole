package com.limplungs.blockhole.blocks;

import java.util.ArrayList;
import java.util.List;

import com.limplungs.blockhole.dimensions.DimensionList;
import com.limplungs.blockhole.dimensions.TeleporterSingularity;
import com.limplungs.blockhole.tileentities.TileEntityBlockhole;
import com.limplungs.blockhole.tileentities.TileEntityBlockholeWall;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class BlockBlockhole extends BlockBasic implements ITileEntityProvider
{
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
			tile.setDimensionID(DimensionList.registerNewSingularity());
		}
		
        return tile;
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
	
	// End of forge trick
	

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) 
	{
		TileEntityBlockhole tile = (TileEntityBlockhole)world.getTileEntity(pos);
		
		if (player instanceof EntityPlayerMP && world instanceof WorldServer)
		{
			int previous = player.dimension;
			BlockPos location = player.getPosition();
			
			player.getServer().getPlayerList().transferPlayerToDimension((EntityPlayerMP)player, tile.getDimensionID(), new TeleporterSingularity((WorldServer)world, player.dimension, new BlockPos(8.5,2,8.5)));
			
			for (int num = 0; num < player.getServer().worlds.length; num++)
				if (player.getServer().worlds[num].getPlayerEntityByUUID(player.getUniqueID()) != null)
				{
					World current = player.getServer().worlds[num];
					TileEntityBlockholeWall wall;
					
					int i = 0;
					int j = 0;
					int k = 0;

					for (j = 0; j < 16; j+=15)
					{
						for (i = 1; i < 15; i++)
						{
							for (k = 1; k < 15; k++)
							{  
								wall = (TileEntityBlockholeWall)current.getTileEntity(new BlockPos(i,j,k));

								if (wall != null)
								{
									wall.setDimensionID(previous);
									wall.setTeleportLocation(location);
								}
							}
						}
					}
					
					i = 0; j = 0; k = 0;
					
					for (j = 1; j < 15; j++)
					{
						k = 0;
						
						for (i = 1; i < 15; i++)
						{
							wall = (TileEntityBlockholeWall)current.getTileEntity(new BlockPos(i,j,k));

							if (wall != null)
							{
								wall.setDimensionID(previous);
								wall.setTeleportLocation(location);
							}
						}
						
						k = 15;
						
						for (i = 1; i < 15; i++)
						{
							wall = (TileEntityBlockholeWall)current.getTileEntity(new BlockPos(i,j,k));

							if (wall != null)
							{
								wall.setDimensionID(previous);
								wall.setTeleportLocation(location);
							}
						}
						
						i = 0;
						
						for (k = 1; k < 15; k++)
						{
							wall = (TileEntityBlockholeWall)current.getTileEntity(new BlockPos(i,j,k));

							if (wall != null)
							{
								wall.setDimensionID(previous);
								wall.setTeleportLocation(location);
							}
						}
						
						i = 15;
						
						for (k = 1; k < 15; k++)
						{
							wall = (TileEntityBlockholeWall)current.getTileEntity(new BlockPos(i,j,k));

							if (wall != null)
							{
								wall.setDimensionID(previous);
								wall.setTeleportLocation(location);
							}
						}
					}
				}
			
			return true;
		}
		
		return false;
	}
	
}
