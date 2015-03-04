
public class Vector {
	byte[] vals = new byte[2];
	
	Vector (byte a, byte b) {
		vals[0] = a;
		vals[1] = b;
	}
	
	Vector (byte[] vals) {
		this.vals[0] = vals[0];
		this.vals[1] = vals[1];
	}
	
	public boolean equals(Vector other) {
		return (this.vals[0] == other.vals[0] &&
				this.vals[1] == other.vals[1]);
	}

	public void hashcode() {
		
	}
}
