package com.limplungs.blockhole.blocks;

import com.limplungs.blockhole.DoubleLinkedQueue;
import com.limplungs.blockhole.items.ItemList;
import com.limplungs.blockhole.tileentities.TileEntityTeleporter;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
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
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess blockAccess, BlockPos pos) 
	{
		return BOUNDING_BOX;
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState state, World world, BlockPos pos) 
	{
		return BOUNDING_BOX;
	}

	@Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TileEntityTeleporter();
    }
	
	@SuppressWarnings({ "deprecation", "unused" })
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) 
	{
		TileEntityTeleporter tile = (TileEntityTeleporter)world.getTileEntity(pos);
		BlockPos tploc = new BlockPos(tile.tp_x, tile.tp_y, tile.tp_z);
		
		// CHANGE SO IF YOU RIGHT CLICK WITH BLOCK YOU ADD ONE TO THE INTERNAL BUFFER (BLOCKHOLE)
		// THEN WHEN IT IS POWERED, IT ATTEMPTS TO PLACE THE BLOCK (flag the TE for placing)
		// THEN WHEN IT IS UNPOWER, IT ATTEMPTS TO REMOVE THE BLOCK (flag the TE for remove)
		// UPON REMOVE, PUT BLOCK AT THE FRONT/BACK (decide) OF THE QUEUE
		                // Double Linked Queue 
							// back of queue comes out first on origin location
							// back of queue comes out last on teleport location
							// front of queue comes out first on teleport location
							// front of queue comes out last on origin location		
		
		if (heldItem != null && heldItem.getItem() instanceof ItemBlock)
		{
			tile.queue.insert(Block.getBlockFromItem(heldItem.getItem()));
			heldItem.stackSize--;
			
			return true;
		}

		/**
		if (world.isBlockIndirectlyGettingPowered(pos) == 0 )
		{
			if (heldItem == null || (heldItem != null && heldItem.getItem() != ItemList.TUNER))
			{
				if (world.getBlockState(tploc) != null && world.getBlockState(tploc).getBlock() != null && world.getBlockState(tploc).getBlock() != BlockList.TELEPORTER && world.getBlockState(tploc).getBlock() != Blocks.AIR)
				{
					int meta = world.getBlockState(tploc).getBlock().getMetaFromState(world.getBlockState(tploc));
					
					for (int i = 0; i < player.inventory.getSizeInventory(); i++)
					{
						if (player.inventory.getStackInSlot(i) == null) 
						{
							player.inventory.setInventorySlotContents(i, new ItemStack(Item.getItemFromBlock(world.getBlockState(tploc).getBlock()), 1, meta));
							world.setBlockState(tploc, Blocks.AIR.getDefaultState());
							return true;
						}
						if (player.inventory.getStackInSlot(i).getItem() == new ItemStack(Item.getItemFromBlock(world.getBlockState(tploc).getBlock()), 1, meta).getItem());
						{
							if ( player.inventory.getStackInSlot(i).stackSize < player.inventory.getStackInSlot(i).getMaxStackSize())
							{
								player.inventory.setInventorySlotContents(i, new ItemStack(Item.getItemFromBlock(world.getBlockState(tploc).getBlock()), player.inventory.getStackInSlot(i).stackSize + 1, meta));
								world.setBlockState(tploc, Blocks.AIR.getDefaultState());
								return true;
							}
						}
					}
				}
			}
		}
		**/
		
		return false;
	}
}
