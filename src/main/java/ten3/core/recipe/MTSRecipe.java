package ten3.core.recipe;

import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class MTSRecipe extends SingleRecipe {
    
    public List<CmItemList> ingredients;

    public MTSRecipe(ResourceLocation regName, ResourceLocation idIn,
                     List<CmItemList> ings, ItemStack resultIn, ItemStack add,
                     int cookTimeIn, int countOut, double cc) {

        super(regName, idIn, new CmItemList(), resultIn, add, cookTimeIn, countOut, cc);
        this.ingredients = ings;
    }

    @Override
    public int time()
    {
        return time;
    }

    @Override
    public boolean matches(Container inv, Level worldIn)
    {
        List<ItemStack> invs = new ArrayList<>();
        for(int i = 0; i < ingredients.size(); i++) {
            invs.add(inv.getItem(i));
        }
        for(CmItemList cmItemList : ingredients) {
            if(!cmItemList.hasValidIn(invs.toArray(new ItemStack[0]))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public NonNullList<Ingredient> getIngredients()
    {
        NonNullList<Ingredient> nonNullList = NonNullList.create();//fucking mojang extends AbstractList
        for(CmItemList lst : ingredients) {
            if(lst != null)
            nonNullList.add(lst.vanillaIngre());
        }
        return nonNullList;
    }

    @Override
    public int inputLimit(ItemStack stack)
    {
        for(CmItemList lst : ingredients) {
            if(lst.hasValidIn(stack)) {
                return lst.limit;
            }
        }
        return 0;
    }

}
