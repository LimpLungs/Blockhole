package com.limplungs.blockhole.tileentities;

import com.limplungs.blockhole.dimensions.DimensionList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.capabilities.Capability;

public class TileEntityBlockhole extends TileEntity implements IInventory
{
	private int dimID = -999;
	
	public TileEntityBlockhole()
	{
		
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) 
	{
		if (dimID != -999 && DimensionManager.isDimensionRegistered(dimID))
		{
			compound.setInteger("dimID", this.dimID);
		}
		
		return super.writeToNBT(compound);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) 
	{
		super.readFromNBT(compound);
		
		dimID = compound.getInteger("dimID");
		
		if (dimID != -999 && !DimensionManager.isDimensionRegistered(dimID))
		{
			DimensionManager.registerDimension(dimID, DimensionType.getById(DimensionList.SINGULARITY_ID));
		}
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
      writeToNBT(tag);    
      return new SPacketUpdateTileEntity(getPos(), 0, tag);
    }
    
    @Override
    public final void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt)
    {
      super.onDataPacket(net, pkt);
      
      if(world.isRemote)
      {
        readFromNBT(pkt.getNbtCompound());
      }
    }

	public int getDimensionID() 
	{
		return dimID;
	}

	public void setDimensionID(int id) 
	{
		dimID = id;
		this.markDirty();
	}

	@Override
	public void markDirty() 
	{
		super.markDirty();
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
	
	
	
	
	
	/**
	 *    IInventory Implementation!  
	 */
	
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

	
	// start of implementation
	
	@Override
	public int getSizeInventory() 
	{
		// if IInventory or ISidedInventory is adjacent, check it's function.
		
		if (this.dimID != -999)
		{
			
		}
	}

	@Override
	public boolean isEmpty()
	{
		
	}

	@Override
	public ItemStack getStackInSlot(int index) 
	{
	
	}

	@Override
	public ItemStack decrStackSize(int index, int count) 
	{
		
	}

	@Override
	public ItemStack removeStackFromSlot(int index) 
	{
		
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) 
	{
		
	}

	@Override
	public int getInventoryStackLimit() 
	{
		
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) 
	{
		
	}

	// stop of implementation
	

	@Override
	public boolean isUsableByPlayer(EntityPlayer player) 
	{
		return false;
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
	public void clear() 
	{
		
	}
	
}
