package com.limplungs.blockhole;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class DoubleLinkedQueue 
{
	private Node headptr;
	private Node tailptr;
	private int size;
	
	public DoubleLinkedQueue()
	{
		headptr = new Node();
		headptr.block = null;
		headptr.next = null;
		headptr.back = null;
		tailptr = headptr;
		size = 0;
	}

	public Node insert(Block block)
	{
		Node temp = new Node();
		temp.block = block;
		
		// empty list
		if (headptr.block == null && tailptr.block == null)
		{
			temp.next = null;
			temp.back = null;
			headptr = temp;
			tailptr = headptr;
		}
		// one or more
		else if (headptr.block != null && tailptr.block != null)
		{
			temp.next = null;
			temp.back = tailptr;
			tailptr.next = temp;
			tailptr = temp;
		}
		
		size += 1;
		
		return tailptr;
	}

	public void writeNBT(NBTTagCompound compound) 
	{
		System.out.println("NODE DATA STARTING SAVE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		
		compound.setInteger("size", size);
		
		Node node = headptr;
		
		for (int i = 0; i < size; i++)
		{
			compound.setTag("block" + i, new ItemStack(node.block).writeToNBT(new NBTTagCompound()));
			System.out.println(node.block + "   .write.");
			node = node.next;
		}
		
		System.out.println("NODE DATA STOPPING SAVE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
	}
	
	public void readNBT(NBTTagCompound compound)
	{
		System.out.println("NODE DATA STARTING READ!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		
		size = compound.getInteger("size");
		
		for (int i = 0; i < size; i++)
		{
			NBTTagCompound tag = compound.getCompoundTag("block" + i);
			
			if(tag != null && !tag.hasNoTags())
			{
				insert(Block.getBlockFromItem(ItemStack.loadItemStackFromNBT(tag).getItem()));
				System.out.println("read a block.");
			}
		}
		
		System.out.println("NODE DATA STOPPING READ!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
	}
}

class Node
{
	Block block;
	Node next;
	Node back;
}