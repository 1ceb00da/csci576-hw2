import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class MainHW2 {

	public static void main(String args[]) {
		
		String filename;
		String NString;

		filename = args[0];
		NString = args[1];

		//filename = "Image1.raw";
		//NString = "100";
		
		int N = Integer.parseInt(NString);
		byte[] bytes = ImageHandler.readImageFromFile(filename);
		BufferedImage originalImg = ImageHandler.toBufferedImage(bytes, 352,
				288, BufferedImage.TYPE_INT_RGB);
		BufferedImage processedImg = originalImg;

		/*
		 * Step 0; Preprocessing Generate a list of all possible pixel pairs
		 * i.e. all possible (px1, px2) vals Then select inital N pairs from
		 * this list
		 */
		ArrayList<Vector<Integer, Integer>> allCodes = new ArrayList<Vector<Integer, Integer>>();
		for (int i = 0; i < 256; i++) {
			for (int j = 0; j < 256; j++) {
				allCodes.add(new ImmutableVector<Integer, Integer>(new Integer(
						i).intValue(), new Integer(j).intValue()));
			}
		}

		/*
		 * Step 1 ------ Get Vector representation of image
		 */

		Integer left, right;
		Vector<Integer, Integer> vector;
		ArrayList<Vector<Integer, Integer>> imageVector = new ArrayList<Vector<Integer, Integer>>();
		for (int i = 0; i < bytes.length; i += 2) {
			left = new Integer(Byte.toUnsignedInt(bytes[i]));
			right = new Integer(Byte.toUnsignedInt(bytes[i + 1]));
			vector = new ImmutableVector<Integer, Integer>(left, right);
			imageVector.add(vector);
		}

		System.out.println("Step1 fin "); // Arrays.toString(imageVector.toArray()));

		/* END of step1 */

		/*
		 * Step 2 ------ Init N random px1,px2 means or centroids or codewords
		 */

		Set<Vector<Integer, Integer>> meansSet = new HashSet<Vector<Integer, Integer>>();

		while (meansSet.size() < N) {
			int idx = new Double(Math.random() * allCodes.size()).intValue();
			meansSet.add(allCodes.get(idx));
		}
		List<Vector<Integer, Integer>> means = new ArrayList<Vector<Integer, Integer>>(
				meansSet);

		System.out.println("Step2 fin ");
		
		int[] mxi = new int[means.size()];
		int[] myi = new int[means.size()];

		for (int i = 0; i < means.size(); i++) {
			mxi[i] = means.get(i).getLeft().intValue();
			myi[i] = means.get(i).getRight().intValue();

		}
		
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

		System.out.println("Steps 3 & 4 beginning. This can take a while if N is large");
		while (KMeans.meanDiff(oldMeans, means) > 0) {

			/*
			 * Step 3 **** K-MEANS algorithm step 1 **** ------ Find closest
			 * centroids
			 */

			for (int i = 0; i < imageVector.size(); i++) {
				vec = imageVector.get(i);
				closestMeanIndex = KMeans.findClosestMean(vec, means);
				cluster[i] = closestMeanIndex;
			}
			
			/*
			 * Step 4 **** K-MEANS algorithm step 2**** ------ Compute new
			 * centroids
			 */
			oldMeans = new ArrayList<Vector<Integer, Integer>>(means);
			means = KMeans.computeNewMeans(means, cluster, imageVector);
			iter++;
		}

		System.out.println("Steps 3&4 fin in " + iter + " iterations:-");

		int[] mx = new int[means.size()];
		int[] my = new int[means.size()];

		for (int i = 0; i < means.size(); i++) {
			mx[i] = means.get(i).getLeft().intValue();
			my[i] = means.get(i).getRight().intValue();

		}

		/* END of step3 & step4 */

		/*
		 * Step 5 ------ Quantize input vectors to produce output image and
		 * display
		 */

		bytes = new byte[bytes.length];

		for (int i = 0; i < imageVector.size(); i += 1) {

			Vector<Integer, Integer> code = means.get(cluster[i]);
			bytes[i * 2] = code.getLeft().byteValue();
			bytes[i * 2 + 1] = code.getRight().byteValue();
		}
		
		processedImg = ImageHandler.toBufferedImage(bytes, 352, 288,
				BufferedImage.TYPE_INT_RGB);
		System.out.println("Step5 fin:- ");

		ViewFrame frame = new ViewFrame("Display Images");
		frame.addImage(new JLabel(new ImageIcon(originalImg)));
		frame.addImage(new JLabel(new ImageIcon(processedImg)));
		frame.setVisible(true);

	}

}