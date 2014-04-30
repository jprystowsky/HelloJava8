package io.mapping.apps.HelloJava8.document;

import java.util.List;

public interface Document {
	public String getName();
	public List<DocumentImpl.Vote> getVotes();

	public interface Vote {
		public short getAmount();
	}
}
