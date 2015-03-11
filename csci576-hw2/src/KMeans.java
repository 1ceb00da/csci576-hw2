import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.usc.adhulipa.DataStructs.Vector;


public class KMeans {

	public static int meanDiff(List<Vector<Integer, Integer>> oldMeans,
			List<Vector<Integer, Integer>> newMeans) {

		//System.out.println("\n in mean diff fn");
		//System.out.println("oldMeans = " + oldMeans);
		//System.out.println("means = " + newMeans);

		Vector<Integer, Integer> oldMean, newMean;
		List<Vector<Integer, Integer>> diffs = new ArrayList<Vector<Integer, Integer>>();
		
		
		int sumOfDiffs = 0;
		
		for (int i = 0; i < newMeans.size(); i++) {
			oldMean = oldMeans.get(i);
			newMean = newMeans.get(i);
			diffs.add(Vector.difference(oldMean, newMean));
		}
		//System.out.println("diffs are " + diffs);
		
		int d;
		for (Vector<Integer, Integer> diff : diffs) {
			
			// approach 1
			d = diff.getLeft().intValue() - diff.getRight().intValue();
			//sumOfDiffs += d * d; 
			
			// approach 2
			sumOfDiffs += diff.getLeft().intValue() * diff.getLeft().intValue() + diff.getRight().intValue() * diff.getRight().intValue();
			
		}
		
		//System.out.println("SumofDiffs are " + sumOfDiffs);
		
		
		return sumOfDiffs;
	}

	public static int findClosestMean(Vector<Integer, Integer> vec,
			List<Vector<Integer, Integer>> means) {

		int capacity = means.size();
		ArrayList<Double> dists = new ArrayList<Double>(capacity);
		for (int i = 0; i < means.size(); i++) {
			dists.add(new Double(0.0));
		}
		
		Vector<Integer, Integer> mean;
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

	public static List<Vector<Integer, Integer>> computeNewMeans(List<Vector<Integer, Integer>> means, int[] cluster, ArrayList<Vector<Integer, Integer>> imageVector) {
		
		
		double count;
		List<Vector<Integer, Integer>> newMeans = new ArrayList<Vector<Integer, Integer>>(means);


		for (int k = 0 ; k < means.size(); k++) {
			count = 0;
			
			int sumLeft = 0;
			int sumRight = 0;
			
			for (int i = 0; i < cluster.length; i++) {
				if (cluster[i] == k) {
					sumLeft = sumLeft + imageVector.get(i).getLeft().intValue();
					sumRight = sumRight + imageVector.get(i).getRight().intValue();
					count++;
				}
			}
			
//			System.out.println("sumleft " + sumLeft);
//			System.out.println("sumrigth " + sumRight);
//			System.out.println("count " + count);
//			
			Vector<Double, Double> doubleMean = Vector.of(new Double(sumLeft/count), new Double(sumRight/count));
			
			Vector<Integer, Integer> newMean = Vector.of(new Double(sumLeft/count).intValue(), new Double(sumRight/count).intValue());
			
			newMeans.set(k, newMean);
		}
		
		return newMeans;		
	}

	private static double distance(Vector<Integer, Integer> mean, Vector<Integer, Integer> vec) {
		Integer l1,l2,r1,r2;
		
		l1 = mean.getLeft();
		r1 = mean.getRight();
		
		l2 = vec.getLeft();
		r2 = vec.getRight();
		
		double dist = Math.sqrt(((l1-l2) * (l1-l2)) + ((r1-r2) * (r1-r2)));
		return dist;
	}
}
