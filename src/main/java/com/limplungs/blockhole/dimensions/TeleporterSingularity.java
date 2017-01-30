package com.limplungs.blockhole.dimensions;

import net.minecraft.entity.Entity;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class TeleporterSingularity extends Teleporter
{
	
	public TeleporterSingularity(WorldServer worldIn) 
	{
		super(worldIn);
	}

	@Override
	public boolean placeInExistingPortal(Entity entityIn, float rotationYaw) 
	{
		entityIn.setPosition(8.5,2,8.5);
		return false;
	}
	
	@Override
	public boolean makePortal(Entity entityIn) 
	{
		entityIn.setPosition(8.5,2,8.5);
		return false;
	}
	
	@Override
	public void placeInPortal(Entity entityIn, float rotationYaw) 
	{
		entityIn.setPosition(8.5,2,8.5);
	}
}
