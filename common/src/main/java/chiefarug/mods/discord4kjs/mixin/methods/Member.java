package chiefarug.mods.discord4kjs.mixin.methods;

import dev.latvian.mods.rhino.util.RemapForJS;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.PrivateChannel;
import net.dv8tion.jda.api.requests.restaction.CacheRestAction;
import net.dv8tion.jda.api.utils.ImageProxy;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.EnumSet;
import java.util.List;

@Mixin(value = net.dv8tion.jda.api.entities.Member.class, remap = false)
public interface Member extends User {

	@Shadow
	User getUser();
	
	@Override
	@RemapForJS("getUsername")
	default String getName() {
		return getUser().getName();
	}

	@Override
	default String getGlobalName() {
		return getUser().getName();
	}

	@Override
	@RemapForJS("getName")
	default String getEffectiveName() {
		return getUser().getEffectiveName();
	}

	@Override
	@Deprecated
	default String getDiscriminator() {
		return getUser().getDiscriminator();
	}

	@Override
	default String getAvatarId() {
		return getUser().getAvatarId();
	}

	@Override
	default String getAvatarUrl() {
		return getUser().getAvatarUrl();
	}

	@Override
	default ImageProxy getAvatar() {
		return getUser().getAvatar();
	}

	@Override
	default String getEffectiveAvatarUrl() {
		return getUser().getEffectiveAvatarUrl();
	}

	@Override
	default ImageProxy getEffectiveAvatar() {
		return getUser().getEffectiveAvatar();
	}

	@Override
	default CacheRestAction<Profile> retrieveProfile() {
		return getUser().retrieveProfile();
	}

	@Override
	default String getAsTag() {
		return getUser().getAsTag();
	}

	@Override
	default boolean hasPrivateChannel() {
		return getUser().hasPrivateChannel();
	}

	@Override
	default CacheRestAction<PrivateChannel> openPrivateChannel() {
		return getUser().openPrivateChannel();
	}

	@Override
	default List<Guild> getMutualGuilds() {
		return getUser().getMutualGuilds();
	}

	@Override
	default boolean isBot() {
		return getUser().isBot();
	}

	@Override
	default boolean isSystem() {
		return getUser().isSystem();
	}

	@Override
	default JDA getJDA() {
		return getUser().getJDA();
	}

	@Override
	default EnumSet<UserFlag> getFlags() {
		return getUser().getFlags();
	}

	@Override
	default int getFlagsRaw() {
		return getUser().getFlagsRaw();
	}
}
