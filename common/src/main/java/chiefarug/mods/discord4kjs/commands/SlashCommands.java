package chiefarug.mods.discord4kjs.commands;

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

	record SlashCommandDataJS(LiteralCommandNode<SlashCommandInteraction> command, String description) {
		String name() {
			return command.getName();
		}

		Collection<ArgumentCommandNode<SlashCommandInteraction, ?>> arguments() {
			return getArgumentsAndVerifyLevels(command);
		}

		Collection<LiteralCommandNode<SlashCommandInteraction>> subCommmands() {
			return getDirectSubCommands(command);
		}

		CommandData build() {
			var builder = Commands.slash(name(), description());
			for (ArgumentCommandNode<SlashCommandInteraction, ?> argument : arguments()) {
//				builder.addOption()
			}
			return null;
		}

		private static void addArgument(SlashCommandData builder, ArgumentCommandNode<SlashCommandInteraction, ?> arg) {
//			builder.addOption(argumentToOption(arg.getType()))
		}
	}

	public final CommandDispatcher<SlashCommandInteraction> dispatcher = new CommandDispatcher<>();


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

	static List<ArgumentCommandNode<SlashCommandInteraction, ?>> getArgumentsAndVerifyLevels(LiteralCommandNode<SlashCommandInteraction> command) {
		List<ArgumentCommandNode<SlashCommandInteraction, ?>> args = new ArrayList<>();

		Boolean isSubcommand = null;
		for (CommandNode<SlashCommandInteraction> node : command.getChildren()) {
			if (node instanceof ArgumentCommandNode<SlashCommandInteraction,?> arg) {
				if (isSubcommand != null) {
					if (isSubcommand) throw new IllegalArgumentException("Cannot have subcommands and arguments on the same level for a Discord command!");
					else throw new IllegalArgumentException("Cannot have multiple arguments on the same level for a Discord command!");
				}
				isSubcommand = false;
				args.add(arg);
			} else if (node instanceof LiteralCommandNode<SlashCommandInteraction> subcommand) {
				if (isSubcommand != null && !isSubcommand) throw new IllegalArgumentException("Cannot have subcommands and arguments on the same level for a Discord command!");
				isSubcommand = true;
			}
		}

		if (isSubcommand == null || isSubcommand) return List.of();
		return args;
	}

	static Collection<LiteralCommandNode<SlashCommandInteraction>> getDirectSubCommands(LiteralCommandNode<SlashCommandInteraction> command) {
		return command.getChildren().stream()
				.filter(LiteralCommandNode.class::isInstance)
				.map((Function<CommandNode<SlashCommandInteraction>, LiteralCommandNode<SlashCommandInteraction>>) (node -> (LiteralCommandNode<SlashCommandInteraction>) node))
				.toList();
	}





}
