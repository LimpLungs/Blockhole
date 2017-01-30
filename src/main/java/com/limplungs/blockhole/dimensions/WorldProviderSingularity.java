package com.limplungs.blockhole.dimensions;

import net.minecraft.init.Biomes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class WorldProviderSingularity extends WorldProvider
{
	
	@Override
	protected void createBiomeProvider() 
	{
		this.biomeProvider = new BiomeProviderSingle(Biomes.VOID);
        this.isHellWorld = false;
        this.hasNoSky = true;
	}
	
	public DimensionType getDimensionType()
    {
        return DimensionType.getById(DimensionList.SINGULARITY_ID);
    }
	
	@Override
	public boolean isSurfaceWorld() 
	{
		return false;
	}
	
	public IChunkGenerator createChunkGenerator()
	{
		return new ChunkProviderSingularity(this.worldObj);
	}

    @SideOnly(Side.CLIENT)
    public boolean doesXZShowFog(int x, int z)
    {
        return true;
    }
    
    @Override
    public WorldBorder createWorldBorder() 
    {
    	WorldBorder border = new WorldBorder();
    	border.setCenter(0, 0);
    	border.setSize(7);
    	return border;
    }
    
    @Override
    public BlockPos getSpawnCoordinate() 
    {
    	return new BlockPos(8.5,5,8.5);
    }
    
    @Override
    public BlockPos getSpawnPoint() 
    {
    	return this.getSpawnCoordinate();
    }
    
    @Override
    public BlockPos getRandomizedSpawnPoint() 
    {
    	return this.getSpawnCoordinate();
    }
    
    @Override
    public boolean shouldMapSpin(String entity, double x, double y, double z) 
    {
    	return true;
    }
    
    @Override
    public boolean canRespawnHere() 
    {
    	return false;
    }
    
    @Override
    public float calculateCelestialAngle(long worldTime, float partialTicks) 
    {
    	return 0.75F;
    }
    
    @Override
    public boolean getHasNoSky() 
    {
    	return this.hasNoSky;
    }
    
    @Override
    public double getVoidFogYFactor() 
    {
    	return 1;
    }
    
    @Override
    public float getSunBrightness(float par1) 
    {
    	return 0.0F;
    }
    
    @Override
    public float getSunBrightnessFactor(float par1) 
    {
    	return 0.0F;
    }
    
    @SideOnly(Side.CLIENT)
    public float getCloudHeight()
    {
        return 200.0F;
    }
}
