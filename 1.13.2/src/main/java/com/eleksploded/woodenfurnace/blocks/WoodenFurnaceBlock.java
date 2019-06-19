package com.eleksploded.woodenfurnace.blocks;

import com.eleksploded.woodenfurnace.tile.TileWoodFurnace;

import net.minecraft.block.BlockFurnace;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class WoodenFurnaceBlock extends BlockFurnace {

	public WoodenFurnaceBlock(Properties builder) {
		super(builder);
	}


	public TileEntity createNewTileEntity(IBlockReader worldIn) {
		return new TileWoodFurnace();
	}

	public boolean onBlockActivated(IBlockState state, World worldIn, BlockPos pos, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (worldIn.isRemote) {
			return true;
		} else {
			TileEntity tileentity = worldIn.getTileEntity(pos);
			if (tileentity instanceof TileWoodFurnace) {
				player.displayGUIChest((TileWoodFurnace)tileentity);
			}

			return true;
		}
	}

	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		if (stack.hasDisplayName()) {
			TileEntity tileentity = worldIn.getTileEntity(pos);
			if (tileentity instanceof TileWoodFurnace) {
				((TileWoodFurnace)tileentity).setCustomName(stack.getDisplayName());
			}
		}

	}

	public void onReplaced(IBlockState state, World worldIn, BlockPos pos, IBlockState newState, boolean isMoving) {
		if (state.getBlock() != newState.getBlock()) {
			TileEntity tileentity = worldIn.getTileEntity(pos);
			if (tileentity instanceof TileWoodFurnace) {
				InventoryHelper.dropInventoryItems(worldIn, pos, (TileWoodFurnace)tileentity);
			}

			super.onReplaced(state, worldIn, pos, newState, isMoving);
		}
	}
}
