package chiefarug.mods.discord4kjs.config;

import com.google.common.collect.Lists;
import com.mojang.logging.LogUtils;
import dev.architectury.platform.Platform;
import org.slf4j.Logger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 *
 * Yet another config class because I don't want yet another dep.
 *
 * @author ChiefArug
 * @param name The name that should be used to describe the config, usually a modid.
 * @param location The full path of the config file.
 * @param _valueMap A new empty map.
 *                  Reccomended to be a LinkedHashMap so that order of insertion is preserved.
 *                  This is a workaround to making our own in a private field so this can still be a record.
 *                  Don't touch it plsthanks
 */
public record Configger(String name, Path location, Map<String, ConfigValue<?>> _valueMap) {
	private static final Logger CLOGGER = LogUtils.getLogger();
	public static final Path MC_ROOT = Platform.getGameFolder();

	public <V> ConfigValue<V> value(String name, V defaultValue, ConfigValueReader<V> reader, ConfigValueWriter<V> writer, String[] comments) {
		name = name.trim();
		validateName(name);

		ConfigValue<V> cv = new ConfigValue<>(name, defaultValue, reader, writer, comments);
		_valueMap.put(name, cv);
		return cv;
	}
	public <V> ConfigValue<V> value(String name, V defaultValue, ConfigValueReader<V> reader, String ...comments) {
		return value(name, defaultValue, reader, (ConfigValueWriter<V>) ConfigValueWriter.DEFAULT, comments);
	}

	public ConfigValue<Boolean> booleanValue(String name, boolean defaultvalue, String ...comments) {
		return value(name, defaultvalue, ConfigValueReader.BOOLEAN_READER, comments);
	}
	public ConfigValue<Integer> integerValue(String name, int defaultValue, String ...comments) {
		return value(name, defaultValue, ConfigValueReader.INTEGER_READER, comments);
	}
	public ConfigValue<Float> floatValue(String name, float defaultValue, String ...comments) {
		return value(name, defaultValue, ConfigValueReader.FLOAT_READER, comments);
	}
	public <E extends Enum<E>> ConfigValue<E> enumValue(String name, Enum<E> defaultValue, String ...comments) {
		return value(name, defaultValue, ConfigValueReader.fromEnum(defaultValue.getClass()), comments);
	}
	public <T> ConfigValue<List<T>> listValue(String name, List<T> defaultValues,ConfigValueReader<T> innerReader, String ...comments) {
		return value(name, defaultValues, ConfigValueReader.asList(innerReader), ConfigValueWriter.DEFAULT_COLLECTION.cast(), comments);
	}
	public <E extends Enum<E>> ConfigValue<EnumSet<E>> enumSetValue(String name, EnumSet<E> defaultValues, Class<E> enumClass, String ...comments) {
		return value(name, defaultValues, ConfigValueReader.enumSet(enumClass), ConfigValueWriter.ENUM_COLLECTION.cast(), comments);
	}

	private boolean validateName(String name) {
		if (name.indexOf('=') != -1)
			throw new IllegalArgumentException("Cannot use = in config value name, it is a reserved character");
		if (name.startsWith("#"))
			throw new IllegalArgumentException("Cannot start config value names with # as it is the comment character");
		return true;
	}

	public final class ConfigValue<V> implements Supplier<V> {

		private String name;
		private final V defaultValue;
		private V value;
		private ConfigValueReader<V> reader;
		private ConfigValueWriter<V> writer;
		private String[] comments;

		// Use the methods in Configger!
		private ConfigValue(String name, V defaultValue, ConfigValueReader<V> reader, ConfigValueWriter<V> writer, String ...comments) {
			this.name = name;
			this.defaultValue = defaultValue;
			this.reader = reader;
			this.writer = writer;
			this.comments = comments;
		}

		protected void write(Writer out) throws IOException {
			for (String comment : comments) {
				out.write("# " + comment);
				out.write(System.lineSeparator());
			}
			out.write(name + "=" + writer.apply(get()));
		}

		protected void set(String value) {
			this.value = reader.apply(value);
		}

		@Override
		public V get() {
			return value == null ? defaultValue : value;
		}
	}

	@FunctionalInterface
	public interface ConfigValueWriter<T> extends Function<T, String> {
		public static final ConfigValueWriter<? extends Object> DEFAULT = v -> String.valueOf(v);
		public static final ConfigValueWriter<Enum<? extends Enum<?>>> ENUM = e -> e.name();
		public static final ConfigValueWriter<? extends Collection<?>> DEFAULT_COLLECTION = asCollection(DEFAULT);
		public static final ConfigValueWriter<? extends Collection<?>> ENUM_COLLECTION = asCollection(ENUM);
		public static <T> ConfigValueWriter<Collection<T>> asCollection(ConfigValueWriter<T> inner) {
			return v -> v.stream()
					.map(inner)
					.collect(Collectors.joining(", "));
		}
		default <V> ConfigValueWriter<V> cast() {
			return (ConfigValueWriter<V>) this; // Grumble grumble generics
		}
	}

	@FunctionalInterface
	public interface ConfigValueReader<T> extends Function<String, T> {

		public static final ConfigValueReader<Boolean> BOOLEAN_READER = illegalArgumentToNull(Boolean::valueOf);
		public static final ConfigValueReader<Integer> INTEGER_READER = illegalArgumentToNull(Integer::valueOf);
		public static final ConfigValueReader<Float> FLOAT_READER = illegalArgumentToNull(Float::valueOf);

		private static <T> ConfigValueReader<T> illegalArgumentToNull(Function<String, T> inner) {
			return s -> {
				try {
					return inner.apply(s);
				} catch (IllegalArgumentException _ignored) {
					CLOGGER.error("Unkown/invalid config value: {}", s); // We don't have enough context here to say more
					return null;
				}
			};
		}

		public static <E extends Enum<E>> ConfigValueReader<E> fromEnum(Class<E> e) {
			return illegalArgumentToNull(s -> Enum.valueOf(e, s));
		}
		public static <T> ConfigValueReader<List<T>> asList(ConfigValueReader<T> inner) {
			return s -> Arrays.stream(s.split("\\s*,\\s*"))
					.map(inner)
					.filter(Objects::nonNull)
					.toList();
		}
		public static <E extends Enum<E>> ConfigValueReader<EnumSet<E>> enumSet(Class<E> e) {
			final ConfigValueReader<E> enumReader = fromEnum(e);
			return s -> Arrays.stream(s.split("\\s*,\\s*"))
					.map(enumReader)
					.filter(Objects::nonNull)
					.collect(Collectors.toCollection(() -> EnumSet.noneOf(e)));
		}
		/**
		 * Reads a value from a string.
		 *
		 * If the value is not valid you can either throw an <code>IllegalArgumentException</code>
		 * (or some subclass like <code>NumberFormatException</code>), or return <code>null</code>.
		 * Both will set the value to null, causing the default value to be used, but only throwing will log an error.
		 *
		 * @param s the String to be converted to the type of the value. Will be trimmed of blank characters
		 * @return the value that s represents, or null if no value is present
		 * @throws IllegalArgumentException if the value is not valid
		 */
		T apply(String s) throws IllegalArgumentException;
	}

	private static final Map.Entry<String, String> EMPTY = Map.entry("","");

	public void load() {
		List<ConfigValue<?>> toWrite = new ArrayList<>(_valueMap.size());
		if (Files.exists(location)) {
			try {
				BufferedReader reader = Files.newBufferedReader(location);
				Set<String> foundValues = reader.lines()
						.map(String::trim)
						.filter(s -> !s.startsWith("#"))
						.map(s -> {
							String[] key_value = s.split("\\s*=\\s*");
							if (key_value.length < 2) {
								CLOGGER.error("Malformed config line in {} config: '{}'. Expected a '=' to seperate key and value, but none were found. Line will be ignored", name, s);
								return EMPTY;
							};
							return Map.entry(key_value[0].trim(), key_value[1].trim());
						})
						.filter(e -> e.getKey().isBlank() || e.getValue().isBlank()) // ignore blank things.
						.filter(e -> {
							if (_valueMap.containsKey(e.getKey())) {
								CLOGGER.warn("Unknown key {} for {} config at {}. Ignoring", e.getKey(), name, location);
								return false;
							}
							return true;
						})
						.map(e -> {
							ConfigValue<?> configValue = _valueMap.get(e.getKey());
							configValue.set(e.getValue());
							return e.getKey(); // Used to filter which keys we don't have
						})
						.collect(Collectors.toUnmodifiableSet());

				String missingKeys = _valueMap.keySet().stream()
						.filter(foundValues::contains)
						.collect(Collectors.joining(", "));
				if (!missingKeys.isEmpty()) {
					CLOGGER.warn("{} config file at {} is missing required value(s): {}. Appending these with default values.", name, location, missingKeys);
				}
			} catch (IOException e) {
				CLOGGER.error("Failed loading {} config file from {}. {}" + location, name, e);
			}
		} else {
			toWrite.addAll(_valueMap.values());
		}
		try {
			var writer = new BufferedWriter(new FileWriter(location.toFile(), true));
			writer.write(name + " config file");
			writer.newLine();
			for (ConfigValue<?> value : toWrite) {
				writer.newLine();
				value.write(writer);
			}
			writer.flush();
		} catch (IOException e) {
			CLOGGER.error("Failed saving {} config file to {}. {}", name, location, e);
		}
	}

	@Override
	public String toString() {
		return name + " config file at: " + MC_ROOT.relativize(location).toString();
	}
}