package io.mapping.apps.HelloJava8.document;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class DocumentImplTest {
	static class ContextConfiguration {
	}

	private List<Document> documentList;

	@Before
	public void setUp() throws Exception {
		Document comedy = new DocumentImpl("Comedy");
		comedy.getVotes().addAll(Arrays.asList(
				new DocumentImpl.VoteImpl((short) 4),
				new DocumentImpl.VoteImpl((short) 5),
				new DocumentImpl.VoteImpl((short) 5),
				new DocumentImpl.VoteImpl((short) 2),
				new DocumentImpl.VoteImpl((short) 4)
		));

		Document drama = new DocumentImpl("Drama");
		drama.getVotes().addAll(Arrays.asList(
				new DocumentImpl.VoteImpl((short) 3),
				new DocumentImpl.VoteImpl((short) 1),
				new DocumentImpl.VoteImpl((short) 5),
				new DocumentImpl.VoteImpl((short) 3),
				new DocumentImpl.VoteImpl((short) 4)
		));

		Document documentary = new DocumentImpl("Documentary");
		documentary.getVotes().addAll(Arrays.asList(
				new DocumentImpl.VoteImpl((short) 1),
				new DocumentImpl.VoteImpl((short) 5),
				new DocumentImpl.VoteImpl((short) 1),
				new DocumentImpl.VoteImpl((short) 2),
				new DocumentImpl.VoteImpl((short) 5)
		));

		documentList = Arrays.asList(comedy, drama, documentary);
	}

	@Test
	public void shouldFindTopDocument() throws Exception {
		ToDoubleFunction<Document> summingFunction = (d) -> d.getVotes()
				.parallelStream()
				.collect(Collectors.summingDouble(Document.Vote::getAmount));

		final Collector<Document, ?, Map<String, Double>> documentVoteSummer =
				Collectors.groupingBy(Document::getName, Collectors.summingDouble(summingFunction));

		ToDoubleFunction<Document> averagingFunction = (d) -> d.getVotes()
				.parallelStream()
				.collect(Collectors.averagingDouble(Document.Vote::getAmount));

		final Collector<Document, ?, Map<String, Double>> documentVoteAverager =
				Collectors.groupingBy(Document::getName, Collectors.averagingDouble(averagingFunction));

		final Map<String, Double> sumVoteList = documentList
				.parallelStream()
				.collect(documentVoteSummer);

		assertEquals("Comedy votes should sum to 20", 20, (double) sumVoteList.get("Comedy"), 0);
		assertEquals("Drama votes should sum to 16", 16, (double) sumVoteList.get("Drama"), 0);
		assertEquals("Documentary votes should sum to 14", 14, (double) sumVoteList.get("Documentary"), 0);

		final Map<String, Double> averageVoteList = documentList.parallelStream().collect(documentVoteAverager);

		assertEquals("Comedy votes should average 4", 4, (double) averageVoteList.get("Comedy"), 0.01);
		assertEquals("Drama votes should average 3.2", 3.2, (double) averageVoteList.get("Drama"), 0.01);
		assertEquals("Documentary votes should average 2.8", 2.8, (double) averageVoteList.get("Documentary"), 0.01);
	}
}
