package ten3.core.recipe;

import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;
import ten3.TConst;

import java.util.ArrayList;
import java.util.List;

import static ten3.core.recipe.SingleSerial.getIngJSON;
import static ten3.core.recipe.SingleSerial.getStackJSON;

public class MTSSerial<T extends MTSRecipe> extends BaseSerial implements CmSerializer<T> {

    public final ResourceLocation regName;
    private final IFactoryMTSCm<T> factory;
    public int size;

    public MTSSerial(IFactoryMTSCm<T> factory, String reg, int s) {

        regName = new ResourceLocation(TConst.modid, reg);
        this.factory = factory;
        size = s;

    }

    @Override
    public String id() {
        return regName.getPath();
    }

    public T fromJson(ResourceLocation recipeId, JsonObject json) {

        ItemStack res;
        List<CmItemList> igs = new ArrayList<>();
        ItemStack addition;

        res = getStackJSON(json, "output");
        addition = getStackJSON(json, "addition");
        for(int i = 0; i < size; i++) {
            igs.add(getIngJSON(json, i));
        }

        int i = JsonParser.getIntOr(json, "time", fallBackTime);
        int c = JsonParser.getIntOr(json, "count", 1);
        float cc = JsonParser.getFloatOr(json, "chance", -1);

        return factory.create(regName, recipeId, igs, res, addition, i, c, cc);

    }

    public T fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
        List<CmItemList> igs = new ArrayList<>();
        for(int i = 0; i < size; i++) {
            igs.add(CmItemList.parseFrom(buffer));
        }
        ItemStack res = buffer.readItem();
        ItemStack add = buffer.readItem();
        int cook = buffer.readVarInt();
        int count = buffer.readVarInt();
        double cc = buffer.readDouble();

        return factory.create(regName, recipeId, igs, res, add, cook, count, cc);
    }

    public void toNetwork(FriendlyByteBuf buffer, T recipe) {

        for(CmItemList l : recipe.ingredients) {
            l.writeTo(buffer);
        }
        buffer.writeItem(recipe.result);
        buffer.writeItem(recipe.addition);
        buffer.writeVarInt(recipe.time);
        buffer.writeVarInt(recipe.count);
        buffer.writeDouble(recipe.chance);

    }

}
