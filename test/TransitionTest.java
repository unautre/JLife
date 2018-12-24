import static org.junit.Assert.assertEquals;

import java.awt.image.BufferedImage;
import java.util.Arrays;

import org.junit.Test;

public class TransitionTest {

	final int ALIVE = 0xFFFFFFFF;

	final int DEAD = 0x0;

	@Test
	public void test() {
		final BufferedImage initImage = new BufferedImage(3, 3, BufferedImage.TYPE_BYTE_BINARY);

		initImage.setRGB(0, 0, DEAD);
		initImage.setRGB(0, 1, DEAD);
		initImage.setRGB(0, 2, DEAD);
		initImage.setRGB(1, 0, ALIVE);
		initImage.setRGB(1, 1, ALIVE);
		initImage.setRGB(1, 2, ALIVE);
		initImage.setRGB(2, 0, DEAD);
		initImage.setRGB(2, 1, DEAD);
		initImage.setRGB(2, 2, DEAD);

		Transition transition = new Transition(initImage);

		show("Input", initImage);

		final BufferedImage newImage = transition.next();

		show("Intermediate", transition.getIntermediate());
		show("Output", newImage);

		assertEquals(DEAD, newImage.getRGB(0, 0) >>> 31);
		assertEquals(ALIVE, newImage.getRGB(0, 1) >>> 31);
		assertEquals(DEAD, newImage.getRGB(0, 2) >>> 31);
		assertEquals(DEAD, newImage.getRGB(1, 0) >>> 31);
		assertEquals(ALIVE, newImage.getRGB(1, 1) >>> 31);
		assertEquals(DEAD, newImage.getRGB(1, 2) >>> 31);
		assertEquals(DEAD, newImage.getRGB(2, 0) >>> 31);
		assertEquals(ALIVE, newImage.getRGB(2, 1) >>> 31);
		assertEquals(DEAD, newImage.getRGB(2, 2) >>> 31);
	}

	public static void show(String name, BufferedImage newImage) {
		System.out.println("Shown: " + name);
		for (int i = 0; i < newImage.getHeight(); i++) {
			for (int j = 0; j < newImage.getWidth(); j++) {
				final byte[] value = (byte[]) newImage.getRaster().getDataElements(i, j, null);
				System.out.print(Arrays.toString(value) + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
}