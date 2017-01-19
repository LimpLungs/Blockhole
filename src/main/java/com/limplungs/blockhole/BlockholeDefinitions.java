package com.limplungs.blockhole;

import net.minecraft.util.DamageSource;

public class BlockholeDefinitions 
{
	public static DamageSource teleporter;
	
	public static void initialize()
	{
		teleporter = new DamageSource("Teleporter");
		teleporter.setDamageBypassesArmor();
	}
}
