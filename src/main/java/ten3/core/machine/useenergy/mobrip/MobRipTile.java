package ten3.core.machine.useenergy.mobrip;

import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import ten3.lib.tile.option.Type;
import ten3.lib.tile.extension.CmTileMachineRadiused;
import ten3.lib.wrapper.SlotCustomCm;
import ten3.util.ExcUtil;
import ten3.util.ItemUtil;

import java.util.List;

public class MobRipTile extends CmTileMachineRadiused {

    public MobRipTile(BlockPos pos, BlockState state) {

        super(pos, state);

        info.setCap(kFE(20));
        setEfficiency(15);
        initialRadius = 8;

        addSlot(new SlotCustomCm(inventory, 0, 79, 31, (e) -> {
            return e.getItem() instanceof DiggerItem || e.getItem() instanceof SwordItem;
        }, false, false));

    }

    @Override
    public Type typeOf() {
        return Type.MACHINE_EFFECT;
    }

    public void effect()
    {
        AABB axisalignedbb = (new AABB(worldPosition)).inflate(radius);
        List<LivingEntity> list = level.getEntitiesOfClass(LivingEntity.class, axisalignedbb);

        ItemStack st1 = inventory.getItem(0);

        if(list.size() == 0) return;

        LivingEntity entity = ExcUtil.randomInCollection(list);

        if(entity instanceof Player && ((Player) entity).isCreative()) return;

        float damage = 0.5f;
        if(!st1.isEmpty()) {
            Item iti = st1.getItem();
            if(iti instanceof DiggerItem) {
                damage = ((DiggerItem) iti).getAttackDamage();
            }
            if(iti instanceof SwordItem) {
                damage = ((SwordItem) iti).getDamage();
            }
        }
        entity.hurt(DamageSource.CACTUS, damage);
        ItemUtil.damage(st1, level, 1);
    }

    public double seconds()
    {
        return 3;
    }

}
