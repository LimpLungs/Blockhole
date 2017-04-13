package com.limplungs.blockhole.items;

import java.util.List;

import com.limplungs.blockhole.lists.DimensionList;
import com.limplungs.blockhole.tileentities.TileEntitySingularityDimensionWall;
import com.limplungs.blockhole.tileentities.TileEntitySingularityPortal;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

public class ItemTuner extends ItemBasic
{
	public ItemTuner(ItemData itemdata) 
	{
		super(itemdata);
		this.setMaxStackSize(1);
		this.setHasSubtypes(true);
	}
	
	
	
	@Override
	public void onCreated(ItemStack stack, World world, EntityPlayer player)
	{
		if (!stack.hasTagCompound())
		{
			stack.setTagCompound(new NBTTagCompound());
			stack.getTagCompound().setInteger("mode", 0);
		}
	}

	
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		ItemStack stack = player.getHeldItem(hand);
		
		if (!stack.hasTagCompound())
		{
			stack.setTagCompound(new NBTTagCompound());
			stack.getTagCompound().setInteger("mode", 0);
		}
		
		Block target = world.getBlockState(pos).getBlock();
		TileEntity tile = world.getTileEntity(pos);
		
		if (target != null && tile != null)
		{	
			// Manipulate Mode
			if (stack.getTagCompound().getInteger("mode") == 3)
			{
				// Dimension Wall - Set transport to on / off
				if (tile instanceof TileEntitySingularityDimensionWall)
				{
					TileEntitySingularityDimensionWall wall = (TileEntitySingularityDimensionWall) tile;
					
					// Flips transport mode on / off.
					if (!player.isSneaking())
					{
						wall.flipTransport();
					}

					// Shows transport mode to player.
					if (world.isRemote)
					{
						player.sendMessage(new TextComponentString("Interdimensional Transport: " + wall.getTransport()));
					}
					
					return EnumActionResult.SUCCESS;
				}
				
				// Portal - change dimension number between known Singularities.
				if (tile instanceof TileEntitySingularityPortal)
				{
					int dims[] = DimensionManager.getDimensions(DimensionType.getById(DimensionList.SINGULARITY_ID));

					System.out.print('\n');
					
					for (int i = 0; i < dims.length; i++)
					{
						System.out.print(dims[i] + " ");              ///////////////////////////////// dimension will sometimes set to 1 or -999?
					}
					
					for (int i = 0; i < dims.length; i++)
					{
						if (dims[i] == ((TileEntitySingularityPortal)tile).getDimensionID())
						{
							System.out.println('\n' + dims[i]);
							System.out.println("**********");
							
							// Increase dimension number.
							if (!player.isSneaking())
							{
								if (i < dims.length - 1)
									((TileEntitySingularityPortal)tile).setDimensionID(dims[i] + 1);
								else
								{
									((TileEntitySingularityPortal)tile).setDimensionID(dims[0]);
								}
							}
							
							// Decrease dimension number
							else
							{
								if (i > 0)
									((TileEntitySingularityPortal)tile).setDimensionID(dims[i] - 1);
								else
								{
									((TileEntitySingularityPortal)tile).setDimensionID(dims[dims.length - 1]);
								}
							}
							
							// Shows new dimension ID to player.
							if (world.isRemote)
							{
								player.sendMessage(new TextComponentString("Dimension set to: " + ((TileEntitySingularityPortal)tile).getDimensionID()));
							}
							
							i = dims.length;
						}
					}
					
					System.out.print('\n');
					
					return EnumActionResult.SUCCESS;
				}
			}
		}
		
		return EnumActionResult.FAIL;
	}
	
	
	
	@Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
		ItemStack stack = player.getHeldItem(hand);
		
		if (!stack.hasTagCompound())
		{
			stack.setTagCompound(new NBTTagCompound());
			stack.getTagCompound().setInteger("mode", 0);
		}
		
		if (stack.getTagCompound() != null)
		{
			if (stack.getTagCompound().getInteger("mode") == 3)
			{
				stack.getTagCompound().setInteger("mode", stack.getTagCompound().getInteger("mode") - 4);
			}
		
			stack.getTagCompound().setInteger("mode", stack.getTagCompound().getInteger("mode") + 1);
			
		}
		
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
    }
	
	
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) 
	{
		super.addInformation(stack, player, tooltip, advanced);
		
		if (!stack.hasTagCompound())
		{
			stack.setTagCompound(new NBTTagCompound());
			stack.getTagCompound().setInteger("mode", 0);
		}
		
		tooltip.add("Used to manipulate blackhole objects");
		tooltip.add("");
		
		if (stack.getTagCompound().getInteger("mode") == 0)
			tooltip.add("X-Coord");
		
		if (stack.getTagCompound().getInteger("mode") == 1)
			tooltip.add("Y-Coord");
		
		if (stack.getTagCompound().getInteger("mode") == 2)
			tooltip.add("Z-Coord");
		
		if (stack.getTagCompound().getInteger("mode") == 3)
			tooltip.add("Manipulate");
	}
}
