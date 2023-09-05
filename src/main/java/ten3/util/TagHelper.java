package ten3.util;

import com.google.common.collect.Lists;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.tags.ITag;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TagHelper
{

    public static Collection<Item> getItems(TagKey<Item> tag)
    {
        List<Item> list = Lists.newArrayList();

        for(Holder<Item> holder : Registry.ITEM.getTagOrEmpty(tag)) {
            list.add(holder.value());
        }

        return list;
    }

    public static Collection<Fluid> getFluids(TagKey<Fluid> tag)
    {
        List<Fluid> list = Lists.newArrayList();

        for(Holder<Fluid> holder : Registry.FLUID.getTagOrEmpty(tag)) {
            list.add(holder.value());
        }

        return list;
    }

    public static TagKey<Item> keyItem(String s) {
        return TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(s));
    }

    public static TagKey<Fluid> keyFluid(String s) {
        return TagKey.create(Registry.FLUID_REGISTRY, new ResourceLocation(s));
    }

    public static TagKey<Block> keyBlock(String s) {
        return TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(s));
    }

    public static boolean containsItem(Item t, TagKey<Item> s) {
        return t.getDefaultInstance().is(s);
    }

    public static boolean containsFluid(Fluid t, TagKey<Fluid> s) {
        return t.defaultFluidState().is(s);
    }

    public static boolean containsBlock(Block t, TagKey<Block> s) {
        return t.defaultBlockState().is(s);
    }

}
