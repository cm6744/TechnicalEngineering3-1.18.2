package ten3;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import ten3.init.*;

@Mod(TConst.modid)
public class TechnicalEngineering {

    public TechnicalEngineering() {

        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        BlockInit.regAll();
        TileInit.regAll();
        ContInit.regAll();

        ItemInit.regAll();
        RecipeInit.regAll();

        BlockInit.BLOCKS.register(bus);
        TileInit.TILES.register(bus);
        ContInit.CONS.register(bus);

        ItemInit.ITEMS.register(bus);
        RecipeInit.RECIPES_SERIALS.register(bus);
        RecipeInit.RECIPES_TYPES.register(bus);

    }

}
