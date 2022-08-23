package ten3.util;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.tags.ITag;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TagUtil {

    public static boolean containsItem(Item t, String s) {
        return getItemsTag(s).contains(t);
    }

    public static TagKey<Item> key(String s) {
        return TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(s));
    }

    public static Collection<Item> getItemsTag(String s) {
        List<Item> lst = new ArrayList<>();
        for(ItemStack stk : new Ingredient.TagValue(key(s)).getItems()) {
            lst.add(stk.getItem());
        }
        return lst;
    }

    public static boolean containsItem(Item t, TagKey<Item> s) {
        return t.getDefaultInstance().is(s);
    }

    public static boolean containsBlock(Block t, String s) {
        return t.defaultBlockState().is(TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(s)));
    }

}
