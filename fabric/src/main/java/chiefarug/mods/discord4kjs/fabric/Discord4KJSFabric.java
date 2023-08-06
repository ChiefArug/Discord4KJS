package chiefarug.mods.discord4kjs.fabric;

import chiefarug.mods.discord4kjs.Discord4KJS;
import net.fabricmc.api.ModInitializer;

public class Discord4KJSFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Discord4KJS.init();
    }
}
