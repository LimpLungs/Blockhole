package com.limplungs.blockhole;

import com.limplungs.blockhole.dimensions.WorldProviderSingularity;
import com.limplungs.blockhole.entities.EntityPowderKeg;
import com.limplungs.blockhole.lists.BlockList;
import com.limplungs.blockhole.lists.DimensionList;
import com.limplungs.blockhole.lists.ItemList;
import com.limplungs.blockhole.lists.TileEntityList;
import com.limplungs.blockhole.render.RenderPowderKeg;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.MinecraftForge;
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
    public static final String VERSION = "1.0alpha14";
    
    public static BlockholeBasicEvents event_handler = new BlockholeBasicEvents();
    public static BlockholeRegisterEvents register_handler = new BlockholeRegisterEvents();
    
    //Ideas
    //Blockhole Teleporter
    //Bitreader -- redstone repeater style?
    
    @EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
    	// Registers basic and early events.
    	MinecraftForge.EVENT_BUS.register(event_handler);
    	
    	BlockholeDefinitions.initialize();
    	
    	BlockList.registerBlockData();
    	BlockList.registerBlocks();
    	BlockList.registerItemBlocks();
    	
    	ItemList.registerItemData();
    	ItemList.registerItems();
    	
    	TileEntityList.registerTileEntities();
    	
		DimensionType.register("SINGULARITY", "_singularity", DimensionList.SINGULARITY_ID, WorldProviderSingularity.class, true);
		
		// Registers Blocks AFTER they are populated.
    	MinecraftForge.EVENT_BUS.register(register_handler);
	}

    
    
	@SuppressWarnings("deprecation")
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		if(event.getSide() == Side.CLIENT)
		{
			// Render Blocks & Items
			RenderItem renderer = Minecraft.getMinecraft().getRenderItem();
			
			BlockList.renderBlocks(renderer);
			ItemList.renderItems(renderer);
			
			
			// Render Powder Keg
			ResourceLocation rlpowderkeg = new ResourceLocation(MODID, "powderkeg");
			EntityRegistry.registerModEntity(rlpowderkeg, EntityPowderKeg.class, "entityPowderKeg", 1, Blockhole.MODID, 100, 1, true);
			
			RenderManager factory = Minecraft.getMinecraft().getRenderManager();
			RenderingRegistry.registerEntityRenderingHandler(EntityPowderKeg.class, new RenderPowderKeg(factory));
		}
	}

	
	
	public static CreativeTabs tabBlockhole = new CreativeTabs("tabBlockhole")
	{
		@Override
		@SideOnly(Side.CLIENT)
		public ItemStack getTabIconItem()
		{
			return new ItemStack(BlockList.PORTAL);
		}
	};
}