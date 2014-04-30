package io.mapping.apps.HelloJava8.functions;

import java.util.List;

@FunctionalInterface
public interface MathFn<T> {
	public List<T> apply(List<T> in);
}
