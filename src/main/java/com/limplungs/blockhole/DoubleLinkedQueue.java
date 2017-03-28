package com.limplungs.blockhole;

import com.limplungs.blockhole.tileentities.TileEntityTeleporter;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class DoubleLinkedQueue 
{
	private Node headptr;
	private Node tailptr;
	private int size;
	private TileEntityTeleporter tile;
	
	public DoubleLinkedQueue(TileEntityTeleporter tile)
	{
		this.headptr = null;
		this.tailptr = null;
		this.size    = 0;
		this.tile    = tile;
	}
	
	
	public ItemStack insert_front(ItemStack data)
	{
		this.tile.markDirty();
		
		if (headptr == null && tailptr == null)
		{
			headptr = new Node(data, null, null);
			tailptr = headptr;
		}
		else
		{
			Node temp = headptr;
			
			headptr = new Node(data, temp, null);
		}
		
		this.addSize();
		
		return headptr.getData();
	}

	
	
	
	public ItemStack insert_back(ItemStack data)
	{
		this.tile.markDirty();

		if (tailptr == null && headptr == null)
		{
			tailptr = new Node(data, null, null);
			headptr = tailptr;
		}
		else
		{
			Node temp = tailptr;
			
			tailptr = new Node(data, null, temp);
		}
		
		this.addSize();
		
		return tailptr.getData();
	}
	
	
	
	
	public ItemStack pop_front()
	{
		if (headptr == null && tailptr == null)
			return ItemStack.EMPTY;

		this.tile.markDirty();
		
		Node temp = headptr;
		
		if (headptr == tailptr)
		{
			headptr = null;
			tailptr = null;
		}
		else
		{
			headptr = headptr.next;
		}
		
		this.subSize();
		
		return temp.getData();
	}
	
	
	
	
	public ItemStack pop_back()
	{
		if (tailptr == null && headptr == null)
			return ItemStack.EMPTY;

		this.tile.markDirty();
		
		Node temp = tailptr;
		
		if (tailptr == headptr)
		{
			tailptr = null;
			headptr = null;
		}
		else
		{
			tailptr = tailptr.back;
		}
		
		this.subSize();
		
		return temp.getData();
	}
	
	
	
	
	public ItemStack getFront()
	{
		return this.headptr.getData();
	}
	

	
	
	public ItemStack getBack()
	{
		return this.tailptr.getData();
	}
	

	
	public void writeNBT(NBTTagCompound compound) 
	{
		this.tile.markDirty();
		
		
	}
	
	
	
	public void readNBT(NBTTagCompound compound)
	{
		this.tile.markDirty();
		
		
	}
	

	/**
	 * Gets the size of the queue.
	 * @return size - size of queue.
	 */
	public int getSize() 
	{
		return size;
	}
	
	private void addSize()
	{
		this.tile.markDirty();
		
		this.size += 1;
	}
	
	private void subSize()
	{
		this.tile.markDirty();
		
		this.size -= 1;
	}
	
}

class Node
{
	public NBTTagCompound nbt = null;
	private ItemStack data     = ItemStack.EMPTY;
	public Node next          = null;
	public Node back          = null;
	
	public Node(ItemStack data, Node next, Node back) 
	{
		this.nbt  = data.getTagCompound();
		this.data = data;
		this.next = next;
		this.back = back;
	}
	
	public ItemStack getData()
	{
		return this.data;
	}
}