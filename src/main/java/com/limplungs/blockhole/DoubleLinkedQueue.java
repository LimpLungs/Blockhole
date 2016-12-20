package com.limplungs.blockhole;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class DoubleLinkedQueue 
{
	private Node headptr;
	private Node tailptr;
	private int size;
	
	public DoubleLinkedQueue()
	{
		headptr = null;
		tailptr = headptr;
		size = 0;
	}

	public Node insert_back(ItemStack item)
	{
		Node temp = new Node();
		temp.item = item;
		
		// empty list
		if (headptr == null && tailptr == null)
		{
			temp.next = null;
			temp.back = null;
			headptr = temp;
			tailptr = headptr;
		}
		// one or more
		else if (headptr != null && tailptr != null)
		{
			temp.next = null;
			temp.back = tailptr;
			
			tailptr.next = temp;
			tailptr = temp;
		}
		
		size += 1;
		
		return tailptr;
	}
	
	public Node insert_front(ItemStack item)
	{
		Node temp = new Node();
		temp.item = item;
		
		// empty list
		if (headptr == null && tailptr == null)
		{
			temp.back = null;
			temp.next = null;
			tailptr = temp;
			headptr = tailptr;
		}
		// one or more
		else if (headptr != null && tailptr != null)
		{
			temp.next = headptr;
			temp.back = null;
			
			headptr.back = temp;
			headptr = temp;
		}
	
		size += 1;
		
		return headptr;
	}
	
	public ItemStack pop_front()
	{
		if (headptr == null)
		{
			return null;
		}
		
		Node temp = headptr;
		
		headptr = headptr.next;
		
		if (headptr != null)
		{
			headptr.back = null;
		}
		
		if (headptr == null)
		{
			tailptr = headptr;
		}
		
		size--;
		
		return temp.item;
	}
	
	public ItemStack pop_back()
	{
		if (tailptr == null)
		{
			return null;
		}
		
		Node temp = tailptr;
		
		tailptr = tailptr.back;
		
		if (tailptr != null)
		{
			tailptr.next = null;
		}
		
		if (tailptr == null)
		{
			headptr = tailptr;
		}
		
		size--;
		
		return temp.item;
	}
	
	public ItemStack getFront()
	{
		if (headptr == null)
		{
			return null;
		}
		
		return headptr.item;
	}
	

	
	public ItemStack getBack()
	{
		if (tailptr == null)
		{
			return null;
		}
		
		return tailptr.item;
	}
	

	public void writeNBT(NBTTagCompound compound) 
	{
		compound.setInteger("size", size);
		
		Node node = headptr;
		
		if (headptr != null)
		{
			for (int i = 0; i < getSize(); i++)
			{
				compound.setTag("item" + i, node.item.writeToNBT(new NBTTagCompound()));
			
				node = node.next;
			}
		}
	}
	
	public void readNBT(NBTTagCompound compound)
	{
		int tempsize = compound.getInteger("size");
		
		for (int i = 0; i < tempsize; i++)
		{
			NBTTagCompound tag = compound.getCompoundTag("item" + i);
			
			if(tag != null && !tag.hasNoTags())
			{
				insert_back(ItemStack.loadItemStackFromNBT(tag));
			}
		}
	}

	public int getSize() 
	{
		return size;
	}

	public ItemStack getStackAtNode(int i) 
	{
		if (headptr == null)
		{
			return null;
		}
		
		if (i > getSize())
		{
			return null;
		}
		
		Node temp = headptr;
		
		for (int j = 1; j < i; j++)
		{
			temp = temp.next;
		}
		
		return temp.item;
	}
}

class Node
{
	ItemStack item;
	Node next;
	Node back;
}