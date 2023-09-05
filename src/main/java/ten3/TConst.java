package ten3;

import net.minecraft.resources.ResourceLocation;

public class TConst {

    public static final String modid = "ten3";
    public static final ResourceLocation guiHandler = asRes("textures/gui/handler.png");
    public static final ResourceLocation jeiHandler1 = asRes("textures/gui/jei_handler1.png");
    public static final ResourceLocation jeiHandler2 = asRes("textures/gui/jei_handler2.png");

    public static int WORLD_MIN = -64;
    public static int WORLD_MAX = 256;

    public static ResourceLocation asRes(String v)
    {
        return new ResourceLocation(modid, v);
    }

}
