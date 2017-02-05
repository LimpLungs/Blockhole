package com.limplungs.blockhole.dimensions;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class TeleporterSingularity extends Teleporter
{
	public World world;
	public int dimension;
	public BlockPos position;
	
	public TeleporterSingularity(WorldServer world, int dimension, BlockPos blockPos) 
	{
		super(world);
		this.world = world;
		this.dimension = dimension;
		this.position = blockPos;
	}

	@Override
	public boolean placeInExistingPortal(Entity entity, float rotationYaw) 
	{
		entity.setPosition(this.position.getX(), this.position.getY(), this.position.getZ());
		
		return false;
	}
	
	@Override
	public boolean makePortal(Entity entity) 
	{
		entity.setPosition(this.position.getX(), this.position.getY(), this.position.getZ());
		
		return false;
	}
	
	@Override
	public void placeInPortal(Entity entity, float rotationYaw) 
	{
		entity.setPosition(this.position.getX(), this.position.getY(), this.position.getZ());
		
	}
}
