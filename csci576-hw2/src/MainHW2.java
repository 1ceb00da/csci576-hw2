import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JLabel;




import edu.usc.adhulipa.DataStructs.ImmutableVector;
import edu.usc.adhulipa.DataStructs.Vector;


public class MainHW2 {

	private static void TEMPORARY_TEST_IF_YOU_SEE_THIS_IN_SUBMISSION_PLEASE_DELETE_THIS_LINE() {
		
		//pairCompareToTest();
		vectorCompareToTest();		
	}

	public static void main(String args[]) {
		
		
		
		//TEMPORARY_TEST_IF_YOU_SEE_THIS_IN_SUBMISSION_PLEASE_DELETE_THIS_LINE();

		
		
		String filename = "Image1.raw";
		String NString = "4";
		
		int N = Integer.parseInt(NString);
		byte[] bytes = ImageHandler.readImageFromFile(filename);
		BufferedImage originalImg = ImageHandler.toBufferedImage(bytes, 352, 288, BufferedImage.TYPE_INT_RGB);
		BufferedImage originalImg2 = ImageHandler.toBufferedImage(bytes, 352, 288, BufferedImage.TYPE_BYTE_GRAY);
		BufferedImage processedImg = originalImg;
		
		/* Step 0; Preprocessing
		 * Generate a list of all possible pixel pairs
		 * i.e. all possible (px1, px2) vals
		 * Then select inital N pairs from this list
		 */
		ArrayList<Vector<Integer,Integer>> allCodes = new ArrayList<Vector<Integer,Integer>>();
		for (int i = 0; i < 256; i++) {
			for (int j = 0; j < 256; j++) {
				allCodes.add(new ImmutableVector<Integer,Integer>(new Integer(i).intValue(), new Integer(j).intValue()));
			}
		}
		
		/* TODO Step 1
		 * ------
		 * Get Vector representation of image
		 */
		
		
		Integer left, right;
		Vector<Integer, Integer> vector;
		ArrayList<Vector<Integer, Integer>> imageVector = new ArrayList<Vector<Integer,Integer>>();
		for (int i = 0; i < bytes.length; i += 2) {
			left = new Integer(Byte.toUnsignedInt(bytes[i]));
			right = new Integer(Byte.toUnsignedInt(bytes[i+1]));
			vector = new ImmutableVector<Integer, Integer>(left, right);
			imageVector.add(vector);
		}

//		/////////////
		//// ImageVectors as arrays -- for matlab plotting
//		
//		int[] x = new int[imageVector.size()];
//		int[] y = new int[imageVector.size()];
//		
//		for (int i = 0; i < imageVector.size(); i++) {
//			
//			x[i] = imageVector.get(i).getLeft();
//			y[i] = imageVector.get(i).getRight();
//		}
//		//System.out.println(Arrays.toString(x));
//		System.out.println(Arrays.toString(y));
//		
//		System.exit(1);
//		////////////
		
		System.out.println("Step1 fin:- " + Arrays.toString(imageVector.toArray()));



		/* END of step1 */
		
		/* TODO Step 2
		 * ------
		 * Init N random px1,px2 means or centroids or codewords
		 */
		
		Set<Vector<Integer, Integer>> meansSet = new HashSet<Vector<Integer,Integer>>();
		
		while (meansSet.size() < N) {
			int idx = new Double(Math.random() * allCodes.size()).intValue();
			meansSet.add(allCodes.get(idx));
		}
		List<Vector<Integer,Integer>> means = new ArrayList<Vector<Integer,Integer>>(meansSet);
		
		System.out.println("\nStep2 fin:-\n" + means);
		////////////////
		// initial means as arrays for matlab plotting
		//
		int[] mxi = new int[means.size()];
		int[] myi = new int[means.size()];
		
		for (int i =0 ; i < means.size(); i++) {
			mxi[i] = means.get(i).getLeft().intValue();
			myi[i] = means.get(i).getRight().intValue();
			
		}
		System.out.println("init mx " + Arrays.toString(mxi));
		System.out.println("init my " + Arrays.toString(myi));
		
		
		///////////////

		/* END of step2 */
		
				
		int iter = 0;
		int closestMeanIndex;
		
		int[] cluster = new int[imageVector.size()];
		Vector<Integer, Integer> vec;
		
		// newmeans initlaization
		List<Vector<Integer, Integer>> oldMeans = new ArrayList<Vector<Integer, Integer>>();
		for (int i = 0; i < means.size(); i++) {
			oldMeans.add(Vector.of(0, 0));
		}
		
		System.out.println(means);
		System.out.println(oldMeans);
		
		
		
		while (KMeans.meanDiff(oldMeans, means) > 0 && iter < 500) {			
			
			/* TODO Step 3 **** K-MEANS algorithm step 1 ****
			 * ------
			 * Find closest centroids
			 */
			
			for (int i = 0; i < imageVector.size(); i++) {
				vec = imageVector.get(i);
				closestMeanIndex = KMeans.findClosestMean(vec, means);
				cluster[i] = closestMeanIndex;
			}
		
			//System.out.println("\nStep3 fin:-");
			//System.out.println(Arrays.toString(Arrays.copyOfRange(cluster, 1090, 1100)));

			/* TODO Step 4 **** K-MEANS algorithm step 2****
			 * ------
			 * Compute new centroids
			 */
			oldMeans = new ArrayList<Vector<Integer,Integer>>(means);
			means = KMeans.computeNewMeans(means, cluster, imageVector);
			iter++;
		}
		
		System.out.println("\nSteps 3&4 fin in " + iter + " iterations:-");
		System.out.println(means);
		
		////////////////
		// means as arrays for matlab plotting
		//
		int[] mx = new int[means.size()];
		int[] my = new int[means.size()];
		
		for (int i =0 ; i < means.size(); i++) {
			mx[i] = means.get(i).getLeft().intValue();
			my[i] = means.get(i).getRight().intValue();
			
		}
		System.out.println("final mx" + Arrays.toString(mx));
		System.out.println("final my" + Arrays.toString(my));
		
		
		///////////////
		
		/* END of step3 & step4 */


		
		/* TODO Step 5
		 * ------
		 * Quantize input vectors to produce output image
		 * and display
		 */
		
		System.out.println();
		
		bytes = new byte[bytes.length];
		//approach 1
		for (int i = 0; i < imageVector.size(); i += 1) {
			
			Vector<Integer, Integer> code = means.get(cluster[i]);
			bytes[i*2] = code.getLeft().byteValue();
			bytes[i*2+1] = code.getRight().byteValue();
		}
		
//		// approach 2
//		for (int i = 0; i < bytes.length; i += 2) {
//			left = new Integer(Byte.toUnsignedInt(bytes[i]));
//			right = new Integer(Byte.toUnsignedInt(bytes[i+1]));
//			vector = new ImmutableVector<Integer, Integer>(left, right);
//			imageVector.get(i/2);
//			
//			int clusterIdx = cluster[i];
//			vector = means.get(clusterIdx);
//			bytes[i] = vector.getLeft().byteValue();
//			bytes[i + 1] = vector.getRight().byteValue();
//		}

		
		processedImg = ImageHandler.toBufferedImage(bytes, 352, 288, BufferedImage.TYPE_INT_RGB);
		System.out.println("Step5 fin:- ");
		
		ViewFrame frame = new ViewFrame("Display Images");
		frame.addImage(new JLabel(new ImageIcon(originalImg)));
		frame.addImage(new JLabel(new ImageIcon(processedImg)));
		frame.setVisible(true);

		
	}


	private static void vectorCompareToTest() {
		System.out.println("Vec test");
		byte x1;
		byte y1;
		byte x2;
		byte y2;
		Vector<Byte, Byte> p1,p2;

		//case
		x1  = 1; x2 = 2;
		y1  = 1; y2 = 2;
		p1 = Vector.of(x1, y1);
		p2 = Vector.of(x2, y2);
		System.out.println(p1 + "-" + p2 + " = " +p1.compareTo(p2));
		//case
		x1  = 1; x2 = 2;
		y1  = 1; y2 = 1;
		p1 = Vector.of(x1, y1);
		p2 = Vector.of(x2, y2);
		System.out.println(p1 + "-" + p2 + " = " +p1.compareTo(p2));
		//case
		x1  = 1; x2 = 2;
		y1  = 3; y2 = 2;
		p1 = Vector.of(x1, y1);
		p2 = Vector.of(x2, y2);
		System.out.println(p1 + "-" + p2 + " = " +p1.compareTo(p2));
		//case
		x1  = 1; x2 = 1;
		y1  = 1; y2 = 2;
		p1 = Vector.of(x1, y1);
		p2 = Vector.of(x2, y2);
		System.out.println(p1 + "-" + p2 + " = " +p1.compareTo(p2));
		//case
		x1  = 1; x2 = 1;
		y1  = 1; y2 = 1;
		p1 = Vector.of(x1, y1);
		p2 = Vector.of(x2, y2);
		System.out.println(p1 + "-" + p2 + " = " +p1.compareTo(p2));
		//case
		x1  = 1; x2 = 1;
		y1  = 3; y2 = 2;
		p1 = Vector.of(x1, y1);
		p2 = Vector.of(x2, y2);
		System.out.println(p1 + "-" + p2 + " = " +p1.compareTo(p2));
		//case
		x1  = 4; x2 = 2;
		y1  = 1; y2 = 2;
		p1 = Vector.of(x1, y1);
		p2 = Vector.of(x2, y2);
		System.out.println(p1 + "-" + p2 + " = " +p1.compareTo(p2));
		//case
		x1  = 4; x2 = 2;
		y1  = 2; y2 = 2;
		p1 = Vector.of(x1, y1);
		p2 = Vector.of(x2, y2);
		System.out.println(p1 + "-" + p2 + " = " +p1.compareTo(p2));
		//case
		x1  = 4; x2 = 2;
		y1  = 50; y2 = 2;
		p1 = Vector.of(x1, y1);
		p2 = Vector.of(x2, y2);
		System.out.println(p1 + "-" + p2 + " = " +p1.compareTo(p2));
		
	}

/*
	private static void pairCompareToTest() {
		System.out.println("Pair test");
		byte x1;
		byte y1;
		byte x2;
		byte y2;
		Pair<Byte, Byte> p1,p2;

		//case
		x1  = 1; x2 = 2;
		y1  = 1; y2 = 2;
		p1 = Pair.of(x1, y1);
		p2 = Pair.of(x2, y2);
		System.out.println(p1 + "-" + p2 + " = " +p1.compareTo(p2));
		//case
		x1  = 1; x2 = 2;
		y1  = 1; y2 = 1;
		p1 = Pair.of(x1, y1);
		p2 = Pair.of(x2, y2);
		System.out.println(p1 + "-" + p2 + " = " +p1.compareTo(p2));
		//case
		x1  = 1; x2 = 2;
		y1  = 3; y2 = 2;
		p1 = Pair.of(x1, y1);
		p2 = Pair.of(x2, y2);
		System.out.println(p1 + "-" + p2 + " = " +p1.compareTo(p2));
		//case
		x1  = 1; x2 = 1;
		y1  = 1; y2 = 2;
		p1 = Pair.of(x1, y1);
		p2 = Pair.of(x2, y2);
		System.out.println(p1 + "-" + p2 + " = " +p1.compareTo(p2));
		//case
		x1  = 1; x2 = 1;
		y1  = 1; y2 = 1;
		p1 = Pair.of(x1, y1);
		p2 = Pair.of(x2, y2);
		System.out.println(p1 + "-" + p2 + " = " +p1.compareTo(p2));
		//case
		x1  = 1; x2 = 1;
		y1  = 3; y2 = 2;
		p1 = Pair.of(x1, y1);
		p2 = Pair.of(x2, y2);
		System.out.println(p1 + "-" + p2 + " = " +p1.compareTo(p2));
		//case
		x1  = 4; x2 = 2;
		y1  = 1; y2 = 2;
		p1 = Pair.of(x1, y1);
		p2 = Pair.of(x2, y2);
		System.out.println(p1 + "-" + p2 + " = " +p1.compareTo(p2));
		//case
		x1  = 4; x2 = 2;
		y1  = 2; y2 = 2;
		p1 = Pair.of(x1, y1);
		p2 = Pair.of(x2, y2);
		System.out.println(p1 + "-" + p2 + " = " +p1.compareTo(p2));
		//case
		x1  = 4; x2 = 2;
		y1  = 50; y2 = 2;
		p1 = Pair.of(x1, y1);
		p2 = Pair.of(x2, y2);
		System.out.println(p1 + "-" + p2 + " = " +p1.compareTo(p2));
		

		
	}
*/
	}
