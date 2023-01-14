package ten3.init;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import ten3.TConst;
import ten3.core.item.energy.BlockItemFEStorage;
import ten3.core.item.upgrades.*;
import ten3.core.item.*;
import ten3.core.machine.useenergy.compressor.Mould;
import ten3.init.tab.DefGroup;
import ten3.init.template.DefItem;
import ten3.init.template.DefItemBlock;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static ten3.init.template.DefItem.build;

public class ItemInit {

    static Map<String, RegistryObject<Item>> regs = new HashMap<>();
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, TConst.modid);

    public static void regAll() {

        //Protected:
        //regItem("pedia", InvisibleItem::new);
        //regItem("technical_item", InvisibleItem::new);
        //regItem("technical_block", InvisibleItem::new);

        regItem("spanner", Spanner::new);
        regItem("mould_gear", Mould::new);
        regItem("mould_plate", Mould::new);
        regItem("mould_rod", Mould::new);
        regItem("mould_string", Mould::new);
        //regItem("energy_capacity", () -> new ItemFEStorage(kFE(500), kFE(5), kFE(5)));

        //produced things
        //regItemDef("energy_core");
       // regItemDef("machine_frame");
        regItemDef("redstone_conductor");
        regItemDef("redstone_converter");
        regItemDef("redstone_storer");
        regItemDef("indigo");
        regItemDef("azure_glass");
        regItemDef("royal_jelly");
        regItemDef("spicy_jelly");
        regItemDef("bizarrerie");
        regItemDef("starlight_dust");

        //too imba
        //regItem("world_bag", new WorldBag());

        //base materials
        regPairMetal("iron", true);
        regPairMetal("gold", true);
        regPairMetal("copper", true);
        regPairMetal("tin", false);
        regPairMetal("nickel", false);
        regPairMetal("powered_tin", false);
        regPairMetal("chlorium", false);

        //upgrades
        regItem("augmented_levelup", LevelupAug::new);
        regItem("powered_levelup", LevelupPower::new);
        regItem("relic_levelup", LevelupShulker::new);
        regItem("photosyn_levelup", LevelupSyn::new);
        regItem("range_levelup", LevelupRg::new);
        regItem("smoke_levelup", LevelupSmoke::new);
        regItem("blast_levelup", LevelupBlast::new);
        regItem("potion_levelup", LevelupPotion::new);
        regItem("stream_levelup", LevelupStream::new);
        regItem("knowledge_levelup", LevelupKnow::new);
        regItem("ice_levelup", LevelupIce::new);
        regItem("magma_levelup", LevelupMagma::new);
        regItem("mineral_levelup", LevelupMineral::new);

        //ores
        regItemBlockDef("tin_ore");
        regItemBlockDef("nickel_ore");
        regItemBlockDef("deep_tin_ore");
        regItemBlockDef("deep_nickel_ore");
        regItemDef("raw_tin");
        regItemDef("raw_nickel");
        regItemBlockDef("raw_tin_block");
        regItemBlockDef("raw_nickel_block");

        //machines
        regItemMachineWithoutID("engine_extraction");
        regItemMachineWithoutID("engine_metal");
        regItemMachineWithoutID("engine_biomass");
        regItemMachineWithoutID("engine_solar");
        regItemMachine("smelter");
        regItemMachine("farm_manager");
        regItemMachine("pulverizer");
        regItemMachine("compressor");
        regItemMachine("beacon_simulator");
        regItemMachine("mob_ripper");
        regItemMachine("quarry");
        regItemMachine("psionicant");
        regItemMachine("induction_furnace");
        regItemMachine("enchantment_flusher");

        regItemMachineWithoutID("cell");
        regItemBlockDefInMac("pipe");
        regItemBlockDefInMac("pipe_white");
        regItemBlockDefInMac("pipe_black");
        regItemBlockDefInMac("cable");
        regItemBlockDefInMac("cable_quartz");
        regItemBlockDefInMac("cable_azure");
        regItemBlockDefInMac("cable_star");
        //regItemBlockDef("pole");

    }

    public static void regPairMetal(String id, boolean vanilla) {

        if(!vanilla) {
            regItemDef(id + "_ingot");
            regItemDef(id + "_nugget");
            regItemBlockDef(id + "_block");
        }

        regItemDef(id + "_dust");
        regItemDef(id + "_plate");
        regItemDef(id + "_gear");
    }

    public static void regItemBlockDef(String id, CreativeModeTab tab) {
        regItem(id, () -> new DefItemBlock(BlockInit.getBlock(id), build(64, tab)));
    }

    public static void regItemBlockDef(String id) {
        regItemBlockDef(id, DefGroup.BLOCK);
    }

    public static void regItemBlockDefInMac(String id) {
        regItemBlockDef(id, DefGroup.MAC);
    }

    public static void regItemMachine(String id) {
        String idi = "machine_" + id;
        regItem(idi, () -> new BlockItemFEStorage(BlockInit.getBlock(idi)));
    }
    public static void regItemMachineWithoutID(String id) {
        regItem(id, () -> new BlockItemFEStorage(BlockInit.getBlock(id)));
    }

    public static void regItemDef(String id) {
        regItem(id, DefItem::new);
    }

    public static void regItem(String id, Supplier<Item> im) {

        RegistryObject<Item> reg = ITEMS.register(id, im);
        regs.put(id, reg);

    }

    public static Item getItem(String id) {

        return regs.get(id).get();

    }

}
