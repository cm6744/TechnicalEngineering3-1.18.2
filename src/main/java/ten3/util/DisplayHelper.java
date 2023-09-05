package ten3.util;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;

import java.util.StringJoiner;

public class DisplayHelper
{

    public static Component join(double e, double me) {

        if(e >= 0) {
            if(e < 1000) {
                return (ComponentHelper.make(ComponentHelper.RED, e + " FE / " + String.format("%.1f", me / 1000) + " kFE"));
            }
            else {
                return (ComponentHelper.make(ComponentHelper.RED, String.format("%.1f", e / 1000) + " kFE / " + String.format("%.1f", me / 1000) + " kFE"));
            }
        }

        return ComponentHelper.make("ERROR");

    }

    public static Component joinmB(double e, double me) {

        if(e >= 0) {
            return (ComponentHelper.make(e + " mB / " + me + " mB"));
        }

        return ComponentHelper.make("ERROR");

    }

    public static String toString(BlockPos b)
    {
        StringJoiner joiner = new StringJoiner(", ", "(", ")");
        b = b.offset(0, 0, 1);
        //Fucking MOJANG what are you doing why it isn't correct???
        joiner.add(String.valueOf(b.getX()));
        joiner.add(String.valueOf(b.getY()));
        joiner.add(String.valueOf(b.getZ()));
        return joiner.toString();
    }

}
