package ten3.util;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

public class PatternUtil {

    public static Component join(double e, double me) {

        if(e >= 0) {
            if(e < 1000) {
                return (KeyUtil.make(KeyUtil.RED, e + " FE / " + String.format("%.1f", me / 1000) + " kFE"));
            }
            else {
                return (KeyUtil.make(KeyUtil.RED, String.format("%.1f", e / 1000) + " kFE / " + String.format("%.1f", me / 1000) + " kFE"));
            }
        }

        return KeyUtil.make("ERROR");

    }

    public static Component joinmB(double e, double me) {

        if(e >= 0) {
            return (KeyUtil.make(e + " mB / " + me + " mB"));
        }

        return KeyUtil.make("ERROR");

    }

}
