package com.limplungs.blockhole.dimensions;

import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;

public class DimensionList 
{
	public static int SINGULARITY_ID = 277301;
	
	public static int registerNewDimension()
	{
		int id = DimensionManager.getNextFreeDimId();
		
		DimensionManager.registerDimension(id, DimensionType.getById(SINGULARITY_ID));
		
		return id;
	}
}
