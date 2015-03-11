package edu.usc.adhulipa.DataStructs;

public abstract class Vector<L, R> implements Comparable<Vector<L, R>> {

    /**
     * <p>Obtains an immutable pair of from two objects inferring the generic types.</p>
     * 
     * <p>This factory allows the pair to be created using inference to
     * obtain the generic types.</p>
     * 
     * @param <L> the left element type
     * @param <R> the right element type
     * @param left  the left element, may be null
     * @param right  the right element, may be null
     * @return a pair formed from the two parameters, not null
     */
    public static <L, R> Vector<L, R> of(final L left, final R right) {
        return new ImmutableVector<L, R>(left, right);
    }

    //-----------------------------------------------------------------------
    /**
     * <p>Gets the left element from this pair.</p>
     * 
     * <p>When treated as a key-value pair, this is the key.</p>
     * 
     * @return the left element, may be null
     */
    public abstract L getLeft();

    /**
     * <p>Gets the right element from this pair.</p>
     * 
     * <p>When treated as a key-value pair, this is the value.</p>
     * 
     * @return the right element, may be null
     */
    public abstract R getRight();

    /**
     * <p>Gets the key from this pair.</p>
     * 
     * <p>This method implements the {@code Map.Entry} interface returning the
     * left element as the key.</p>
     * 
     * @return the left element as the key, may be null
     */

        
    //-----------------------------------------------------------------------
    /**
     * <p>Compares the pair based on the left element followed by the right element.
     * The types must be {@code Comparable}.</p>
     * 
     * @param other  the other pair, not null
     * @return negative if this is less, zero if equal, positive if greater
     */
    @Override
    public int compareTo(final Vector<L, R> other) {
    	L x1, x2;
    	R y1, y2;
    	
    	x1 = this.getLeft();
    	x2 = other.getLeft();
    	
    	y1 = this.getRight();
    	y2 = other.getRight();
    	
    	if ( ! x1.equals(x2)) {
    		return x1.hashCode() - x2.hashCode();
    	}
    	else if ( ! y1.equals(y2)) {
    		return y1.hashCode() - y2.hashCode();
    	}
    	else {
    		return 0;
    	}
    }

    /**
     * <p>Compares this pair to another based on the two elements.</p>
     * 
     * @param obj  the object to compare to, null returns false
     * @return true if the elements of the pair are equal
     */

    public boolean equals(final Vector<L,R> other) {
        if (other == this) {
            return true;
        }
        if (other instanceof Vector) {

        	return (this.getLeft().equals(other.getLeft())
                    && this.getRight().equals(other.getRight()));
        }
        return false;
    }

    /**
     * <p>Returns a suitable hash code.
     * The hash code follows the definition in {@code Map.Entry}.</p>
     * 
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return (getLeft() == null ? 0 : getLeft().hashCode()) ^
                (getRight() == null ? 0 : getRight().hashCode());
    }

    /**
     * <p>Returns a String representation of this pair using the format {@code ($left,$right)}.</p>
     * 
     * @return a string describing this object, not null
     */
    @Override
    public String toString() {
        return new StringBuilder().append('(').append(getLeft()).append(',').append(getRight()).append(')').toString();
    }

    /**
     * <p>Formats the receiver using the given format.</p>
     * 
     * <p>This uses {@link java.util.Formattable} to perform the formatting. Two variables may
     * be used to embed the left and right elements. Use {@code %1$s} for the left
     * element (key) and {@code %2$s} for the right element (value).
     * The default format used by {@code toString()} is {@code (%1$s,%2$s)}.</p>
     * 
     * @param format  the format string, optionally containing {@code %1$s} and {@code %2$s}, not null
     * @return the formatted string, not null
     */
    public String toString(final String format) {
        return String.format(format, getLeft(), getRight());
    }

	public static Vector<Integer, Integer> difference(
			Vector<Integer, Integer> subtrahend, Vector<Integer, Integer> minuend) {
		
		int x1, y1;
		int x2, y2;
		
		x1 = subtrahend.getLeft();
		y1 = subtrahend.getRight();
		
		x2 = minuend.getLeft();
		y2 = minuend.getRight();
				
		return Vector.of(x1-x2, y1-y2);
	}

}
