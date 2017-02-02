package com.limplungs.blockhole.tileentities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.capabilities.Capability;

public class TileEntityBlockholeWall extends TileEntity
{
	private int dimID = -999;
	
	public TileEntityBlockholeWall()
	{
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) 
	{
		compound.setInteger("dimID", this.dimID);
		
		return super.writeToNBT(compound);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) 
	{
		super.readFromNBT(compound);
		
		this.dimID = compound.getInteger("dimID");
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
      
      if(worldObj.isRemote)
      {
        readFromNBT(pkt.getNbtCompound());
      }
    }

	public int getDimensionID() 
	{
		return this.dimID;
	}

	public void setDimensionID(int id) 
	{
		this.dimID = id;
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
