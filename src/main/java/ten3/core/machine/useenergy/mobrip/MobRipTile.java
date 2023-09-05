package ten3.core.machine.useenergy.mobrip;

import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.fluids.FluidStack;
import ten3.lib.tile.mac.IngredientType;
import ten3.lib.tile.extension.CmTileMachineRadiused;
import ten3.lib.wrapper.SlotCm;
import ten3.util.SafeOperationHelper;
import ten3.util.ItemNBTHelper;

import java.util.List;

public class MobRipTile extends CmTileMachineRadiused {

    public MobRipTile(BlockPos pos, BlockState state) {

        super(pos, state);

        info.setCap(kFE(20));
        setEfficiency(15);
        initialRadius = 8;

        addSlot(new SlotCm(this, 0, 79, 31));

    }

    public IngredientType slotType(int slot)
    {
        return IngredientType.INPUT;
    }

    public boolean valid(int slot, ItemStack stack)
    {
        return stack.getItem() instanceof DiggerItem || stack.getItem() instanceof SwordItem;
    }

    public IngredientType tankType(int tank)
    {
        return IngredientType.IGNORE;
    }

    public boolean valid(int slot, FluidStack stack)
    {
        return true;
    }

    public int inventorySize()
    {
        return 1;
    }

    public void effect()
    {
        AABB axisalignedbb = (new AABB(worldPosition)).inflate(radius);
        List<LivingEntity> list = level.getEntitiesOfClass(LivingEntity.class, axisalignedbb);

        ItemStack st1 = inventory.getItem(0);

        if(list.size() == 0) return;

        LivingEntity entity = SafeOperationHelper.randomInCollection(list);

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
        ItemNBTHelper.damage(st1, level, 1);
    }

    public double seconds()
    {
        return 3;
    }

}
