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

@RemapPrefixForJS("d4kjs$")
@Mixin(value = GuildImpl.class, remap = false)
public abstract class Guild implements IGuildChannelContainer {

	@Unique
	public MessageChannel d4kjs$getChannel(ISnowflake snowflake) {
		return getChannelById(MessageChannel.class, snowflake.getIdLong());
	}
}
