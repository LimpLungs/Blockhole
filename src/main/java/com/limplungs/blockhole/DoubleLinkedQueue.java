package com.limplungs.blockhole;

import com.limplungs.blockhole.tileentities.TileEntitySingularityStorage;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class DoubleLinkedQueue 
{
	private Node headptr;
	private Node tailptr;
	private int size;
	private TileEntitySingularityStorage tile;
	
	public DoubleLinkedQueue(TileEntitySingularityStorage tile)
	{
		this.headptr = null;
		this.tailptr = null;
		this.size    = 0;
		this.tile    = tile;
	}
	
	
	public ItemStack insert_front(ItemStack data)
	{
		this.tile.markDirty();
		
		if (this.headptr == null && this.tailptr == null)
		{
			this.headptr = new Node(data, null, null);
			this.tailptr = this.headptr;
		}
		else
		{
			Node temp = this.headptr;
			
			this.headptr = new Node(data, temp, null);
		}
		
		this.addSize();
		
		return this.headptr.getData();
	}

	
	
	
	public ItemStack insert_back(ItemStack data)
	{
		this.tile.markDirty();

		if (this.tailptr == null && this.headptr == null)
		{
			this.tailptr = new Node(data, null, null);
			this.headptr = this.tailptr;
		}
		else
		{
			Node temp = this.tailptr;
			
			this.tailptr = new Node(data, null, temp);
		}
		
		this.addSize();
		
		return this.tailptr.getData();
	}
	
	
	
	
	public ItemStack pop_front()
	{
		if (this.headptr == null && this.tailptr == null)
			return ItemStack.EMPTY;

		this.tile.markDirty();
		
		Node temp = this.headptr;
		
		if (this.headptr == this.tailptr)
		{
			this.headptr = null;
			this.tailptr = null;
		}
		else
		{
			this.headptr = this.headptr.next;
		}
		
		this.subSize();
		
		return temp.getData();
	}
	
	
	
	
	public ItemStack pop_back()
	{
		if (this.tailptr == null && this.headptr == null)
			return ItemStack.EMPTY;

		this.tile.markDirty();
		
		Node temp = this.tailptr;
		
		if (this.tailptr == this.headptr)
		{
			this.tailptr = null;
			this.headptr = null;
		}
		else
		{
			this.tailptr = this.tailptr.back;
		}
		
		this.subSize();
		
		return temp.getData();
	}
	
	
	
	
	public ItemStack getFront()
	{
		if (this.tailptr == null && this.headptr == null)
			return ItemStack.EMPTY;
		
		else
			return this.headptr.getData();
	}
	

	
	
	public ItemStack getBack()
	{
		if (this.tailptr == null && this.headptr == null)
			return ItemStack.EMPTY;
		
		else
			return this.tailptr.getData();
	}
	
	
	

	/**
	 * 
	 * @param compound - NBTTagCompound used for storing queue NBT data for writing in the Tile Entity.
	 */
	public void writeNBT(NBTTagCompound compound) 
	{
		if (this.headptr != null)
		{
			compound.setInteger("size", this.getSize());
			
			Node node = this.headptr;
			
			for (int i = 0; i < this.getSize(); i++)
			{
				compound.setTag("item" + i, node.getData().writeToNBT(new NBTTagCompound()));
			
				node = node.next;
			}
		}
		else
		{
			compound.setInteger("size", this.getSize());
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
			
			if(itemtag != null && !itemtag.hasNoTags())
			{
				insert_back(new ItemStack(itemtag));
			}
		}
	}
	
	

	/**
	 * Gets the size of the queue.
	 * @return size - size of queue.
	 */
	public int getSize() 
	{
		return this.size;
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