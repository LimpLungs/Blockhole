package com.limplungs.blockhole.items;

import com.limplungs.blockhole.blocks.BlockBlockholeWall;
import com.limplungs.blockhole.blocks.BlockTeleporter;
import com.limplungs.blockhole.tileentities.TileEntityBlockholeWall;
import com.limplungs.blockhole.tileentities.TileEntityTeleporter;

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
		
		if (stack.getTagCompound() != null && target != null && target instanceof BlockTeleporter)
		{
			if (world.getTileEntity(pos) != null && world.getTileEntity(pos) instanceof TileEntityTeleporter)
			{
				TileEntityTeleporter tile = (TileEntityTeleporter) world.getTileEntity(pos); 
				
				if (stack.getTagCompound().getInteger("mode") == 0)
				{
					if (player.isSneaking())
					{
						// subtract X
						tile.setCoordinate(0, -1);
					}
					else
					{
						// add X
						tile.setCoordinate(0, 1);
					}
				}
				if (stack.getTagCompound().getInteger("mode") == 1)
				{
					if (player.isSneaking())
					{
						// subtract Y
						tile.setCoordinate(1, -1);
					}
					else
					{
						// add Y
						tile.setCoordinate(1, 1);
					}
				}
				if (stack.getTagCompound().getInteger("mode") == 2)
				{
					if (player.isSneaking())
					{
						// subtract Z
						tile.setCoordinate(2, -1);
					}
					else
					{
						// add Z
						tile.setCoordinate(2, 1);
					}
				}
			
				if (world.isRemote)
				{
					player.sendMessage(new TextComponentString("XYZ: " + tile.tp_x + " " + tile.tp_y + " " + tile.tp_z));
				}
			}
			
			return EnumActionResult.SUCCESS;
		}
		
		if (stack.getTagCompound() != null && target != null && target instanceof BlockBlockholeWall)
		{
			if (world.getTileEntity(pos) != null && world.getTileEntity(pos) instanceof TileEntityBlockholeWall)
			{
				TileEntityBlockholeWall tile = (TileEntityBlockholeWall) world.getTileEntity(pos);
				
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
			
			if (world.isRemote)
			{
				if (stack.getTagCompound().getInteger("mode") == 0)
				{
					player.sendMessage(new TextComponentString("MODE: X - COORDINATE"));
				}
				if (stack.getTagCompound().getInteger("mode") == 1)
				{
					player.sendMessage(new TextComponentString("MODE: Y - COORDINATE"));
				}
				if (stack.getTagCompound().getInteger("mode") == 2)
				{
					player.sendMessage(new TextComponentString("MODE: Z - COORDINATE"));
				}
				if (stack.getTagCompound().getInteger("mode") == 3)
				{
					player.sendMessage(new TextComponentString("MODE: Manipulate"));
				}
			}
		}
		
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
    }
}
