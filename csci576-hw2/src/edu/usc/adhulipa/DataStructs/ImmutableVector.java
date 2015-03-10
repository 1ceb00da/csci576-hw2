package edu.usc.adhulipa.DataStructs;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.tuple.ImmutablePair;

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public R getRight() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
    public int compareTo(final Vector<L, R> other) {
	
		L x1 = this.getLeft();
		R y1 = this.getRight();
		
		L x2 = other.getLeft();
		R y2 = other.getRight();
		
		if (!x1.equals(x2)) {
			return x1.hashCode() - x2.hashCode();
		}
		else if (!y1.equals(y2)) {
			return y1.hashCode() - y2.hashCode();
		}
		else
			return 0;
	
	}


}
