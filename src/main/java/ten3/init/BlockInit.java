package ten3.init;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import ten3.TConst;
import ten3.core.block.*;
import ten3.core.machine.*;

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
        regBlock("nickel_ore", () -> new OreCm(4));
        regBlock("deep_tin_ore", () -> new OreCm(4));
        regBlock("deep_nickel_ore", () -> new OreCm(5));

        regBlock("tin_block", () -> new OreBloCm(4));
        regBlock("nickel_block", () -> new OreBloCm(5));
        regBlock("powered_tin_block", () -> new OreBloCm(5.5));
        regBlock("chlorium_block", () -> new OreBloCm(5));

        regBlock("raw_tin_block", () -> new RawBloCm(3));
        regBlock("raw_nickel_block", () -> new RawBloCm(4));

        regEngine("engine_extraction");
        regEngine("engine_metal");
        regEngine("engine_biomass");
        regEngine("engine_solar");

        regMachine("smelter");
        regMachine("farm_manager");
        regMachine("pulverizer");
        regMachine("compressor");
        regMachine("beacon_simulator");
        regMachine("mob_ripper");
        regMachine("quarry");
        regMachine("psionicant");
        regMachine("induction_furnace");
        regMachine("enchantment_flusher");

        regCable("cable", Material.GLASS, SoundType.GLASS);
        regCable("cable_quartz", Material.STONE, SoundType.STONE);
        regCable("cable_azure", Material.METAL, SoundType.METAL);
        regCable("cable_star", Material.GLASS, SoundType.GLASS);
        regPipe("pipe", Material.METAL, SoundType.METAL);
        regPipe("pipe_white", Material.METAL, SoundType.METAL);
        regPipe("pipe_black", Material.METAL, SoundType.METAL);
        regCell("cell", Material.GLASS, SoundType.GLASS);
        //regPole("pole", Material.GLASS, SoundType.GLASS);
    }

    public static void regMachine(String id_out) {

        String id = "machine_" + id_out;
        Supplier<Block> m = () -> new Machine(id);
        regBlock(id, m);

    }

    public static void regEngine(String id) {
        regBlock(id, () -> new MatedMac(id));
    }

    public static void regCell(String id, Material m, SoundType s) {
       regBlock(id, () -> new Cell(m, s, id, false));
    }

    public static void regCable(String id, Material m, SoundType s) {
        regBlock(id, () -> new CableBased(m, s, id));
    }

    public static void regPipe(String id, Material m, SoundType s) {
        regBlock(id, () -> new PipeBased(m, s, id));
    }

    public static void regBlock(String id, Supplier<Block> im) {

        RegistryObject<Block> reg = BLOCKS.register(id, im);
        regs.put(id, reg);

    }

    public static Block getBlock(String id) {

        return regs.get(id).get();

    }

}
