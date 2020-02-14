package jihuayu.cube;

import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.data.ModelDataMap;
import net.minecraftforge.client.model.data.ModelProperty;
import net.minecraftforge.fml.common.thread.EffectiveSide;
import snownee.kiwi.tile.BaseTile;

public class IMTile extends BaseTile {
    public static ModelProperty<ModelResourceLocation> IMBT = new ModelProperty<>();
    private ModelResourceLocation location;
    protected IModelData modelData;

    public IMTile() {
        super(IMModule.IM_TILE);
        persistData = true;
    }

    public void setLocation(ModelResourceLocation location) {
        this.location = location;
        if (EffectiveSide.get().isClient()) {
            modelData = new ModelDataMap.Builder().withInitial(IMBT, location).build();
            requestModelDataUpdate();
        }
    }

    public ModelResourceLocation getLocation() {
        return location;
    }


    @Override
    public void requestModelDataUpdate() {
        super.requestModelDataUpdate();
        if (world != null && world.isRemote) {
            world.notifyBlockUpdate(pos, getBlockState(), getBlockState(), 8);
        }
    }

    @Override
    protected void readPacketData(CompoundNBT data) {
        if (!data.contains("imbt")) {
            return;
        }
        System.out.println(data.getString("imbt"));

        if (EffectiveSide.get().isClient()) {
            modelData = new ModelDataMap.Builder().withInitial(IMBT, new ModelResourceLocation(data.getString("imbt"))).build();
            requestModelDataUpdate();
        }
        location = new ModelResourceLocation(data.getString("imbt"));
    }

    @Override
    public IModelData getModelData() {
        return modelData == null ? EmptyModelData.INSTANCE : modelData;
    }

    @Override
    protected CompoundNBT writePacketData(CompoundNBT data) {
        if (location != null)
            data.putString("imbt", location.toString());
        return data;
    }

    @Override
    public void read(CompoundNBT compound) {
        readPacketData(compound);
        super.read(compound);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        writePacketData(compound);
        return super.write(compound);
    }
}
