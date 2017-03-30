package com.limplungs.blockhole.lists;

import com.limplungs.blockhole.tileentities.TileEntitySingularityPortal;
import com.limplungs.blockhole.tileentities.TileEntitySingularityStorage;
import com.limplungs.blockhole.tileentities.TileEntitySingularityDimensionWall;

import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileEntityList 
{
	/**
	 * Tile Entity Register
	 * 
	 */
	
	public static void registerTileEntities()
	{
		GameRegistry.registerTileEntity(TileEntitySingularityStorage.class, "teStorage");
		GameRegistry.registerTileEntity(TileEntitySingularityPortal.class, "tePortal");
		GameRegistry.registerTileEntity(TileEntitySingularityDimensionWall.class, "teDimensionWall");
	}
}
