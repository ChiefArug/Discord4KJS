package chiefarug.mods.discord4kjs;

import com.google.common.base.Stopwatch;
import dev.architectury.platform.Platform;
import dev.latvian.mods.kubejs.KubeJSPaths;
import dev.latvian.mods.kubejs.util.ConsoleJS;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.time.Duration;

import static chiefarug.mods.discord4kjs.Discord4KJS.CONSOLE;
import static chiefarug.mods.discord4kjs.Discord4KJS.LGGR;

public class Discord4KJSWorkingThread extends Thread {

	static final Path TOKEN_PATH = KubeJSPaths.LOCAL.resolve("discord4kjs.token.txt");
	static final Path TOKEN_PATH_DISPLAY = Platform.getGameFolder().relativize(TOKEN_PATH);
	@HideFromJS
	public static boolean running;

	public Discord4KJSWorkingThread() {
		super("Discord4KJS working thread");
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
			   LGGR.error("The Discord bot token in {} seems to be invalid. Will attempt to start anyway, but this probably wont go well.", TOKEN_PATH_DISPLAY);
			}
			return token;
		} catch (IOException e) {
			LGGR.error("Error while trying to read Discord4KJS bot token from {}. {}", TOKEN_PATH_DISPLAY, e);
		}
		return token;
	}

	static ConsoleJS getConsole() {
		return CONSOLE;
	}


	@Override
	public void start() {
		LGGR.info("Initializing Discord connection thread");
		super.start();
	}

	public static void shutdown() throws InterruptedException {
		running = false;
		//todo add config option, off by default, to sleep. So that we dont delay regular packs from shutting down
		// if off maybe it could also just disable the thread checking, meaning that this thread can shutdown early... unless i can find some other use for it
		Thread.sleep(500);
	}


	@Override
	public void run() {
		running = true;

		String token = readToken();
		if (token.isEmpty()) return;

		var loginTimer = Stopwatch.createStarted();
		// todo move these intents to a config with default values
		JDABuilder builder = JDABuilder.create(GatewayIntent.MESSAGE_CONTENT, GatewayIntent.DIRECT_MESSAGES, GatewayIntent.DIRECT_MESSAGE_TYPING, GatewayIntent.DIRECT_MESSAGE_REACTIONS, GatewayIntent.GUILD_MEMBERS);
		builder.setToken(token);
		builder.setActivity(Activity.competing("the most jank Discord bot api setup"));
		builder.addEventListeners(new EventListeners());

		LGGR.info("Connecting to Discord...");
		JDA jda = builder.build();
		Discord4KJS.jda.set(jda);
		try {
			jda.awaitReady();
		} catch (InterruptedException e) {
			LGGR.error("Error while waiting for JDA to connect to Discord. Aborting!", e);
			return;
		}
		LGGR.info("Connected to Discord in {}", loginTimer.stop());


		while (running) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				LGGR.error("Discord4KJS working thread interrupted while sleeping", e);
			}
		}

		var disconnectTimer = Stopwatch.createStarted();
		LGGR.info("Disconnecting from Discord");
		jda.shutdown();

		try {
			if (!jda.awaitShutdown(Duration.ofSeconds(10))) {
				LGGR.warn("Wasn't able to disconnect nicely from Discord within 10 seconds, forcing shutdown immediately (this will skip the any queued requests!)");
				jda.shutdownNow();
				jda.awaitShutdown();
			}
			LGGR.info("Disconnected from Discord in {}", disconnectTimer.stop());
		} catch (InterruptedException e) {
			LGGR.error("Error while waiting for JDA to shutdown", e);
		}
	}
}
