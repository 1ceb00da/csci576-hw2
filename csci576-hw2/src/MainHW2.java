import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;


public class MainHW2 {

	public static void main(String args[]) {
		
		String filename = "Image1.raw";
		byte[] bytes = ImageHandler.readImageFromFile(filename);
		BufferedImage originalImg = ImageHandler.toBufferedImage(bytes, 352, 288, BufferedImage.TYPE_INT_RGB);
		BufferedImage originalImg2 = ImageHandler.toBufferedImage(bytes, 352, 288, BufferedImage.TYPE_BYTE_GRAY);
		BufferedImage processedImg = originalImg;
		
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
		
		System.out.println(Arrays.toString(imageVector.toArray()));
		
		System.exit(1);
		
		/* END of step1 */
		
		
		/* TODO Step 2
		 * ------
		 * Init N random px1,px2 means or centroids or codewords
		 */
		
		/* TODO Step 3 **** K-MEANS algorithm step 1 ****
		 * ------
		 * Find closest centroids
		 */
		
		/* TODO Step 4 **** K-MEANS algorithm step 2****
		 * ------
		 * Compute new centroids
		 */
		
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
