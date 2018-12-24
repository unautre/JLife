import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		final JDrawer drawer = new JDrawer(800, 600);

		final JFrame frame = new JFrame("JLife v0.1");
		frame.setSize(800, 600);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setLayout(new GridLayout(1, 1));
		frame.add(drawer);

		final BufferedImage initImage = randomSourceImage();
		drawer.setImage(initImage);

		final Transition transition = new Transition(initImage);
		while (transition.hasNext()) {
			TimeUnit.MILLISECONDS.sleep(100L);
			drawer.setImage(transition.next());
		}
	}

	private static BufferedImage randomSourceImage() {
		final BufferedImage image = new BufferedImage(80, 60, BufferedImage.TYPE_BYTE_BINARY);

		final Random rand = new Random();
		for (int i = 0; i < image.getWidth(); i++) {
			for (int j = 0; j < image.getHeight(); j++) {
				final int nextInt;

				if (rand.nextInt() < Integer.MAX_VALUE / 4) {
					nextInt = 0xFFFFFFFF;
				} else {
					nextInt = 0x0;
				}

				image.setRGB(i, j, nextInt);
			}
		}

		return image;
	}
}
