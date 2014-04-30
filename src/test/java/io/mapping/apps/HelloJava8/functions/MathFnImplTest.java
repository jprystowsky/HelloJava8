package io.mapping.apps.HelloJava8.functions;

import com.google.common.collect.Iterables;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class MathFnImplTest {
	@Configuration
	static class ContextConfiguration {
		@Bean
		MathFn provideMathFn() {
			return new MathFnImpl();
		}
	}

	@Autowired
	private MathFn<Double> mMathFn;

	private static List<Double> apply(MathFn<Double> mathFn, List<Double> input) {
		return mathFn.apply(input);
	}

	@Test
	public void shouldApplyMathFunctionCorrectly() throws Exception {
		List<Double> inputList = Arrays.asList((double) 1, (double) 2, (double) 3),
				inputListTwo = Arrays.asList((double) 1, (double) 2, (double) 3);

		Collection<Double> outputList = apply(mMathFn, inputList);

		List<Double> outputListTwo = apply(
				(List<Double> c) ->
						inputList
								.parallelStream()
								.map(
										(Double d) ->
												Math.pow(2, d) + 1
								)
								.collect(Collectors.toList())
				,
				inputListTwo);

		assertTrue("List maps of 2^x+1 should equal", Iterables.elementsEqual(outputList, outputListTwo));
	}
}
