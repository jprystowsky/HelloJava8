package io.mapping.apps.HelloJava8.functions;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MathFnImpl implements MathFn<Double> {

	@Override
	public List<Double> apply(final List<Double> in) {
		return in.parallelStream()
				.map((Double val) -> Math.pow(2, val) + 1)
				.collect(Collectors.toList());
	}

}
