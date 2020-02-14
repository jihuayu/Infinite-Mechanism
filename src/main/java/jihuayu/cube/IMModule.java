package jihuayu.cube;

import net.minecraft.block.Blocks;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.registries.ObjectHolder;
import snownee.kiwi.AbstractModule;
import snownee.kiwi.KiwiModule;
import snownee.kiwi.Name;
import snownee.kiwi.NoItem;
import snownee.kiwi.test.TestTile;

@KiwiModule(name = IMMod.ID)
@KiwiModule.Group("building_blocks")
@KiwiModule.Subscriber(KiwiModule.Subscriber.Bus.MOD)
@OnlyIn(Dist.CLIENT)
public class IMModule extends AbstractModule {
    public static final IMItem IM_ITEM = new IMItem(itemProp());
    public static final IMBlock IM_BLOCK = new IMBlock(blockProp(Blocks.STONE));
    public static final TileEntityType<?> IM_TILE = TileEntityType.Builder.create(IMTile::new, IM_BLOCK).build(null);

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void replaceModel(ModelBakeEvent event) {
        ModelResourceLocation key = new ModelResourceLocation(IM_ITEM.getRegistryName(), "inventory");
        ModelResourceLocation key2 = BlockModelShapes.getModelLocation(IM_BLOCK.getDefaultState());
        ModelResourceLocation key3 = new ModelResourceLocation(IM_BLOCK.getRegistryName(), "inventory");
        IBakedModel oldModel = event.getModelRegistry().get(key);
        IBakedModel oldModel2 = event.getModelRegistry().get(key2);
        IBakedModel oldModel3 = event.getModelRegistry().get(key3);
        event.getModelRegistry().put(key, new IMModel(oldModel));
        event.getModelRegistry().put(key2, new IMModel(oldModel2));
        event.getModelRegistry().put(key3, new IMModel(oldModel3));
    }

}
