package com.limplungs.blockhole;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.limplungs.blockhole.lists.BlockList;
import com.limplungs.blockhole.lists.ItemList;

public class BlockholeRegisterEvents
{
	@SubscribeEvent
	public void registerBlocks(RegistryEvent.Register<Block> event) 
	{
		for (int i = 0; i < BlockList.BLOCKS.size(); i++)
		{
			event.getRegistry().register(BlockList.BLOCKS.get(i));
		}
	}
	
	@SubscribeEvent
	public void registerItemBlocks(RegistryEvent.Register<Item> event) 
	{
		for (int i = 0; i < BlockList.ITEMBLOCKS.size(); i++)
		{
			event.getRegistry().register(BlockList.ITEMBLOCKS.get(i));
		}
	}
	
	@SubscribeEvent
	public void registerItems(RegistryEvent.Register<Item> event) 
	{
		for (int i = 0; i < ItemList.ITEMS.size(); i++)
		{
			event.getRegistry().register(ItemList.ITEMS.get(i));
		}
	}
}
