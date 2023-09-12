package chiefarug.mods.discord4kjs;

import com.neovisionaries.ws.client.WebSocket;
import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.script.BindingsEvent;
import dev.latvian.mods.kubejs.script.ScriptType;
import dev.latvian.mods.kubejs.util.ClassFilter;
import dev.latvian.mods.rhino.util.wrap.TypeWrappers;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.ISnowflake;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.UserSnowflake;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.entities.channel.concrete.ForumChannel;
import net.dv8tion.jda.api.entities.channel.concrete.NewsChannel;
import net.dv8tion.jda.api.entities.channel.concrete.StageChannel;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.entities.channel.middleman.AudioChannel;
import net.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.internal.JDAImpl;
import net.dv8tion.jda.internal.entities.UserSnowflakeImpl;
import net.minecraft.server.players.UserWhiteList;
import okhttp3.OkHttp;

import static chiefarug.mods.discord4kjs.Discord4KJS.LGGR;

public class Disord4KJSPlugin extends KubeJSPlugin {
	@Override
	public void init() {
		//TODO: load config here, then maybe conditionally register events based on intents?
		DiscordEvents.register();
		Discord4KJS.init();
	}

	@Override
	public void registerClasses(ScriptType type, ClassFilter filter) {
		// use .class.getPackage() so that shadow moves the path for us when it *asms*
		filter.deny(JDA.class.getPackage().getName());
		filter.deny(JDAImpl.class.getPackage().getName());
		filter.deny(OkHttp.class.getPackage().getName());
		filter.deny(WebSocket.class.getPackage().getName()); //todo: check if i need to stop excluding this from the shadow jar
	}

	@Override
	public void registerBindings(BindingsEvent event) {
		switch (event.getType()) {
			case SERVER -> serverBindings(event);
		}
	}

	@Override
	public void registerTypeWrappers(ScriptType type, TypeWrappers event) {
		event.register(Activity.class, DiscordTypeWrappers::activity);
		event.register(Guild.class, DiscordTypeWrappers::guild);
		event.register(Channel.class, DiscordTypeWrappers::channel);
		event.register(GuildChannel.class, DiscordTypeWrappers.guildChannel);
		event.register(MessageChannel.class, DiscordTypeWrappers.messageChannel);
 		event.register(ForumChannel.class, DiscordTypeWrappers.forumChannel);
		event.register(NewsChannel.class, DiscordTypeWrappers.newsChannel);
		event.register(AudioChannel.class, DiscordTypeWrappers.audioChannel);
 		event.register(StageChannel.class, DiscordTypeWrappers.stageChannel);
 		event.register(VoiceChannel.class, DiscordTypeWrappers.voiceChannel);
		event.register(ISnowflake.class, DiscordTypeWrappers::snowflake);
		event.register(User.class, DiscordTypeWrappers::user);
		event.register(Member.class, DiscordTypeWrappers::member);
	}

	private void serverBindings(BindingsEvent event) {
		event.add("Discord", DiscordWrapper.class);
		//TODO add binding and helper methods for net.dv8tion.jda.api.Permission
	}

	@Override
	public void registerEvents() {
	}
}
