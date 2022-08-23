package ten3.init;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import ten3.TConst;
import ten3.core.block.*;
import ten3.core.machine.Engine;
import ten3.core.machine.Machine;
import ten3.core.machine.Cable;
import ten3.core.machine.Cell;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class BlockInit {

    static Map<String, RegistryObject<Block>> regs = new HashMap<>();
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, TConst.modid);

    public static void regAll() {
        //Protects:
        //regBlock("state", new State());

        regBlock("tin_ore", () -> new OreCm(3));
        //regBlock("copper_ore", () -> new OreCm(2.5));
        regBlock("nickel_ore", () -> new OreCm(4));
        regBlock("deep_tin_ore", () -> new OreCm(4));
        regBlock("deep_nickel_ore", () -> new OreCm(5));

        regEngine("engine_extraction");
        regEngine("engine_metal");
        regEngine("engine_biomass");

        regMachine("smelter");
        regMachine("farm_manager");
        regMachine("pulverizer");
        regMachine("compressor");
        regMachine("beacon_simulator");
        regMachine("mob_ripper");
        regMachine("quarry");
        regMachine("psionicant");
        regMachine("induction_furnace");

        regCable("cable_glass", Material.GLASS, SoundType.GLASS);
        regCable("cable_golden", Material.METAL, SoundType.STONE);

        regCell("cell_glass", Material.GLASS, SoundType.GLASS);
        regCell("cell_golden", Material.METAL, SoundType.STONE);
    }

    public static void regMachine(String id_out) {

        String id = "machine_" + id_out;
        Supplier<Block> m = () -> new Machine(id);
        RegistryObject<Block> reg = BLOCKS.register(id, m);
        regs.put(id, reg);

    }

    public static void regEngine(String id) {

        Supplier<Block> m = () -> new Engine(id);
        RegistryObject<Block> reg = BLOCKS.register(id, m);
        regs.put(id, reg);

    }

    public static void regCell(String id, Material m, SoundType s) {

        Supplier<Block> c = () -> new Cell(m, s, id);
        RegistryObject<Block> reg = BLOCKS.register(id, c);
        regs.put(id, reg);

    }

    public static void regCable(String id, Material m, SoundType s) {

        Supplier<Block> c = () -> new Cable(m, s, id);
        RegistryObject<Block> reg = BLOCKS.register(id, c);
        regs.put(id, reg);

    }

    public static void regBlock(String id, Supplier<Block> im) {

        RegistryObject<Block> reg = BLOCKS.register(id, im);
        regs.put(id, reg);

    }

    public static Block getBlock(String id) {

        return regs.get(id).get();

    }

}
