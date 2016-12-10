package com.limplungs.blockhole.items;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class ItemTuner extends ItemBasic{

	public ItemTuner(ItemData itemdata) 
	{
		super(itemdata);
		this.setMaxStackSize(1);
	}

	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		Block target = world.getBlockState(pos).getBlock();
		
		if (target != null && target instanceof Block)
		{
			if(world.isRemote && player.isSneaking())
			{
				player.addChatMessage(new TextComponentString(target.getUnlocalizedName()));
			}
		}
		return EnumActionResult.SUCCESS;
	}
}
