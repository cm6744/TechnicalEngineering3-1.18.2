package ten3.core.machine.engine;

import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.ForgeHooks;
import ten3.util.TagHelper;

public class MatchFuel {

    //a coal:32 kFE
    //a gold: 20 kFE
    //a netherite: 84 kFE

    public static int matchFuelAndShrink(ItemStack i, boolean shr) {

        if(i.getCount() <= 0 || i.isEmpty()) return 0;

        int time = ForgeHooks.getBurnTime(i, RecipeType.SMELTING);

        if(i.getItem() == Items.LAVA_BUCKET) {
            return 0;
        }

        if(time > 0 && !shr) {
            i.shrink(1);
        }

        return time * 20;

    }

    public static int matchFuelAndShrinkPlant(ItemStack i, boolean shr) {

        if(i.getCount() <= 0 || i.isEmpty()) return 0;

        int time = 0;

        boolean t1 = TagHelper.containsItem(i.getItem(), ItemTags.LEAVES);
        boolean t2 = TagHelper.containsItem(i.getItem(), ItemTags.LOGS);
        boolean t3 = TagHelper.containsItem(i.getItem(), ItemTags.FLOWERS);
        boolean t4 = TagHelper.containsItem(i.getItem(), ItemTags.SAPLINGS);

        if(t1) {
            time = 20;
        }
        else if(t2) {
            time = 120;
        }
        else if(t3) {
            time = 10;
        }
        else if(t4) {
            time = 15;
        }
        else if(i.getItem() instanceof BlockItem) {
            Material m = ((BlockItem) i.getItem()).getBlock().defaultBlockState().getMaterial();
            if(m == Material.PLANT) time = 10;
            if(m == Material.BAMBOO) time = 8;
            if(m == Material.BAMBOO_SAPLING) time = 8;
            if(m == Material.GRASS) time = 10;
            if(m == Material.WATER_PLANT) time = 15;
        }

        if(time > 0 && !shr) {
            i.shrink(1);
        }

        return time * 60;

    }

    public static int matchFuelAndShrinkMetal(ItemStack i, boolean shr) {

        if(i.getCount() <= 0 || i.isEmpty()) return 0;

        int time = 0;

        //ITag<Item> ig = ItemTags.getCollection().getTagByID(new ResourceLocation("forge:ingots"));
        boolean cn = TagHelper.containsItem(i.getItem(), TagHelper.keyItem("ten3:common_ingots"));
        boolean uc = TagHelper.containsItem(i.getItem(), TagHelper.keyItem("ten3:uncommon_ingots"));
        boolean vc = TagHelper.containsItem(i.getItem(), TagHelper.keyItem("ten3:valuable_ingots"));

        if(cn) {
            time = 1000;
        }
        else if(uc) {
            time = 1500;
        }
        else if(vc) {
            time = 6400;
        }

        if(time > 0 && !shr) {
            i.shrink(1);
        }

        return time * 20;

    }

}