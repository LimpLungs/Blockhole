package com.limplungs.blockhole.dimensions;

import net.minecraft.entity.Entity;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class TeleporterSingularity extends Teleporter
{
	
	public TeleporterSingularity(WorldServer world) 
	{
		super(world);
	}

	@Override
	public boolean placeInExistingPortal(Entity entity, float rotationYaw) 
	{
		entity.setPosition(8.5,2,8.5);
		return false;
	}
	
	@Override
	public boolean makePortal(Entity entity) 
	{
		entity.setPosition(8.5,2,8.5);
		return false;
	}
	
	@Override
	public void placeInPortal(Entity entity, float rotationYaw) 
	{
		entity.setPosition(8.5,2,8.5);
	}
}
