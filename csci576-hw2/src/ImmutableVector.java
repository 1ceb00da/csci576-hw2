

public class ImmutableVector<L, R> extends Vector<L, R> {
    
	/** Left object */
    public final L left;
    /** Right object */
    public final R right;

    public ImmutableVector(final L left, final R right) {
        super();
        this.left = left;
        this.right = right;
    }

	public static <L, R> ImmutableVector<L, R> of(final L left, final R right) {
        return new ImmutableVector<L, R>(left, right);
    }

	@Override
	public L getLeft() {
		return this.left;
	}

	@Override
	public R getRight() {
		return this.right;
	}
}
