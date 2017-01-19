package com.limplungs.blockhole.tileentities;

import com.limplungs.blockhole.DoubleLinkedQueue;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.capabilities.Capability;

public class TileEntityTeleporter extends TileEntity implements IInventory
{
	public int tp_x = this.getPos().getX();
	public int tp_y = this.getPos().getY();  
	public int tp_z = this.getPos().getZ();
	public BlockPos loc = new BlockPos(0,0,0);
	public DoubleLinkedQueue queue = new DoubleLinkedQueue(this);
	public boolean isOn = false;
	
	public TileEntityTeleporter()
	{
		if (loc.getX() != pos.getX() || loc.getY() != pos.getY() || loc.getZ() != pos.getZ())
		{
			tp_x = this.getPos().getX();
			tp_y = this.getPos().getY();
			tp_z = this.getPos().getZ();
			loc  = this.getPos();
		}
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) 
	{
		compound.setInteger("tp_x", this.tp_x);
		compound.setInteger("tp_y", this.tp_y);
		compound.setInteger("tp_z", this.tp_z);
		compound.setInteger("loc_x", loc.getX());
		compound.setInteger("loc_y", loc.getY());
		compound.setInteger("loc_z", loc.getZ());
		compound.setBoolean("isOn", isOn);
		
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
		isOn = compound.getBoolean("isOn");
		
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
		
		if (tp_x == loc.getX() && tp_y == loc.getY() && tp_z == loc.getZ())
		{
			this.setCoordinate(xyz, value);
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

	@Override
	public int getSizeInventory() 
	{
		return queue.getSize() + 1;
	}

	@Override
	public ItemStack getStackInSlot(int index) 
	{
		if (index != queue.getSize() || queue.getSize() == 0)
		{
			return null;
		}

		this.markDirty();

		return queue.getStackAtNode(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int count) 
	{
		if (index != queue.getSize() || queue.getSize() == 0)
		{
			return null;
		}
		
		this.markDirty();
	
		return queue.pop_back();
	}

	/**
	 * This can only remove from the rear.
	 */
	@Override
	public ItemStack removeStackFromSlot(int index) 
	{
		if (index != queue.getSize() || queue.getSize() == 0)
		{
			return null;
		}
		
		this.markDirty();
		
		return queue.pop_back();
	}

	/**
	 * This can only insert to the rear.
	 */
	@Override
	public void setInventorySlotContents(int index, ItemStack stack) 
	{
		// index == size addresses when an item gets pulled to be put into a new inventory 
		// but is found out to be invalid for new inventory, ie hoppers
		if (index == this.getSizeInventory())
		{
			index -= 1;
		}
		
		if (this.isItemValidForSlot(index, stack))
		{
			queue.insert_back(new ItemStack(stack.getItem(), 1, stack.getMetadata()), stack.getTagCompound());
		}
		
		this.markDirty();
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) 
	{
		return (stack.getItem() instanceof ItemBlock && index + 1 == this.getSizeInventory()) ? true : false;
	}

	@Override
	public int getInventoryStackLimit() 
	{
		return 1;
	}

	@Override
	public void markDirty() 
	{
		super.markDirty();
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) 
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
	
}
