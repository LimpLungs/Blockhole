package com.limplungs.blockhole.tileentities;

import com.limplungs.blockhole.DoubleLinkedQueue;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public class TileEntityTeleporter extends TileEntity
{
	public int tp_x = this.getPos().getX();
	public int tp_y = this.getPos().getY();  
	public int tp_z = this.getPos().getZ();
	public BlockPos loc = new BlockPos(0,0,0);
	public DoubleLinkedQueue queue = new DoubleLinkedQueue();
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) 
	{
		compound.setInteger("tp_x", this.tp_x);
		compound.setInteger("tp_y", this.tp_y);
		compound.setInteger("tp_z", this.tp_z);
		compound.setInteger("loc_x", loc.getX());
		compound.setInteger("loc_y", loc.getY());
		compound.setInteger("loc_z", loc.getZ());
		
		queue.writeNBT(compound);
		
		return super.writeToNBT(compound);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) 
	{
		super.readFromNBT(compound);
		
		tp_x = compound.getInteger("tp_x");
		tp_y = compound.getInteger("tp_y");
		tp_z = compound.getInteger("tp_z");
		loc = new BlockPos(compound.getInteger("loc_x"), compound.getInteger("loc_y"), compound.getInteger("loc_z"));
		
		queue.readNBT(compound);
	}
	
	public void setCoordinate(int xyz, int value)
	{
		if (loc.getX() != pos.getX() || loc.getY() != pos.getY() || loc.getZ() != pos.getZ())
		{
			tp_x = this.getPos().getX();
			tp_y = this.getPos().getY();
			tp_z = this.getPos().getZ();
			loc  = this.getPos();
		}
		
		if (xyz == 0)
		{
			tp_x += value;
		}
		else if (xyz == 1)
		{
			tp_y += value;
		}
		else if (xyz == 2)
		{
			tp_z += value;
		}
		
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
}
