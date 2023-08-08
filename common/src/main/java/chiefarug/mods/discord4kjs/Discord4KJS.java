package chiefarug.mods.discord4kjs;

import chiefarug.mods.discord4kjs.markdown.Parser;
import com.mojang.logging.LogUtils;
import dev.architectury.platform.Platform;
import dev.latvian.mods.kubejs.KubeJSPaths;
import dev.latvian.mods.kubejs.script.ScriptType;
import dev.latvian.mods.kubejs.util.ConsoleJS;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.network.chat.Component;
import org.slf4j.Logger;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Discord4KJS {

    public transient static final Path TOKEN_PATH = KubeJSPaths.LOCAL.resolve("discord4kjs.token.txt");
    static final Path TOKEN_PATH_DISPLAY = Platform.getGameFolder().relativize(TOKEN_PATH);
    public static final String MOD_ID = "discord4kjs";
    public static final Logger LGGR = LogUtils.getLogger();
    public transient static ConsoleJS CONSOLE = new ConsoleJS(ScriptType.STARTUP, LGGR);
    static final Discord4KJS INSTANCE = new Discord4KJS();
    static volatile boolean connected = false;
    private static transient Parser markdownParser = new Parser();

//    public static GatewayDiscordClient CLIENT;

    @HideFromJS
    public static void init() {
        new Discord4KJSWorkingThread().start();
    }

    public Component parseMarkdown(String stringWithMarkdown) {
        return markdownParser.setText(stringWithMarkdown).parse();
    }

}
