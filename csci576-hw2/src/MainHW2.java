import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;


public class MainHW2 {

	public static void main(String args[]) {
		
		String filename = "Image1.raw";
		String NString = "8";
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
		ArrayList<Pair<Byte, Byte>> allCodes = new ArrayList<Pair<Byte,Byte>>();
		for (int i = 0; i < 256; i++) {
			for (int j = 0; j < 256; j++) {
				allCodes.add(new ImmutablePair<Byte, Byte>(new Integer(i).byteValue(), new Integer(j).byteValue()));
			}
		}
		
		
		/* TODO Step 1
		 * ------
		 * Get Vector representation of image
		 */
		
		
		Byte left, right;
		Pair<Byte, Byte> vector;
		ArrayList<Pair<Byte, Byte>> imageVector = new ArrayList<Pair<Byte,Byte>>();
		for (int i = 0; i < bytes.length; i += 2) {
			left = new Byte(bytes[i]);
			right = new Byte(bytes[i+1]);
			vector = new ImmutablePair<Byte, Byte>(left, right);
			imageVector.add(vector);
		}
		
		//System.out.println(Arrays.toString(imageVector.toArray()));
		
		/* END of step1 */
		
		/* TODO Step 2
		 * ------
		 * Init N random px1,px2 means or centroids or codewords
		 */
		
		Set<Pair<Byte, Byte>> meansSet = new HashSet<Pair<Byte, Byte>>();
		
		while (meansSet.size() < N) {
			int idx = new Double(Math.random() * allCodes.size()).intValue();
			meansSet.add(allCodes.get(idx));
		}
		List<Pair<Byte,Byte>> means = new ArrayList<Pair<Byte,Byte>>(meansSet);
		
		System.out.println("Step2 fin-" + means);
		
/*		List<Byte> l = new ArrayList<Byte>();
		List<Byte> r = new ArrayList<Byte>();
		for (Object each : meansSet.toArray()) {
			l.add(((Pair<Byte, Byte>) each).getLeft());
			r.add(((Pair<Byte, Byte>) each).getRight());
		}
		System.out.println(l);
		System.out.println(r);
*/		
		/* END of step2 */
		
				
		int iter = 0;
		int closestMeanIndex;
		
		int[] cluster = new int[imageVector.size()];
		Pair<Byte,Byte> vec;
		
		while (iter < 50 ) {
			/* TODO Step 3 **** K-MEANS algorithm step 1 ****
			 * ------
			 * Find closest centroids
			 */
			
			for (int i = 0; i < imageVector.size(); i++) {
				vec = imageVector.get(i);
				closestMeanIndex = KMeans.findClosestMean(vec, means);
				cluster[i] = closestMeanIndex;
			}
			
			/* TODO Step 4 **** K-MEANS algorithm step 2****
			 * ------
			 * Compute new centroids
			 */
			
			means = KMeans.computeNewMeans(means, cluster, imageVector);
			
			iter++;
		}
		
		System.out.println("Done with step 4");
		System.out.println(means);
		System.exit(1);
		
		
		/* TODO Step 5
		 * ------
		 * Quantize input vectors to prduce output image
		 * and display
		 */
		
		ViewFrame frame = new ViewFrame("Display Images");
		frame.addImage(new JLabel(new ImageIcon(originalImg)));
		frame.addImage(new JLabel(new ImageIcon(processedImg)));
		frame.setVisible(true);

		
	}
}
