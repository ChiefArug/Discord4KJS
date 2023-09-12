package chiefarug.mods.discord4kjs;

import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.requests.restaction.CommandCreateAction;

import static chiefarug.mods.discord4kjs.Discord4KJS.jda;

public class CommandRegistry {

	public CommandCreateAction create(String name, String description) {
		return jda().upsertCommand(name, description);
	}

}
