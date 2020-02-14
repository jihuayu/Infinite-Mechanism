package jihuayu.cube;

import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.BakedModelWrapper;
import net.minecraftforge.client.model.data.IModelData;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class IMModel extends BakedModelWrapper<IBakedModel> {
    public IMModel(IBakedModel original) {
        super(original);
    }

    private final ItemOverrideList itemHandler = new ItemOverrideList() {
        @Override
        public IBakedModel getModelWithOverrides(@Nonnull IBakedModel original, @Nonnull ItemStack stack,
                                                 @Nullable World world, @Nullable LivingEntity entity) {
            CompoundNBT tag = stack.getTag();
            if (tag == null || !tag.contains("imit")) {
                return original;
            }
            ModelResourceLocation modelPath = new ModelResourceLocation(new ResourceLocation(tag.getString("imit")), "inventory");
            return Minecraft.getInstance().getModelManager().getModel(modelPath);
        }
    };

    @Nonnull
    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull Random rand, @Nonnull IModelData extraData) {
        if (extraData.getData(IMTile.IMBT) != null)
            return Minecraft.getInstance().getModelManager().getModel(extraData.getData(IMTile.IMBT)).getQuads(state, side, rand, extraData);
        return new ArrayList<>();
    }

    @Nonnull
    @Override
    public ItemOverrideList getOverrides() {
        return itemHandler;
    }
}
