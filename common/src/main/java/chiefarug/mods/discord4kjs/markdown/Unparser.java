package chiefarug.mods.discord4kjs.markdown;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;

import java.util.Optional;

import static chiefarug.mods.discord4kjs.markdown.Parser.*;

public class Unparser {

	private Component component;

	public Unparser() {
	}

	public Unparser setComponent(Component component) {
		this.component = component;
		return this;
	}

	static class Visitor implements FormattedText.StyledContentConsumer<Void> {

		private Style previousStyle = Style.EMPTY;
		private final StringBuilder stringBuilder;

		Visitor(StringBuilder sb) {
			this.stringBuilder = sb;
		}

		@Override
		public Optional<Void> accept(Style style, String s) {
			if (style.isEmpty()) {
				stringBuilder.append(s);
				return Optional.empty();
			}
			if (previousStyle.isBold() != style.isBold()) stringBuilder.append(boldCharacters);
			if (previousStyle.isItalic() != style.isItalic()) stringBuilder.append(italicCharacters);
			if (previousStyle.isObfuscated() != style.isObfuscated()) stringBuilder.append(obfuscatedCharacters);
			if (previousStyle.isUnderlined() != style.isUnderlined()) stringBuilder.append(underlinedCharacters);
			if (previousStyle.isStrikethrough() != style.isStrikethrough()) stringBuilder.append(strikethroughCharacters);

			stringBuilder.append(s);
			return Optional.empty();
		}

		public void after() {
			accept(Style.EMPTY, "");
		}
	}

	public String unparse() {
		StringBuilder sb = new StringBuilder();
		Visitor v = new Visitor(sb);

		component.visit(v, Style.EMPTY);
		v.after();

		return sb.toString();
	}
}
