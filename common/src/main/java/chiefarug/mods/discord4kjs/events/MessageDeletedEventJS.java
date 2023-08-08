package chiefarug.mods.discord4kjs.events;

import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageDeleteEvent;
import net.minecraft.network.chat.Component;
import org.apache.commons.lang3.NotImplementedException;
import org.jetbrains.annotations.MustBeInvokedByOverriders;

public class MessageDeletedEventJS extends MessageEventJS {

	protected MessageDeletedEventJS(MessageDeleteEvent wrappedEvent) {
		super(null, wrappedEvent);
	}

	@Override
	@HideFromJS
	@Info("Cannot get the content of a deleted message!")
	public Message getMessage() {
		throw new NotImplementedException();
	}

	@Override
	@HideFromJS
	@Info("Cannot get the content of a deleted message!")
	public Component getMessageFormatted() {
		throw new NotImplementedException();
	}


}
