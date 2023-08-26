package chiefarug.mods.discord4kjs.mixin.methods;

import dev.latvian.mods.rhino.util.RemapPrefixForJS;
import net.dv8tion.jda.api.entities.ISnowflake;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.entities.channel.attribute.IGuildChannelContainer;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.internal.entities.GuildImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import static chiefarug.mods.discord4kjs.DiscordWrapper.defaultGuild;

@RemapPrefixForJS("d4kjs$")
@Mixin(value = net.dv8tion.jda.api.entities.Guild.class, remap = false)
public interface Guild extends IGuildChannelContainer, net.dv8tion.jda.api.entities.Guild {

	@Unique
	default boolean d4kjs$isDefault() {
		return getIdLong() == defaultGuild.getIdLong();
	}

	@Unique
	default MessageChannel d4kjs$getChannel(ISnowflake snowflake) {
		return getChannelById(MessageChannel.class, snowflake.getIdLong());
	}
}
