package chiefarug.mods.discord4kjs.events;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.user.GenericUserEvent;
import net.dv8tion.jda.api.events.user.update.GenericUserUpdateEvent;

public abstract class UserUpdateEventJS<T> extends DiscordEventJS {

	private final User user;
	protected final T oldValue;
	protected final T newValue;

	protected UserUpdateEventJS(GenericUserUpdateEvent<T> wrappedEvent) {
		super(wrappedEvent);
		this.user = wrappedEvent.getUser();
		this.oldValue = wrappedEvent.getOldValue();
		this.newValue = wrappedEvent.getNewValue();
	}

	protected UserUpdateEventJS(User user, T oldValue, T newValue, GenericEvent wrappedEvent) {
		super(wrappedEvent);
		this.user = user;
		this.oldValue = oldValue;
		this.newValue = newValue;
	}

	public User getUser() {
		return user;
	}
}
