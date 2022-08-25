package ten3.core.recipe;

import com.google.common.collect.Lists;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.registries.tags.ITag;
import ten3.util.TagUtil;

import java.util.*;

public class CmItemList {

    String type;
    Collection<Item> matches;
    TagKey<Item> tagKey;
    ResourceLocation loc;
    boolean isTag;
    int limit;

    public Collection<ItemStack> stackLstOf() {
        List<ItemStack> lst = new ArrayList<>();
        for(Item i : matches)
            lst.add(new ItemStack(i));
        return lst;
    }

    public CmItemList(Item s, int lm, ResourceLocation rl) {
        matches = Lists.newArrayList(s);
        type = "item";
        loc = rl;
        limit = lm;
    }

    public CmItemList(TagKey<Item> s, int lm, ResourceLocation rl) {
        matches = Lists.newArrayList();
        tagKey = s;
        type = "tag";
        isTag = true;
        loc = rl;
        limit = lm;
    }

    public CmItemList() {
        matches = Lists.newArrayList(Items.AIR);
        type = "empty";
        limit = 1;
    }

    public boolean hasValidIn(ItemStack... s) {
        boolean ret = false;
        for(ItemStack k : s) {
            if(type.equals("empty")) {
                if(k.isEmpty())
                    ret = true;
            }
            else {
                if(vanillaIngre().test(k) && k.getCount() >= limit)
                    ret = true;
            }
        }
        return ret;
    }

    public Ingredient vanillaIngre() {
        return !isTag ? Ingredient.of(stackLstOf().stream()) : Ingredient.of(tagKey);
    }

    private static CmItemList GET_ITEM(String i, int im) {
        ResourceLocation rl = new ResourceLocation(i);
        Optional<Item> item = Registry.ITEM.getOptional(rl);
        if(item.isPresent() && item.get() != Items.AIR) {
            return new CmItemList(item.get(), im, rl);
        }
        return new CmItemList();
    }

    private static CmItemList GET_TAG(String i, int im) {
        ResourceLocation rl = new ResourceLocation(i);
        TagKey<Item> tag = TagUtil.key(rl.toString());
        if (tag == null) {
            throw new JsonSyntaxException("Empty item tag '" + rl + "'");
        } else {
            return new CmItemList(tag, im, rl);
        }
    }

    public static CmItemList parseFrom(JsonObject json) {

        int lm = JsonParser.getIntOr(json, "count", 1);

        if (json.has("item")) {
            return GET_ITEM(JsonParser.getString(json, "item"), lm);
        } else if (json.has("tag")) {
            return GET_TAG(JsonParser.getString(json, "tag"), lm);
        }

        return new CmItemList();//cannot check item!

    }

    public static CmItemList parseFrom(FriendlyByteBuf buffer) {

        String type = buffer.readUtf();
        ResourceLocation rl = buffer.readResourceLocation();
        int lm = buffer.readInt();

        if(type.equals("item")) {
            return GET_ITEM(rl.toString(), lm);
        }
        else if(type.equals("tag")) {
            return GET_TAG(rl.toString(), lm);
        }

        return new CmItemList();//cannot check item!

    }

    public void writeTo(FriendlyByteBuf buffer) {

        buffer.writeUtf(type);
        buffer.writeResourceLocation(loc);
        buffer.writeInt(limit);

    }

}
