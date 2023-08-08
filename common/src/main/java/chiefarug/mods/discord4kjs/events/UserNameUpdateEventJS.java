package chiefarug.mods.discord4kjs.events;

import dev.latvian.mods.kubejs.typings.Info;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdateNicknameEvent;
import net.dv8tion.jda.api.events.user.update.GenericUserUpdateEvent;
import net.dv8tion.jda.api.events.user.update.UserUpdateGlobalNameEvent;
import net.dv8tion.jda.api.events.user.update.UserUpdateNameEvent;
import org.jetbrains.annotations.Nullable;

import static chiefarug.mods.discord4kjs.Discord4KJS.CONSOLE;
import static chiefarug.mods.discord4kjs.events.UserNameUpdateEventJS.NameType.*;

public class UserNameUpdateEventJS extends UserUpdateEventJS<String> {

	private final NameType type;
	@Nullable
	private final Guild server;

	enum NameType {
		USERNAME, DISPLAY_NAME, NICKNAME
	}

	private UserNameUpdateEventJS(GenericUserUpdateEvent<String> wrappedEvent, NameType type) {
		super(wrappedEvent);
		this.type = type;
		this.server = null;
	}

	public UserNameUpdateEventJS(UserUpdateNameEvent wrappedEvent) {
		this(wrappedEvent, USERNAME);
	}

	public UserNameUpdateEventJS(UserUpdateGlobalNameEvent wrappedEvent) {
		this(wrappedEvent, DISPLAY_NAME);
	}

	public UserNameUpdateEventJS(GuildMemberUpdateNicknameEvent wrappedEvent) {
		super(wrappedEvent.getUser(), wrappedEvent.getOldNickname(), wrappedEvent.getNewNickname());
		this.type = NICKNAME;
		this.server = wrappedEvent.getGuild();
	}

	@Nullable
	@Info("Gets the Guild that the name changed in. Only valid if .nameType == 'NICKNAME'")
	public Guild getGuild() {
		if (type != NICKNAME) CONSOLE.error("Cannot get the server/guild of non-nickname name changes as they are global!");
		return server;
	}

	public NameType getNameType() {
		return type;
	}

	public String getNewName() {
		return newValue;
	}

	public String getOldName() {
		return oldValue;
	}
}
