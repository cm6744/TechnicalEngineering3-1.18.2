package ten3.init;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.VanillaIngredientSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import ten3.TConst;
import ten3.TechnicalEngineering;
import ten3.core.recipe.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class RecipeInit {

    static Map<String, RegistryObject<RecipeSerializer<?>>> regs = new HashMap<>();
    public static Map<String, RegistryObject<RecipeType<?>>> types = new HashMap<>();
    public static final DeferredRegister<RecipeSerializer<?>> RECIPES_SERIALS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, TConst.modid);
    public static final DeferredRegister<RecipeType<?>> RECIPES_TYPES = DeferredRegister.create(Registry.RECIPE_TYPE_REGISTRY, TConst.modid);

    public static void regAll() {

        regRcp(new SingleSerial<>(SingleRecipe::new, "pulverizer"));
        regRcp(new SingleSerial<>(SingleRecipe::new, "compressor"));
        regRcp(new MTSSerial<>(MTSRecipe::new, "psionicant", 2));
        regRcp(new MTSSerial<>(MTSRecipe::new, "induction_furnace", 3));
    }

    public static void regRcp(CmSerializer<?> s) {

        String id = s.id();
        RegistryObject<RecipeSerializer<?>> reg = RECIPES_SERIALS.register(id, () -> s);//singleton
        regs.put(id, reg);

        RecipeTypeCm<?> type = new RecipeTypeCm<>(id);
        RegistryObject<RecipeType<?>> reg2 = RECIPES_TYPES.register(id, () -> type);//singleton

        types.put(id, reg2);

    }

    public static RecipeSerializer<?> getRcp(String id) {

        return regs.get(id).get();

    }

    public static RecipeType<?> getRcpType(String id) {

        return types.get(id).get();

    }

}
