package chiefarug.mods.discord4kjs.forge;

import chiefarug.mods.discord4kjs.Discord4KJS;
import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Discord4KJS.MOD_ID)
public class Discord4KJSForge {
    public Discord4KJSForge() {
        EventBuses.registerModEventBus(Discord4KJS.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        Discord4KJS.init();
    }
}
