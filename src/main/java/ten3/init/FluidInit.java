package ten3.init;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import ten3.TConst;
import ten3.core.block.fluid.NullFluidTicker;

import java.util.HashMap;
import java.util.Map;

public class FluidInit
{

    static Map<String, ForgeFlowingFluid.Properties> props = new HashMap<>();
    static Map<String, RegistryObject<FlowingFluid>> regs1 = new HashMap<>();
    static Map<String, RegistryObject<FlowingFluid>> regs2 = new HashMap<>();
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, TConst.modid);

    public static void regAll()
    {
        regFluid("liquid_xp", build("liquid_xp", 100, 10, 100, 25000, false),
                 1, 1000, 6, new NullFluidTicker()
        );
        regFluid("liquid_bizarrerie", build("liquid_bizarrerie", 100000, 15, -500, 1000, false),
                 2, 5000, 4, new NullFluidTicker());
        /*
        regFluid("liquid_honey", build("liquid_honey", 6000, 0, 30, 25000, false),
                 4, 2000, 3);
        regFluid("liquid_royal_jelly", build("liquid_royal_jelly", 6000, 0, 30, 25000, false),
                 4, 2000, 3);
        regFluid("liquid_spicy_jelly", build("liquid_spicy_jelly", 6000, 0, 30, 25000, false),
                 4, 2000, 3);
         */
    }

    public static FluidAttributes.Builder build(String id, int dens, int lum, int temp, int visc, boolean isGas) {
        FluidAttributes.Builder bd = FluidAttributes.builder(new ResourceLocation(TConst.modid, "fluid/"+id),
                                                        new ResourceLocation(TConst.modid, "fluid/"+id+"_flowing"))
                .density(dens)
                .temperature(temp)
                .luminosity(lum)
                .viscosity(visc)
                .sound(SoundEvents.BOTTLE_FILL, SoundEvents.BOTTLE_EMPTY)
                .translationKey(TConst.modid+"."+id);
        if(isGas) bd.gaseous();
        return bd;
    }

    public interface FluidTicker {
        void tick(Level level, BlockPos pos, FluidState state);
    }

    public static void regFluid(String id, FluidAttributes.Builder attr, int dec, float res, int slope, FluidTicker ticker)
    {
        RegistryObject<FlowingFluid> reg = FLUIDS.register(id, () -> {
            return new ForgeFlowingFluid.Source(props.get(id)) {
                public void tick(Level p_75995_, BlockPos p_75996_, FluidState p_75997_)
                {
                    super.tick(p_75995_, p_75996_, p_75997_);
                    ticker.tick(p_75995_, p_75996_, p_75997_);
                }
            };
        });
        RegistryObject<FlowingFluid> reg2 = FLUIDS.register(id+"_flowing", () -> {
            return new ForgeFlowingFluid.Flowing(props.get(id));
        });
        regs1.put(id, reg);
        regs2.put(id, reg2);
        props.put(id, new ForgeFlowingFluid.Properties(reg, reg2, attr)
                .levelDecreasePerBlock(dec)
                .explosionResistance(res)
                .slopeFindDistance(slope)
                .tickRate(10)
                .block(() -> (LiquidBlock) BlockInit.getBlock(id))
                .bucket(() -> ItemInit.getItem(id+"_bucket")));
    }

    public static FlowingFluid getSource(String id) {
        return regs1.get(id).get();
    }

    public static FlowingFluid getFlowing(String id) {
        return regs2.get(id).get();
    }

}
