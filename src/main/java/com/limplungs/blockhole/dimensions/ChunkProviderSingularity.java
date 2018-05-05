package com.limplungs.blockhole.dimensions;

import java.util.List;

import com.limplungs.blockhole.lists.BlockList;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.IChunkGenerator;

public class ChunkProviderSingularity implements IChunkGenerator 
{
	private World worldObj;
	
	public ChunkProviderSingularity(World world)
	{
		this.worldObj = world;
	}

	@Override
	public Chunk generateChunk(int x, int z) 
	{
		ChunkPrimer primer = new ChunkPrimer();
		
		for (int j = 0; j < 256; j++)
		{
			for (int i = 0; i < 16; i++)
			{
				for (int k = 0; k < 16; k++)
				{
					primer.setBlockState(i, j, k, Blocks.AIR.getDefaultState());
				}
			}
		}
		
		if (x == 0 && z == 0)
		{
			int i = 0;
			int j = 0;
			int k = 0;

			for (j = 0; j < 16; j+=15)
			{
				for (i = 1; i < 15; i++)
				{
					for (k = 1; k < 15; k++)
					{  
						primer.setBlockState(i, j, k, BlockList.DIMENSION_WALL.getDefaultState());
					}
				}
			}
			
			i = 0; j = 0; k = 0;
			
			for (j = 1; j < 15; j++)
			{
				k = 0;
				
				for (i = 1; i < 15; i++)
					primer.setBlockState(i, j, k, BlockList.DIMENSION_WALL.getDefaultState());
				
				k = 15;
				
				for (i = 1; i < 15; i++)
					primer.setBlockState(i, j, k, BlockList.DIMENSION_WALL.getDefaultState());
				
				i = 0;
				
				for (k = 1; k < 15; k++)
					primer.setBlockState(i, j, k, BlockList.DIMENSION_WALL.getDefaultState());
				
				i = 15;
				
				for (k = 1; k < 15; k++)
					primer.setBlockState(i, j, k, BlockList.DIMENSION_WALL.getDefaultState());
			}
		}
		
        Chunk chunk = new Chunk(this.worldObj, primer, x, z);
        
        // Biome handling from ChunkProviderFlat class. Not sure if necessary.
        Biome[] abiome = this.worldObj.getBiomeProvider().getBiomes((Biome[])null, x * 16, z * 16, 16, 16);
        byte[] abyte = chunk.getBiomeArray();

        for (int l = 0; l < abyte.length; ++l)
        {
            abyte[l] = (byte)Biome.getIdForBiome(abiome[l]);
        }
        
		return chunk;
	}

	@Override
	public void populate(int x, int z) 
	{
		
	}

	@Override
	public boolean generateStructures(Chunk chunkIn, int x, int z) 
	{
		return false;
	}

	@Override
	public List<SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) 
	{
		return null;
	}

	@Override
	public void recreateStructures(Chunk chunkIn, int x, int z) 
	{
		
	}

	@Override
	public BlockPos getNearestStructurePos(World worldIn, String structureName, BlockPos position, boolean findUnexplored) {
		
		return null;
	}

	@Override
	public boolean isInsideStructure(World worldIn, String structureName, BlockPos pos) 
	{
		return false;
	}
	
}
