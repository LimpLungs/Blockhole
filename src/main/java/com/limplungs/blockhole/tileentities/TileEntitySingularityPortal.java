package com.limplungs.blockhole.tileentities;

import com.limplungs.blockhole.lists.DimensionList;

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
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.capabilities.Capability;

public class TileEntitySingularityPortal extends TileEntity implements ISidedInventory
{
	private int dimID = -999;
	private int currDirection = 4;
	
	public TileEntitySingularityPortal()
	{
		
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) 
	{
		compound.setInteger("dimID", this.getDimensionID());
		
		compound.setInteger("currDirection", this.currDirection);
		
		return super.writeToNBT(compound);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) 
	{
		super.readFromNBT(compound);
		
		this.dimID = compound.getInteger("dimID");
		
		// Re-registers dimension when needed. (ie, world reload)
		if (dimID != -999 && !DimensionManager.isDimensionRegistered(this.getDimensionID()))
		{
			DimensionManager.registerDimension(this.getDimensionID(), DimensionType.getById(DimensionList.SINGULARITY_ID));
		}
		
		this.currDirection = compound.getInteger("currDirection");
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
	public int[] getSlotsForFace(EnumFacing blockSide) 
	{
		this.markDirty();
		
		if (this.getDimensionID() != -999)
		{
			WorldServer dimWorld = DimensionManager.getWorld(this.getDimensionID());
			TileEntity tile;
			TileEntitySingularityDimensionWall wall;

			if (blockSide == EnumFacing.EAST)
				this.currDirection = 1;
			else if (blockSide == EnumFacing.WEST)
				this.currDirection = 3;
			else if (blockSide == EnumFacing.SOUTH)
				this.currDirection = 2;
			else if (blockSide == EnumFacing.NORTH)
				this.currDirection = 0;
			else if (blockSide == EnumFacing.DOWN)
				this.currDirection = 5;
			else if (blockSide == EnumFacing.UP)
				this.currDirection = 4;
			
			// West direction - east block side input
			if (dimWorld != null && this.currDirection == 1)
			{
				for (int j = 1; j < 15; j++)
				{
					for (int k = 1; k < 15; k++)
					{
						tile = dimWorld.getTileEntity(new BlockPos(15,j,k));
						
						if (tile != null && tile instanceof TileEntitySingularityDimensionWall)
						{
							wall = (TileEntitySingularityDimensionWall)tile;
							
							if (wall.getTransport())
							{
								TileEntity target;
								
								target = dimWorld.getTileEntity(new BlockPos(14,j,k));
								
								if (target != null && target instanceof IInventory)
								{
									IInventory inv = (IInventory)target;
									
									if (inv instanceof ISidedInventory)
									{
										ISidedInventory side = (ISidedInventory)tile;
												
										return side.getSlotsForFace(blockSide);
									}
									else
									{
										return new int[inv.getSizeInventory()];
									}
								}
							}
						}
					}
				}
			}
			
			// East direction - west block side input
			else if (dimWorld != null && this.currDirection == 3)
			{
				for (int j = 1; j < 15; j++)
				{
					for (int k = 1; k < 15; k++)
					{
						tile = dimWorld.getTileEntity(new BlockPos(0,j,k));
						
						if (tile != null && tile instanceof TileEntitySingularityDimensionWall)
						{
							wall = (TileEntitySingularityDimensionWall)tile;
							
							if (wall.getTransport())
							{
								TileEntity target;
								
								target = dimWorld.getTileEntity(new BlockPos(1,j,k));
								
								if (target != null && target instanceof IInventory)
								{
									IInventory inv = (IInventory)target;
									
									if (inv instanceof ISidedInventory)
									{
										ISidedInventory side = (ISidedInventory)tile;
												
										return side.getSlotsForFace(blockSide);
									}
									else
									{
										return new int[inv.getSizeInventory()];
									}
								}
							}
						}
					}
				}
			}
			
			// North direction - South block side input
			else if (dimWorld != null && this.currDirection == 2)
			{
				for (int j = 1; j < 15; j++)
				{
					for (int i = 1; i < 15; i++)
					{
						tile = dimWorld.getTileEntity(new BlockPos(i,j,15));
						
						if (tile != null && tile instanceof TileEntitySingularityDimensionWall)
						{
							wall = (TileEntitySingularityDimensionWall)tile;
							
							if (wall.getTransport())
							{
								TileEntity target;
								
								target = dimWorld.getTileEntity(new BlockPos(i,j,14));
								
								if (target != null && target instanceof IInventory)
								{
									IInventory inv = (IInventory)target;
									
									if (inv instanceof ISidedInventory)
									{
										ISidedInventory side = (ISidedInventory)tile;
												
										return side.getSlotsForFace(blockSide);
									}
									else
									{
										return new int[inv.getSizeInventory()];
									}
								}
							}
						}
					}
				}
			}
			
			// South direction - North block side input
			else if (dimWorld != null && this.currDirection == 0)
			{
				for (int j = 1; j < 15; j++)
				{
					for (int i = 1; i < 15; i++)
					{
						tile = dimWorld.getTileEntity(new BlockPos(i,j,0));
						
						if (tile != null && tile instanceof TileEntitySingularityDimensionWall)
						{
							wall = (TileEntitySingularityDimensionWall)tile;
							
							if (wall.getTransport())
							{
								TileEntity target;
								
								target = dimWorld.getTileEntity(new BlockPos(i,j,1));
								
								if (target != null && target instanceof IInventory)
								{
									IInventory inv = (IInventory)target;
									
									if (inv instanceof ISidedInventory)
									{
										ISidedInventory side = (ISidedInventory)tile;
												
										return side.getSlotsForFace(blockSide);
									}
									else
									{
										return new int[inv.getSizeInventory()];
									}
								}
							}
						}
					}
				}
			}
			
			// Up direction - Down block side input
			else if (dimWorld != null && this.currDirection == 5)
			{
				for (int i = 1; i < 15; i++)
				{
					for (int k = 1; k < 15; k++)
					{
						tile = dimWorld.getTileEntity(new BlockPos(i,0,k));
						
						if (tile != null && tile instanceof TileEntitySingularityDimensionWall)
						{
							wall = (TileEntitySingularityDimensionWall)tile;
							
							if (wall.getTransport())
							{
								TileEntity target;
								
								target = dimWorld.getTileEntity(new BlockPos(i,1,k));
								
								if (target != null && target instanceof IInventory)
								{
									IInventory inv = (IInventory)target;
									
									if (inv instanceof ISidedInventory)
									{
										ISidedInventory side = (ISidedInventory)tile;
												
										return side.getSlotsForFace(blockSide);
									}
									else
									{
										return new int[inv.getSizeInventory()];
									}
								}
							}
						}
					}
				}
			}
			
			// Down direction - Up block side input
			else if (dimWorld != null && this.currDirection == 4)
			{
				for (int i = 1; i < 15; i++)
				{
					for (int k = 1; k < 15; k++)
					{
						tile = dimWorld.getTileEntity(new BlockPos(i,15,k));
						
						if (tile != null && tile instanceof TileEntitySingularityDimensionWall)
						{
							wall = (TileEntitySingularityDimensionWall)tile;
							
							if (wall.getTransport())
							{
								TileEntity target;
								
								target = dimWorld.getTileEntity(new BlockPos(i,14,k));
								
								if (target != null && target instanceof IInventory)
								{
									IInventory inv = (IInventory)target;
									
									if (inv instanceof ISidedInventory)
									{
										ISidedInventory side = (ISidedInventory)tile;
												
										return side.getSlotsForFace(blockSide);
									}
									else
									{
										return new int[inv.getSizeInventory()];
									}
								}
							}
						}
					}
				}
			}
		}
		
		return new int[0];
	}

	@Override
	public boolean canInsertItem(int index, ItemStack stack, EnumFacing direction) 
	{
		this.markDirty();
		
		if (this.getDimensionID() != -999)
		{
			WorldServer dimWorld = DimensionManager.getWorld(this.getDimensionID());
			TileEntity tile;
			TileEntitySingularityDimensionWall wall;

			if (direction == EnumFacing.EAST)
				this.currDirection = 1;
			else if (direction == EnumFacing.WEST)
				this.currDirection = 3;
			else if (direction == EnumFacing.SOUTH)
				this.currDirection = 2;
			else if (direction == EnumFacing.NORTH)
				this.currDirection = 0;
			else if (direction == EnumFacing.DOWN)
				this.currDirection = 5;
			else if (direction == EnumFacing.UP)
				this.currDirection = 4;
			
			// West direction - east block side input
			if (dimWorld != null && this.currDirection == 1)
			{
				for (int j = 1; j < 15; j++)
				{
					for (int k = 1; k < 15; k++)
					{
						tile = dimWorld.getTileEntity(new BlockPos(15,j,k));
						
						if (tile != null && tile instanceof TileEntitySingularityDimensionWall)
						{
							wall = (TileEntitySingularityDimensionWall)tile;
							
							if (wall.getTransport())
							{
								TileEntity target;
								
								target = dimWorld.getTileEntity(new BlockPos(14,j,k));
								
								if (target != null && target instanceof IInventory)
								{
									IInventory inv = (IInventory)target;
									
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
							}
						}
					}
				}
			}
			
			// East direction - west block side input
			else if (dimWorld != null && this.currDirection == 3)
			{
				for (int j = 1; j < 15; j++)
				{
					for (int k = 1; k < 15; k++)
					{
						tile = dimWorld.getTileEntity(new BlockPos(0,j,k));
						
						if (tile != null && tile instanceof TileEntitySingularityDimensionWall)
						{
							wall = (TileEntitySingularityDimensionWall)tile;
							
							if (wall.getTransport())
							{
								TileEntity target;
								
								target = dimWorld.getTileEntity(new BlockPos(1,j,k));
								
								if (target != null && target instanceof IInventory)
								{
									IInventory inv = (IInventory)target;
									
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
							}
						}
					}
				}
			}
			
			// North direction - South block side input
			else if (dimWorld != null && this.currDirection == 2)
			{
				for (int j = 1; j < 15; j++)
				{
					for (int i = 1; i < 15; i++)
					{
						tile = dimWorld.getTileEntity(new BlockPos(i,j,15));
						
						if (tile != null && tile instanceof TileEntitySingularityDimensionWall)
						{
							wall = (TileEntitySingularityDimensionWall)tile;
							
							if (wall.getTransport())
							{
								TileEntity target;
								
								target = dimWorld.getTileEntity(new BlockPos(i,j,14));
								
								if (target != null && target instanceof IInventory)
								{
									IInventory inv = (IInventory)target;
									
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
							}
						}
					}
				}
			}
			
			// South direction - North block side input
			else if (dimWorld != null && this.currDirection == 0)
			{
				for (int j = 1; j < 15; j++)
				{
					for (int i = 1; i < 15; i++)
					{
						tile = dimWorld.getTileEntity(new BlockPos(i,j,0));
						
						if (tile != null && tile instanceof TileEntitySingularityDimensionWall)
						{
							wall = (TileEntitySingularityDimensionWall)tile;
							
							if (wall.getTransport())
							{
								TileEntity target;
								
								target = dimWorld.getTileEntity(new BlockPos(i,j,1));
								
								if (target != null && target instanceof IInventory)
								{
									IInventory inv = (IInventory)target;
									
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
							}
						}
					}
				}
			}
			
			// Up direction - Down block side input
			else if (dimWorld != null && this.currDirection == 5)
			{
				for (int i = 1; i < 15; i++)
				{
					for (int k = 1; k < 15; k++)
					{
						tile = dimWorld.getTileEntity(new BlockPos(i,0,k));
						
						if (tile != null && tile instanceof TileEntitySingularityDimensionWall)
						{
							wall = (TileEntitySingularityDimensionWall)tile;
							
							if (wall.getTransport())
							{
								TileEntity target;
								
								target = dimWorld.getTileEntity(new BlockPos(i,1,k));
								
								if (target != null && target instanceof IInventory)
								{
									IInventory inv = (IInventory)target;
									
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
							}
						}
					}
				}
			}
			
			// Down direction - Up block side input
			else if (dimWorld != null && this.currDirection == 4)
			{
				for (int i = 1; i < 15; i++)
				{
					for (int k = 1; k < 15; k++)
					{
						tile = dimWorld.getTileEntity(new BlockPos(i,15,k));
						
						if (tile != null && tile instanceof TileEntitySingularityDimensionWall)
						{
							wall = (TileEntitySingularityDimensionWall)tile;
							
							if (wall.getTransport())
							{
								TileEntity target;
								
								target = dimWorld.getTileEntity(new BlockPos(i,14,k));
								
								if (target != null && target instanceof IInventory)
								{
									IInventory inv = (IInventory)target;
									
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
							}
						}
					}
				}
			}
		}
		
		return false;
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) 
	{
		this.markDirty();
		
		if (this.getDimensionID() != -999)
		{
			WorldServer dimWorld = DimensionManager.getWorld(this.getDimensionID());
			TileEntity tile;
			TileEntitySingularityDimensionWall wall;

			if (direction == EnumFacing.EAST)
				this.currDirection = 1;
			else if (direction == EnumFacing.WEST)
				this.currDirection = 3;
			else if (direction == EnumFacing.SOUTH)
				this.currDirection = 2;
			else if (direction == EnumFacing.NORTH)
				this.currDirection = 0;
			else if (direction == EnumFacing.DOWN)
				this.currDirection = 5;
			else if (direction == EnumFacing.UP)
				this.currDirection = 4;
			
			// West direction - east block side input
			if (dimWorld != null && this.currDirection == 1)
			{
				for (int j = 1; j < 15; j++)
				{
					for (int k = 1; k < 15; k++)
					{
						tile = dimWorld.getTileEntity(new BlockPos(15,j,k));
						
						if (tile != null && tile instanceof TileEntitySingularityDimensionWall)
						{
							wall = (TileEntitySingularityDimensionWall)tile;
							
							if (wall.getTransport())
							{
								TileEntity target;
								
								target = dimWorld.getTileEntity(new BlockPos(14,j,k));
								
								if (target != null && target instanceof IInventory)
								{
									IInventory inv = (IInventory)target;
									
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
							}
						}
					}
				}
			}
			
			// East direction - west block side input
			else if (dimWorld != null && this.currDirection == 3)
			{
				for (int j = 1; j < 15; j++)
				{
					for (int k = 1; k < 15; k++)
					{
						tile = dimWorld.getTileEntity(new BlockPos(0,j,k));
						
						if (tile != null && tile instanceof TileEntitySingularityDimensionWall)
						{
							wall = (TileEntitySingularityDimensionWall)tile;
							
							if (wall.getTransport())
							{
								TileEntity target;
								
								target = dimWorld.getTileEntity(new BlockPos(1,j,k));
								
								if (target != null && target instanceof IInventory)
								{
									IInventory inv = (IInventory)target;
									
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
							}
						}
					}
				}
			}
			
			// North direction - South block side input
			else if (dimWorld != null && this.currDirection == 2)
			{
				for (int j = 1; j < 15; j++)
				{
					for (int i = 1; i < 15; i++)
					{
						tile = dimWorld.getTileEntity(new BlockPos(i,j,15));
						
						if (tile != null && tile instanceof TileEntitySingularityDimensionWall)
						{
							wall = (TileEntitySingularityDimensionWall)tile;
							
							if (wall.getTransport())
							{
								TileEntity target;
								
								target = dimWorld.getTileEntity(new BlockPos(i,j,14));
								
								if (target != null && target instanceof IInventory)
								{
									IInventory inv = (IInventory)target;
									
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
							}
						}
					}
				}
			}
			
			// South direction - North block side input
			else if (dimWorld != null && this.currDirection == 0)
			{
				for (int j = 1; j < 15; j++)
				{
					for (int i = 1; i < 15; i++)
					{
						tile = dimWorld.getTileEntity(new BlockPos(i,j,0));
						
						if (tile != null && tile instanceof TileEntitySingularityDimensionWall)
						{
							wall = (TileEntitySingularityDimensionWall)tile;
							
							if (wall.getTransport())
							{
								TileEntity target;
								
								target = dimWorld.getTileEntity(new BlockPos(i,j,1));
								
								if (target != null && target instanceof IInventory)
								{
									IInventory inv = (IInventory)target;
									
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
							}
						}
					}
				}
			}
			
			// Up direction - Down block side input
			else if (dimWorld != null && this.currDirection == 5)
			{
				for (int i = 1; i < 15; i++)
				{
					for (int k = 1; k < 15; k++)
					{
						tile = dimWorld.getTileEntity(new BlockPos(i,0,k));
						
						if (tile != null && tile instanceof TileEntitySingularityDimensionWall)
						{
							wall = (TileEntitySingularityDimensionWall)tile;
							
							if (wall.getTransport())
							{
								TileEntity target;
								
								target = dimWorld.getTileEntity(new BlockPos(i,1,k));
								
								if (target != null && target instanceof IInventory)
								{
									IInventory inv = (IInventory)target;
									
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
							}
						}
					}
				}
			}
			
			// Down direction - Up block side input
			else if (dimWorld != null && this.currDirection == 4)
			{
				for (int i = 1; i < 15; i++)
				{
					for (int k = 1; k < 15; k++)
					{
						tile = dimWorld.getTileEntity(new BlockPos(i,15,k));
						
						if (tile != null && tile instanceof TileEntitySingularityDimensionWall)
						{
							wall = (TileEntitySingularityDimensionWall)tile;
							
							if (wall.getTransport())
							{
								TileEntity target;
								
								target = dimWorld.getTileEntity(new BlockPos(i,14,k));
								
								if (target != null && target instanceof IInventory)
								{
									IInventory inv = (IInventory)target;
									
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
							}
						}
					}
				}
			}
		}
		
		return false;
	}
	
	@Override
	public int getSizeInventory() 
	{
		this.markDirty();
		
		if (this.getDimensionID() != -999)
		{
			WorldServer dimWorld = DimensionManager.getWorld(this.getDimensionID());
			TileEntity tile;
			TileEntitySingularityDimensionWall wall;
			
			// West direction - east block side input
			if (dimWorld != null && this.currDirection == 1)
			{
				for (int j = 1; j < 15; j++)
				{
					for (int k = 1; k < 15; k++)
					{
						tile = dimWorld.getTileEntity(new BlockPos(15,j,k));
						
						if (tile != null && tile instanceof TileEntitySingularityDimensionWall)
						{
							wall = (TileEntitySingularityDimensionWall)tile;
							
							if (wall.getTransport())
							{
								TileEntity target;
								
								target = dimWorld.getTileEntity(new BlockPos(14,j,k));
								
								if (target != null && target instanceof IInventory)
								{
									IInventory inv = (IInventory)target;
									
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
							}
						}
					}
				}
			}
			
			// East direction - west block side input
			else if (dimWorld != null && this.currDirection == 3)
			{
				for (int j = 1; j < 15; j++)
				{
					for (int k = 1; k < 15; k++)
					{
						tile = dimWorld.getTileEntity(new BlockPos(0,j,k));
						
						if (tile != null && tile instanceof TileEntitySingularityDimensionWall)
						{
							wall = (TileEntitySingularityDimensionWall)tile;
							
							if (wall.getTransport())
							{
								TileEntity target;
								
								target = dimWorld.getTileEntity(new BlockPos(1,j,k));
								
								if (target != null && target instanceof IInventory)
								{
									IInventory inv = (IInventory)target;
									
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
							}
						}
					}
				}
			}
			
			// North direction - South block side input
			else if (dimWorld != null && this.currDirection == 2)
			{
				for (int j = 1; j < 15; j++)
				{
					for (int i = 1; i < 15; i++)
					{
						tile = dimWorld.getTileEntity(new BlockPos(i,j,15));
						
						if (tile != null && tile instanceof TileEntitySingularityDimensionWall)
						{
							wall = (TileEntitySingularityDimensionWall)tile;
							
							if (wall.getTransport())
							{
								TileEntity target;
								
								target = dimWorld.getTileEntity(new BlockPos(i,j,14));
								
								if (target != null && target instanceof IInventory)
								{
									IInventory inv = (IInventory)target;
									
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
							}
						}
					}
				}
			}
			
			// South direction - North block side input
			else if (dimWorld != null && this.currDirection == 0)
			{
				for (int j = 1; j < 15; j++)
				{
					for (int i = 1; i < 15; i++)
					{
						tile = dimWorld.getTileEntity(new BlockPos(i,j,0));
						
						if (tile != null && tile instanceof TileEntitySingularityDimensionWall)
						{
							wall = (TileEntitySingularityDimensionWall)tile;
							
							if (wall.getTransport())
							{
								TileEntity target;
								
								target = dimWorld.getTileEntity(new BlockPos(i,j,1));
								
								if (target != null && target instanceof IInventory)
								{
									IInventory inv = (IInventory)target;
									
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
							}
						}
					}
				}
			}
			
			// Up direction - Down block side input
			else if (dimWorld != null && this.currDirection == 5)
			{
				for (int i = 1; i < 15; i++)
				{
					for (int k = 1; k < 15; k++)
					{
						tile = dimWorld.getTileEntity(new BlockPos(i,0,k));
						
						if (tile != null && tile instanceof TileEntitySingularityDimensionWall)
						{
							wall = (TileEntitySingularityDimensionWall)tile;
							
							if (wall.getTransport())
							{
								TileEntity target;
								
								target = dimWorld.getTileEntity(new BlockPos(i,1,k));
								
								if (target != null && target instanceof IInventory)
								{
									IInventory inv = (IInventory)target;
									
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
							}
						}
					}
				}
			}
			
			// Down direction - Up block side input
			else if (dimWorld != null && this.currDirection == 4)
			{
				for (int i = 1; i < 15; i++)
				{
					for (int k = 1; k < 15; k++)
					{
						tile = dimWorld.getTileEntity(new BlockPos(i,15,k));
						
						if (tile != null && tile instanceof TileEntitySingularityDimensionWall)
						{
							wall = (TileEntitySingularityDimensionWall)tile;
							
							if (wall.getTransport())
							{
								TileEntity target;
								
								target = dimWorld.getTileEntity(new BlockPos(i,14,k));
								
								if (target != null && target instanceof IInventory)
								{
									IInventory inv = (IInventory)target;
									
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
							}
						}
					}
				}
			}
		}
		
		return 0;
	}
	
	@Override
	public boolean isEmpty()
	{
		this.markDirty();
		
		if (this.getDimensionID() != -999)
		{
			WorldServer dimWorld = DimensionManager.getWorld(this.getDimensionID());
			TileEntity tile;
			TileEntitySingularityDimensionWall wall;
			
			// West direction - east block side input
			if (dimWorld != null && this.currDirection == 1)
			{
				for (int j = 1; j < 15; j++)
				{
					for (int k = 1; k < 15; k++)
					{
						tile = dimWorld.getTileEntity(new BlockPos(15,j,k));
						
						if (tile != null && tile instanceof TileEntitySingularityDimensionWall)
						{
							wall = (TileEntitySingularityDimensionWall)tile;
							
							if (wall.getTransport())
							{
								TileEntity target;
								
								target = dimWorld.getTileEntity(new BlockPos(14,j,k));
								
								if (target != null && target instanceof IInventory)
								{
									IInventory inv = (IInventory)target;
									
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
							}
						}
					}
				}
			}
			
			// East direction - west block side input
			else if (dimWorld != null && this.currDirection == 3)
			{
				for (int j = 1; j < 15; j++)
				{
					for (int k = 1; k < 15; k++)
					{
						tile = dimWorld.getTileEntity(new BlockPos(0,j,k));
						
						if (tile != null && tile instanceof TileEntitySingularityDimensionWall)
						{
							wall = (TileEntitySingularityDimensionWall)tile;
							
							if (wall.getTransport())
							{
								TileEntity target;
								
								target = dimWorld.getTileEntity(new BlockPos(1,j,k));
								
								if (target != null && target instanceof IInventory)
								{
									IInventory inv = (IInventory)target;
									
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
							}
						}
					}
				}
			}
			
			// North direction - South block side input
			else if (dimWorld != null && this.currDirection == 2)
			{
				for (int j = 1; j < 15; j++)
				{
					for (int i = 1; i < 15; i++)
					{
						tile = dimWorld.getTileEntity(new BlockPos(i,j,15));
						
						if (tile != null && tile instanceof TileEntitySingularityDimensionWall)
						{
							wall = (TileEntitySingularityDimensionWall)tile;
							
							if (wall.getTransport())
							{
								TileEntity target;
								
								target = dimWorld.getTileEntity(new BlockPos(i,j,14));
								
								if (target != null && target instanceof IInventory)
								{
									IInventory inv = (IInventory)target;
									
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
							}
						}
					}
				}
			}
			
			// South direction - North block side input
			else if (dimWorld != null && this.currDirection == 0)
			{
				for (int j = 1; j < 15; j++)
				{
					for (int i = 1; i < 15; i++)
					{
						tile = dimWorld.getTileEntity(new BlockPos(i,j,0));
						
						if (tile != null && tile instanceof TileEntitySingularityDimensionWall)
						{
							wall = (TileEntitySingularityDimensionWall)tile;
							
							if (wall.getTransport())
							{
								TileEntity target;
								
								target = dimWorld.getTileEntity(new BlockPos(i,j,1));
								
								if (target != null && target instanceof IInventory)
								{
									IInventory inv = (IInventory)target;
									
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
							}
						}
					}
				}
			}
			
			// Up direction - Down block side input
			else if (dimWorld != null && this.currDirection == 5)
			{
				for (int i = 1; i < 15; i++)
				{
					for (int k = 1; k < 15; k++)
					{
						tile = dimWorld.getTileEntity(new BlockPos(i,0,k));
						
						if (tile != null && tile instanceof TileEntitySingularityDimensionWall)
						{
							wall = (TileEntitySingularityDimensionWall)tile;
							
							if (wall.getTransport())
							{
								TileEntity target;
								
								target = dimWorld.getTileEntity(new BlockPos(i,1,k));
								
								if (target != null && target instanceof IInventory)
								{
									IInventory inv = (IInventory)target;
									
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
							}
						}
					}
				}
			}
			
			// Down direction - Up block side input
			else if (dimWorld != null && this.currDirection == 4)
			{
				for (int i = 1; i < 15; i++)
				{
					for (int k = 1; k < 15; k++)
					{
						tile = dimWorld.getTileEntity(new BlockPos(i,15,k));
						
						if (tile != null && tile instanceof TileEntitySingularityDimensionWall)
						{
							wall = (TileEntitySingularityDimensionWall)tile;
							
							if (wall.getTransport())
							{
								TileEntity target;
								
								target = dimWorld.getTileEntity(new BlockPos(i,14,k));
								
								if (target != null && target instanceof IInventory)
								{
									IInventory inv = (IInventory)target;
									
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
							}
						}
					}
				}
			}
		}
		
		return false;
	}

	@Override
	public ItemStack getStackInSlot(int index) 
	{
		this.markDirty();
		
		if (this.getDimensionID() != -999)
		{
			WorldServer dimWorld = DimensionManager.getWorld(this.getDimensionID());
			TileEntity tile;
			TileEntitySingularityDimensionWall wall;
			
			// West direction - east block side input
			if (dimWorld != null && this.currDirection == 1)
			{
				for (int j = 1; j < 15; j++)
				{
					for (int k = 1; k < 15; k++)
					{
						tile = dimWorld.getTileEntity(new BlockPos(15,j,k));
						
						if (tile != null && tile instanceof TileEntitySingularityDimensionWall)
						{
							wall = (TileEntitySingularityDimensionWall)tile;
							
							if (wall.getTransport())
							{
								TileEntity target;
								
								target = dimWorld.getTileEntity(new BlockPos(14,j,k));
								
								if (target != null && target instanceof IInventory)
								{
									IInventory inv = (IInventory)target;
									
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
							}
						}
					}
				}
			}
			
			// East direction - west block side input
			else if (dimWorld != null && this.currDirection == 3)
			{
				for (int j = 1; j < 15; j++)
				{
					for (int k = 1; k < 15; k++)
					{
						tile = dimWorld.getTileEntity(new BlockPos(0,j,k));
						
						if (tile != null && tile instanceof TileEntitySingularityDimensionWall)
						{
							wall = (TileEntitySingularityDimensionWall)tile;
							
							if (wall.getTransport())
							{
								TileEntity target;
								
								target = dimWorld.getTileEntity(new BlockPos(1,j,k));
								
								if (target != null && target instanceof IInventory)
								{
									IInventory inv = (IInventory)target;
									
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
							}
						}
					}
				}
			}
			
			// North direction - South block side input
			else if (dimWorld != null && this.currDirection == 2)
			{
				for (int j = 1; j < 15; j++)
				{
					for (int i = 1; i < 15; i++)
					{
						tile = dimWorld.getTileEntity(new BlockPos(i,j,15));
						
						if (tile != null && tile instanceof TileEntitySingularityDimensionWall)
						{
							wall = (TileEntitySingularityDimensionWall)tile;
							
							if (wall.getTransport())
							{
								TileEntity target;
								
								target = dimWorld.getTileEntity(new BlockPos(i,j,14));
								
								if (target != null && target instanceof IInventory)
								{
									IInventory inv = (IInventory)target;
									
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
							}
						}
					}
				}
			}
			
			// South direction - North block side input
			else if (dimWorld != null && this.currDirection == 0)
			{
				for (int j = 1; j < 15; j++)
				{
					for (int i = 1; i < 15; i++)
					{
						tile = dimWorld.getTileEntity(new BlockPos(i,j,0));
						
						if (tile != null && tile instanceof TileEntitySingularityDimensionWall)
						{
							wall = (TileEntitySingularityDimensionWall)tile;
							
							if (wall.getTransport())
							{
								TileEntity target;
								
								target = dimWorld.getTileEntity(new BlockPos(i,j,1));
								
								if (target != null && target instanceof IInventory)
								{
									IInventory inv = (IInventory)target;
									
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
							}
						}
					}
				}
			}
			
			// Up direction - Down block side input
			else if (dimWorld != null && this.currDirection == 5)
			{
				for (int i = 1; i < 15; i++)
				{
					for (int k = 1; k < 15; k++)
					{
						tile = dimWorld.getTileEntity(new BlockPos(i,0,k));
						
						if (tile != null && tile instanceof TileEntitySingularityDimensionWall)
						{
							wall = (TileEntitySingularityDimensionWall)tile;
							
							if (wall.getTransport())
							{
								TileEntity target;
								
								target = dimWorld.getTileEntity(new BlockPos(i,1,k));
								
								if (target != null && target instanceof IInventory)
								{
									IInventory inv = (IInventory)target;
									
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
							}
						}
					}
				}
			}
			
			// Down direction - Up block side input
			else if (dimWorld != null && this.currDirection == 4)
			{
				for (int i = 1; i < 15; i++)
				{
					for (int k = 1; k < 15; k++)
					{
						tile = dimWorld.getTileEntity(new BlockPos(i,15,k));
						
						if (tile != null && tile instanceof TileEntitySingularityDimensionWall)
						{
							wall = (TileEntitySingularityDimensionWall)tile;
							
							if (wall.getTransport())
							{
								TileEntity target;
								
								target = dimWorld.getTileEntity(new BlockPos(i,14,k));
								
								if (target != null && target instanceof IInventory)
								{
									IInventory inv = (IInventory)target;
									
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
							}
						}
					}
				}
			}
		}
		
		return ItemStack.EMPTY;
	}

	@Override
	public ItemStack decrStackSize(int index, int count) 
	{
		this.markDirty();
		
		if (this.getDimensionID() != -999)
		{
			WorldServer dimWorld = DimensionManager.getWorld(this.getDimensionID());
			TileEntity tile;
			TileEntitySingularityDimensionWall wall;
			
			// West direction - east block side input
			if (dimWorld != null && this.currDirection == 1)
			{
				for (int j = 1; j < 15; j++)
				{
					for (int k = 1; k < 15; k++)
					{
						tile = dimWorld.getTileEntity(new BlockPos(15,j,k));
						
						if (tile != null && tile instanceof TileEntitySingularityDimensionWall)
						{
							wall = (TileEntitySingularityDimensionWall)tile;
							
							if (wall.getTransport())
							{
								TileEntity target;
								
								target = dimWorld.getTileEntity(new BlockPos(14,j,k));
								
								if (target != null && target instanceof IInventory)
								{
									IInventory inv = (IInventory)target;
									
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
							}
						}
					}
				}
			}
			
			// East direction - west block side input
			else if (dimWorld != null && this.currDirection == 3)
			{
				for (int j = 1; j < 15; j++)
				{
					for (int k = 1; k < 15; k++)
					{
						tile = dimWorld.getTileEntity(new BlockPos(0,j,k));
						
						if (tile != null && tile instanceof TileEntitySingularityDimensionWall)
						{
							wall = (TileEntitySingularityDimensionWall)tile;
							
							if (wall.getTransport())
							{
								TileEntity target;
								
								target = dimWorld.getTileEntity(new BlockPos(1,j,k));
								
								if (target != null && target instanceof IInventory)
								{
									IInventory inv = (IInventory)target;
									
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
							}
						}
					}
				}
			}
			
			// North direction - South block side input
			else if (dimWorld != null && this.currDirection == 2)
			{
				for (int j = 1; j < 15; j++)
				{
					for (int i = 1; i < 15; i++)
					{
						tile = dimWorld.getTileEntity(new BlockPos(i,j,15));
						
						if (tile != null && tile instanceof TileEntitySingularityDimensionWall)
						{
							wall = (TileEntitySingularityDimensionWall)tile;
							
							if (wall.getTransport())
							{
								TileEntity target;
								
								target = dimWorld.getTileEntity(new BlockPos(i,j,14));
								
								if (target != null && target instanceof IInventory)
								{
									IInventory inv = (IInventory)target;
									
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
							}
						}
					}
				}
			}
			
			// South direction - North block side input
			else if (dimWorld != null && this.currDirection == 0)
			{
				for (int j = 1; j < 15; j++)
				{
					for (int i = 1; i < 15; i++)
					{
						tile = dimWorld.getTileEntity(new BlockPos(i,j,0));
						
						if (tile != null && tile instanceof TileEntitySingularityDimensionWall)
						{
							wall = (TileEntitySingularityDimensionWall)tile;
							
							if (wall.getTransport())
							{
								TileEntity target;
								
								target = dimWorld.getTileEntity(new BlockPos(i,j,1));
								
								if (target != null && target instanceof IInventory)
								{
									IInventory inv = (IInventory)target;
									
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
							}
						}
					}
				}
			}
			
			// Up direction - Down block side input
			else if (dimWorld != null && this.currDirection == 5)
			{
				for (int i = 1; i < 15; i++)
				{
					for (int k = 1; k < 15; k++)
					{
						tile = dimWorld.getTileEntity(new BlockPos(i,0,k));
						
						if (tile != null && tile instanceof TileEntitySingularityDimensionWall)
						{
							wall = (TileEntitySingularityDimensionWall)tile;
							
							if (wall.getTransport())
							{
								TileEntity target;
								
								target = dimWorld.getTileEntity(new BlockPos(i,1,k));
								
								if (target != null && target instanceof IInventory)
								{
									IInventory inv = (IInventory)target;
									
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
							}
						}
					}
				}
			}
			
			// Down direction - Up block side input
			else if (dimWorld != null && this.currDirection == 4)
			{
				for (int i = 1; i < 15; i++)
				{
					for (int k = 1; k < 15; k++)
					{
						tile = dimWorld.getTileEntity(new BlockPos(i,15,k));
						
						if (tile != null && tile instanceof TileEntitySingularityDimensionWall)
						{
							wall = (TileEntitySingularityDimensionWall)tile;
							
							if (wall.getTransport())
							{
								TileEntity target;
								
								target = dimWorld.getTileEntity(new BlockPos(i,14,k));
								
								if (target != null && target instanceof IInventory)
								{
									IInventory inv = (IInventory)target;
									
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
							}
						}
					}
				}
			}
		}
		
		return ItemStack.EMPTY;
	}

	@Override
	public ItemStack removeStackFromSlot(int index) 
	{
		this.markDirty();
		
		if (this.getDimensionID() != -999)
		{
			WorldServer dimWorld = DimensionManager.getWorld(this.getDimensionID());
			TileEntity tile;
			TileEntitySingularityDimensionWall wall;
			
			// West direction - east block side input
			if (dimWorld != null && this.currDirection == 1)
			{
				for (int j = 1; j < 15; j++)
				{
					for (int k = 1; k < 15; k++)
					{
						tile = dimWorld.getTileEntity(new BlockPos(15,j,k));
						
						if (tile != null && tile instanceof TileEntitySingularityDimensionWall)
						{
							wall = (TileEntitySingularityDimensionWall)tile;
							
							if (wall.getTransport())
							{
								TileEntity target;
								
								target = dimWorld.getTileEntity(new BlockPos(14,j,k));
								
								if (target != null && target instanceof IInventory)
								{
									IInventory inv = (IInventory)target;
									
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
							}
						}
					}
				}
			}
			
			// East direction - west block side input
			else if (dimWorld != null && this.currDirection == 3)
			{
				for (int j = 1; j < 15; j++)
				{
					for (int k = 1; k < 15; k++)
					{
						tile = dimWorld.getTileEntity(new BlockPos(0,j,k));
						
						if (tile != null && tile instanceof TileEntitySingularityDimensionWall)
						{
							wall = (TileEntitySingularityDimensionWall)tile;
							
							if (wall.getTransport())
							{
								TileEntity target;
								
								target = dimWorld.getTileEntity(new BlockPos(1,j,k));
								
								if (target != null && target instanceof IInventory)
								{
									IInventory inv = (IInventory)target;
									
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
							}
						}
					}
				}
			}
			
			// North direction - South block side input
			else if (dimWorld != null && this.currDirection == 2)
			{
				for (int j = 1; j < 15; j++)
				{
					for (int i = 1; i < 15; i++)
					{
						tile = dimWorld.getTileEntity(new BlockPos(i,j,15));
						
						if (tile != null && tile instanceof TileEntitySingularityDimensionWall)
						{
							wall = (TileEntitySingularityDimensionWall)tile;
							
							if (wall.getTransport())
							{
								TileEntity target;
								
								target = dimWorld.getTileEntity(new BlockPos(i,j,14));
								
								if (target != null && target instanceof IInventory)
								{
									IInventory inv = (IInventory)target;
									
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
							}
						}
					}
				}
			}
			
			// South direction - North block side input
			else if (dimWorld != null && this.currDirection == 0)
			{
				for (int j = 1; j < 15; j++)
				{
					for (int i = 1; i < 15; i++)
					{
						tile = dimWorld.getTileEntity(new BlockPos(i,j,0));
						
						if (tile != null && tile instanceof TileEntitySingularityDimensionWall)
						{
							wall = (TileEntitySingularityDimensionWall)tile;
							
							if (wall.getTransport())
							{
								TileEntity target;
								
								target = dimWorld.getTileEntity(new BlockPos(i,j,1));
								
								if (target != null && target instanceof IInventory)
								{
									IInventory inv = (IInventory)target;
									
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
							}
						}
					}
				}
			}
			
			// Up direction - Down block side input
			else if (dimWorld != null && this.currDirection == 5)
			{
				for (int i = 1; i < 15; i++)
				{
					for (int k = 1; k < 15; k++)
					{
						tile = dimWorld.getTileEntity(new BlockPos(i,0,k));
						
						if (tile != null && tile instanceof TileEntitySingularityDimensionWall)
						{
							wall = (TileEntitySingularityDimensionWall)tile;
							
							if (wall.getTransport())
							{
								TileEntity target;
								
								target = dimWorld.getTileEntity(new BlockPos(i,1,k));
								
								if (target != null && target instanceof IInventory)
								{
									IInventory inv = (IInventory)target;
									
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
							}
						}
					}
				}
			}
			
			// Down direction - Up block side input
			else if (dimWorld != null && this.currDirection == 4)
			{
				for (int i = 1; i < 15; i++)
				{
					for (int k = 1; k < 15; k++)
					{
						tile = dimWorld.getTileEntity(new BlockPos(i,15,k));
						
						if (tile != null && tile instanceof TileEntitySingularityDimensionWall)
						{
							wall = (TileEntitySingularityDimensionWall)tile;
							
							if (wall.getTransport())
							{
								TileEntity target;
								
								target = dimWorld.getTileEntity(new BlockPos(i,14,k));
								
								if (target != null && target instanceof IInventory)
								{
									IInventory inv = (IInventory)target;
									
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
							}
						}
					}
				}
			}
		}
		
		return ItemStack.EMPTY;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) 
	{
		this.markDirty();
		
		if (this.getDimensionID() != -999)
		{
			WorldServer dimWorld = DimensionManager.getWorld(this.getDimensionID());
			TileEntity tile;
			TileEntitySingularityDimensionWall wall;
			
			// West direction - east block side input
			if (dimWorld != null && this.currDirection == 1)
			{
				for (int j = 1; j < 15; j++)
				{
					for (int k = 1; k < 15; k++)
					{
						tile = dimWorld.getTileEntity(new BlockPos(15,j,k));
						
						if (tile != null && tile instanceof TileEntitySingularityDimensionWall)
						{
							wall = (TileEntitySingularityDimensionWall)tile;
							
							if (wall.getTransport())
							{
								TileEntity target;
								
								target = dimWorld.getTileEntity(new BlockPos(14,j,k));
								
								if (target != null && target instanceof IInventory)
								{
									IInventory inv = (IInventory)target;
									
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
					}
				}
			}
			
			// East direction - west block side input
			else if (dimWorld != null && this.currDirection == 3)
			{
				for (int j = 1; j < 15; j++)
				{
					for (int k = 1; k < 15; k++)
					{
						tile = dimWorld.getTileEntity(new BlockPos(0,j,k));
						
						if (tile != null && tile instanceof TileEntitySingularityDimensionWall)
						{
							wall = (TileEntitySingularityDimensionWall)tile;
							
							if (wall.getTransport())
							{
								TileEntity target;
								
								target = dimWorld.getTileEntity(new BlockPos(1,j,k));
								
								if (target != null && target instanceof IInventory)
								{
									IInventory inv = (IInventory)target;
									
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
					}
				}
			}
			
			// North direction - South block side input
			else if (dimWorld != null && this.currDirection == 2)
			{
				for (int j = 1; j < 15; j++)
				{
					for (int i = 1; i < 15; i++)
					{
						tile = dimWorld.getTileEntity(new BlockPos(i,j,15));
						
						if (tile != null && tile instanceof TileEntitySingularityDimensionWall)
						{
							wall = (TileEntitySingularityDimensionWall)tile;
							
							if (wall.getTransport())
							{
								TileEntity target;
								
								target = dimWorld.getTileEntity(new BlockPos(i,j,14));
								
								if (target != null && target instanceof IInventory)
								{
									IInventory inv = (IInventory)target;
									
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
					}
				}
			}
			
			// South direction - North block side input
			else if (dimWorld != null && this.currDirection == 0)
			{
				for (int j = 1; j < 15; j++)
				{
					for (int i = 1; i < 15; i++)
					{
						tile = dimWorld.getTileEntity(new BlockPos(i,j,0));
						
						if (tile != null && tile instanceof TileEntitySingularityDimensionWall)
						{
							wall = (TileEntitySingularityDimensionWall)tile;
							
							if (wall.getTransport())
							{
								TileEntity target;
								
								target = dimWorld.getTileEntity(new BlockPos(i,j,1));
								
								if (target != null && target instanceof IInventory)
								{
									IInventory inv = (IInventory)target;
									
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
					}
				}
			}
			
			// Up direction - Down block side input
			else if (dimWorld != null && this.currDirection == 5)
			{
				for (int i = 1; i < 15; i++)
				{
					for (int k = 1; k < 15; k++)
					{
						tile = dimWorld.getTileEntity(new BlockPos(i,0,k));
						
						if (tile != null && tile instanceof TileEntitySingularityDimensionWall)
						{
							wall = (TileEntitySingularityDimensionWall)tile;
							
							if (wall.getTransport())
							{
								TileEntity target;
								
								target = dimWorld.getTileEntity(new BlockPos(i,1,k));
								
								if (target != null && target instanceof IInventory)
								{
									IInventory inv = (IInventory)target;
									
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
					}
				}
			}
			
			// Down direction - Up block side input
			else if (dimWorld != null && this.currDirection == 4)
			{
				for (int i = 1; i < 15; i++)
				{
					for (int k = 1; k < 15; k++)
					{
						tile = dimWorld.getTileEntity(new BlockPos(i,15,k));
						
						if (tile != null && tile instanceof TileEntitySingularityDimensionWall)
						{
							wall = (TileEntitySingularityDimensionWall)tile;
							
							if (wall.getTransport())
							{
								TileEntity target;
								
								target = dimWorld.getTileEntity(new BlockPos(i,14,k));
								
								if (target != null && target instanceof IInventory)
								{
									IInventory inv = (IInventory)target;
									
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
					}
				}
			}
		}
	}

	@Override
	public int getInventoryStackLimit() 
	{
		this.markDirty();
		
		if (this.getDimensionID() != -999)
		{
			WorldServer dimWorld = DimensionManager.getWorld(this.getDimensionID());
			TileEntity tile;
			TileEntitySingularityDimensionWall wall;
			
			// West direction - east block side input
			if (dimWorld != null && this.currDirection == 1)
			{
				for (int j = 1; j < 15; j++)
				{
					for (int k = 1; k < 15; k++)
					{
						tile = dimWorld.getTileEntity(new BlockPos(15,j,k));
						
						if (tile != null && tile instanceof TileEntitySingularityDimensionWall)
						{
							wall = (TileEntitySingularityDimensionWall)tile;
							
							if (wall.getTransport())
							{
								TileEntity target;
								
								target = dimWorld.getTileEntity(new BlockPos(14,j,k));
								
								if (target != null && target instanceof IInventory)
								{
									IInventory inv = (IInventory)target;
									
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
							}
						}
					}
				}
			}
			
			// East direction - west block side input
			else if (dimWorld != null && this.currDirection == 3)
			{
				for (int j = 1; j < 15; j++)
				{
					for (int k = 1; k < 15; k++)
					{
						tile = dimWorld.getTileEntity(new BlockPos(0,j,k));
						
						if (tile != null && tile instanceof TileEntitySingularityDimensionWall)
						{
							wall = (TileEntitySingularityDimensionWall)tile;
							
							if (wall.getTransport())
							{
								TileEntity target;
								
								target = dimWorld.getTileEntity(new BlockPos(1,j,k));
								
								if (target != null && target instanceof IInventory)
								{
									IInventory inv = (IInventory)target;
									
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
							}
						}
					}
				}
			}
			
			// North direction - South block side input
			else if (dimWorld != null && this.currDirection == 2)
			{
				for (int j = 1; j < 15; j++)
				{
					for (int i = 1; i < 15; i++)
					{
						tile = dimWorld.getTileEntity(new BlockPos(i,j,15));
						
						if (tile != null && tile instanceof TileEntitySingularityDimensionWall)
						{
							wall = (TileEntitySingularityDimensionWall)tile;
							
							if (wall.getTransport())
							{
								TileEntity target;
								
								target = dimWorld.getTileEntity(new BlockPos(i,j,14));
								
								if (target != null && target instanceof IInventory)
								{
									IInventory inv = (IInventory)target;
									
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
							}
						}
					}
				}
			}
			
			// South direction - North block side input
			else if (dimWorld != null && this.currDirection == 0)
			{
				for (int j = 1; j < 15; j++)
				{
					for (int i = 1; i < 15; i++)
					{
						tile = dimWorld.getTileEntity(new BlockPos(i,j,0));
						
						if (tile != null && tile instanceof TileEntitySingularityDimensionWall)
						{
							wall = (TileEntitySingularityDimensionWall)tile;
							
							if (wall.getTransport())
							{
								TileEntity target;
								
								target = dimWorld.getTileEntity(new BlockPos(i,j,1));
								
								if (target != null && target instanceof IInventory)
								{
									IInventory inv = (IInventory)target;
									
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
							}
						}
					}
				}
			}
			
			// Up direction - Down block side input
			else if (dimWorld != null && this.currDirection == 5)
			{
				for (int i = 1; i < 15; i++)
				{
					for (int k = 1; k < 15; k++)
					{
						tile = dimWorld.getTileEntity(new BlockPos(i,0,k));
						
						if (tile != null && tile instanceof TileEntitySingularityDimensionWall)
						{
							wall = (TileEntitySingularityDimensionWall)tile;
							
							if (wall.getTransport())
							{
								TileEntity target;
								
								target = dimWorld.getTileEntity(new BlockPos(i,1,k));
								
								if (target != null && target instanceof IInventory)
								{
									IInventory inv = (IInventory)target;
									
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
							}
						}
					}
				}
			}
			
			// Down direction - Up block side input
			else if (dimWorld != null && this.currDirection == 4)
			{
				for (int i = 1; i < 15; i++)
				{
					for (int k = 1; k < 15; k++)
					{
						tile = dimWorld.getTileEntity(new BlockPos(i,15,k));
						
						if (tile != null && tile instanceof TileEntitySingularityDimensionWall)
						{
							wall = (TileEntitySingularityDimensionWall)tile;
							
							if (wall.getTransport())
							{
								TileEntity target;
								
								target = dimWorld.getTileEntity(new BlockPos(i,14,k));
								
								if (target != null && target instanceof IInventory)
								{
									IInventory inv = (IInventory)target;
									
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
							}
						}
					}
				}
			}
		}
		
		return 0;
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) 
	{
		this.markDirty();
		
		if (this.getDimensionID() != -999)
		{
			WorldServer dimWorld = DimensionManager.getWorld(this.getDimensionID());
			TileEntity tile;
			TileEntitySingularityDimensionWall wall;
			
			// West direction - east block side input
			if (dimWorld != null && this.currDirection == 1)
			{
				for (int j = 1; j < 15; j++)
				{
					for (int k = 1; k < 15; k++)
					{
						tile = dimWorld.getTileEntity(new BlockPos(15,j,k));
						
						if (tile != null && tile instanceof TileEntitySingularityDimensionWall)
						{
							wall = (TileEntitySingularityDimensionWall)tile;
							
							if (wall.getTransport())
							{
								TileEntity target;
								
								target = dimWorld.getTileEntity(new BlockPos(14,j,k));
								
								if (target != null && target instanceof IInventory)
								{
									IInventory inv = (IInventory)target;
									
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
							}
						}
					}
				}
			}
			
			// East direction - west block side input
			else if (dimWorld != null && this.currDirection == 3)
			{
				for (int j = 1; j < 15; j++)
				{
					for (int k = 1; k < 15; k++)
					{
						tile = dimWorld.getTileEntity(new BlockPos(0,j,k));
						
						if (tile != null && tile instanceof TileEntitySingularityDimensionWall)
						{
							wall = (TileEntitySingularityDimensionWall)tile;
							
							if (wall.getTransport())
							{
								TileEntity target;
								
								target = dimWorld.getTileEntity(new BlockPos(1,j,k));
								
								if (target != null && target instanceof IInventory)
								{
									IInventory inv = (IInventory)target;
									
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
							}
						}
					}
				}
			}
			
			// North direction - South block side input
			else if (dimWorld != null && this.currDirection == 2)
			{
				for (int j = 1; j < 15; j++)
				{
					for (int i = 1; i < 15; i++)
					{
						tile = dimWorld.getTileEntity(new BlockPos(i,j,15));
						
						if (tile != null && tile instanceof TileEntitySingularityDimensionWall)
						{
							wall = (TileEntitySingularityDimensionWall)tile;
							
							if (wall.getTransport())
							{
								TileEntity target;
								
								target = dimWorld.getTileEntity(new BlockPos(i,j,14));
								
								if (target != null && target instanceof IInventory)
								{
									IInventory inv = (IInventory)target;
									
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
							}
						}
					}
				}
			}
			
			// South direction - North block side input
			else if (dimWorld != null && this.currDirection == 0)
			{
				for (int j = 1; j < 15; j++)
				{
					for (int i = 1; i < 15; i++)
					{
						tile = dimWorld.getTileEntity(new BlockPos(i,j,0));
						
						if (tile != null && tile instanceof TileEntitySingularityDimensionWall)
						{
							wall = (TileEntitySingularityDimensionWall)tile;
							
							if (wall.getTransport())
							{
								TileEntity target;
								
								target = dimWorld.getTileEntity(new BlockPos(i,j,1));
								
								if (target != null && target instanceof IInventory)
								{
									IInventory inv = (IInventory)target;
									
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
							}
						}
					}
				}
			}
			
			// Up direction - Down block side input
			else if (dimWorld != null && this.currDirection == 5)
			{
				for (int i = 1; i < 15; i++)
				{
					for (int k = 1; k < 15; k++)
					{
						tile = dimWorld.getTileEntity(new BlockPos(i,0,k));
						
						if (tile != null && tile instanceof TileEntitySingularityDimensionWall)
						{
							wall = (TileEntitySingularityDimensionWall)tile;
							
							if (wall.getTransport())
							{
								TileEntity target;
								
								target = dimWorld.getTileEntity(new BlockPos(i,1,k));
								
								if (target != null && target instanceof IInventory)
								{
									IInventory inv = (IInventory)target;
									
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
							}
						}
					}
				}
			}
			
			// Down direction - Up block side input
			else if (dimWorld != null && this.currDirection == 4)
			{
				for (int i = 1; i < 15; i++)
				{
					for (int k = 1; k < 15; k++)
					{
						tile = dimWorld.getTileEntity(new BlockPos(i,15,k));
						
						if (tile != null && tile instanceof TileEntitySingularityDimensionWall)
						{
							wall = (TileEntitySingularityDimensionWall)tile;
							
							if (wall.getTransport())
							{
								TileEntity target;
								
								target = dimWorld.getTileEntity(new BlockPos(i,14,k));
								
								if (target != null && target instanceof IInventory)
								{
									IInventory inv = (IInventory)target;
									
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
							}
						}
					}
				}
			}
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
