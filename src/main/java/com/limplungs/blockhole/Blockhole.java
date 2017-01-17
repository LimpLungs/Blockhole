package com.limplungs.blockhole;

import com.limplungs.blockhole.blocks.BlockList;
import com.limplungs.blockhole.entities.EntityPowderKeg;
import com.limplungs.blockhole.items.ItemList;
import com.limplungs.blockhole.render.RenderPowderKeg;
import com.limplungs.blockhole.tileentities.TileEntityList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(modid = Blockhole.MODID, version = Blockhole.VERSION)
public class Blockhole
{
    public static final String MODID = "blockhole";
    public static final String VERSION = "1.0alpha1";
    
    //Ideas
    //Blockhole Teleporter
    //Bitreader -- redstone repeater style?
    
    @EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
    	BlockList.registerBlocks();
    	ItemList.registerItems();
    	TileEntityList.registerTileEntities();
    	
    	RecipesList.addShaped();
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		if(event.getSide() == Side.CLIENT)
		{
			// Render Item
			
			RenderItem renderer = Minecraft.getMinecraft().getRenderItem();
			
			BlockList.renderBlocks(renderer);
			ItemList.renderItems(renderer);
			
			
			// Render Manager
			EntityRegistry.registerModEntity(EntityPowderKeg.class, "entityPowderKeg", 10, "blockhole", 100, 1, false);
			
			RenderManager factory = Minecraft.getMinecraft().getRenderManager();
			RenderingRegistry.registerEntityRenderingHandler(EntityPowderKeg.class, new RenderPowderKeg(factory));
			
		}
	}
	
	public static CreativeTabs tabBlockhole = new CreativeTabs("tabBlockhole")
	{
		@Override
		@SideOnly(Side.CLIENT)
		public Item getTabIconItem()
		{
			return Item.getItemFromBlock(BlockList.TELEPORTER);
		}
	};
}
