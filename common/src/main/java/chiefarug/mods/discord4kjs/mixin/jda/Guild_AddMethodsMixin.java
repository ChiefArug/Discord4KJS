package chiefarug.mods.discord4kjs.mixin.jda;

import dev.latvian.mods.rhino.util.RemapPrefixForJS;
import net.dv8tion.jda.api.entities.ISnowflake;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import static chiefarug.mods.discord4kjs.DiscordWrapper.defaultGuild;

@RemapPrefixForJS("discord4kjs$")
@Mixin(value = net.dv8tion.jda.api.entities.Guild.class, remap = false)
public interface Guild_AddMethodsMixin extends net.dv8tion.jda.api.entities.Guild {

	@Unique
	default boolean discord4kjs$isDefault() {
		return getIdLong() == defaultGuild.getIdLong();
	}

	@Unique
	default MessageChannel discord4kjs$getChannel(ISnowflake snowflake) {
		return getChannelById(MessageChannel.class, snowflake.getIdLong());
	}
}
