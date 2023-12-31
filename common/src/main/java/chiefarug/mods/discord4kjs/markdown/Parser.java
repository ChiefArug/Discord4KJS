package chiefarug.mods.discord4kjs.markdown;

import com.mojang.authlib.GameProfile;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

//TODO: Clean up leftovers from MarkCraft. Rewrite completely?
public class Parser {

	static final String emojiCharacters = ":";
	static final String escapeCharacters = "\\";
	static final String boldCharacters = "**";
	static final String underlinedCharacters = "__";
	static final String italicCharacters = "*"; // "_" as well
	static final String strikethroughCharacters = "~~";
	static final String obfuscatedCharacters = "||";
	static final String mentionCharacters = "\\";
	static final TextColor mentionColor = TextColor.fromRgb(0x6E6EFF);
	static final String everyoneWord = "everyone";
	static final boolean translatableMentions = false;

	private String message;
	private final List<Component> parsed = new ArrayList<>();
//	private final MinecraftServer server;
	private String textBuffer = "";
	private Style style = Style.EMPTY;
	private boolean mention = false;
	@Nullable
	// null when no colour set
	private TextColor color = null;

	public Parser(/*MinecraftServer s*/) {
//		server = s;
	}

	public Parser setText(String text) {
		message = text;

		parsed.clear();
		textBuffer = "";
		style = Style.EMPTY;
		mention = false;
		color = null;

		return this;
	}

	public Component parse() {
		Reader reader = new Reader(Emojis.decorate(message));
		while (reader.hasNext()) {
			parseCharacter(reader);
		}
		update();

		return combine(parsed);
	}

	private Component combine(List<Component> components) {
		MutableComponent component = Component.empty().copy();
		for (Component c : components) {
			component.append(c);
		}

		return component;
	}

	private void update() {
		if (!textBuffer.isEmpty()) {
			update(Component.literal(textBuffer));
		}
	}

	private void update(MutableComponent base) {
		textBuffer = "";
		MutableComponent t = base.withStyle(style);
		parsed.add(t);
	}

	private void addChar(char c) {
		textBuffer += c;
	}

	@SuppressWarnings("SpellCheckingInspection")
	private void parseCharacter(Reader reader) {
		char c = reader.next();
		boolean charConsumed = false;

//		// end of mentions (basically any punctuation)
//		if (isMention() && (!Util.isUsernameCharacter(c) || reader.isLast())) {
//			if (reader.isLast() || Util.isUsernameCharacter(c)) {
//				charConsumed = true;
//				addChar(c);
//			}
//			MutableComponent mentionComponent = getMentionComponent(textBuffer);
//			if (mentionComponent == null) {
//				update();
//			} else {
//				update(mentionComponent);
//			}
//
//			mention = false;
//			style = style.withHoverEvent(null).withColor(color != null ? color : defaultColor);
//			// don't return or add the character so that other processing can still happen
//		}

		// \escape character
		// Has priority over everything except the end of a mention (note it will still get processed if used to end a mention as the mention does not return)
		if (isFormatted(escapeCharacters, reader)) {
			if (reader.hasNext()) {
				addChar(reader.next());
			}
			return;
		}

		// **bold**
		if (isFormatted(boldCharacters, reader)) {
			update();
			style = style.withBold(!isBold());
			return;
		}

		// __underline__
		if (isFormatted(underlinedCharacters, reader)) {
			update();
			style = style.withUnderlined(!isUnderlined());
			return;
		}

		// *italics*
		if (isFormatted(italicCharacters, reader)) {
			update();
			style = style.withItalic(!isItalic());
			return;
		}

		// ~~strikethrough~~
		if (isFormatted(strikethroughCharacters, reader)) {
			update();
			style = style.withStrikethrough(!isStrikethrough());
			return;
		}

		// ||obfuscated||
		if (isFormatted(obfuscatedCharacters, reader)) {
			update();
			style = style.withObfuscated(!isObfuscated());
			return;
		}

//		// `ccolor`
//		if (isFormatted(coloredCharacters, reader)) {
//			update();
//			if (isColored() && reader.hasNext()) {
//				TextColor _color = coloringCharacters.get(reader.next());
//				color = _color == null ? defaultColor : _color;
//			} else {
//				color = null;
//			}
//			style = style.withColor(color != null ? color : defaultColor);
//			return;
//		}

//		// @players
//		if (isFormatted(mentionCharacters, reader)) {
//			update();
//			mention = true;
//			// Set the colour here so that it shows up even if the mention is not complete. We can't set the hover component here yet because we do not know the name.
//			// We could resolve the name here though, because all the characters after this till a character that ends the mention will be part of the name
//			// That would also minisculy improve performance because we don't do a bunch of unnescary format character checks.
//			// TODO: Make this use a 'lookahead' system and do all logic here, instead of checking seperately for the end. (L#88)
//			style = style.withColor(mentionColor);
//			addChar(c);
//			return;
//		}

		// normal, boring text
		if (!charConsumed) {
			addChar(c);
		}
	}

	private boolean isFormatted(String formatCharacters, Reader reader) {
		if (reader.isLast()) {
			return reader.current() == formatCharacters.charAt(0);
		}
		int i = 0;
		for (;i < formatCharacters.length();i++) {
			if (reader.has(i) && reader.peek(i) != formatCharacters.charAt(i)) {
				return false;
			}
		}
		reader.move(i - 1);
		return true;
	}

//	// TODO: Split mentions into their own class to tidy some of this up.
//	private MutableComponent getMentionComponent(String name) {
//		if (getEveryoneMention().equals(name)) {
//			return getEveryoneMentionComponent();
//		} else if (name.length() > 1) {
//			return getPlayerNameHoverComponent(name.substring(mentionCharacters.length()));
//		}
//		return null;
//	}
//
//	@NotNull
//	private String getEveryoneMention() {
//		return mentionCharacters + everyoneWord;
//	}
//
//	private MutableComponent getPlayerNameHoverComponent(String name) {
//		ServerPlayer serverPlayer = getServerPlayer(name);
//		if (serverPlayer != null) {
//			return nameHoverComponent(serverPlayer.getGameProfile().getName(), serverPlayer.getName().copy(), serverPlayer.getUUID());
//		}
//
//		return Component.literal(mentionCharacters + name);
//	}
//
//	private MutableComponent getEveryoneMentionComponent() {
//		style = style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, translatableMentions ? Component.translatable("markcraft.mentions.everyone") : Component.literal(everyoneWord)));
//		return Component.literal(getEveryoneMention()).withStyle(style);
//	}
//
//	private MutableComponent nameHoverComponent(String username, MutableComponent name, UUID id) {
//		MutableComponent hoverComponent;
//		if (translatableMentions) {
//			hoverComponent = Component.translatable("markcraft.mentions.player", username, Component.literal(id.toString()).withStyle(ChatFormatting.GRAY));
//		} else {
//			hoverComponent = Component.literal(username).append(Component.literal('#' + id.toString()).withStyle(ChatFormatting.GRAY));
//		}
//		style = style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hoverComponent));
//
//
//		return Component.literal(mentionCharacters).append(name);
//
//	}
//
//	@Nullable
//	private ServerPlayer getServerPlayer(String name) {
//		name = name.toLowerCase(Locale.ROOT);
//		for (ServerPlayer p : server.getPlayerList().getPlayers()) {
//			if (p.getGameProfile().getName().toLowerCase(Locale.ROOT).contains(name)) {
//				return p;
//			}
//		}
//		return null;
//	}
//
//	@Nullable
//	private GameProfile getPlayerProfile(String name) {
//		// TODO: use GameProfileCache#getAsync so the server doesn't freeze
//		return server.getProfileCache().get(name).orElse(null);
//	}

	private boolean isBold() {
		return style.isBold();
	}
	private boolean isItalic() {
		return style.isItalic();
	}
	private boolean isMention() {
		return mention;
	}
	private boolean isObfuscated() {
		return style.isObfuscated();
	}
	private boolean isUnderlined() {
		return style.isUnderlined();
	}
	private boolean isStrikethrough() {
		return style.isStrikethrough();
	}
	private boolean isColored() {
		return color == null;
	}


}
