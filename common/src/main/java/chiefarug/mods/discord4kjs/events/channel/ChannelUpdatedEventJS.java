package chiefarug.mods.discord4kjs.events.channel;

import dev.latvian.mods.kubejs.typings.Info;
import net.dv8tion.jda.api.entities.channel.ChannelField;
import net.dv8tion.jda.api.events.channel.GenericChannelEvent;
import net.dv8tion.jda.api.events.channel.update.GenericChannelUpdateEvent;

public class ChannelUpdatedEventJS<T> extends ChannelEventJS {

	protected final String changed;
	protected final T oldValue;
	protected final T newValue;

	public ChannelUpdatedEventJS(GenericChannelUpdateEvent<T> wrappedEvent) {
		super(wrappedEvent);
		this.changed = wrappedEvent.getPropertyIdentifier();
		this.oldValue = wrappedEvent.getOldValue();
		this.newValue = wrappedEvent.getNewValue();
	}

	public ChannelUpdatedEventJS(String changed, T oldValue, T newValue, GenericChannelEvent wrappedEvent) {
		super(wrappedEvent);
		this.changed = changed;
		this.oldValue = oldValue;
		this.newValue = newValue;
	}

	@Info("""
			Returns the property that changed.
			Note that not all properties are available on all channels. Some are voice specific, some are thread specific and some are forum specific
			type, name, flags, parent, position, default_thread_slowmode, default_reaction_emoji, topic, nsfw, slowmode, available_tags, bitrate, region, userlimit, autoArchiveDuration, archived, archiveTimestamp, locked, invitable, applied_tags, default_forum_layout
			""")
	public String getProperty() {
		return changed;
	}

	@Info("Gets the old value of the property")
	public T getOld() {
		return oldValue;
	}

	@Info("Gets the new value of the property")
	public T getNew() {
		return newValue;
	}
}
