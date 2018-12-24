import java.awt.image.BufferedImage;
import java.awt.image.ByteLookupTable;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.awt.image.LookupOp;
import java.util.Arrays;
import java.util.Iterator;

public class Transition implements Iterator<BufferedImage> {

	private static final Kernel CONV_KERNEL = new Kernel(3, 3, new float[] {
			2.0f, 2.0f, 2.0f, //
			2.0f, 1.0f, 2.0f, //
			2.0f, 2.0f, 2.0f, //
	});

	private static final byte[] LOOKUP_TABLE = new byte[256];
	static {
		Arrays.fill(LOOKUP_TABLE, (byte) 0);
		LOOKUP_TABLE[5] = LOOKUP_TABLE[6] = LOOKUP_TABLE[7] = 1;
	}

	private volatile BufferedImage flip1, flip2, flip3;

	private boolean flipOver = true;

	private final ConvolveOp conv = new ConvolveOp(CONV_KERNEL, ConvolveOp.EDGE_ZERO_FILL, null);

	private final LookupOp lookUp = new LookupOp(new ByteLookupTable(0, LOOKUP_TABLE), null);

	public Transition(BufferedImage source) {
		this.flip1 = source;
		this.flip2 = new BufferedImage(source.getWidth(), source.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
		this.flip3 = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());
	}

	public void transition(BufferedImage source, BufferedImage target) {
		conv.filter(source.getRaster(), flip2.getRaster());

		lookUp.filter(flip2.getRaster(), target.getRaster());
	}

	public BufferedImage getIntermediate() {
		return flip2;
	}

	@Override
	public boolean hasNext() {
		return true;
	}

	@Override
	public BufferedImage next() {
		final BufferedImage source, target;

		if (flipOver) {
			source = flip1;
			target = flip3;
		} else {
			source = flip3;
			target = flip1;
		}

		flipOver = !flipOver;
		transition(source, target);
		return target;
	}

	public static Iterable<BufferedImage> on(BufferedImage source) {
		return () -> new Transition(source);
	}
}