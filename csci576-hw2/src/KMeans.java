import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;


public class KMeans {


	public static int findClosestMean(Pair<Byte, Byte> vec,
			List<Pair<Byte, Byte>> means) {

		int capacity = means.size();
		ArrayList<Double> dists = new ArrayList<Double>(capacity);
		for (int i = 0; i < means.size(); i++) {
			dists.add(new Double(0.0));
		}
		
		Pair<Byte, Byte> mean;
		Double minDist;
		int minIndex;
		for (int i = 0; i < means.size(); i++) {
			mean = means.get(i);
			dists.set(i, distance(mean, vec));
		}
		minDist = Collections.min(dists);
		minIndex = dists.indexOf(minDist);
		
		return minIndex;
	}

	public static List<Pair<Byte, Byte>> computeNewMeans(List<Pair<Byte, Byte>> means, int[] cluster, ArrayList<Pair<Byte, Byte>> data) {
		MutablePair<Byte,Byte> sum;
		double count;
		

		for (int k = 0 ; k < means.size(); k++) {
			Byte Zero = new Integer(0).byteValue();
			sum = new MutablePair<Byte, Byte>(Zero, Zero);
			count = 0;
			for (int i = 0; i < cluster.length; i++) {
				if (cluster[i] == k) {
					byte l = new Double((sum.getLeft().byteValue()) + (data.get(i).getLeft().byteValue())).byteValue();
					byte r = new Double((sum.getRight().byteValue()) + (data.get(i).getRight().byteValue())).byteValue();
					sum.setLeft(l);
					sum.setRight(r);
					count++;
				}
			}
			Pair<Byte, Byte> newMean = Pair.of(new Double(sum.getLeft()/count).byteValue(), 
					new Double(sum.getRight()/count).byteValue());
			means.set(k, newMean);
		}
		
		return means;		
	}

	private static double distance(Pair<Byte, Byte> p1, Pair<Byte, Byte> p2) {
		Byte l1,l2,r1,r2;
		
		l1 = p1.getLeft();
		r1 = p1.getRight();
		
		l2 = p2.getLeft();
		r2 = p2.getRight();
		
		return Math.sqrt(((l1-l2) * (l1-l2)) + ((r1-r2) * (r1-r2)));
	}
}
