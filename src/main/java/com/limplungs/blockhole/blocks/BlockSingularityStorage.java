package com.limplungs.blockhole.blocks;

import com.limplungs.blockhole.tileentities.TileEntitySingularityStorage;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockSingularityStorage extends BlockBasic implements ITileEntityProvider
{
	public BlockSingularityStorage(BlockData blockdata) 
	{
		super(blockdata);
	}

	
	
	@Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TileEntitySingularityStorage();
    }
	
	
	
	@Override
	public boolean isFullCube(IBlockState state)
	{
		return true;
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
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) 
	{
		TileEntitySingularityStorage tile = (TileEntitySingularityStorage)world.getTileEntity(pos);
		
		if (tile != null)
		{
			ItemStack held = player.getHeldItem(hand);
			ItemStack back = tile.getQueue().getBack();
			
			// Insertion
			if (!player.isSneaking())
			{
				if (held.getItem() instanceof ItemBlock)
				{
					ItemStack insert = new ItemStack(held.getItem(), 1, held.getMetadata());
					
					insert.setTagCompound(held.getTagCompound());
					
					tile.setInventorySlotContents(1, insert);
					
					held.setCount(held.getCount() - 1);
					
					if (held.getCount() < 1)
						held = ItemStack.EMPTY;
					
					return true;
				}
			}
			// Removal
			else
			{
				if (back != ItemStack.EMPTY)
				{
					if (player.inventory.addItemStackToInventory(back))
					{
						tile.removeStackFromSlot(0);
						
						return true;
					}
				}
			}
		}
		
		return false;
	}
		
	// TODO: Get Forge Trick to work and remove breakBlock / dropping of inventory.
	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) 
	{
		TileEntitySingularityStorage tile = (TileEntitySingularityStorage) world.getTileEntity(pos);
		
		if (tile != null)
		{
			while (tile.getQueue().getSize() > 0)
			{
				ItemStack stack = tile.getQueue().pop_back();

				if (stack != ItemStack.EMPTY)
				{
					float spawnx = pos.getX() + world.rand.nextFloat();
					float spawny = pos.getY() + world.rand.nextFloat();
					float spawnz = pos.getZ() + world.rand.nextFloat();

					EntityItem drop = new EntityItem(world, spawnx, spawny, spawnz, stack);

					float m = 0.05F;

					drop.motionX = (-.5F + world.rand.nextFloat()) * m;
					drop.motionY = (4F + world.rand.nextFloat()) * m;
					drop.motionZ = (-.5F + world.rand.nextFloat()) * m;

					world.spawnEntity(drop);
				}
			}

			// might add output levels in the future.
			world.updateComparatorOutputLevel(pos, this);
		}
		
		super.breakBlock(world, pos, state);
	}
	
	
	
	//Unable to get it to consistantly work. Sometimes it crashes with nullpointer on either pick-block or block-drop. Possibly due to it not
	//completely saving the block before attempting?
	/**
	 
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) 
	{
		TileEntityBlockholeStorage tile = (TileEntityBlockholeStorage)world.getTileEntity(pos);
		
		if (tile != null)
		{
			ItemStack stack = new ItemStack(BlockList.STORAGE, 1);
			
			stack.setTagCompound(new NBTTagCompound());
			
			tile.writeToNBT(stack.getTagCompound());
			
			return stack;
		}
		
		return ItemStack.EMPTY;
	}
	
	
	// Forge Trick to drop with NBT
	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) 
	{
		List<ItemStack> list = new ArrayList<ItemStack>();
		
		TileEntityBlockholeStorage tile = (TileEntityBlockholeStorage)world.getTileEntity(pos);
		
		if (tile != null)
		{
			ItemStack stack = new ItemStack(BlockList.STORAGE, 1);
			
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
		
		TileEntityBlockholeStorage tile = (TileEntityBlockholeStorage)worldIn.getTileEntity(pos);
		
		if(tile != null) 
		{
			if (stack.getTagCompound() != null)
			{
		      tile.readFromNBT(stack.getTagCompound());
		      tile.markDirty();
			}
		}
	}
	// End of Forge Trick
	
	**/
}
