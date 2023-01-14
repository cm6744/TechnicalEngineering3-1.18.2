package ten3.plugin.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import ten3.TConst;
import ten3.core.machine.useenergy.compressor.CompressorScreen;
import ten3.core.machine.useenergy.indfur.IndfurScreen;
import ten3.core.machine.useenergy.psionicant.PsionicantScreen;
import ten3.core.machine.useenergy.pulverizer.PulverizerScreen;
import ten3.core.machine.useenergy.smelter.FurnaceScreen;
import ten3.lib.recipe.FormsCombinedRecipe;
import ten3.init.ItemInit;
import ten3.init.RecipeInit;

import java.util.List;

@JeiPlugin
public class TEJeiPlugins implements IModPlugin {

    static RecipeType<FormsCombinedRecipe> PULV = getType(FormsCombinedRecipe.class, "pulverizer");
    static RecipeType<FormsCombinedRecipe> COMP = getType(FormsCombinedRecipe.class, "compressor");
    static RecipeType<SmeltingRecipe> SMLT = getType(SmeltingRecipe.class, "smelter");
    static RecipeType<FormsCombinedRecipe> PSIO = getType(FormsCombinedRecipe.class, "psionicant");
    static RecipeType<FormsCombinedRecipe> INDF = getType(FormsCombinedRecipe.class, "induction_furnace");

    private static<T> mezz.jei.api.recipe.RecipeType<T> getType(Class<T> recipe, String name) {
        return mezz.jei.api.recipe.RecipeType.create(TConst.modid, name, recipe);
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration)
    {
        addRecipe(registration, PULV);
        addRecipe(registration, COMP);
        addRecipeSM(registration, SMLT);
        addRecipe(registration, PSIO);
        addRecipe(registration, INDF);
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
        registration.addRecipeCategories(new TECategorySgAddition( "pulverizer", 27, 32));
        registration.addRecipeCategories(new TECategorySg2( "compressor", 27, 63));
        registration.addRecipeCategories(new TECategorySmelt("smelter", 27, 0));
        registration.addRecipeCategories(new TECategorySg2("psionicant", 27, 95));
        registration.addRecipeCategories(new TECategorySg3("induction_furnace", 27, 0));
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration)
    {
        addArea(registration, PULV, PulverizerScreen.class);
        addArea(registration, COMP, CompressorScreen.class);
        addArea(registration, SMLT, FurnaceScreen.class);
        addArea(registration, PSIO, PsionicantScreen.class);
        addArea(16, registration, INDF, IndfurScreen.class);
    }

    private<T> void addArea(IGuiHandlerRegistration registration, RecipeType<T> type, Class<? extends AbstractContainerScreen<?>> clazz) {
        addArea(0, registration, type, clazz);
    }

    private<T> void addArea(int xOff, IGuiHandlerRegistration registration, RecipeType<T> type, Class<? extends AbstractContainerScreen<?>> clazz) {
        registration.addRecipeClickArea(clazz, 76 + xOff, 35, 22, 16, type);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration)
    {
        addCatalyst(registration, PULV);
        addCatalyst(registration, COMP);
        addCatalyst(registration, SMLT);
        addCatalyst(registration, PSIO);
        addCatalyst(registration, INDF);
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
