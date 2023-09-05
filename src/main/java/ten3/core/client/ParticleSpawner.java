package ten3.core.client;

import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.level.Level;


public class ParticleSpawner {
    public static DustParticleOptions RANGE = DustParticleOptions.REDSTONE;

    public static void spawnClt(ParticleOptions data, double x, double y, double z, double spread) {

        Level world = Minecraft.getInstance().level;
        if(world != null)
        world.addParticle(data, x, y, z, spread, spread, spread);

    }

    public static void spawnClt(ParticleOptions data, double x, double y, double z, double sx, double sy, double sz) {

        Level world = Minecraft.getInstance().level;
        if(world != null)
            world.addParticle(data, x, y, z, sx, sy, sz);

    }

}
