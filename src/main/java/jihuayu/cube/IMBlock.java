package jihuayu.cube;


import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import snownee.kiwi.block.ModBlock;

import java.util.ArrayList;
import java.util.List;

public class IMBlock extends ModBlock {
    //更多Material
    public IMBlock(Properties properties) {
        super(properties);
    }
    @Override
    public boolean hasTileEntity(BlockState state)
    {
        return true;
    }

    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new IMTile();
    }

    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        CompoundNBT nbt = stack.getTag();
        if (nbt != null && nbt.contains("imbt")) {
            TileEntity tileentity = worldIn.getTileEntity(pos);
            ModelResourceLocation i = new ModelResourceLocation(nbt.getString("imbt"));
            if (tileentity instanceof IMTile) {
                ((IMTile) tileentity).setLocation(i);
            }
        }
    }

    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        TileEntity tileentity = worldIn.getTileEntity(pos);
        System.out.println(tileentity);
        if (tileentity instanceof IMTile) {
            ModelResourceLocation location = ((IMTile) tileentity).getLocation();
            if (location != null) {
                CompoundNBT nbt = new CompoundNBT();
                nbt.putString("imbt", location.toString());
                ItemStack itemStack = new ItemStack(IMModule.IM_BLOCK, 1);
                itemStack.setTag(nbt);
                ItemEntity item = new ItemEntity(worldIn, pos.getX(), pos.getY(), pos.getZ());
                item.setItem(itemStack);
                worldIn.addEntity(item);
            }
        }
        super.onBlockHarvested(worldIn, pos, state, player);
    }

}
