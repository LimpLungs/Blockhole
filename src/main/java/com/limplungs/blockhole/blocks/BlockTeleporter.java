package com.limplungs.blockhole.blocks;

import com.limplungs.blockhole.items.ItemList;
import com.limplungs.blockhole.tileentities.TileEntityTeleporter;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
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
	public AxisAlignedBB getCollisionBoundingBox(IBlockState state, World world, BlockPos pos) 
	{
		return BOUNDING_BOX;
	}
	
	@SuppressWarnings({ "unused" })
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) 
	{
		TileEntityTeleporter tile = (TileEntityTeleporter)world.getTileEntity(pos);
		
		if (!world.isRemote)
		{
			tile = (TileEntityTeleporter)world.getTileEntity(pos);
		}	
		
		BlockPos tploc = new BlockPos(tile.tp_x, tile.tp_y, tile.tp_z);
		ItemStack tstack = tile.queue.getBack();
			
		if (heldItem != null)
		{
			if(tile.isItemValidForSlot(tile.queue.getSize(), heldItem))
			{
				tile.setInventorySlotContents(tile.queue.getSize(), heldItem);
				heldItem.stackSize -= 1;
				player.attackEntityFrom(DamageSource.generic, 1);
				
				return true;
			}
			
			if (heldItem.getItem() == Items.ENDER_PEARL)
			{
				if (player.attemptTeleport(tile.tp_x + (tile.tp_x / Math.abs(tile.tp_x) * .5), tile.tp_y, tile.tp_z + (tile.tp_z / Math.abs(tile.tp_z) * .5)))
				{
					heldItem.stackSize -= 1;
					player.attackEntityFrom(DamageSource.generic, 6);
					
					return true;
				}
			}
		}
		
		if (heldItem == null || (!(heldItem.getItem() instanceof ItemBlock) && heldItem.getItem() != ItemList.TUNER))
		{
			if (tstack != null)
			{
				for (int i = 0; i < player.inventory.getSizeInventory(); i++)
				{
					ItemStack pstack = player.inventory.getStackInSlot(i);
					
					if (!world.isRemote)
					if (pstack != null && pstack.stackSize < pstack.getMaxStackSize() && pstack.getItem() == tstack.getItem() && pstack.getMetadata() == tstack.getMetadata() && pstack.getTagCompound() == tstack.getTagCompound())
					{
						tile.removeStackFromSlot(tile.queue.getSize());
						pstack.stackSize += 1;
						player.attackEntityFrom(DamageSource.generic, 1);
							
						return true;
					}
				}
			
				for (int i = 0; i < player.inventory.getSizeInventory(); i++)
				{
					ItemStack pstack = player.inventory.getStackInSlot(i);

					if (!world.isRemote)	
					if (pstack == null)
					{
						player.inventory.setInventorySlotContents(i, tile.removeStackFromSlot(tile.queue.getSize()));
						player.attackEntityFrom(DamageSource.generic, 1);
						
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	//@SuppressWarnings("deprecation")
	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block) 
	{
		TileEntityTeleporter tile = (TileEntityTeleporter)world.getTileEntity(pos);
		
		if (!world.isRemote)
		{
			tile = (TileEntityTeleporter)world.getTileEntity(pos);
		}	
		
		
		BlockPos tploc = new BlockPos(tile.tp_x, tile.tp_y, tile.tp_z);
		
		if (pos != tploc)
		{
			if (world.isBlockIndirectlyGettingPowered(pos) > 0)
			{
				if (tile.isOn == false)
				{
					//world.setBlockState(tploc, Block.getBlockFromItem(stack.getItem()).getStateFromMeta(stack.getMetadata()));
					
					tile.isOn = true;
				}
			}
			else if (world.isBlockIndirectlyGettingPowered(pos) == 0)
			{
				if (tile.isOn == true)
				{
					tile.isOn = false;
				}
			}
		}
	}
}
