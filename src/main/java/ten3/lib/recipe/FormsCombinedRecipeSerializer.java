package ten3.lib.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import ten3.TConst;

import java.util.ArrayList;
import java.util.List;

public class FormsCombinedRecipeSerializer
        <T extends FormsCombinedRecipe> extends BaseSerial implements CmSerializer<T>
{

    public final ResourceLocation regName;
    private final IFactoryCm<T> factory;
    public int sizeOut;
    public int sizeInp;

    public FormsCombinedRecipeSerializer(IFactoryCm<T> fac, String reg, int si, int so) {

        regName = new ResourceLocation(TConst.modid, reg);
        factory = fac;
        sizeInp = si;
        sizeOut = so;
    }

    @Override
    public String id()
    {
        return regName.getPath();
    }

    public T fromJson(ResourceLocation recipeId, JsonObject json) {

        List<FormsCombinedIngredient> ip = getInputs(json);
        List<FormsCombinedIngredient> op = getOutputs(json);
        int time = JsonParser.getIntOr(json, "time", fallBackTime);

        return factory.create(regName, recipeId, ip, op, time);

    }

    public T fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {

        List<FormsCombinedIngredient> ip = new ArrayList<>();
        List<FormsCombinedIngredient> op = new ArrayList<>();
        for(int i = 0; i < sizeInp; i++) {
            ip.add(FormsCombinedIngredient.parseFrom(buffer));
        }
        for(int i = 0; i < sizeOut; i++) {
            op.add(FormsCombinedIngredient.parseFrom(buffer));
        }
        int cook = buffer.readInt();

        return factory.create(regName, recipeId, ip, op, cook);
    }

    public void toNetwork(FriendlyByteBuf buffer, T recipe) {

        for(FormsCombinedIngredient ip : recipe.input) {
            ip.writeTo(buffer);
        }
        for(FormsCombinedIngredient op : recipe.output) {
            op.writeTo(buffer);
        }
        buffer.writeInt(recipe.time);
    }

    public List<FormsCombinedIngredient> getInputs(JsonObject json)
    {
        List<FormsCombinedIngredient> lst = new ArrayList<>();
        JsonArray array = GsonHelper.getAsJsonArray(json, "inputs");
        for(JsonElement e : array) {
            JsonObject o = e.getAsJsonObject();
            lst.add(FormsCombinedIngredient.parseFrom(o));
        }
        while(lst.size() < sizeInp) {
            lst.add(EMPTY());
        }
        return lst;
    }

    private static FormsCombinedIngredient EMPTY()
    {
        FormsCombinedIngredient ig = FormsCombinedIngredient.create(0, "item", "static", "air", 1);
        ig.ALLOW_ALL = true;
        return ig;
    }

    public List<FormsCombinedIngredient> getOutputs(JsonObject json)
    {
        List<FormsCombinedIngredient> lst = new ArrayList<>();
        JsonArray array = GsonHelper.getAsJsonArray(json, "outputs");
        for(JsonElement e : array) {
            JsonObject o = e.getAsJsonObject();
            String form = JsonParser.getString(o, "form");
            String key = JsonParser.getString(o, "key");
            int count = 0;
            switch(form) {
                case "fluid":
                    count = JsonParser.getIntOr(o, "amount", 0);
                    break;
                case "item":
                    count = JsonParser.getIntOr(o, "count", 1);
                    break;
            }
            double chance = JsonParser.getFloatOr(o, "chance", 1);
            lst.add(FormsCombinedIngredient.create(count, form, "static", key, chance));
        }
        while(lst.size() < sizeOut) {
            lst.add(EMPTY());
        }
        return lst;
    }

}
