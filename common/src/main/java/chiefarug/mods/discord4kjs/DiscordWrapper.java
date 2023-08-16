package chiefarug.mods.discord4kjs;

import dev.latvian.mods.kubejs.typings.Info;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.SelfUser;
import net.dv8tion.jda.api.managers.Presence;
import net.dv8tion.jda.internal.JDAImpl;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;

import static chiefarug.mods.discord4kjs.Discord4KJS.jda;

public interface DiscordWrapper {

	static class CodeHolder{static{
		jda().getSelfUser().getAsMention();// self mention
		jda().getGuilds();// get all guilds in
		jda().getGatewayPing(); // ping!
		jda().getHttpClient(); // you are NOT allowed to access this.
		jda().getInviteUrl();// invite url for the bot. woo. need to calc perms somehow.. not sure how
		jda().getPrivateChannels();// all dms the bot is in
		jda().getStatus(); // maybe?
		jda().getSelfUser(); // getBot() and getSelf()
		jda().getUsers(); // uuuuusers
		jda().getUsersByName(null, false); //search users
		jda().getMutualGuilds(new ArrayList<>()); // get mutuals
		jda().getNewsChannels(); // channel helpers
		jda().getCategories(); // moer channel helpers
		((JDAImpl) jda()).getClient(); // no using this either, untyped js people.



	}}

	// Bot helpers
	@Info("Gets the bot")
	default SelfUser getBot() {
		return getSelf();
	}

	@Info("Gets the bot")
	default SelfUser getSelf() {
		return jda().getSelfUser();
	}


	// Message helpers
	@Info("Parses markdown in the input string to a Component, for display in chat, tooltips, entity names or similar.")
	default Component parseMarkdown(String text) {
		return Discord4KJS.parseMarkdown(text);
	}

	@Info("Gets a mention for the bot, as a string in the format '<@id>' (which Discord will display as a mention)")
	default String getSelfMention() {
		return getSelf().getAsMention();
	}

	@Info("Gets the bots global name if present, or username if not. Ignores nicknames because there is no server context here.")
	default String getSelfName() {
		return getSelf().getEffectiveName();
	}

	// Presence helpers
	@Info("Gets the bots presence information, like statusa and status message.")
	default Presence getPresence() {
		return jda().getPresence();
	}

	@Info("Sets the bot's status to 🟢Online")
	default void online() {
		setStatus(OnlineStatus.ONLINE);
	}

	@Info("Sets the bot's status to 🟡Idle")
	default void away() {
		idle();
	}

	@Info("Sets the bot's status to 🟡Idle")
	default void idle() {
		setStatus(OnlineStatus.IDLE);
	}

	@Info("Sets the bot's status to ⚪Invisible")
	default void invisible() {
		setStatus(OnlineStatus.INVISIBLE);
	}

	@Info("Sets the bot's status to 🔴Do Not Disturb")
	default void dnd() {
		doNotDisturb();
	}

	@Info("Sets the bot's status to 🔴Do Not Disturb")
	default void doNotDisturb() {
		setStatus(OnlineStatus.DO_NOT_DISTURB);
	}

	@Info("Sets the bot's status. Can be one of ONLINE, IDLE, INVISIBLE or DO_NOT_DISTURB.")
	default void setStatus(OnlineStatus status) {
		getPresence().setPresence(status, getPresence().getActivity());
	}

	@Info("Sets the bot's status message. Note that bot statuses MUST start with one of 'Playing ', 'Watching ', 'Listening to ', 'Streaming ' or 'Competing in '!")
	default void setStatusMessage(Activity activity) {
		getPresence().setActivity(activity);
	}

	//
}
