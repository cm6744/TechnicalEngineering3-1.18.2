package ten3.plugin.jei;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotTooltipCallback;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.ChatFormatting;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.fluids.FluidStack;
import ten3.lib.client.element.ElementFluid;
import ten3.lib.recipe.FormsCombinedIngredient;
import ten3.lib.recipe.FormsCombinedRecipe;

import java.util.List;

import static ten3.lib.tile.CmScreen.handler;

public abstract class JeiCmCategory extends JeiCategory<FormsCombinedRecipe>
{

    public JeiCmCategory(RecipeType<FormsCombinedRecipe> name, int u, int v, int w, int h)
    {
        super(name, u, v, w, h);
    }

    public Class<FormsCombinedRecipe> getRecipeClass()
    {
        return FormsCombinedRecipe.class;
    }

    public void input(int x, int y, int i, FormsCombinedRecipe recipe, IRecipeLayoutBuilder bd)
    {
        NonNullList<Ingredient> fs = recipe.getIngredients();
        if(fs.size() <= i) return;
        bd.addSlot(RecipeIngredientRole.INPUT, x + 1, y + 1)
                .addIngredients(fs.get(i));
    }

    public void inputLiquid(int x, int y, int i, FormsCombinedRecipe recipe, IRecipeLayoutBuilder bd)
    {
        List<FormsCombinedIngredient> fs = recipe.allInputFluids();
        if(fs.size() <= i) return;
        FormsCombinedIngredient fi = fs.get(i);
        bd.addSlot(RecipeIngredientRole.INPUT, x + 1, y + 1)
                .addIngredients(TEJeiPlugins.FLUID_TYPE, fi.fluidStacks())
                .setFluidRenderer(getCapFluid(fi.fluidStacks()), true, 16, 48);
    }

    private int getCapFluid(List<FluidStack> stacks)
    {
        int max = 0;
        for(FluidStack f : stacks)
        {
            max = Math.max(f.getAmount(), max);
        }
        return max;
    }

    public void output(int x, int y, int i, FormsCombinedRecipe recipe, IRecipeLayoutBuilder bd)
    {
        List<FormsCombinedIngredient> fs = recipe.allOutputItems();
        if(fs.size() <= i) return;
        FormsCombinedIngredient ing = fs.get(i);
        bd.addSlot(RecipeIngredientRole.OUTPUT, x + 1, y + 1)
                .addItemStack(ing.symbolItem())
                .addTooltipCallback(callbackChance(ing));
    }

    public void outputLiquid(int x, int y, int i, FormsCombinedRecipe recipe, IRecipeLayoutBuilder bd)
    {
        List<FormsCombinedIngredient> fs = recipe.allOutputFluids();
        if(fs.size() <= i) return;
        FormsCombinedIngredient ing = fs.get(i);
        bd.addSlot(RecipeIngredientRole.OUTPUT, x + 1, y + 1)
                .addIngredient(TEJeiPlugins.FLUID_TYPE, ing.symbolFluid())
                .addTooltipCallback(callbackChance(ing))
                .setFluidRenderer(getCapFluid(ing.fluidStacks()), true, 16, 48);;
    }

    private IRecipeSlotTooltipCallback callbackChance(FormsCombinedIngredient ing)
    {
        return (r, lst) -> {
            double chance = ing.chance();
            //if(chance >= 1) return;
            lst.add(new TextComponent(((int) (chance * 100)) + "%")
                            .withStyle(ChatFormatting.GOLD));
        };
    }

}
