package ten3.plugin.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.forge.ForgeTypes;
import mezz.jei.api.gui.handlers.IGlobalGuiHandler;
import mezz.jei.api.ingredients.IIngredientHelper;
import mezz.jei.api.ingredients.IIngredientRenderer;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.ingredients.subtypes.UidContext;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;
import ten3.TConst;
import ten3.core.machine.useenergy.compressor.CompressorScreen;
import ten3.core.machine.useenergy.indfur.IndfurScreen;
import ten3.core.machine.useenergy.psionicant.PsionicantScreen;
import ten3.core.machine.useenergy.pulverizer.PulverizerScreen;
import ten3.core.machine.useenergy.refiner.RefinerScreen;
import ten3.core.machine.useenergy.smelter.FurnaceScreen;
import ten3.lib.recipe.FormsCombinedRecipe;
import ten3.init.ItemInit;
import ten3.init.RecipeInit;
import ten3.lib.tile.CmScreenMachine;
import ten3.plugin.jei.impl.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@JeiPlugin
public class TEJeiPlugins implements IModPlugin {

    static RecipeType<FormsCombinedRecipe> pulverizer = getType(FormsCombinedRecipe.class, "pulverizer");
    static RecipeType<FormsCombinedRecipe> compressor = getType(FormsCombinedRecipe.class, "compressor");
    static RecipeType<SmeltingRecipe> smelter = getType(SmeltingRecipe.class, "smelter");
    static RecipeType<FormsCombinedRecipe> psionicant = getType(FormsCombinedRecipe.class, "psionicant");
    static RecipeType<FormsCombinedRecipe> inductionFurnace = getType(FormsCombinedRecipe.class, "induction_furnace");
    static RecipeType<FormsCombinedRecipe> refiner = getType(FormsCombinedRecipe.class, "refiner");

    private static<T> mezz.jei.api.recipe.RecipeType<T> getType(Class<T> recipe, String name) {
        return mezz.jei.api.recipe.RecipeType.create(TConst.modid, name, recipe);
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration)
    {
        addRecipeSM(registration, smelter);
        addRecipe(registration, psionicant);
        addRecipe(registration, pulverizer);
        addRecipe(registration, compressor);
        addRecipe(registration, inductionFurnace);
        addRecipe(registration, refiner);
    }

    @SuppressWarnings("all")
    private void addRecipe(IRecipeRegistration registration, RecipeType<? extends Recipe<Container>> type) {
        if(Minecraft.getInstance().level == null) return;
        RecipeManager manager = Minecraft.getInstance().level.getRecipeManager();
        net.minecraft.world.item.crafting.RecipeType<? extends Recipe<Container>> typeVl =
                (net.minecraft.world.item.crafting.RecipeType<? extends Recipe<Container>>) RecipeInit.getRcpType(type.getUid().getPath());
        List<Recipe<Container>> lst = (List<Recipe<Container>>) manager.getAllRecipesFor(typeVl);
        registration.addRecipes((RecipeType<Recipe<Container>>) type, lst);
    }

    private void addRecipeSM(IRecipeRegistration registration, RecipeType<SmeltingRecipe> type) {
        if(Minecraft.getInstance().level == null) return;
        RecipeManager manager = Minecraft.getInstance().level.getRecipeManager();
        net.minecraft.world.item.crafting.RecipeType<SmeltingRecipe> typeVl = net.minecraft.world.item.crafting.RecipeType.SMELTING;
        List<SmeltingRecipe> lst = manager.getAllRecipesFor(typeVl);
        registration.addRecipes(type, lst);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration)
    {
        registration.addRecipeCategories(new CategorySmelter(smelter));
        registration.addRecipeCategories(new CategoryPsionicant(psionicant));
        registration.addRecipeCategories(new CategoryCompressor(compressor));
        registration.addRecipeCategories(new CategoryInduction(inductionFurnace));
        registration.addRecipeCategories(new CategoryPulverizer(pulverizer));
        registration.addRecipeCategories(new CategoryRefiner(refiner));
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration)
    {
        addArea(76, 35, 22, 16, pulverizer, PulverizerScreen.class, registration);
        addArea(76, 35, 22, 16, compressor, CompressorScreen.class, registration);
        addArea(76, 35, 22, 16, smelter, FurnaceScreen.class, registration);
        addArea(76, 35, 22, 16, psionicant, PsionicantScreen.class, registration);
        addArea(92, 35, 22, 16, inductionFurnace, IndfurScreen.class, registration);
        addArea(81, 35, 22, 16, refiner, RefinerScreen.class, registration);

        registration.addGlobalGuiHandler(new IGlobalGuiHandler()
        {
            public Collection<Rect2i> getGuiExtraAreas()
            {
                if(!(Minecraft.getInstance().screen instanceof CmScreenMachine scr)) return Collections.emptyList();
                return scr.getExtras();
            }
        });
    }

    public static IIngredientType<FluidStack> FLUID_TYPE = ForgeTypes.FLUID_STACK;

    private<T> void addArea(int x, int y, int w, int h, RecipeType<T> type, Class<? extends AbstractContainerScreen<?>> clazz, IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(clazz, x, y, w, h, type);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration)
    {
        addCatalyst(registration, pulverizer);
        addCatalyst(registration, compressor);
        addCatalyst(registration, smelter);
        addCatalyst(registration, psionicant);
        addCatalyst(registration, inductionFurnace);
        addCatalyst(registration, refiner);
    }

    private void addCatalyst(IRecipeCatalystRegistration registration, RecipeType<?> type) {
        registration.addRecipeCatalyst(
                ItemInit.getItem("machine_" + type.getUid().getPath()).getDefaultInstance(),
                type);
    }

    @Override
    public ResourceLocation getPluginUid()
    {
        return new ResourceLocation("jei", TConst.modid);
    }

}
