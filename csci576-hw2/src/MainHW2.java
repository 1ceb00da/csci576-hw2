import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;


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
