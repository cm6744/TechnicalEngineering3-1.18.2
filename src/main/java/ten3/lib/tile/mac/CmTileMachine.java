package ten3.lib.tile.mac;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import ten3.TConst;
import ten3.init.ContInit;
import ten3.lib.capability.energy.BatteryTile;
import ten3.lib.capability.fluid.FluidTransferor;
import ten3.lib.capability.fluid.Tank;
import ten3.lib.capability.fluid.TankArray;
import ten3.lib.capability.item.AdvancedInventory;
import ten3.lib.capability.item.InvHandler;
import ten3.lib.tile.CmContainerMachine;
import ten3.util.*;
import ten3.lib.capability.energy.EnergyTransferor;
import ten3.lib.capability.item.ItemTransferor;
import ten3.lib.tile.option.FaceOption;
import ten3.lib.tile.option.RedstoneMode;
import ten3.lib.tile.option.Type;
import ten3.lib.wrapper.*;

import javax.annotation.Nonnull;
import java.util.*;

public abstract class CmTileMachine extends CmTileEntity implements ISlotAcceptor
{

    public EnergyTransferor etr;
    public ItemTransferor itr;
    public FluidTransferor ftr;

    public List<SlotCm> slots = new ArrayList<>();
    public AdvancedInventory inventory = new AdvancedInventory(inventorySize(), slots, this);
    public List<Tank> tanks = new ArrayList<>();
    public IntArrayCm fluidData = ContInit.createDefaultIntArr();
    public IntArrayCm fluidAmount = ContInit.createDefaultIntArr();

    public abstract int inventorySize();

    public static int kFE(double k)
    {
        return (int) (1000 * k);
    }

    //data poses.
    //how to use them:
    //data.set(energy<-index, xxx<-value)
    public static final int ENERGY = 2;
    public static final int MAX_ENERGY = 3;
    public static final int PROGRESS = 0;
    public static final int MAX_PROGRESS = 1;
    public static final int FUEL = 4;
    public static final int MAX_FUEL = 5;
    public static final int E_REC = 6;
    public static final int E_EXT = 7;
    public static final int I_REC = 8;
    public static final int I_EXT = 9;
    public static final int F_REC = 10;
    public static final int F_EXT = 11;

    public static final int RED_MODE = 12;
    public static final int FACE = 13;
    public static final int EFF_AUC = 14;
    public static final int EFF = 15;
    public static final int UPGSIZE = 16;
    //public static int tranMode = 11;

    public int efficientIn;
    public int initialEfficientIn;//initial vars won't be change!

    public int initialFacing;

    public TransferManager info;
    public MacNBTManager nbtManager;
    public UpgradeSlots upgradeSlots;
    public MachineReflection reflection;

    public CmTileMachine(BlockPos pos, BlockState state)
    {
        super(SafeOperationHelper.regNameOf(state.getBlock()), pos, state);

        initHandlers();
        info = new TransferManager(this);
        upgradeSlots = new UpgradeSlots(this);
        etr = new EnergyTransferor(this);
        itr = new ItemTransferor(this);
        ftr = new FluidTransferor(this);
        nbtManager = new MacNBTManager(this);
        reflection = new MachineReflection(this);
    }

    public boolean hasUpgrade()
    {
        return true;
    }

    public boolean hasSideBar()
    {
        return true;
    }

    public List<ItemStack> drops()
    {
        List<ItemStack> stacks = new ArrayList<>();

        for(int i = 0; i < inventory.getContainerSize(); i++) {
            stacks.add(inventory.getItem(i));
        }

        return stacks;
    }

    public void addSlot(SlotCm s)
    {
        slots.add(s);
    }

    public void addTank(Tank s)
    {
        tanks.add(s);
    }

    public List<Tank> copyTank()
    {
        List<Tank> lst = new ArrayList<>();
        for(Tank t : tanks)
            lst.add(t.copy());
        return lst;
    }

    public void setEfficiency(int eff)
    {
        initialEfficientIn = efficientIn = eff;
    }

    public boolean customFitStackIn(ItemStack s, int slot)
    {
        return true;
    }

    public boolean customFitStackIn(FluidStack s, int tank)
    {
        return true;
    }

    public abstract Type typeOf();

    public abstract IngredientType slotType(int slot);

    public abstract boolean valid(int slot, ItemStack stack);

    public abstract IngredientType tankType(int tank);

    public abstract boolean valid(int slot, FluidStack stack);

    public Container getInv()
    {
        return inventory;
    }

    public int initialFaceMode(Capability<?> cap)
    {
        return FaceOption.BOTH;
    }

    @Override
    public void whenPlaceToWorld()
    {
        for(Direction d : Direction.values()) {
            info.setOpenEnergy(d, initialFaceMode(CapabilityEnergy.ENERGY));
            info.setOpenItem(d, initialFaceMode(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY));
            info.setOpenFluid(d, initialFaceMode(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY));
        }

        initialFacing = DirectionHelper.direToInt(reflection.direction());
    }

    public void readTileData(CompoundTag nbt)
    {
        super.readTileData(nbt);
        nbtManager.rdt(nbt);
    }

    public void writeTileData(CompoundTag nbt)
    {
        super.writeTileData(nbt);
        nbtManager.wdt(nbt);
    }

    double actualEffPercent;

    public MutableComponent getDisplayWith()
    {
        return ComponentHelper.translated(TConst.modid + "." + id);
        //TConst.modid + ".level." + levelIn);
    }

    public void resetAll()
    {
        efficientIn = initialEfficientIn;
        info.resetAll();
    }

    int cacheTimeActive;

    public void doBaseData()
    {
        resetAll();
        upgradeSlots.upgradeUpdate(true);
        upgradeSlots.upgradeUpdate(false);

        component = getDisplayWith();

        //to client
        data.set(MAX_ENERGY, info.maxStorageEnergy);
        data.set(E_REC, info.maxReceiveEnergy);
        data.set(E_EXT, info.maxExtractEnergy);
        data.set(EFF, efficientIn);
        data.set(I_REC, info.maxReceiveItem);
        data.set(I_EXT, info.maxExtractItem);
        data.set(F_REC, info.maxReceiveFluid);
        data.set(F_EXT, info.maxExtractFluid);
        data.set(UPGSIZE, upgradeSlots.upgSize);

        data.set(FACE, initialFacing);
        reflection.setFace(DirectionHelper.intToDire(initialFacing));

        if(getTileAliveTime() % 20 == 0) {
            int i = 0;
            for(Tank tank : tanks) {
                int amt = tank.getFluid().getAmount();
                int id = Registry.FLUID.getId(tank.getFluid().getFluid());
                fluidAmount.set(i, amt);
                fluidData.set(i, id);
                i++;
            }
        }

        //avoid out of cap
        if(data.get(ENERGY) > info.maxStorageEnergy) {
            data.set(ENERGY, info.maxStorageEnergy);
        }

        //set actual efficientIn
        if(getTileAliveTime() % 4 == 0) {
            actualEffPercent = EfficientCalculator.gen(typeOf(), data);
            data.set(EFF_AUC, (int) (actualEffPercent * efficientIn));
        }

        if(getActualEfficiencyPercent() > 0 && signalAllowRun()) {
            cacheTimeActive = 12;
        }
        cacheTimeActive--;
        reflection.setActive(cacheTimeActive > 0);

        //tran items
        if(signalAllowRun()) {
            etr.transferEnergy();
            itr.transferItem();
            ftr.transferFluid();
        }
    }

    public FluidActionResult handleFluidClick(ItemStack bucket, Player player)
    {
        int f = -1;
        FluidActionResult f1 = FluidActionResult.FAILURE, f2 = FluidActionResult.FAILURE;
        for(Tank tank : tanks) {
            f++;
            if(tankType(f).canIn()) {
                f1 = FluidUtil.tryEmptyContainer(bucket, tank, tank.getCapacity(), player, true);
            }
            if(tankType(f).canOut()) {
                f2 = FluidUtil.tryFillContainer(bucket, tank, tank.getCapacity(), player, true);
            }
            if(f1.isSuccess()) {
                return f1;
            }
            if(f2.isSuccess()) {
                return f2;
            }
        }
        return FluidActionResult.FAILURE;
    }

    @Override
    public void update()
    {
        doBaseData();
    }

    public int getActualEfficiency()
    {
        return data.get(EFF_AUC);
    }

    public double getActualEfficiencyPercent()
    {
        return actualEffPercent;
    }

    public boolean signalAllowRun()
    {
        boolean power = level.hasNeighborSignal(worldPosition);

        return switch(data.get(RED_MODE)) {
            case RedstoneMode.LOW -> !power;
            case RedstoneMode.HIGH -> power;
            case RedstoneMode.OFF -> true;
            default -> false;
        };
    }

    public boolean energyAllowRun()
    {
        boolean checkEnergy = false;
        if(typeOf() == Type.MACHINE_PROCESS) {
            checkEnergy = data.get(ENERGY) >= efficientIn;
        }
        if(typeOf() == Type.MACHINE_EFFECT) {
            checkEnergy = data.get(ENERGY) >= efficientIn;
        }
        if(typeOf() == Type.GENERATOR) {
            checkEnergy = data.get(ENERGY) + getActualEfficiency() <= info.maxStorageEnergy;
        }
        return checkEnergy;
    }

    @Override
    public AbstractContainerMenu createMenu(int cid, Inventory pi, Player p_createMenu_3_)
    {
        return new CmContainerMachine(cid, id, this, pi, worldPosition);
    }

    /*caps*/

    @Nonnull
    @Override
    @SuppressWarnings("all")
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, Direction side)
    {
        if(Objects.equals(cap, CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)) {
            if(side == null) {
                return (LazyOptional<T>) getItemHandler(side);
            }

            return info.direCheckItem(side) != FaceOption.OFF && hasFaceCapability(cap, side) && signalAllowRun()
                    ? (LazyOptional<T>) getItemHandler(side) : LazyOptional.empty();
        }

        if(Objects.equals(cap, CapabilityEnergy.ENERGY)) {
            if(side == null) {
                return (LazyOptional<T>) getEnergyHandler(side);
            }

            return info.direCheckEnergy(side) != FaceOption.OFF && hasFaceCapability(cap, side) && signalAllowRun()
                    ? (LazyOptional<T>) getEnergyHandler(side) : LazyOptional.empty();
        }

        if(Objects.equals(cap, CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)) {
            if(side == null) {
                return (LazyOptional<T>) getFluidHandler(side);
            }

            return info.direCheckFluid(side) != FaceOption.OFF && hasFaceCapability(cap, side) && signalAllowRun()
                    ? (LazyOptional<T>) getFluidHandler(side) : LazyOptional.empty();
        }

        return LazyOptional.empty();
    }

    protected boolean hasFaceCapability(Capability<?> cap, Direction d)
    {
        if(cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return tanks.size() > 0;
        }
        return true;
    }

    public LazyOptional<IItemHandler> getItemHandler(Direction d)
    {
        if(d == null) {
            return LazyOptional.of(() -> handlerItemNull);
        }
        return LazyOptional.of(() -> handlerItem.get(d));
    }

    public LazyOptional<IEnergyStorage> getEnergyHandler(Direction d)
    {
        if(d == null) {
            return LazyOptional.of(() -> handlerEnergyNull);
        }
        return LazyOptional.of(() -> handlerEnergy.get(d));
    }

    public LazyOptional<IFluidHandler> getFluidHandler(Direction d)
    {
        if(d == null) {
            return LazyOptional.of(() -> handlerFluidNull);
        }
        return LazyOptional.of(() -> handlerFluid.get(d));
    }

    public Map<Direction, IEnergyStorage> handlerEnergy = new HashMap<>();
    public Map<Direction, IItemHandler> handlerItem = new HashMap<>();
    public Map<Direction, IFluidHandler> handlerFluid = new HashMap<>();
    public IEnergyStorage handlerEnergyNull;
    public IItemHandler handlerItemNull;
    public IFluidHandler handlerFluidNull;

    public void initHandlers()
    {
        for(Direction d : Direction.values()) {
            handlerEnergy.put(d, new BatteryTile(d, this));
            handlerItem.put(d, new InvHandler(d, this));
            handlerFluid.put(d, new TankArray(d, this));
        }
        handlerItemNull = new InvHandler(null, this);
        handlerEnergyNull = new BatteryTile(null, this);
        handlerFluidNull = new TankArray(null, this);
    }

}
