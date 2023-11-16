package chiefarug.mods.discord4kjs.commands;

import chiefarug.mods.discord4kjs.Discord4KJS;
import chiefarug.mods.discord4kjs.DiscordWrapper;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import dev.latvian.mods.kubejs.event.EventJS;
import dev.latvian.mods.kubejs.event.EventResult;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;

public class SlashCommandRegistryEventJS extends EventJS {

	private final SlashCommands slashCommands;

	public SlashCommandRegistryEventJS(SlashCommands slashCommands) {
		this.slashCommands = slashCommands;
	}

	public LiteralCommandNode<SlashCommandInteraction> registerGlobal(String description, LiteralArgumentBuilder<SlashCommandInteraction> command) {
		var node = slashCommands.registerAndCheck(command);
		slashCommands.globalCommands.add(new SlashCommands.SlashCommandData(node, description));
		return node;
	}

	@Override
	protected void afterPosted(EventResult result) {
		for (SlashCommands.SlashCommandData commandData : slashCommands.globalCommands) {
//			Discord4KJS.jda().upsertCommand();
		}
		for (SlashCommands.SlashCommandData commandData : slashCommands.guildCommands) {
//			DiscordWrapper.defaultGuild.upsertCommand(commandData.name(), commandData.description());
		}
	}
}
