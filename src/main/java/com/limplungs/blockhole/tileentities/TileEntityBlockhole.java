package com.limplungs.blockhole.tileentities;

import com.limplungs.blockhole.dimensions.DimensionList;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.capabilities.Capability;

public class TileEntityBlockhole extends TileEntity
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
	
}
