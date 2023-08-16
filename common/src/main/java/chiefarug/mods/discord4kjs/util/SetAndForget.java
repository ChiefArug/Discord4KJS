package chiefarug.mods.discord4kjs.util;

import dev.latvian.mods.rhino.util.HideFromJS;

import java.util.function.Supplier;

public class SetAndForget<T> implements Supplier<T> {

	private T value;

	public T get() {
		return value;
	}

	public T set(T v) {
		if (this.value != null) throw new IllegalStateException("Cannot set value of a computed field more than once!");
		return this.value = v;
	}

}
