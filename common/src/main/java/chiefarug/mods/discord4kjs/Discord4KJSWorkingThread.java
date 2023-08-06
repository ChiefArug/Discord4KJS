package chiefarug.mods.discord4kjs;

import dev.latvian.mods.kubejs.script.ScriptType;
import dev.latvian.mods.kubejs.util.ConsoleJS;
import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.shard.ShardingStrategy;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.jetbrains.annotations.NotNull;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

import static chiefarug.mods.discord4kjs.Discord4KJS.LGGR;

public class Discord4KJSWorkingThread extends Thread {

	public static boolean running;
	private static ConsoleJS console = new ConsoleJS(ScriptType.STARTUP, LGGR);
	private Mono<GatewayDiscordClient> clientCacher = Mono.fromSupplier(Discord4KJSWorkingThread::readToken)
			.doFirst(() -> LGGR.info("Loading Discord connection..."))
			.flatMap(Mono::justOrEmpty)
			.flatMap(token -> DiscordClient.create(token)
					.gateway()
					.setSharding(ShardingStrategy.single())
					.login()
					.doFirst(() -> LGGR.info("Connecting to Discord..."))
			)
			.doOnError(Discord4KJSWorkingThread::onConnectionError)
			.elapsed()
			.map(tuple -> {LGGR.info("Connecting to Discord took {}", formatTimeInMs(tuple.getT1())); return tuple.getT2();})
			.cache();

	@NotNull
	private static String formatTimeInMs(long ms) {
		return DurationFormatUtils.formatDuration(ms, "SSSS'ms'");
	}

	public Discord4KJSWorkingThread() {
		super("Discord4KJS working thread");
	}

	private static void onConnectionError(Throwable error) {
		LGGR.error("Error while connecting to Discord", error);
	}

	private static void onDisconnectionError(Throwable error) {
		LGGR.error("Error while disconnecting from Discord", error);
	}

	static String readToken() {
		try {
			var tokenFile = Discord4KJS.TOKEN_PATH.toFile();
			if (!tokenFile.exists()) {
				tokenFile.createNewFile();
				LGGR.info("Discord4KJS token file created at {}", Discord4KJS.TOKEN_PATH_DISPLAY);
			}
			String token = new BufferedReader(new FileReader(tokenFile)).readLine();
			token = token != null ? token.trim() : "";
			if (token.isEmpty()) {
				LGGR.warn("No valid token in Discord4KJS token file at {}. Discord4KJS will not load", Discord4KJS.TOKEN_PATH_DISPLAY);
				return null;
			} else if (token.length() < 59) {
			   LGGR.error("The Discord bot token in {} seems to be invalid. Will attempt to start anyway, but this probably wont go well.",Discord4KJS.TOKEN_PATH_DISPLAY);
			}
			return token;
		} catch (IOException e) {
			LGGR.error("Error while trying to read Discord4KJS bot token from {}. {}", Discord4KJS.TOKEN_PATH_DISPLAY, e);
		}
		return null;
	}

	static ConsoleJS getConsole() {
		return console;
	}


	@Override
	public void start() {
		LGGR.info("Initializing Discord connection thread");
		super.start();
	}


	@Override
	public void run() {
		running = true;
		GatewayDiscordClient client = clientCacher.block();
		if (client == null) return;
		while (running) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				LGGR.error("Discord4KJS working thread interrupted while sleeping", e);
			}
		}
		// logout event?
		client.logout().elapsed().map(Tuple2::getT1)
				.doFirst(() -> LGGR.info("Disconnecting from Discord"))
				.doOnError(Discord4KJSWorkingThread::onDisconnectionError)
				.doOnSuccess(t -> LGGR.info("Disconnected from Discord in ", formatTimeInMs(t)))
				.block();
	}
}
