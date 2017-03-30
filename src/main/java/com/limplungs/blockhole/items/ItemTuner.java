package com.limplungs.blockhole.items;

import java.util.List;

import com.limplungs.blockhole.blocks.BlockSingularityDimensionWall;
import com.limplungs.blockhole.tileentities.TileEntitySingularityDimensionWall;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

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
		
		if (stack.getTagCompound() != null && target != null && target instanceof BlockSingularityDimensionWall)
		{
			if (world.getTileEntity(pos) != null && world.getTileEntity(pos) instanceof TileEntitySingularityDimensionWall)
			{
				TileEntitySingularityDimensionWall tile = (TileEntitySingularityDimensionWall) world.getTileEntity(pos);
				
				if (stack.getTagCompound().getInteger("mode") == 3)
				{
					if (!player.isSneaking())
					{
						tile.flipTransport();
					}

					if (world.isRemote)
					{
						player.sendMessage(new TextComponentString("Interdimensional Transport: " + tile.getTransport()));
					}
					
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
