package chiefarug.mods.discord4kjs;

import chiefarug.mods.discord4kjs.markdown.Parser;
import chiefarug.mods.discord4kjs.util.SetAndForget;
import com.mojang.logging.LogUtils;
import dev.latvian.mods.kubejs.script.ScriptType;
import dev.latvian.mods.kubejs.util.ConsoleJS;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.dv8tion.jda.api.JDA;
import net.minecraft.network.chat.Component;
import org.slf4j.Logger;

public class Discord4KJS {

    public static final String MOD_ID = "discord4kjs";
    public static final Logger LGGR = LogUtils.getLogger();
    public transient static ConsoleJS CONSOLE = new ConsoleJS(ScriptType.SERVER, LGGR);

    static final Discord4KJS INSTANCE = new Discord4KJS();
    static volatile boolean connected = false;
    private transient static Parser markdownParser = new Parser();
    static final SetAndForget<JDA> jda = new SetAndForget<>();

    @HideFromJS
    public static final JDA jda() {
        return jda.get();
    }

//    public static GatewayDiscordClient CLIENT;

    @HideFromJS
    public static void init() {
        new Discord4KJSWorkingThread().start();
    }

    public static Component parseMarkdown(String stringWithMarkdown) {
        return markdownParser.setText(stringWithMarkdown).parse();
    }

}
