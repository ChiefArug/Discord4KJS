package chiefarug.mods.discord4kjs.commands;

import chiefarug.mods.discord4kjs.Discord4KJS;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;

public class GlobalCommandManager extends CommandManager {
	@Override
	protected void upsertCommand(SlashCommand command) {
		Discord4KJS.jda().upsertCommand(command.name(), command.description());
	}
}
