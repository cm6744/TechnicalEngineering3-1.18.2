package ten3.lib.recipe;

import com.google.gson.JsonObject;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;
import ten3.lib.tile.extension.CmTileMachineRecipe;
import ten3.util.TagUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class FormsCombinedIngredient
{

    String type;//inputUsed
    String form;
    //ITEM
    Collection<Item> matchItems = new ArrayList<>();//static used
    TagKey<Item> ifTagItem;//tagged used
    //END
    //FLUID
    Collection<Fluid> matchFluids = new ArrayList<>();
    TagKey<Fluid> ifTagFluid;
    //END
    ResourceLocation key;
    int amountOrCount;
    double chance;//outputUsed

    public List<ItemStack> itemStacks()
    {
        List<ItemStack> lst = new ArrayList<>();
        for(Item i : matchItems) {
            lst.add(new ItemStack(i, amountOrCount));
        }
        return lst;
    }

    public List<FluidStack> fluidStacks()
    {
        List<FluidStack> lst = new ArrayList<>();
        for(Fluid i : matchFluids) {
            lst.add(new FluidStack(i, amountOrCount));
        }
        return lst;
    }

    public boolean check(CmTileMachineRecipe tile)
    {
        boolean ret = false;
        switch(form) {
            case "item":
                ItemStack k;
                for(int i = 0; i < tile.inventory.getContainerSize(); i++) {
                    if(!tile.inventory.isUsed(i)
                    || tile.slotType(i) != CmTileMachineRecipe.RecipeCheckType.INPUT) continue;
                    k = tile.inventory.getItem(i);
                    if(k.getCount() < amountOrCount) {
                        continue;
                    }
                    switch(type) {
                        case "tag":
                            if(TagUtil.containsItem(k.getItem(), ifTagItem)) {
                                ret = true;
                            }
                            break;
                        case "static":
                            if(matchItems.contains(k.getItem())) {
                                ret = true;
                            }
                            break;
                    }
                }
                break;
            case "fluid":
                FluidStack f;
                for(int i = 0; i < tile.tanks.size(); i++) {
                    f = tile.tanks.get(i).getFluid();
                    if(tile.tankType(i) != CmTileMachineRecipe.RecipeCheckType.INPUT) continue;
                    if(f.getAmount() < amountOrCount) {
                        continue;
                    }
                    switch(type) {
                        case "tag":
                            if(TagUtil.containsFluid(f.getFluid(), ifTagFluid)) {
                                ret = true;
                            }
                            break;
                        case "static":
                            if(matchFluids.contains(f.getFluid())) {
                                ret = true;
                            }
                            break;
                    }
                }
        }
        return ret;
    }

    public Ingredient toOriginStackIngredients()
    {
        return type.equals("tag") ? tagged() : Ingredient.of(itemStacks().stream());
    }

    private Ingredient tagged()
    {
        Ingredient ing = Ingredient.of(ifTagItem);
        for(ItemStack s : ing.getItems()) {
            s.setCount(amountOrCount);
        }
        return ing;
    }

    private static Item parseItem(String i)
    {
        ResourceLocation rl = new ResourceLocation(i);
        Optional<Item> item = Registry.ITEM.getOptional(rl);
        if(item.isPresent() && item.get() != Items.AIR) {
            return item.get();
        }
        return Items.AIR;
    }

    private static Fluid parseFluid(String i)
    {
        ResourceLocation rl = new ResourceLocation(i);
        Optional<Fluid> fluid = Registry.FLUID.getOptional(rl);
        if(fluid.isPresent() && fluid.get() != Fluids.EMPTY) {
            return fluid.get();
        }
        return Fluids.EMPTY;
    }

    private static TagKey<Item> parseItemTag(String i)
    {
        ResourceLocation rl = new ResourceLocation(i);
        return TagUtil.keyItem(rl.toString());
    }

    private static TagKey<Fluid> parseFluidTag(String i)
    {
        ResourceLocation rl = new ResourceLocation(i);
        return TagUtil.keyFluid(rl.toString());
    }

    public static FormsCombinedIngredient parseFrom(JsonObject json) {

        String form = JsonParser.getString(json, "form");
        String type = JsonParser.getString(json, "type");
        String key = JsonParser.getString(json, "key");
        int lm = JsonParser.getIntOr(json, "count", 1);
        double chance = JsonParser.getFloatOr(json, "chance", 1);

        return create(lm, form, type, key, chance);
    }

    public static FormsCombinedIngredient parseFrom(FriendlyByteBuf buffer) {

        String form = buffer.readUtf();
        String type = buffer.readUtf();
        ResourceLocation rl = buffer.readResourceLocation();
        int lm = buffer.readInt();
        double chance = buffer.readDouble();

        return create(lm, form, type, rl.getPath(), chance);
    }

    public static FormsCombinedIngredient create(int limit, String form, String type, String key, double chance)
    {
        FormsCombinedIngredient ingredient = new FormsCombinedIngredient();
        ingredient.form = form;
        ingredient.type = type;
        ingredient.amountOrCount = limit;
        ingredient.key = new ResourceLocation(key);
        ingredient.chance = chance;

        switch(form) {
            case "item":
                switch(type) {
                    case "tag" -> {
                        ingredient.ifTagItem = parseItemTag(key);
                        ingredient.matchItems = TagUtil.getItemsTag(key);
                    }
                    case "static" -> ingredient.matchItems = List.of(parseItem(key));
                }
                break;
            case "fluid":
                switch(type) {
                    case "tag" -> {
                        ingredient.ifTagFluid = parseFluidTag(key);
                        ingredient.matchFluids = TagUtil.getFluidsTag(key);
                    }
                    case "static" -> ingredient.matchFluids = List.of(parseFluid(key));
                }
                break;
        }

        return ingredient;//cannot check item!
    }

    public void writeTo(FriendlyByteBuf buffer)
    {
        buffer.writeUtf(form);
        buffer.writeUtf(type);
        buffer.writeResourceLocation(key);
        buffer.writeInt(amountOrCount);
        buffer.writeDouble(chance);
    }

    //OUTPUT:

    public double chance()
    {
        return chance;
    }

    public ItemStack genItem()
    {
        if(Math.random() < chance) {
            return symbolItem();
        }
        return ItemStack.EMPTY;
    }

    public ItemStack symbolItem()
    {
        if(itemStacks().size() == 0) {
            return ItemStack.EMPTY;
        }
        return new ItemStack(itemStacks().get(0).getItem(), amountOrCount);
    }

    public FluidStack genFluid()
    {
        if(Math.random() < chance) {
            return symbolFluid();
        }
        return FluidStack.EMPTY;
    }

    public FluidStack symbolFluid()
    {
        if(fluidStacks().size() == 0) {
            return FluidStack.EMPTY;
        }
        return new FluidStack(fluidStacks().get(0).getFluid(), amountOrCount);
    }

}
