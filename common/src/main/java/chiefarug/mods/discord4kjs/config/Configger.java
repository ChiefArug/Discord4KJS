package chiefarug.mods.discord4kjs.config;

import com.mojang.logging.LogUtils;
import dev.architectury.platform.Platform;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.lang.IllegalArgumentException;

/**
 *
 * Yet another config class because I don't want yet another dep.
 *
 * @author ChiefArug
 * @param name The name that should be used to describe the config, usually a modid.
 * @param location The full path of the config file.
 * @param _valueMap A new empty map that you, the user, pass in..
 *                  Recommended to be a LinkedHashMap so that order of insertion is preserved.
 *                  This is a workaround to making our own in a private field so this can still be a record.
 *                  Don't do anything other than construct it pls & thanks
 */
@SuppressWarnings("unused")
public record Configger(String name, Path location, Map<String, ConfigValue<?>> _valueMap, int version) {
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
		return value(name, defaultValue, reader, ConfigValueWriter.TO_STRING_WRITER.cast(), comments);
	}

	public ConfigValue<Boolean> booleanValue(String name, boolean defaultValue, String ...comments) {
		return value(name, defaultValue, ConfigValueReader.BOOLEAN_READER, comments);
	}
	public ConfigValue<Integer> integerValue(String name, int defaultValue, String ...comments) {
		return value(name, defaultValue, ConfigValueReader.INTEGER_READER, comments);
	}
	public ConfigValue<Float> floatValue(String name, float defaultValue, String ...comments) {
		return value(name, defaultValue, ConfigValueReader.FLOAT_READER, comments);
	}
	@SuppressWarnings("unchecked")
	public <E extends Enum<E>> ConfigValue<E> enumValue(String name, Enum<E> defaultValue, String ...comments) {
		return value(name, defaultValue, ConfigValueReader.fromEnum(defaultValue.getClass()), comments);
	}
	public <T> ConfigValue<List<T>> listValue(String name, List<T> defaultValues,ConfigValueReader<T> innerReader, String ...comments) {
		return value(name, defaultValues, ConfigValueReader.asList(innerReader), ConfigValueWriter.DEFAULT_COLLECTION.cast(), comments);
	}
	public <E extends Enum<E>> ConfigValue<EnumSet<E>> enumSetValue(String name, EnumSet<E> defaultValues, Class<E> enumClass, String ...comments) {
		return value(name, defaultValues, ConfigValueReader.enumSet(enumClass), ConfigValueWriter.ENUM_COLLECTION.cast(), comments);
	}

	private void validateName(String name) {
		if (name.indexOf('=') != -1)
			throw new IllegalArgumentException("Cannot use = in config value name, it is a reserved character");
		if (name.startsWith("#"))
			throw new IllegalArgumentException("Cannot start config value names with # as it is the comment character");
	}

	public static final class ConfigValue<V> implements Supplier<V> {

		private final String name;
		private final V defaultValue;
		private V value;
		private final ConfigValueReader<V> reader;
		private final ConfigValueWriter<V> writer;
		private final String[] comments;

		// Use the methods in Configger!
		private ConfigValue(String name, V defaultValue, ConfigValueReader<V> reader, ConfigValueWriter<V> writer, String ...comments) {
			this.name = name;
			this.defaultValue = defaultValue;
			this.reader = reader;
			this.writer = writer;
			this.comments = comments;
		}

		private void write(Writer out) throws IOException {
			for (String comment : comments) {
				out.write("# " + comment);
				out.write(System.lineSeparator());
			}
			out.write(name + "=" + writer.apply(get()));
		}

		private String set(String value) {
			this.value = reader.apply(value);
			return name;
		}

		@Override
		public V get() {
			return value == null ? defaultValue : value;
		}

		@Override
		public String toString() {
			return String.valueOf(get());
		}
	}

	@FunctionalInterface
	public interface ConfigValueWriter<T> extends Function<T, String> {
		ConfigValueWriter<?> TO_STRING_WRITER = String::valueOf;
		ConfigValueWriter<Enum<? extends Enum<?>>> ENUM = Enum::name;
		ConfigValueWriter<? extends Collection<?>> DEFAULT_COLLECTION = asCollection(TO_STRING_WRITER);
		ConfigValueWriter<? extends Collection<?>> ENUM_COLLECTION = asCollection(ENUM);
		static <T> ConfigValueWriter<Collection<T>> asCollection(ConfigValueWriter<T> inner) {
			return inner::applyAll;
		}
		@SuppressWarnings("unchecked")
		default <V> ConfigValueWriter<V> cast() {
			return (ConfigValueWriter<V>) this; // Grumble grumble generics
		}

		default String applyAll(Collection<T> s) {
			return s.stream().map(this).collect(Collectors.joining(", "));
		}
	}

	@FunctionalInterface
	public interface ConfigValueReader<T> extends Function<String, T> {

		ConfigValueReader<Boolean> BOOLEAN_READER = illegalArgumentToNull(Boolean::valueOf);
		ConfigValueReader<Integer> INTEGER_READER = illegalArgumentToNull(Integer::valueOf);
		ConfigValueReader<Float> FLOAT_READER = illegalArgumentToNull(Float::valueOf);

		private static <T> ConfigValueReader<T> illegalArgumentToNull(Function<String, T> inner) {
			return s -> {
				try {
					return inner.apply(s);
				} catch (IllegalArgumentException _ignored) {
					CLOGGER.error("Unknown/invalid config value: {}", s); // We don't have enough context here to say more
					return null;
				}
			};
		}

		static <E extends Enum<E>> ConfigValueReader<E> fromEnum(Class<E> e) {
			return illegalArgumentToNull(s -> Enum.valueOf(e, s));
		}
		static <T> ConfigValueReader<List<T>> asList(ConfigValueReader<T> inner) {
			return inner::applyAll;
		}
		static <E extends Enum<E>> ConfigValueReader<EnumSet<E>> enumSet(Class<E> e) {
			final ConfigValueReader<E> enumReader = fromEnum(e);
			return s -> Arrays.stream(s.split("\\s*,\\s*"))
					.map(enumReader)
					.filter(Objects::nonNull)
					.collect(Collectors.toCollection(() -> EnumSet.noneOf(e)));
		}
		/**
		 * Reads a value from a string.
		 * If the value is not valid you should return <code>null</code>.
		 * If you are using an implementation you do not have control over
		 * (ie {@link Integer#valueOf}) then you can wrap that
		 * using {@link ConfigValueReader#illegalArgumentToNull} to catch any {@link IllegalArgumentException}s that throws.
		 *
		 * @param s the String to be converted to the type of the value. Will be trimmed of blank characters
		 * @return the value that s represents, or null if no value is present
		 */
		default List<T> applyAll(String s) {
			return Arrays.stream(s.split("\\s*,\\s*"))
					.map(this)
					.filter(Objects::nonNull)
					.toList();
		}
	}

	public void load() {
		boolean shouldOverwrite = false;
		Set<ConfigValue<?>> toWrite = new HashSet<>(_valueMap.size());
		if (Files.exists(location)) {
			try {
				BufferedReader reader = Files.newBufferedReader(location);
				// The first line is the version, so read that and compare against currnet version.
				// If it returns an empty collection, then we are not overwriting.
				if (checkVersion(reader.readLine())) {
					shouldOverwrite = true;
					toWrite.addAll(_valueMap.values());
				}

				Set<String> foundValues = readLines(reader, shouldOverwrite);
				foundValues.retainAll(_valueMap.keySet());

				if (!foundValues.isEmpty() && !shouldOverwrite) {
					CLOGGER.warn("{} config file at {} is missing required value(s): {}. Appending these with default values.", name, location, String.join(", ", foundValues));
					toWrite.addAll(foundValues.stream().map(_valueMap::get).toList());
				}
			} catch (IOException e) {
				CLOGGER.error("Failed loading {} config file from {}. {}", location, name, e);
			}
		} else {
			shouldOverwrite = true;
			toWrite.addAll(_valueMap.values());
		}
		try {
			var writer = new BufferedWriter(new FileWriter(location.toFile(), !shouldOverwrite));
			if (shouldOverwrite) {
				writer.write("# " + name + " config file. Version:[{" + version + "}]");
				writer.newLine();
			}
			for (ConfigValue<?> value : toWrite) {
				writer.newLine();
				value.write(writer);
			}
			writer.flush();
		} catch (IOException e) {
			CLOGGER.error("Failed saving {} config file to {}. {}", name, location, e);
		}
	}

	private boolean checkVersion(String firstLine) throws IOException {
		int versionStart = firstLine.lastIndexOf("[{") + "[{".length();
		int versionEnd = firstLine.lastIndexOf("}]");
		if (versionStart == 1 || versionEnd == -1 || versionStart > versionEnd) {
			CLOGGER.error("Could not find a valid version number in {} config file at {}. We will try to recover any values from it, then overwrite.", name, location);
			return true;
		} else if (Integer.parseInt(firstLine, versionStart, versionEnd, 10) < version) {
			CLOGGER.info("Found an old {} config at {}. We will try to recover any values from it, then overwrite with an updated config.", name, location);
			return true; //TODO: Figure out why value recovery isn't working. Breakpoint ConfigValue#set?
		}
		return false;
	}

	@NotNull
	private Set<String> readLines(BufferedReader reader, boolean ignoreErrors) {
		return reader.lines()
				.map(String::trim)
				.filter(s -> !s.startsWith("#"))
				.map(s -> s.split("\\s*=\\s*"))
				.filter(sa -> Arrays.stream(sa).filter(s -> !s.isBlank()).count() >= 2)
				.map(sa -> Map.entry(sa[0].trim(), sa[1].trim()))
				.filter(e -> {
					if (_valueMap.containsKey(e.getKey())) { //TODO: do we need an error message here?
						if (!ignoreErrors) CLOGGER.warn("Unknown key {} for {} config at {}. Ignoring", e.getKey(), name, location);
						return false;
					}
					return true;
				})
				.map(e -> _valueMap.get(e.getKey()).set(e.getValue()))
				.collect(Collectors.toSet());
	}

	@Override
	public String toString() {
		return name + " config file at: " + MC_ROOT.relativize(location);
	}
}