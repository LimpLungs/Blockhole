package com.limplungs.blockhole;

import net.minecraft.util.DamageSource;

public class BlockholeDefinitions 
{
	public static DamageSource blackhole;
	
	public static void initialize()
	{
		blackhole = new DamageSource("A Blackhole");
		blackhole.setDamageBypassesArmor();
	}
}
