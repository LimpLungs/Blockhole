package com.limplungs.blockhole.blocks;

import java.util.List;

import javax.annotation.Nullable;

import com.limplungs.blockhole.dimensions.TeleporterSingularity;
import com.limplungs.blockhole.tileentities.TileEntitySingularityDimensionWall;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class BlockSingularityDimensionWall extends BlockBasic
{
	public static final PropertyInteger INDEX = PropertyInteger.create("index", 0, 195);
	
	public BlockSingularityDimensionWall(BlockData blockdata) 
	{
		super(blockdata);
		this.setBlockUnbreakable();
		this.setResistance(4096F);
		this.setLightLevel(1.0F);
		this.setDefaultState(this.blockState.getBaseState().withProperty(INDEX, 0));
	}
	
	
	
	@Override
	protected BlockStateContainer createBlockState() 
	{
		return new BlockStateContainer(this, new IProperty[]{INDEX});
	}

	
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) 
	{
		return new TileEntitySingularityDimensionWall();
	}
	
	
	
	@Override
	public boolean hasTileEntity(IBlockState state) 
	{
		return true;
	}
	
	
	
	@Override
	public boolean isOpaqueCube(IBlockState state) 
	{
		return true;
	}
	
	
	
	@Override
	public boolean isFullCube(IBlockState state) 
	{
		return true;
	}
	
	
	
	@Override
	public BlockRenderLayer getBlockLayer() 
	{
		return BlockRenderLayer.TRANSLUCENT;
	}
	
	
	
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) 
	{
		int i = 0;
		
		if (pos.getX() == 0)
			i = (14 - pos.getY()) * 14 + (14 - pos.getZ());
		if (pos.getX() == 15)
			i = (14 - pos.getY()) * 14 + (pos.getZ()-1);
		if (pos.getZ() == 0)
			i = (14 - pos.getY()) * 14 + (pos.getX()-1);
		if (pos.getZ() == 15)
		i = (14 - pos.getY()) * 14 + (14 - pos.getX());
		if (pos.getY() == 0)
			i = (pos.getZ() - 1) * 14 + (pos.getX() - 1);
		if (pos.getY() == 15)
			i = (14 - pos.getZ()) * 14 + (pos.getX() - 1);
		
		if (i <= 196 && i >= 0)
			return state.withProperty(INDEX, i);
		
		else
			return state.withProperty(INDEX, 0);
	}
	
	
	
	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state) 
	{
		//state.
	}
	
	
	
	@Override
	public int getMetaFromState(IBlockState state) 
	{
		return 0;
	}
	
	
	
	@Override
	public IBlockState getStateFromMeta(int meta) 
	{
		return this.getDefaultState().withProperty(INDEX, 0);
	}

	
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,  EnumFacing side, float hitX, float hitY, float hitZ) 
	{
		if (player.isSneaking())
		{
			TileEntitySingularityDimensionWall tile = (TileEntitySingularityDimensionWall)world.getTileEntity(pos);
			
			if (tile.getDimensionID() != -999)
			{
				if (player instanceof EntityPlayerMP && world instanceof WorldServer)
				{
					player.getServer().getPlayerList().transferPlayerToDimension((EntityPlayerMP)player, tile.getDimensionID(), new TeleporterSingularity((WorldServer)world, player.dimension, tile.getTeleportLocation()));
					return true;
				}
			}
		}
		
		return false;
	}
	
	
	
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) 
	{
		return new ItemStack(Blocks.BEDROCK);
	}
	
	
	
	@Override
	public boolean isSideSolid(IBlockState base_state, IBlockAccess world, BlockPos pos, EnumFacing side) 
	{
		return true;
	}
	
	
	
	@Override
	public void onBlockDestroyedByPlayer(World world, BlockPos pos, IBlockState state) 
	{
		
	}
	
	
	
	@Override
	public void onBlockHarvested(World world, BlockPos pos, IBlockState state, EntityPlayer player) 
	{
		
	}
	
	
	
	@Override
	public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) 
	{ 
		return false;
	}
	
	
	
	@Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced)
    {
		tooltip.add("If you somehow got one of these blocks,\nDO NOT PLACE IT!\n");
		tooltip.add("If accidentally placed, remove it with\n/setblock x y z minecraft:air");
	}
}
