package chiefarug.mods.discord4kjs.mixin.jda;

import chiefarug.mods.discord4kjs.DiscordWrapper;
import dev.latvian.mods.rhino.util.RemapPrefixForJS;
import net.dv8tion.jda.api.entities.ISnowflake;
import net.dv8tion.jda.api.entities.UserSnowflake;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@RemapPrefixForJS("discord4kjs$")
@Mixin(value = UserSnowflake.class, remap = false)
public interface UserSnowflakeMixin extends ISnowflake {
	@Unique
	default boolean discord4kjs$isSelf() {
		return DiscordWrapper.getSelf().getIdLong() == getIdLong();
	}
}
