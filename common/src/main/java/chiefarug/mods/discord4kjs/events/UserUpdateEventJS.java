package chiefarug.mods.discord4kjs.events;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.user.update.GenericUserUpdateEvent;

public abstract class UserUpdateEventJS<T> extends DiscordEventJS {

	private final User user;
	protected final T oldValue;
	protected final T newValue;

	protected UserUpdateEventJS(GenericUserUpdateEvent<T> wrappedEvent) {
		this(wrappedEvent.getUser(), wrappedEvent.getOldValue(), wrappedEvent.getNewValue());
	}

	protected UserUpdateEventJS(User user, T oldValue, T newValue) {
		this.user = user;
		this.oldValue = oldValue;
		this.newValue = newValue;
	}

	public User getUser() {
		return user;
	}
}
