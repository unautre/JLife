import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class JDrawer extends JPanel {

	private volatile BufferedImage image;

	private final int w, h;

	public JDrawer(int w, int h) {
		this.w = w;
		this.h = h;
		setSize(w, h);
	}

	@Override
	public void paint(Graphics arg0) {
		super.paint(arg0);

		if (image != null) {
			arg0.drawImage(image, 0, 0, w, h, null);
		} else {
			arg0.drawString("No image.", 0, 0);
		}
	}

	public void setImage(BufferedImage image) {
		// to full RGB image
		this.image = image;
		repaint();
	}
}