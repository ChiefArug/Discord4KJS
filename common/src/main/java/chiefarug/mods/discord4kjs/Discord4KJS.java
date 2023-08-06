package chiefarug.mods.discord4kjs;

import com.mojang.logging.LogUtils;
import dev.architectury.platform.Platform;
import dev.latvian.mods.kubejs.KubeJSPaths;
import dev.latvian.mods.rhino.util.HideFromJS;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.EventDispatcher;
import discord4j.core.event.domain.message.MessageEvent;
import org.slf4j.Logger;
import reactor.core.Disposable;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Discord4KJS {
    public static final Path TOKEN_PATH = KubeJSPaths.LOCAL.resolve("discord4kjs.token.txt");
    static final Path TOKEN_PATH_DISPLAY = Platform.getGameFolder().relativize(TOKEN_PATH);
    public static final String MOD_ID = "discord4kjs";
    public static final Logger LGGR = LogUtils.getLogger();
    static final Discord4KJS INSTANCE = new Discord4KJS();
    static volatile boolean connected = false;
    static volatile EventDispatcher events;
    static volatile GatewayDiscordClient client;
    static final List<Disposable> eventListeners = new ArrayList<>();

//    public static GatewayDiscordClient CLIENT;

    @HideFromJS
    public static void init() {
        new Discord4KJSWorkingThread().start();
    }

    public void doStuffLater() {
        eventListeners.add(events.on(MessageEvent.class).subscribe(something -> LGGR.info("Message event triggered!")));
    }

}
