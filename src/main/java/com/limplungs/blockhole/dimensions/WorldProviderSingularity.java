package com.limplungs.blockhole.dimensions;

import com.limplungs.blockhole.lists.DimensionList;

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
	public WorldProviderSingularity() 
	{
		this.biomeProvider = new BiomeProviderSingle(Biomes.VOID);
        this.hasSkyLight = false;
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
		return new ChunkProviderSingularity(this.world);
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
    	return true;
    }
    
    @Override
    public float calculateCelestialAngle(long worldTime, float partialTicks) 
    {
    	return 0.75F;
    }
    
    @Override
    public double getVoidFogYFactor() 
    {
    	return 0.03125;
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
    
    @Override
    protected void generateLightBrightnessTable() 
    {
    	for (int i = 0; i <= 15; ++i)
    	{
    		float f1 = 1.0F - (float)i / 15.0F;
    		this.lightBrightnessTable[i] = (1.0F - f1) / (f1 * 3.0F + 1.0F) * 1.0F + 0.0F;
    		this.lightBrightnessTable[i] *= .5F;
    	}
    }
}
