package chiefarug.mods.discord4kjs.mixin.jda;

import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.rhino.util.RemapPrefixForJS;
import net.dv8tion.jda.api.entities.IMentionable;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@RemapPrefixForJS("discord4kjs$")
@Mixin(value = net.dv8tion.jda.api.entities.channel.Channel.class, remap = false)
public interface Channel extends net.dv8tion.jda.api.entities.channel.Channel {

	@Shadow
	ChannelType getType();

	@Unique
	default boolean discord4kjs$isDm() {
		return getType() == ChannelType.PRIVATE;
	}
}
