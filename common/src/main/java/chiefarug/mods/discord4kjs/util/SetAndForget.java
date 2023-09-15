package chiefarug.mods.discord4kjs.util;

import dev.latvian.mods.rhino.util.HideFromJS;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Supplier;

public class SetAndForget<T> implements Supplier<T> {

	private T value;

	public boolean isSet() {
		return value != null;
	}

	@Nullable
	public T get() {
		return value;
	}

	public T set(T v) {
		if (this.value != null) throw new IllegalStateException("Cannot set value of a computed field more than once!");
		return this.value = v;
	}

}
