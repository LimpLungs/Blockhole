package com.limplungs.blockhole.items;

import java.util.List;

import org.lwjgl.input.Keyboard;

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
					//player.sendMessage(new TextComponentString("XYZ: " + tile.tp_x + " " + tile.tp_y + " " + tile.tp_z));
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
		
		tooltip.add("Used to manipulate blackhole objects. Mode[SHIFT], Controls[ALT]");
		
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))
		{
			if (stack.getTagCompound().getInteger("mode") == 0)
				tooltip.add("X-Coord:\n  - To be determined.");
			if (stack.getTagCompound().getInteger("mode") == 1)
				tooltip.add("Y-Coord:\n  - To be determined.");
			if (stack.getTagCompound().getInteger("mode") == 2)
				tooltip.add("Z-Coord:\n  - To be determined.");
			if (stack.getTagCompound().getInteger("mode") == 3)
				tooltip.add("Manipulate: Blockhole Walls\n   - switch transfer mode.");
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_LMENU) || Keyboard.isKeyDown(Keyboard.KEY_RMENU))
		{
			tooltip.add("Right click to change mode.");
			tooltip.add("Manipulate: Right Click flips transfer");
			tooltip.add("              Shift Right Click displays transfer.");
		}
	}
}
