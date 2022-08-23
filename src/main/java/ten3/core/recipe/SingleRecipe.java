package ten3.core.recipe;

import com.google.common.collect.Lists;
import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import ten3.init.RecipeInit;

import java.util.List;

public class SingleRecipe implements OpportunityRecipe<Container> {

    protected final ResourceLocation id;
    protected CmItemList ingredient;
    protected final ItemStack result;
    protected final int time;
    protected final ResourceLocation reg;
    protected final int count;
    protected final double chance;
    protected final ItemStack addition;

    public SingleRecipe(ResourceLocation regName, ResourceLocation idIn,
                        CmItemList ingredient, ItemStack resultIn, ItemStack add,
                        int cookTimeIn, int countOut, double cc) {

        this.id = idIn;
        this.ingredient = ingredient;
        this.result = resultIn;
        this.time = cookTimeIn;
        reg = regName;
        count = countOut;
        chance = cc;
        addition = add;

    }

    @Override
    public NonNullList<Ingredient> getIngredients()
    {
        return NonNullList.of(Ingredient.EMPTY, ingredient.vanillaIngre());
    }

    @Override
    public int time() {
        return time;
    }

    @Override
    public boolean matches(Container inv, Level worldIn) {

        return ingredient.hasValidIn(inv.getItem(0));

    }

    @Override
    public ItemStack assemble(Container p_44001_)
    {
        ItemStack s = getResultItem().copy();
        s.setCount(count);

        return s;
    }

    @Override
    public boolean canCraftInDimensions(int p_43999_, int p_44000_)
    {
        return true;
    }

    @Override
    public ItemStack getAdditionOutput()
    {
        return addition;
    }

    public List<ItemStack> output() {
        return Lists.newArrayList(assemble(new SimpleContainer()));
    }

    @Override
    public double chance()
    {
        return chance;
    }

    @Override
    public ItemStack getResultItem()
    {
        return result;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeType<?> getType() {
        return RecipeInit.getRcpType(reg.getPath());
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeInit.getRcp(getId().getPath());
    }

    @Override
    public int inputLimit(ItemStack stack)
    {
        return ingredient.limit;
    }

}
