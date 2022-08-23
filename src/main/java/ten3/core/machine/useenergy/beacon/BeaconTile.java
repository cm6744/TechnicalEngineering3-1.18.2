package ten3.core.machine.useenergy.beacon;

import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import ten3.lib.tile.option.FaceOption;
import ten3.lib.tile.option.Type;
import ten3.lib.tile.recipe.CmTileMachineRadiused;
import ten3.lib.wrapper.SlotCustomCm;

import java.util.List;

public class BeaconTile extends CmTileMachineRadiused {

    public BeaconTile(BlockPos pos, BlockState state) {

        super(pos, state);

        setCap(kFE(20), FaceOption.BE_IN, FaceOption.OFF, 300);
        initialRadius = 32;

        addSlot(new SlotCustomCm(inventory, 0, 79, 31, BrewingRecipeRegistry::isValidInput, false, false));

    }

    @Override
    public Type typeOf() {
        return Type.MACHINE_EFFECT;
    }

    @Override
    public boolean isInWorkRadius(BlockPos pos)
    {
        return pos.closerThan(this.pos, radius);
    }

    public void update() {

        super.update();

        if(!checkCanRun()) {
            return;
        }

        if(energySupportRun()) {
            data.translate(ENERGY, -getActual());

            if(effectApplyTickOnScd(5, 60)) {

                AABB axisalignedbb = (new AABB(pos)).inflate(radius).expandTowards(0, world.getHeight(), 0);
                List<Player> list = world.getEntitiesOfClass(Player.class, axisalignedbb);

                ItemStack its = inventory.getItem(0);
                Potion pt = PotionUtils.getPotion(its);

                if(its.isEmpty()) return;

                for(Player Player : list) {
                    Player.addEffect(new MobEffectInstance(pt.getEffects().get(0).getEffect(), 40 * 10, levelIn, true, true));
                }
            }
        }
        else {
            setActive(false);
        }

    }

}
