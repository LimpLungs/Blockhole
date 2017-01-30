package com.limplungs.blockhole.tileentities;

import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileEntityList 
{
	/**
	 * Tile Entity Register
	 * 
	 */
	
	public static void registerTileEntities()
	{
		GameRegistry.registerTileEntity(TileEntityTeleporter.class, "teTeleporter");
		GameRegistry.registerTileEntity(TileEntityBlockhole.class, "teBlockhole");
		GameRegistry.registerTileEntity(TileEntityBlockholeWall.class, "teBlockholeWall");
	}
}
