package chiefarug.mods.discord4kjs.events;

import chiefarug.mods.discord4kjs.DiscordEvents;
import dev.latvian.mods.kubejs.typings.Info;
import net.dv8tion.jda.api.events.session.ReadyEvent;

public class BotReadyEventJS extends DiscordEventJS {

	private final int connectedGuilds;
	private final int disconnectedGuilds;

	public BotReadyEventJS(ReadyEvent wrappedEvent) {
		super(wrappedEvent);
		this.connectedGuilds = wrappedEvent.getGuildAvailableCount();
		this.disconnectedGuilds = wrappedEvent.getGuildUnavailableCount();
	}

	@Info("Get the number of guilds this bot is in and has access to. Guilds that are having connection issues are not included.")
	public int getConnectedGuilds() {
		return connectedGuilds;
	}

	@Info("Get the number of guilds this bot is in but cannot access, likely because of a Discord outage.")
	public int getDisconnectedGuilds() {
		return disconnectedGuilds;
	}
}
