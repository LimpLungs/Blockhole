package com.limplungs.blockhole.tileentities;

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
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.capabilities.Capability;

public class TileEntityBlockholeWall extends TileEntity implements ISidedInventory
{
	private int dimID = -999;
	
	private BlockPos blockholeLocation = new BlockPos(0,0,0);
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
		
		compound.setInteger("blockX", this.blockholeLocation.getX());
		compound.setInteger("blockY", this.blockholeLocation.getY());
		compound.setInteger("blockZ", this.blockholeLocation.getZ());
		
		compound.setBoolean("transport", this.transport);
		
		return super.writeToNBT(compound);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) 
	{
		super.readFromNBT(compound);
		
		this.dimID = compound.getInteger("dimID");
		
		this.teleportLocation = new BlockPos(compound.getInteger("locateX"), compound.getInteger("locateY"), compound.getInteger("locateZ"));
		this.blockholeLocation = new BlockPos(compound.getInteger("blockX"), compound.getInteger("blockY"), compound.getInteger("blockZ"));
		
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
	
	// Might be useless now with new code
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

	public BlockPos getBlockholeLocation() 
	{
		return this.blockholeLocation;
	}
	
	public void setBlockholeLocation(BlockPos pos)
	{
		this.blockholeLocation = pos;
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
	
	public void flipTransport()
	{
		this.setTransport(!this.getTransport());
		
		int x = this.pos.getX();
		int y = this.pos.getY();
		int z = this.pos.getZ();
		
		if (x == 0 || x == 15)
		{
			for (int j = 1; j < 15; j++)
			{
				for (int k = 1; k < 15; k++)
				{
					TileEntity tile = this.world.getTileEntity(new BlockPos(x,j,k));
					
					if (tile != null && tile != this && tile instanceof TileEntityBlockholeWall)
					{
						TileEntityBlockholeWall wall = (TileEntityBlockholeWall)tile;
						
						if (wall.getTransport())
						{
							wall.setTransport(!wall.getTransport());;
						}
					}
				}
			}
		}
		
		if (y == 0 || y == 15)
		{
			for (int i = 1; i < 15; i++)
			{
				for (int k = 1; k < 15; k++)
				{
					TileEntity tile = this.world.getTileEntity(new BlockPos(i,y,k));

					if (tile != null && tile != this && tile instanceof TileEntityBlockholeWall)
					{
						TileEntityBlockholeWall wall = (TileEntityBlockholeWall)tile;

						if (wall.getTransport())
						{
							wall.setTransport(!wall.getTransport());;
						}
					}
				}
			}
		}
		
		if (z == 0 || z == 15)
		{
			for (int i = 1; i < 15; i++)
			{
				for (int j = 1; j < 15; j++)
				{
					TileEntity tile = this.world.getTileEntity(new BlockPos(i,j,z));

					if (tile != null && tile != this && tile instanceof TileEntityBlockholeWall)
					{
						TileEntityBlockholeWall wall = (TileEntityBlockholeWall)tile;

						if (wall.getTransport())
						{
							wall.setTransport(!wall.getTransport());;
						}
					}
				}
			}
		}
		
		this.markDirty();
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
		if (!this.transport)
		{
			return 0;
		}
		
		// if IInventory or ISidedInventory is adjacent, check it's function.
		TileEntity blockhole = DimensionManager.getWorld(this.dimID).getTileEntity(this.blockholeLocation);
		
		if ( blockhole != null && blockhole instanceof TileEntityBlockhole && DimensionManager.getWorld(((TileEntityBlockhole)blockhole).getDimensionID()) == this.world)
		{
			int x1 = this.blockholeLocation.getX();
			int y1 = this.blockholeLocation.getY();
			int z1 = this.blockholeLocation.getZ();
					
			if (this.pos.getX() == 0)
				x1 -= 1;
			if (this.pos.getX() == 15)
				x1 += 1;
					
			if (this.pos.getY() == 0)
				y1 -= 1;
			if (this.pos.getY() == 15)
				y1 += 1;
					
			if (this.pos.getZ() == 0)
				z1 -= 1;
			if (this.pos.getZ() == 15)
				z1 += 1;
			
			BlockPos target = new BlockPos(x1,y1,z1);
			
			TileEntity tile = DimensionManager.getWorld(this.dimID).getTileEntity(target);
					
			if (tile != null && tile instanceof IInventory)
			{
				IInventory inv = (IInventory)tile;
						
				if (inv instanceof ISidedInventory)
				{
					ISidedInventory side = (ISidedInventory)tile;
							
					return side.getSizeInventory();
				}
				else
				{
					return inv.getSizeInventory();
				}
			}
			
			return 0;
		}
		
		return 0;
	}

	@Override

	public boolean isEmpty() 

	{
		if (!this.transport)
		{
			return false;
		}
		
		// if IInventory or ISidedInventory is adjacent, check it's function.
		TileEntity blockhole = DimensionManager.getWorld(this.dimID).getTileEntity(this.blockholeLocation);
		
		if ( blockhole != null && blockhole instanceof TileEntityBlockhole && DimensionManager.getWorld(((TileEntityBlockhole)blockhole).getDimensionID()) == this.world)
		{
			int x1 = this.blockholeLocation.getX();
			int y1 = this.blockholeLocation.getY();
			int z1 = this.blockholeLocation.getZ();
					
			if (this.pos.getX() == 0)
				x1 -= 1;
			if (this.pos.getX() == 15)
				x1 += 1;
					
			if (this.pos.getY() == 0)
				y1 -= 1;
			if (this.pos.getY() == 15)
				y1 += 1;
					
			if (this.pos.getZ() == 0)
				z1 -= 1;
			if (this.pos.getZ() == 15)
				z1 += 1;
			
			BlockPos target = new BlockPos(x1,y1,z1);
			
			TileEntity tile = DimensionManager.getWorld(this.dimID).getTileEntity(target);
					
			if (tile != null && tile instanceof IInventory)
			{
				IInventory inv = (IInventory)tile;
						
				if (inv instanceof ISidedInventory)
				{
					ISidedInventory side = (ISidedInventory)tile;
							
					return side.isEmpty();
				}
				else
				{
					return inv.isEmpty();
				}
			}
			
			return false;
		}
		
		return false;
	}

	@Override
	public ItemStack getStackInSlot(int index) 
	{
		if (!this.transport)
		{
			return null;
		}
		
		// if IInventory or ISidedInventory is adjacent, check it's function.
		TileEntity blockhole = DimensionManager.getWorld(this.dimID).getTileEntity(this.blockholeLocation);
		
		if ( blockhole != null && blockhole instanceof TileEntityBlockhole && DimensionManager.getWorld(((TileEntityBlockhole)blockhole).getDimensionID()) == this.world)
		{
			int x1 = this.blockholeLocation.getX();
			int y1 = this.blockholeLocation.getY();
			int z1 = this.blockholeLocation.getZ();
					
			if (this.pos.getX() == 0)
				x1 -= 1;
			if (this.pos.getX() == 15)
				x1 += 1;
					
			if (this.pos.getY() == 0)
				y1 -= 1;
			if (this.pos.getY() == 15)
				y1 += 1;
					
			if (this.pos.getZ() == 0)
				z1 -= 1;
			if (this.pos.getZ() == 15)
				z1 += 1;
			
			BlockPos target = new BlockPos(x1,y1,z1);
			
			TileEntity tile = DimensionManager.getWorld(this.dimID).getTileEntity(target);
					
			if (tile != null && tile instanceof IInventory)
			{
				IInventory inv = (IInventory)tile;
						
				if (inv instanceof ISidedInventory)
				{
					ISidedInventory side = (ISidedInventory)tile;
							
					return side.getStackInSlot(index);
				}
				else
				{
					return inv.getStackInSlot(index);
				}
			}

			return ItemStack.EMPTY;
		}
		
		return ItemStack.EMPTY;
	}

	@Override
	public ItemStack decrStackSize(int index, int count) 
	{
		if (!this.transport)
		{
			return null;
		}
		
		// if IInventory or ISidedInventory is adjacent, check it's function.
		TileEntity blockhole = DimensionManager.getWorld(this.dimID).getTileEntity(this.blockholeLocation);
		
		if ( blockhole != null && blockhole instanceof TileEntityBlockhole && DimensionManager.getWorld(((TileEntityBlockhole)blockhole).getDimensionID()) == this.world)
		{
			int x1 = this.blockholeLocation.getX();
			int y1 = this.blockholeLocation.getY();
			int z1 = this.blockholeLocation.getZ();
					
			if (this.pos.getX() == 0)
				x1 -= 1;
			if (this.pos.getX() == 15)
				x1 += 1;
					
			if (this.pos.getY() == 0)
				y1 -= 1;
			if (this.pos.getY() == 15)
				y1 += 1;
					
			if (this.pos.getZ() == 0)
				z1 -= 1;
			if (this.pos.getZ() == 15)
				z1 += 1;
			
			BlockPos target = new BlockPos(x1,y1,z1);
			
			TileEntity tile = DimensionManager.getWorld(this.dimID).getTileEntity(target);
					
			if (tile != null && tile instanceof IInventory)
			{
				IInventory inv = (IInventory)tile;
						
				if (inv instanceof ISidedInventory)
				{
					ISidedInventory side = (ISidedInventory)tile;
							
					return side.decrStackSize(index, count);
				}
				else
				{
					return inv.decrStackSize(index, count);
				}
			}

			return ItemStack.EMPTY;
		}

		return ItemStack.EMPTY;
	}

	@Override
	public ItemStack removeStackFromSlot(int index) 
	{
		if (!this.transport)
		{
			return null;
		}
		
		// if IInventory or ISidedInventory is adjacent, check it's function.
		TileEntity blockhole = DimensionManager.getWorld(this.dimID).getTileEntity(this.blockholeLocation);
		
		if ( blockhole != null && blockhole instanceof TileEntityBlockhole && DimensionManager.getWorld(((TileEntityBlockhole)blockhole).getDimensionID()) == this.world)
		{
			int x1 = this.blockholeLocation.getX();
			int y1 = this.blockholeLocation.getY();
			int z1 = this.blockholeLocation.getZ();
					
			if (this.pos.getX() == 0)
				x1 -= 1;
			if (this.pos.getX() == 15)
				x1 += 1;
					
			if (this.pos.getY() == 0)
				y1 -= 1;
			if (this.pos.getY() == 15)
				y1 += 1;
					
			if (this.pos.getZ() == 0)
				z1 -= 1;
			if (this.pos.getZ() == 15)
				z1 += 1;
			
			BlockPos target = new BlockPos(x1,y1,z1);
			
			TileEntity tile = DimensionManager.getWorld(this.dimID).getTileEntity(target);
					
			if (tile != null && tile instanceof IInventory)
			{
				IInventory inv = (IInventory)tile;
						
				if (inv instanceof ISidedInventory)
				{
					ISidedInventory side = (ISidedInventory)tile;
							
					return side.removeStackFromSlot(index);
				}
				else
				{
					return inv.removeStackFromSlot(index);
				}
			}

			return ItemStack.EMPTY;
		}

		return ItemStack.EMPTY;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) 
	{
		if (!this.transport)
		{
			return;
		}
		
		// if IInventory or ISidedInventory is adjacent, check it's function.
		TileEntity blockhole = DimensionManager.getWorld(this.dimID).getTileEntity(this.blockholeLocation);
		
		if ( blockhole != null && blockhole instanceof TileEntityBlockhole && DimensionManager.getWorld(((TileEntityBlockhole)blockhole).getDimensionID()) == this.world)
		{
			int x1 = this.blockholeLocation.getX();
			int y1 = this.blockholeLocation.getY();
			int z1 = this.blockholeLocation.getZ();
					
			if (this.pos.getX() == 0)
				x1 -= 1;
			if (this.pos.getX() == 15)
				x1 += 1;
					
			if (this.pos.getY() == 0)
				y1 -= 1;
			if (this.pos.getY() == 15)
				y1 += 1;
					
			if (this.pos.getZ() == 0)
				z1 -= 1;
			if (this.pos.getZ() == 15)
				z1 += 1;
			
			BlockPos target = new BlockPos(x1,y1,z1);
			
			TileEntity tile = DimensionManager.getWorld(this.dimID).getTileEntity(target);
					
			if (tile != null && tile instanceof IInventory)
			{
				IInventory inv = (IInventory)tile;
						
				if (inv instanceof ISidedInventory)
				{
					ISidedInventory side = (ISidedInventory)tile;
							
					side.setInventorySlotContents(index, stack);
				}
				else
				{
					inv.setInventorySlotContents(index, stack);
				}
			}
		}
	}

	@Override
	public int getInventoryStackLimit() 
	{
		if (!this.transport)
		{
			return 0;
		}
		
		// if IInventory or ISidedInventory is adjacent, check it's function.
		TileEntity blockhole = DimensionManager.getWorld(this.dimID).getTileEntity(this.blockholeLocation);
		
		if ( blockhole != null && blockhole instanceof TileEntityBlockhole && DimensionManager.getWorld(((TileEntityBlockhole)blockhole).getDimensionID()) == this.world)
		{
			int x1 = this.blockholeLocation.getX();
			int y1 = this.blockholeLocation.getY();
			int z1 = this.blockholeLocation.getZ();
					
			if (this.pos.getX() == 0)
				x1 -= 1;
			if (this.pos.getX() == 15)
				x1 += 1;
					
			if (this.pos.getY() == 0)
				y1 -= 1;
			if (this.pos.getY() == 15)
				y1 += 1;
					
			if (this.pos.getZ() == 0)
				z1 -= 1;
			if (this.pos.getZ() == 15)
				z1 += 1;
			
			BlockPos target = new BlockPos(x1,y1,z1);
			
			TileEntity tile = DimensionManager.getWorld(this.dimID).getTileEntity(target);
					
			if (tile != null && tile instanceof IInventory)
			{
				IInventory inv = (IInventory)tile;
						
				if (inv instanceof ISidedInventory)
				{
					ISidedInventory side = (ISidedInventory)tile;
							
					return side.getInventoryStackLimit();
				}
				else
				{
					return inv.getInventoryStackLimit();
				}
			}
			
			return 0;
		}
		
		return 0;
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) 
	{
		if (!this.transport)
		{
			return false;
		}
		
		// if IInventory or ISidedInventory is adjacent, check it's function.
		TileEntity blockhole = DimensionManager.getWorld(this.dimID).getTileEntity(this.blockholeLocation);
		
		if ( blockhole != null && blockhole instanceof TileEntityBlockhole && DimensionManager.getWorld(((TileEntityBlockhole)blockhole).getDimensionID()) == this.world)
		{
			int x1 = this.blockholeLocation.getX();
			int y1 = this.blockholeLocation.getY();
			int z1 = this.blockholeLocation.getZ();
					
			if (this.pos.getX() == 0)
				x1 -= 1;
			if (this.pos.getX() == 15)
				x1 += 1;
					
			if (this.pos.getY() == 0)
				y1 -= 1;
			if (this.pos.getY() == 15)
				y1 += 1;
					
			if (this.pos.getZ() == 0)
				z1 -= 1;
			if (this.pos.getZ() == 15)
				z1 += 1;
			
			BlockPos target = new BlockPos(x1,y1,z1);
			
			TileEntity tile = DimensionManager.getWorld(this.dimID).getTileEntity(target);
					
			if (tile != null && tile instanceof IInventory)
			{
				IInventory inv = (IInventory)tile;
						
				if (inv instanceof ISidedInventory)
				{
					ISidedInventory side = (ISidedInventory)tile;
							
					return side.isItemValidForSlot(index, stack);
				}
				else
				{
					return inv.isItemValidForSlot(index, stack);
				}
			}
			
			return false;
		}
		
		return false;
	}

	@Override
	public int[] getSlotsForFace(EnumFacing inputSide) 
	{
		if (!this.transport)
		{
			return new int[0];
		}
		
		// if IInventory or ISidedInventory is adjacent, check it's function.
		TileEntity blockhole = DimensionManager.getWorld(this.dimID).getTileEntity(this.blockholeLocation);
		
		if ( blockhole != null && blockhole instanceof TileEntityBlockhole && DimensionManager.getWorld(((TileEntityBlockhole)blockhole).getDimensionID()) == this.world)
		{
			int x1 = this.blockholeLocation.getX();
			int y1 = this.blockholeLocation.getY();
			int z1 = this.blockholeLocation.getZ();
					
			if (this.pos.getX() == 0)
				x1 -= 1;
			if (this.pos.getX() == 15)
				x1 += 1;
					
			if (this.pos.getY() == 0)
				y1 -= 1;
			if (this.pos.getY() == 15)
				y1 += 1;
					
			if (this.pos.getZ() == 0)
				z1 -= 1;
			if (this.pos.getZ() == 15)
				z1 += 1;
			
			BlockPos target = new BlockPos(x1,y1,z1);
			
			TileEntity tile = DimensionManager.getWorld(this.dimID).getTileEntity(target);
					
			if (tile != null && tile instanceof IInventory)
			{
				IInventory inv = (IInventory)tile;
						
				if (inv instanceof ISidedInventory)
				{
					ISidedInventory side = (ISidedInventory)tile;
							
					return side.getSlotsForFace(inputSide);
				}
				else
				{
					return new int[inv.getSizeInventory()];
				}
			}
			
			return new int[0];
		}

		return new int[0];
	}

	@Override
	public boolean canInsertItem(int index, ItemStack stack, EnumFacing direction) 
	{
		if (!this.transport)
		{
			return false;
		}
		
		// if IInventory or ISidedInventory is adjacent, check it's function.
		TileEntity blockhole = DimensionManager.getWorld(this.dimID).getTileEntity(this.blockholeLocation);
		
		if ( blockhole != null && blockhole instanceof TileEntityBlockhole && DimensionManager.getWorld(((TileEntityBlockhole)blockhole).getDimensionID()) == this.world)
		{
			int x1 = this.blockholeLocation.getX();
			int y1 = this.blockholeLocation.getY();
			int z1 = this.blockholeLocation.getZ();
					
			if (this.pos.getX() == 0)
				x1 -= 1;
			if (this.pos.getX() == 15)
				x1 += 1;
					
			if (this.pos.getY() == 0)
				y1 -= 1;
			if (this.pos.getY() == 15)
				y1 += 1;
					
			if (this.pos.getZ() == 0)
				z1 -= 1;
			if (this.pos.getZ() == 15)
				z1 += 1;
			
			BlockPos target = new BlockPos(x1,y1,z1);
			
			TileEntity tile = DimensionManager.getWorld(this.dimID).getTileEntity(target);
					
			if (tile != null && tile instanceof IInventory)
			{
				IInventory inv = (IInventory)tile;
						
				if (inv instanceof ISidedInventory)
				{
					ISidedInventory side = (ISidedInventory)tile;
							
					return side.canInsertItem(index, stack, direction);
				}
				else
				{
					return inv.isItemValidForSlot(index, stack);
				}
			}
			
			return false;
		}
		
		return false;
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) 
	{
		if (!this.transport)
		{
			return false;
		}
		
		// if IInventory or ISidedInventory is adjacent, check it's function.
		TileEntity blockhole = DimensionManager.getWorld(this.dimID).getTileEntity(this.blockholeLocation);
		
		if ( blockhole != null && blockhole instanceof TileEntityBlockhole && DimensionManager.getWorld(((TileEntityBlockhole)blockhole).getDimensionID()) == this.world)
		{
			int x1 = this.blockholeLocation.getX();
			int y1 = this.blockholeLocation.getY();
			int z1 = this.blockholeLocation.getZ();
					
			if (this.pos.getX() == 0)
				x1 -= 1;
			if (this.pos.getX() == 15)
				x1 += 1;
					
			if (this.pos.getY() == 0)
				y1 -= 1;
			if (this.pos.getY() == 15)
				y1 += 1;
					
			if (this.pos.getZ() == 0)
				z1 -= 1;
			if (this.pos.getZ() == 15)
				z1 += 1;
			
			BlockPos target = new BlockPos(x1,y1,z1);
			
			TileEntity tile = DimensionManager.getWorld(this.dimID).getTileEntity(target);
					
			if (tile != null && tile instanceof IInventory)
			{
				IInventory inv = (IInventory)tile;
						
				if (inv instanceof ISidedInventory)
				{
					ISidedInventory side = (ISidedInventory)tile;
							
					return side.canExtractItem(index, stack, direction);
				}
				else
				{
					return true;
				}
			}
			
			return false;
		}
		
		return false;
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
