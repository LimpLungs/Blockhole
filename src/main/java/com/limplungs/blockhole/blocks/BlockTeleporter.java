package com.limplungs.blockhole.blocks;

import com.limplungs.blockhole.items.ItemList;
import com.limplungs.blockhole.tileentities.TileEntityTeleporter;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
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
			BlockPos tploc = new BlockPos(tile.tp_x, tile.tp_y, tile.tp_z);
			
			if (heldItem != null && heldItem.getItem() instanceof ItemBlock )
			{
				tile.queue.insert_back(new ItemStack(heldItem.getItem(), 1, heldItem.getMetadata()));
				heldItem.stackSize--;
			
				return true;
			}
		
			if (heldItem == null || (!(heldItem.getItem() instanceof ItemBlock) && heldItem.getItem() != ItemList.TUNER))
			{
				if (tile.queue.getBack() != null)
				{
					for (int i = 0; i < player.inventory.getSizeInventory(); i++)
					{
						if (player.inventory.getStackInSlot(i) != null && player.inventory.getStackInSlot(i).getItem() == tile.queue.getBack().getItem())
						{
							player.inventory.getStackInSlot(i).stackSize += tile.queue.pop_back().stackSize;
							
							return true;
						}
					}
			
					for (int i = 0; i < player.inventory.getSizeInventory(); i++)
					{
						if (player.inventory.getStackInSlot(i) == null)
						{
							player.inventory.setInventorySlotContents(i, tile.queue.pop_back());
						
							return true;
						}
					}
				}
			}
		
		
		return false;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block) 
	{
		TileEntityTeleporter tile = (TileEntityTeleporter)world.getTileEntity(pos);
		BlockPos tploc = new BlockPos(tile.tp_x, tile.tp_y, tile.tp_z);
		
		if (pos != tploc)
		{
			if (world.isBlockIndirectlyGettingPowered(pos) > 0)
			{
				if (tile.isOn == false)
				{
					if ((world.getBlockState(tploc) == null || world.getBlockState(tploc).getBlock() == Blocks.AIR) && tile.queue.getFront() != null)
					{
						ItemStack stack = tile.queue.pop_front();
						
						world.setBlockState(tploc, Block.getBlockFromItem(stack.getItem()).getStateFromMeta(stack.getMetadata()));
						
						if (!world.isRemote)
						{
							System.out.println("PLACED BLOCK IN WORLD");
							for (int test = 1; test < tile.queue.getSize() + 1; test++)
							{
								System.out.println(tile.queue.getStackAtNode(test));
							}
						}
					}

					tile.isOn = true;
				}
			}
			else if (world.isBlockIndirectlyGettingPowered(pos) == 0)
			{
				if (tile.isOn == true)
				{
					if (world.getBlockState(tploc) != null && world.getBlockState(tploc).getBlock() != Blocks.AIR)
					{
						tile.queue.insert_front(new ItemStack(world.getBlockState(tploc).getBlock(), 1, world.getBlockState(tploc).getBlock().getMetaFromState(world.getBlockState(tploc))));

						world.setBlockState(tploc, Blocks.AIR.getDefaultState());
						
						if (!world.isRemote)
						{
							System.out.println("PLACED BLOCK IN QUEUE");
							for (int test = 1; test < tile.queue.getSize() + 1; test++)
							{
								System.out.println(tile.queue.getStackAtNode(test));
							}
						}
					}
					
					tile.isOn = false;
				}
			}
		}
	}
}
