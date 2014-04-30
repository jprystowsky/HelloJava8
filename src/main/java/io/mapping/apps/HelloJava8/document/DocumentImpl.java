package io.mapping.apps.HelloJava8.document;

import java.util.LinkedList;
import java.util.List;

public class DocumentImpl implements Document {
	private final String mName;
	private final List<Vote> mVotes;

	public DocumentImpl(final String name) {
		this.mName = name;
		this.mVotes = new LinkedList<>();
	}

	@Override
	public String getName() {
		return mName;
	}

	@Override
	public List<Vote> getVotes() {
		return mVotes;
	}

	public static class VoteImpl implements Vote {
		private final short mAmount;

		public VoteImpl(final short amount) {
			this.mAmount = amount;
		}

		@Override
		public short getAmount() {
			return mAmount;
		}
	}
}
