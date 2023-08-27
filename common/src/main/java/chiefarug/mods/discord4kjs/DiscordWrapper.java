package chiefarug.mods.discord4kjs;

import dev.latvian.mods.kubejs.typings.Info;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.SelfUser;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.PrivateChannel;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.managers.Presence;
import net.dv8tion.jda.api.requests.RestAction;
import net.dv8tion.jda.internal.JDAImpl;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static chiefarug.mods.discord4kjs.Discord4KJS.jda;

public class DiscordWrapper {

	static class CodeHolder{static{
		jda().getSelfUser().getAsMention();// self mention
		jda().getGuilds();// get all guilds in
		jda().getInviteUrl();// invite url for the bot. woo. need to calc perms somehow.. not sure how. base it off the intentions config?
		jda().getPrivateChannels();// all dms the bot is in
		jda().getStatus(); // maybe?
		jda().getSelfUser(); // getBot() and getSelf()
		jda().getUsersByName(null, false); //search users
		jda().getMutualGuilds(new ArrayList<>()); // get mutuals
		jda().getNewsChannels(); // channel helpers
		jda().getCategories(); // moer channel helpers



	}}
	
	
	public static Guild defaultGuild = null;

	@Info("Sets the default Guild to do things like get channels from. Useful if your bot will only be in one server")
	public static void setDefaultGuild(Guild guild) {
		defaultGuild = guild;
	}

	public static <T> T getFromGuild(Function<Guild, T> function) {
		if (defaultGuild != null) return function.apply(defaultGuild);
		return null;
	}

	// Bot helpers
	@Info("Gets the bot")
	public static SelfUser getBot() {
		return getSelf();
	}

	@Info("Gets the bot")
	public static SelfUser getSelf() {
		return jda().getSelfUser();
	}


	// Message helpers
	@Info("Parses markdown in the input string to a Component, for display in chat, tooltips, entity names or similar.")
	public static Component parseMarkdown(String text) {
		return Discord4KJS.parseMarkdown(text);
	}

	@Info("Turns the input component into a string with discords markdown characters in it so the text should look the same in both applications")
	public static String unparseMarkdown(Component text) {
		return Discord4KJS.unparseMarkdown(text);
	}

	@Info("Gets a mention for the bot, as a string in the format '<@id>' (which Discord will display as a mention)")
	public static String getSelfMention() {
		return getSelf().getAsMention();
	}

	@Info("Gets the bots global name if present, or username if not. Ignores nicknames because there is no server context here.")
	public static String getSelfName() {
		return getSelf().getEffectiveName();
	}

	// Presence helpers
	@Info("Gets the bots presence information, like statusa and status message.")
	public static Presence getPresence() {
		return jda().getPresence();
	}

	@Info("Sets the bot's status to 🟢Online")
	public static void online() {
		setStatus(OnlineStatus.ONLINE);
	}

	@Info("Sets the bot's status to 🟡Idle")
	public static void away() {
		idle();
	}

	@Info("Sets the bot's status to 🟡Idle")
	public static void idle() {
		setStatus(OnlineStatus.IDLE);
	}

	@Info("Sets the bot's status to ⚪Invisible")
	public static void invisible() {
		setStatus(OnlineStatus.INVISIBLE);
	}

	@Info("Sets the bot's status to 🔴Do Not Disturb")
	public static void dnd() {
		doNotDisturb();
	}

	@Info("Sets the bot's status to 🔴Do Not Disturb")
	public static void doNotDisturb() {
		setStatus(OnlineStatus.DO_NOT_DISTURB);
	}

	@Info("Sets the bot's status. Can be one of ONLINE, IDLE, INVISIBLE or DO_NOT_DISTURB.")
	public static void setStatus(OnlineStatus status) {
		getPresence().setPresence(status, getPresence().getActivity());
	}

	@Info("Sets the bot's status message. Note that bot statuses MUST start with one of 'Playing ', 'Watching ', 'Listening to ', 'Streaming ' or 'Competing in '!")
	public static void setStatusMessage(Activity activity) {
		getPresence().setActivity(activity);
	}

	@Info("Gets the ping time to Discord, in milliseconds")
	public static long getPingTime() {
		return jda().getGatewayPing();
	}

	@Info("Gets a list of all users known to the bot")
	public static List<User> getUsers() {
		return jda().getUsers();
	}

	@Nullable
	@Info("Gets the specified user. Accepts user id or username")
	// Uses the typewrapper, neat
	public static User getUser(User user) {
		return user;
	}

	@Nullable
	@Info("Gets the user specified by snowflake id")
	public static User getUserById(long id) {
		return jda().getUserById(id);
	}

	@Nullable
	@Info("Gets the user specified by their username (case sensitive)")
	public static User getUserByName(String name) {
		return jda().getUsersByName(name, false).get(0);
	}


	@Nullable
	@Info("Gets the specified channel. Accepts channel id, channel name, and user id (user id will get that user dms. Only works if the bot has already dmed that user or blockThread is enabled")
	// Uses the typewrapper, neat
	public static MessageChannel getChannel(MessageChannel channel) {
		return channel;
	}

	@Info("""
		Enables dms with the specified user. Note that this needs to contact Discord, so you may not be able to DM the user for a few seconds after calling this
		
		If you want to send a message as soon as possible, use Discord.enableDms(user).onSuccess(channel => channel.sendMessage("Beep Boop").queue())
		""")
	public static RestAction<PrivateChannel> enableDms(User user) {
		return user.openPrivateChannel();
	}

	@Info("Returns if the bot has dms open with the user. Note this doesn't mean that a message has been sent to them or that the channel is visible to the user")
	public static boolean areDmsEnabled(User user) {
		return user.hasPrivateChannel();
	}

	@Info("Gets the specified guild (a guild is another name for a discord server). Accepts guild id or guild name (case sensitive)")
	public static Guild getGuild(Guild guild) {
		return guild;
	}

}
