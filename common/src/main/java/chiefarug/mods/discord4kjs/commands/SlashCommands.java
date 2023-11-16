package chiefarug.mods.discord4kjs.commands;

import chiefarug.mods.discord4kjs.Discord4KJS;
import chiefarug.mods.discord4kjs.DiscordWrapper;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.LongArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.ArgumentCommandNode;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import dev.latvian.mods.kubejs.event.EventJS;
import dev.latvian.mods.kubejs.event.EventResult;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public class SlashCommands {

	record SlashCommandData(LiteralCommandNode<SlashCommandInteraction> command, String description) {
		String name() {
			return command.getName();
		}

		Collection<ArgumentCommandNode<SlashCommandInteraction, ?>> arguments() {
			return getArguments(command);
		}

		Collection<LiteralCommandNode<SlashCommandInteraction>> subCommmands() {
			return getSubCommands(command);
		}

		CommandData build() {
			var builder = Commands.slash(name(), description());
			for (ArgumentCommandNode<SlashCommandInteraction, ?> argument : arguments()) {
				builder.addOption()
			}
		}
	}

	public final CommandDispatcher<SlashCommandInteraction> dispatcher = new CommandDispatcher<>();

	final List<SlashCommandData> globalCommands = new ArrayList<>();
	final List<SlashCommandData> guildCommands = new ArrayList<>();

	LiteralCommandNode<SlashCommandInteraction> registerAndCheck(LiteralArgumentBuilder<SlashCommandInteraction> command) {
		var builtCommand = command.build();
		if (getMaxSubcommandDepth(builtCommand) >= 2)
			throw new IllegalArgumentException("Too many sub commands! Discord only supports up to 2 sub commands (like /ban users role <args>)!");
		dispatcher.getRoot().addChild(builtCommand);
		return builtCommand;
	}

	// Zero is no subcommands. Discord only supports 2, and even then it may be limited
	static int getMaxSubcommandDepth(CommandNode<?> node) {
		int maxDepth = 0;
		for (CommandNode<?> childNode : node.getChildren()) {
			if (childNode instanceof LiteralCommandNode<?>) {
				int depth = 1;
				depth += getMaxSubcommandDepth(childNode);
				if (depth > maxDepth)
					maxDepth = depth;
			}
		}
		return maxDepth;
	}

	static List<ArgumentCommandNode<SlashCommandInteraction, ?>> getArguments(LiteralCommandNode<SlashCommandInteraction> command) {
		return command.getChildren().stream()
					.filter(ArgumentCommandNode.class::isInstance)
					.map((Function<CommandNode<SlashCommandInteraction>, ArgumentCommandNode<SlashCommandInteraction,?>>) (node -> (ArgumentCommandNode<SlashCommandInteraction, ?>) node))
					.toList();
	}

	static Collection<LiteralCommandNode<SlashCommandInteraction>> getSubCommands(LiteralCommandNode<SlashCommandInteraction> command) {
		return command.getChildren().stream()
				.filter(LiteralCommandNode.class::isInstance)
				.map((Function<CommandNode<SlashCommandInteraction>, LiteralCommandNode<SlashCommandInteraction>>) (node -> (LiteralCommandNode<SlashCommandInteraction>) node))
				.toList();
	}


	static OptionType argumentToOption(ArgumentType<?> arg) {
		if (arg instanceof IntegerArgumentType)
			return OptionType.INTEGER;
		else if (arg instanceof DoubleArgumentType || arg instanceof FloatArgumentType || arg instanceof LongArgumentType)
			return OptionType.NUMBER;
		else if (arg instanceof BoolArgumentType)
			return OptionType.BOOLEAN;
		else if (arg instanceof StringArgumentType)
			return OptionType.STRING;
		else if (arg instanceof UserArgumentType)
			return OptionType.USER;
		else if (arg instanceof RoleArgumentType)
			return OptionType.ROLE;
		else if (arg instanceof ChannelArgumentType)
			return OptionType.CHANNEL;
		else if (arg instanceof MentionableArgumentType)
			return OptionType.MENTIONABLE;
//		else if (arg instanceof AttachmentArgumentType) //TODO: Attachments, maybe
//			return OptionType.ATTACHMENT;
		return OptionType.STRING; // it will have a string form from ArgumentType#parse
	}


}
