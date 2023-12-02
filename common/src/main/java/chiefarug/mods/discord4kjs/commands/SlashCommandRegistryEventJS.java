package chiefarug.mods.discord4kjs.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import dev.latvian.mods.kubejs.event.EventJS;
import dev.latvian.mods.kubejs.event.EventResult;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SlashCommandRegistryEventJS extends EventJS {

	final Guild guild;
	final List<SlashCommands.SlashCommandDataJS> commands = new ArrayList<>();

	public SlashCommandRegistryEventJS(@Nullable Guild guild) {
		this.guild = guild;
	}

	public LiteralCommandNode<SlashCommandInteraction> register(String description, LiteralArgumentBuilder<SlashCommandInteraction> command) {

	}

	@Override
	protected void afterPosted(EventResult result) {
		for (SlashCommands.SlashCommandDataJS commandData : commands) {
//			Discord4KJS.jda().upsertCommand();
		}
		for (SlashCommands.SlashCommandDataJS commandData : commands) {
//			DiscordWrapper.defaultGuild.upsertCommand(commandData.name(), commandData.description());
		}
	}
}
