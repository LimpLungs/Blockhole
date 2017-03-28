package com.limplungs.blockhole.tileentities;

import com.limplungs.blockhole.DoubleLinkedQueue;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.capabilities.Capability;

public class TileEntityTeleporter extends TileEntity implements IInventory
{
	private DoubleLinkedQueue queue;
	
	public TileEntityTeleporter()
	{
		queue = new DoubleLinkedQueue(this);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) 
	{
		this.getQueue().writeNBT(compound);
		
		return super.writeToNBT(compound);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) 
	{
		super.readFromNBT(compound);
		
		this.getQueue().readNBT(compound);
	}
	
	public void setCoordinate(int xyz, int value)
	{
		this.markDirty();
	}
    
    @Override
    public void handleUpdateTag(NBTTagCompound tag) 
    {
    	super.handleUpdateTag(tag);
    }
    
    
    @Override
    public NBTTagCompound getUpdateTag()
    {
      return this.writeToNBT(super.getUpdateTag());
    }
    
    @Override
    public final SPacketUpdateTileEntity getUpdatePacket()
    {
      NBTTagCompound tag = new NBTTagCompound();
      this.writeToNBT(tag);    
      return new SPacketUpdateTileEntity(getPos(), 0, tag);
    }
    
    @Override
    public final void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt)
    {
      super.onDataPacket(net, pkt);
      
      if(world.isRemote)
      {
        this.readFromNBT(pkt.getNbtCompound());
      }
    }

	@Override
	public int getSizeInventory() 
	{
		return 0;
	}

	@Override
	public ItemStack getStackInSlot(int index) 
	{
		this.markDirty();

		return this.getQueue().getBack();
	}

	@Override
	public ItemStack decrStackSize(int index, int count) 
	{
		this.markDirty();
	
		return this.getQueue().pop_back();
	}

	/**
	 * This can only remove from the rear.
	 */
	@Override
	public ItemStack removeStackFromSlot(int index) 
	{
		this.markDirty();
		
		return this.getQueue().pop_back();
	}

	/**
	 * This can only insert to the rear.
	 */
	@Override
	public void setInventorySlotContents(int index, ItemStack stack) 
	{
		
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) 
	{
		return false;
	}

	@Override
	public int getInventoryStackLimit() 
	{
		return 0;
	}

	@Override
	public void markDirty() 
	{
		super.markDirty();
	}

	@Override
	public void openInventory(EntityPlayer player) 
	{
		
	}

	@Override
	public void closeInventory(EntityPlayer player) 
	{
		
	}

	@Override
	public int getField(int id) 
	{
		return 0;
	}

	@Override
	public void setField(int id, int value) 
	{
		
	}

	@Override
	public int getFieldCount() 
	{
		return 0;
	}

	@Override
	public String getName() 
	{
		return null;
	}

	@Override
	public boolean hasCustomName() 
	{
		return false;
	}

	@Override
	public ITextComponent getDisplayName() 
	{
		return null;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) 
	{
		return false;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) 
	{
		return null;
	}

	@Override
	public NBTTagCompound serializeNBT() 
	{
		return null;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) 
	{
		
	}

	@Override
	public void clear() 
	{
		
	}

	public DoubleLinkedQueue getQueue() 
	{
		return this.queue;
	}

	public void setQueue(DoubleLinkedQueue queue) 
	{
		this.queue = queue;
		this.markDirty();
	}

	@Override
	public boolean isEmpty() 
	{
		return this.getQueue().getSize() == 0 ? true : false;
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player) 
	{
		return false;
	}
	
}
