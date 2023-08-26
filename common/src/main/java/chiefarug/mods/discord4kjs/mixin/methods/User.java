package chiefarug.mods.discord4kjs.mixin.methods;

import chiefarug.mods.discord4kjs.Discord4KJS;
import chiefarug.mods.discord4kjs.DiscordWrapper;
import dev.latvian.mods.rhino.util.RemapPrefixForJS;
import net.dv8tion.jda.api.entities.ISnowflake;
import net.dv8tion.jda.api.entities.UserSnowflake;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@RemapPrefixForJS("d4kjs$")
@Mixin(value = UserSnowflake.class, remap = false)
public interface User extends ISnowflake {

	@Unique
	default boolean d4kjs$isSelf() {
		return DiscordWrapper.getSelf().getIdLong() == getIdLong();
	}
}
