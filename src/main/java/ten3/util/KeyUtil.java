package ten3.util;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import ten3.TConst;

@OnlyCore
public class KeyUtil {

    @OnlyCore
    public static MutableComponent getKey(String s) {

        String test = TConst.modid + "." + s;
        return translated(test);

    }

    @OnlyCore
    public static String exceptMachineOrGiveCell(String s) {

        if(s.startsWith("cell_")) {
            return "cell";
        }

        return s.replace("machine_", "");

    }

    public static MutableComponent translated(String... ss) {

        TranslatableComponent t1 = null;

        for(String s : ss) {
            if(t1 == null) {
                t1 = new TranslatableComponent(s);
            }
            else {
                t1.append(new TranslatableComponent(s));
            }
        }

        return t1;

    }

    public static MutableComponent make(String... ss) {

        TextComponent t1 = null;

        for(String s : ss) {
            if(t1 == null) {
                t1 = new TextComponent(s);
            }
            else {
                t1.append(new TextComponent(s));
            }
        }

        return t1;

    }

    public static ChatFormatting GOLD = ChatFormatting.GOLD;
    public static ChatFormatting GREEN = ChatFormatting.GREEN;
    public static ChatFormatting RED = ChatFormatting.RED;

    public static MutableComponent translated(ChatFormatting color, String... ss) {

        return translated(ss).withStyle(color);

    }

    public static MutableComponent make(ChatFormatting color, String... ss) {

        return make(ss).withStyle(color);

    }

}
