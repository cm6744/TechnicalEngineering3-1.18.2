package ten3.core.recipe;

import com.google.gson.JsonObject;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.ShapedRecipe;
import ten3.TConst;

public class SingleSerial<T extends SingleRecipe> extends BaseSerial implements CmSerializer<T> {

    public final ResourceLocation regName;
    private final IFactoryCm<T> factory;

    public SingleSerial(IFactoryCm<T> factory, String reg) {

        regName = new ResourceLocation(TConst.modid, reg);
        this.factory = factory;

    }

    @Override
    public String id() {
        return regName.getPath();
    }

    public T fromJson(ResourceLocation recipeId, JsonObject json) {

        ItemStack res;
        CmItemList ingredient;
        ItemStack addition;

        res = getStackJSON(json, "output");
        addition = getStackJSON(json, "addition");
        ingredient = getIngJSON(json);

        int i = JsonParser.getIntOr(json, "time", fallBackTime);
        int c = JsonParser.getIntOr(json, "count", 1);
        float cc = JsonParser.getFloatOr(json, "chance", -1);

        return factory.create(regName, recipeId, ingredient, res, addition, i, c, cc);

    }

    public T fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {

        CmItemList ingredient = CmItemList.parseFrom(buffer);
        ItemStack res = buffer.readItem();
        ItemStack add = buffer.readItem();
        int cook = buffer.readVarInt();
        int count = buffer.readVarInt();
        double cc = buffer.readDouble();

        return factory.create(regName, recipeId, ingredient, res, add, cook, count, cc);
    }

    public void toNetwork(FriendlyByteBuf buffer, T recipe) {

        recipe.ingredient.writeTo(buffer);
        buffer.writeItem(recipe.result);
        buffer.writeItem(recipe.addition);
        buffer.writeVarInt(recipe.time);
        buffer.writeVarInt(recipe.count);
        buffer.writeDouble(recipe.chance);

    }

    public static ItemStack getStackJSON(JsonObject json, String name) {

        if(json.has(name)) {
            if(json.get(name).isJsonObject()) {
                return ShapedRecipe.itemStackFromJson(JsonParser.getJsonObject(json, name));
            } else {
                String s1 = JsonParser.getString(json, name);
                return new ItemStack(Registry.ITEM.getOptional(new ResourceLocation(s1)).orElse(Items.AIR));
            }
        }

        return ItemStack.EMPTY;

    }

    public static CmItemList getIngJSON(JsonObject json) {

        try {
            return CmItemList.parseFrom(JsonParser.getJsonObject(json, "ingredient"));
        } catch(Exception e) {
            e.printStackTrace();
            return new CmItemList();
        }

    }

    public static CmItemList getIngJSON(JsonObject json, int index) {

        try {
            return CmItemList.parseFrom(JsonParser.getJsonObject(json, "ingredient" + index));
        } catch(Exception e) {
            e.printStackTrace();
            return new CmItemList();
        }

    }

}
