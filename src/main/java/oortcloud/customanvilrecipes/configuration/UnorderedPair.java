package oortcloud.customanvilrecipes.configuration;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

public class UnorderedPair<L, R> extends Pair<L, R> {

	public final L left;
	public final R right;

	public static <L, R> ImmutablePair<L, R> of(final L left, final R right) {
		return new ImmutablePair<L, R>(left, right);
	}

	public UnorderedPair(final L left, final R right) {
		super();
		this.left = left;
		this.right = right;
	}

	@Override
	public L getLeft() {
		return left;
	}

	@Override
	public R getRight() {
		return right;
	}

	@Override
	public R setValue(final R value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof UnorderedPair) {
			UnorderedPair pair = (UnorderedPair)obj;
			return (getLeft().equals(pair.getLeft())&&getRight().equals(pair.getRight()))||(getLeft().equals(pair.getRight())&&getRight().equals(pair.getLeft()));
		}
		return super.equals(obj);
	}

}
