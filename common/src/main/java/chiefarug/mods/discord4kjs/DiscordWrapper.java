package chiefarug.mods.discord4kjs;

import dev.latvian.mods.kubejs.typings.Info;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
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
import static chiefarug.mods.discord4kjs.DiscordTypeWrappers.tryMember;

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

	@Info("""
			Sets the default Guild to do things like get channels from. Useful if your bot will only be active in one server.
			
			Note that if your bot actually is only in one server, this is unneeded as we detect and set that automatically.
			""")
	public static void setDefaultGuild(Guild guild) {
		defaultGuild = guild;
	}

	// todo what was the purpose of this?
	public static <T> T getFromGuild(Function<Guild, T> function) {
		if (defaultGuild != null) return function.apply(defaultGuild);
		return null;
	}

	// Bot helpers
	@Info("Gets the bot's User")
	public static SelfUser getBot() {
		return getSelf();
	}

	@Info("Gets the bot's User")
	public static SelfUser getSelf() {
		return jda().getSelfUser();
	}


	// Message helpers
	@Info("Parses markdown in the input string to a Component, for display in chat, tooltips, entity names or similar.")
	public static Component parseMarkdown(String text) {
		return Discord4KJS.parseMarkdown(text);
	}

	@Info("Turns the input component into a string with Discord's markdown characters in it so the text should look pretty similar in both applications")
	public static String unparseMarkdown(Component text) {
		return Discord4KJS.unparseMarkdown(text);
	}

	@Info("Gets a mention for the bot, as a string in the format `<@id>` (which Discord will display as a mention)")
	public static String getSelfMention() {
		return getSelf().getAsMention();
	}

	@Info("Gets the bots global name if present, or username if not. Ignores nicknames because there is no guild context here.")
	public static String getSelfName() {
		return getSelf().getEffectiveName(); //TODO: should we use default server for nickname if present?
	}

	// Presence helpers
	@Info("Gets the bots presence information, like status and status message.")
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

	@Info("Sets the bot's status message. Note that bot statuses MUST start with one of 'Playing ', 'Watching ', 'Listening to ', 'Streaming ' or 'Competing in '!") //TODO wait on JDA to allow fully custom status as discord does allow that now
	public static void setStatusMessage(Activity activity) {
		getPresence().setActivity(activity);
	}

	@Info("Gets the ping time to Discord, in milliseconds")
	public static long getPingTime() {
		return jda().getGatewayPing();
	}

	@Info("""
		Gets a list of all users known to the bot. 
		
		Note that users in this list will not try to be converted to Members by default, so you will need to use Discord.getMember(user) to convert them to a member if you wish to manipulate them in the context of a guild (ie change roles, nickname or ban them)
		""")
	public static List<User> getUsers() {
		return jda().getUsers();
	}

	@Nullable
	@Info("Gets the specified user. Accepts user id or username")
	// Uses the typewrapper! See chiefarug.mods.discord4kjs.DiscordTypeWrappers#user
	public static User getUser(User user) {
		return user;
	}

	@Nullable
	@Info("Gets the specified user as a member of the default guild. Accepts another user, or any formats that work for that (like user id or username)")
	// Uses the typewrapper! See chiefarug.mods.discord4kjs.DiscordTypeWrappers#member
	public static Member getMember(Member member) {
		return member;
	}

	@Nullable
	@Info("Gets the specified user as a member of the provided guild")
	public static Member getMember(User member, Guild guild) {
		if (member != null) return guild.getMember(member);
		return null;
	}

	@Nullable
	@Info("Gets the user specified by snowflake id")
	public static User getUserById(long id) {
		return tryMember(jda().getUserById(id));
	}

	@Nullable
	@Info("Gets the user specified by their username (case sensitive)")
	public static User getUserByName(String name) {
		return tryMember(jda().getUsersByName(name, false).get(0));
	}


	@Nullable
	@Info("Gets the specified channel. Accepts channel id, channel name, and user id (to get that users dms. Only works if the bot has already dmed that user or blockThread is enabled)")
	// Uses the typewrapper, see chiefarug.mods.discord4kjs.DiscordTypeWrappers#messageChannel
	public static MessageChannel getChannel(MessageChannel channel) {
		return channel;
	}

	@Nullable
	@Info("""
		Gets the bots dms with the specified user. This will return null unless dms are already open with the user, or we are allowed to halt the thread to wait for them to open
		
		Use Discord.enableDms if you want to open them and send a message even if they are currently closed.
		""")
	public static PrivateChannel getDms(User user) {
		if (areDmsEnabled(user) || Discord4KJSConfig.blockThread) return enableDms(user).complete();
		return null;
	}

	@Info("""
		Enables dms with the specified user. Note that this needs to contact Discord, so you may not be able to DM the user for a few seconds after calling this.
		
		IMPORTANT: This returns a RestAction, so you MUST .queue() it otherwise this will method will do nothing!
		Example: Discord.enableDms(user).queue()
		
		If you want to send a message as soon as possible, use Discord.enableDms(user).onSuccess(channel => channel.sendMessage("Beep Boop")).queue()
		""")
	public static RestAction<PrivateChannel> enableDms(User user) {
		var ra = user.openPrivateChannel();
		return ra;
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