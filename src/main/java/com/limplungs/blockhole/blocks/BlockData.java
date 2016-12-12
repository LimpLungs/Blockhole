package com.limplungs.blockhole.blocks;

import com.limplungs.blockhole.Blockhole;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockData 
{
	protected float hardness = 0.0F;
	protected float resistance = 0.0F;
	protected String tool = "pickaxe";
	protected int level = 0;
	
	protected String name = "NewBlock";
	protected String unlocalizedName = (Blockhole.MODID + "_" + name);
	protected Material material = Material.ROCK;
	protected CreativeTabs creativeTab = Blockhole.tabBlockhole;
	
	public BlockData(float hard, float resist, String tool, int level, String name, Material mat, CreativeTabs tab)
	{
		this.hardness = hard;
		this.resistance = resist;
		this.tool = tool;
		this.level = level;
		this.name = name;
		this.unlocalizedName = (Blockhole.MODID + "_" + name);
		this.material = mat;
		this.creativeTab = tab;
	}
}
