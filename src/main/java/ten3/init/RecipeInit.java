package ten3.init;

import net.minecraft.core.Registry;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import ten3.TConst;
import ten3.lib.recipe.*;

import java.util.HashMap;
import java.util.Map;

public class RecipeInit {

    static Map<String, RecipeSerializer<?>> regs = new HashMap<>();
    public static Map<String, RecipeType<?>> types = new HashMap<>();
    public static final DeferredRegister<RecipeSerializer<?>> RECIPES_SERIALS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, TConst.modid);
    public static final DeferredRegister<RecipeType<?>> RECIPES_TYPES = DeferredRegister.create(Registry.RECIPE_TYPE_REGISTRY, TConst.modid);

    public static void regAll() {

        regFormsCombined("pulverizer", 1, 4);
        regFormsCombined("compressor", 2, 1);
        regFormsCombined("psionicant", 2, 1);
        regFormsCombined("induction_furnace", 3, 1);
        regFormsCombined("refiner", 2, 2);
    }

    public static void regFormsCombined(String id, int i, int o)
    {
        regRcp((new FormsCombinedRecipeSerializer<>(FormsCombinedRecipe::new, id, i, o)));
    }

    public static void regRcp(CmSerializer<?> s) {

        String id = s.id();
        RegistryObject<RecipeSerializer<?>> reg = RECIPES_SERIALS.register(id, () -> s);//singleton
        regs.put(id, s);

        RecipeTypeCm<?> type = new RecipeTypeCm<>(id);
        RegistryObject<RecipeType<?>> reg2 = RECIPES_TYPES.register(id, () -> type);//singleton

        types.put(id, type);

    }

    public static RecipeSerializer<?> getSerializer(String id) {

        return regs.get(id);

    }

    public static RecipeType<?> getType(String id) {

        return types.get(id);

    }

}
