package com.limplungs.blockhole.tileentities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.capabilities.Capability;

public class TileEntityBlockholeWall extends TileEntity
{
	private int dimID = -999;
	private BlockPos teleportLocation = new BlockPos(0,0,0);
	private boolean transport = false;
	
	public TileEntityBlockholeWall()
	{
		
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) 
	{
		compound.setInteger("dimID", this.dimID);
		compound.setInteger("locateX", this.teleportLocation.getX());
		compound.setInteger("locateY", this.teleportLocation.getY());
		compound.setInteger("locateZ", this.teleportLocation.getZ());
		
		compound.setBoolean("transport", this.transport);
		
		return super.writeToNBT(compound);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) 
	{
		super.readFromNBT(compound);
		
		this.dimID = compound.getInteger("dimID");
		
		this.teleportLocation = new BlockPos(compound.getInteger("locateX"), compound.getInteger("locateY"), compound.getInteger("locateZ"));
		
		this.transport = compound.getBoolean("transport");
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
	
	void updateDimensionInfo()
	{
		if (dimID == -999 || (this.getTeleportLocation().getX() == 0 && this.getTeleportLocation().getY() == 0 && this.getTeleportLocation().getZ() == 0))
		{
			BlockPos up = new BlockPos(this.pos.getX(), this.pos.getY() + 1, this.pos.getZ());
			BlockPos down = new BlockPos(this.pos.getX(), this.pos.getY() - 1, this.pos.getZ());
			BlockPos east = new BlockPos(this.pos.getX() + 1, this.pos.getY(), this.pos.getZ());
			BlockPos west = new BlockPos(this.pos.getX() - 1, this.pos.getY(), this.pos.getZ());
			BlockPos north = new BlockPos(this.pos.getX(), this.pos.getY(), this.pos.getZ() + 1);
			BlockPos south = new BlockPos(this.pos.getX(), this.pos.getY(), this.pos.getZ() - 1);
			
			// up
			if (this.world.getTileEntity(up) instanceof TileEntityBlockholeWall
					&& ((TileEntityBlockholeWall)this.world.getTileEntity(up)).getDimensionID() != -999)
			{
				this.setDimensionID(((TileEntityBlockholeWall)this.world.getTileEntity(up)).getDimensionID());
				this.setTeleportLocation(((TileEntityBlockholeWall)this.world.getTileEntity(up)).getTeleportLocation());
			}
			
			// down
			else if (this.world.getTileEntity(down) instanceof TileEntityBlockholeWall
					&& ((TileEntityBlockholeWall)this.world.getTileEntity(down)).getDimensionID() != -999)
			{
				this.setDimensionID(((TileEntityBlockholeWall)this.world.getTileEntity(down)).getDimensionID());
				this.setTeleportLocation(((TileEntityBlockholeWall)this.world.getTileEntity(down)).getTeleportLocation());
			}
			
			// east
			else if (this.world.getTileEntity(east) instanceof TileEntityBlockholeWall
					&& ((TileEntityBlockholeWall)this.world.getTileEntity(east)).getDimensionID() != -999)
			{
				this.setDimensionID(((TileEntityBlockholeWall)this.world.getTileEntity(east)).getDimensionID());
				this.setTeleportLocation(((TileEntityBlockholeWall)this.world.getTileEntity(east)).getTeleportLocation());
			}
			
			// west
			else if (this.world.getTileEntity(west) instanceof TileEntityBlockholeWall
					&& ((TileEntityBlockholeWall)this.world.getTileEntity(west)).getDimensionID() != -999)
			{
				this.setDimensionID(((TileEntityBlockholeWall)this.world.getTileEntity(west)).getDimensionID());
				this.setTeleportLocation(((TileEntityBlockholeWall)this.world.getTileEntity(west)).getTeleportLocation());
			}
			
			// north
			else if (this.world.getTileEntity(north) instanceof TileEntityBlockholeWall
					&& ((TileEntityBlockholeWall)this.world.getTileEntity(north)).getDimensionID() != -999)
			{
				this.setDimensionID(((TileEntityBlockholeWall)this.world.getTileEntity(north)).getDimensionID());
				this.setTeleportLocation(((TileEntityBlockholeWall)this.world.getTileEntity(north)).getTeleportLocation());
			}
			
			// south
			else if (this.world.getTileEntity(south) instanceof TileEntityBlockholeWall
					&& ((TileEntityBlockholeWall)this.world.getTileEntity(south)).getDimensionID() != -999)
			{
				this.setDimensionID(((TileEntityBlockholeWall)this.world.getTileEntity(south)).getDimensionID());
				this.setTeleportLocation(((TileEntityBlockholeWall)this.world.getTileEntity(south)).getTeleportLocation());
			}
		}
	}

	public BlockPos getTeleportLocation() 
	{
		return this.teleportLocation;
	}
	
	public void setTeleportLocation(BlockPos tp)
	{
		this.teleportLocation = tp;
		this.markDirty();
	}


	public boolean getTransport() 
	{
		return this.transport;
	}
	
	public void setTransport(boolean transport)
	{
		this.transport = transport;
		this.markDirty();
	}
}
