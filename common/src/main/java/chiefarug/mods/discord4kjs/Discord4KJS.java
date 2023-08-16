package chiefarug.mods.discord4kjs;

import chiefarug.mods.discord4kjs.markdown.Parser;
import chiefarug.mods.discord4kjs.util.SetAndForget;
import com.google.common.base.Stopwatch;
import com.mojang.logging.LogUtils;
import dev.architectury.platform.Platform;
import dev.latvian.mods.kubejs.KubeJSPaths;
import dev.latvian.mods.kubejs.script.ScriptType;
import dev.latvian.mods.kubejs.util.ConsoleJS;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.internal.JDAImpl;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;

public class Discord4KJS {

    static final Path TOKEN_PATH = KubeJSPaths.LOCAL.resolve("discord4kjs.token.txt");
	static final Path TOKEN_PATH_DISPLAY = Platform.getGameFolder().relativize(TOKEN_PATH);
    public static final String MOD_ID = "discord4kjs";
    public static final Logger LGGR = LogUtils.getLogger();
    public transient static ConsoleJS CONSOLE = new ConsoleJS(ScriptType.SERVER, LGGR);

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

    @NotNull
	private static String readToken() {
		String token = "";
		try {
			var tokenFile = TOKEN_PATH.toFile();
			if (!tokenFile.exists()) {
				tokenFile.createNewFile();
				LGGR.info("Discord4KJS token file created at {}", TOKEN_PATH_DISPLAY);
			}
			token = new BufferedReader(new FileReader(tokenFile)).readLine();
			token = token != null ? token.trim() : "";
			if (token.isEmpty()) {
				LGGR.warn("No valid token in Discord4KJS token file at {}. Discord4KJS will not load", TOKEN_PATH_DISPLAY);
			} else if (token.length() < 59) {
			   LGGR.error("The Discord bot token in {} seems to be invalid. Will attempt to start anyway, but this probably won't go well.", TOKEN_PATH_DISPLAY);
			}
			return token;
		} catch (IOException e) {
			LGGR.error("Error while trying to read Discord4KJS bot token from {}. {}", TOKEN_PATH_DISPLAY, e);
		}
		return token;
	}

    static JDA buildJDA() {
        String token = readToken();
		if (token.isEmpty()) return null;

		// todo move these intents to a config with default values
		JDABuilder builder = JDABuilder.create(token, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.DIRECT_MESSAGES, GatewayIntent.DIRECT_MESSAGE_TYPING, GatewayIntent.DIRECT_MESSAGE_REACTIONS, GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGES);
		builder.setActivity(Activity.competing("the most jank Discord bot api setup"));
		builder.addEventListeners(new EventListeners());

		return builder.build();
	}

    static void connectToDiscord(JDA jda) {
		LGGR.info("Connecting to Discord...");
		var loginTimer = Stopwatch.createStarted();
		try {
			jda.awaitReady();
		} catch (InterruptedException e) {
			LGGR.error("Error while waiting for JDA to connect to Discord. Aborting!", e);
			return;
		}
		LGGR.info("Connected to Discord in {}", loginTimer.stop());
		Discord4KJS.jda.set(jda);

		try {
			LGGR.debug("annotations: " + JDAImpl.class.getMethod("getHttpClient").getDeclaredAnnotations().toString());
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
	}

}
