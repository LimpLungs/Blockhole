package com.limplungs.blockhole;

import com.limplungs.blockhole.tileentities.TileEntityTeleporter;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class DoubleLinkedQueue 
{
	private Node headptr;
	private Node tailptr;
	private int size;
	TileEntityTeleporter tile;
	
	public DoubleLinkedQueue(TileEntityTeleporter tile)
	{
		headptr = null;
		tailptr = headptr;
		size = 0;
		
		this.tile = tile;
	}

	public Node insert_back(ItemStack item, NBTTagCompound compound)
	{
		Node temp = new Node();
		temp.item = item;
		temp.nbt = compound;
		
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
		
		this.tile.markDirty();
		
		return tailptr;
	}
	
	public Node insert_front(ItemStack item, NBTTagCompound compound)
	{
		Node temp = new Node();
		temp.item = item;
		temp.nbt = compound;
		
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
		
		this.tile.markDirty();
		
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
		
		if (temp.nbt != null)
		{
			temp.item.setTagCompound(temp.nbt);
		}
		
		this.tile.markDirty();
		
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
		
		if (temp.nbt != null)
		{
			temp.item.setTagCompound(temp.nbt);
		}
		
		this.tile.markDirty();
		
		return temp.item;
	}
	
	public ItemStack getFront()
	{
		if (headptr == null)
		{
			return null;
		}
		
		Node temp = headptr;

		if (temp.nbt != null)
		{
			temp.item.setTagCompound(temp.nbt);
		}
		
		return headptr.item;
	}
	

	
	public ItemStack getBack()
	{
		if (tailptr == null)
		{
			return null;
		}
		
		Node temp = tailptr;

		if (temp.nbt != null)
		{
			temp.item.setTagCompound(temp.nbt);
		}
		
		return temp.item;
	}
	

	/**
	 * 
	 * @param compound - NBTTagCompound used for storing queue NBT data for writing in the Tile Entity.
	 */
	public void writeNBT(NBTTagCompound compound) 
	{
		compound.setInteger("size", size);
		
		Node node = headptr;
		
		if (headptr != null)
		{
			for (int i = 0; i < getSize(); i++)
			{
				compound.setTag("item" + i, node.item.writeToNBT(new NBTTagCompound()));
				compound.setTag("nbt" + i, node.item.writeToNBT(new NBTTagCompound()));
			
				node = node.next;
			}
		}
	}
	
	/**
	 * 
	 * @param compound - NBTTagCompound used for storing queue NBT data for reading in the Tile Entity.
	 */
	public void readNBT(NBTTagCompound compound)
	{
		int tempsize = compound.getInteger("size");
		
		for (int i = 0; i < tempsize; i++)
		{
			NBTTagCompound itemtag = compound.getCompoundTag("item" + i);
			NBTTagCompound nbttag = compound.getCompoundTag("nbt" + i);
			
			if(itemtag != null && !itemtag.hasNoTags())
			{
				insert_back(ItemStack.loadItemStackFromNBT(itemtag), nbttag);
			}
		}
	}
	

	/**
	 * Gets the size of the queue.
	 * @return size - size of queue.
	 */
	public int getSize() 
	{
		return size;
	}

	
	/**
	 * 
	 * Used to retrieve the ItemStack at a specific node.
	 * 
	 * @param i - node you wish to remove stack from
	 * @return
	 */
	public ItemStack getStackAtNode(int i) 
	{
		if (getSize() == 0)
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
	
	/**
	 * 
	 * Use getStackAtNode(int i) for stack retrieval.
	 * 
	 * @param i - node you wish to remove stack from
	 * @return
	 */
	@Deprecated
	public Node getNodeAtNode(int i) 
	{
		if (getSize() == 0)
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
		
		return temp;
	}
	
	/**
	 * 
	 * NOT FULLY CODED, DO NOT USE!
	 * Currently not useful, but changes may occur.
	 * 
	 * @param i - node you wish to remove stack from
	 * @return
	 */
	@Deprecated
	public ItemStack removeStackAtNode(int i)
	{
		Node remove = this.getNodeAtNode(i);
		
		
		return remove.item;
	}
}

class Node
{
	ItemStack item = null;
	NBTTagCompound nbt = null;
	Node next = null;
	Node back = null;
}