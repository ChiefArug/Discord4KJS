package chiefarug.mods.discord4kjs.events;

import dev.latvian.mods.kubejs.event.EventJS;
import dev.latvian.mods.kubejs.typings.Info;
import net.dv8tion.jda.api.events.session.ShutdownEvent;
import net.dv8tion.jda.api.requests.CloseCode;
import org.jetbrains.annotations.Nullable;

public class DisconnectEventJS extends DiscordEventJS {

	@Nullable
	private final CloseCode disconnectReason;

	public DisconnectEventJS(ShutdownEvent wrappedEvent) {
		this.disconnectReason = wrappedEvent.getCloseCode();
	}

	@Nullable
	@Info("Get the CloseCode for this disconnect. Null if unknown")
	public CloseCode getDisconnectReason() {
		return disconnectReason;
	}

	@Info("Get a human understandable message for why this disconnect happened")
	public String getDisconnectMessage() {
		return disconnectReason != null ? disconnectReason.getMeaning() : "Unknown";
	}

	@Info("Returns if JDA will try to reconnect to Discord after this disconnect")
	public boolean isReconnecting() {
		return disconnectReason != null ? disconnectReason.isReconnect() : false;
	}
}
