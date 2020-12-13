
import model.CPU;
import model.Memory;
import model.CPU;
import org.junit.Before;
import org.junit.Test;
import view.GUI;

import java.util.Arrays;

import static org.junit.Assert.*;

public class CPUTest {
	CPU cpu;
	Memory m;

	@Before
	public void setUp() {
		GUI gui = new GUI();
		cpu = new CPU(gui);
		m = new Memory();
	}

	@Test
	public void testBranchInstr() {
		m.store((short) 0x0000, (byte) 0x04);
		m.store((short) 0x0001, new byte[]{0x12, 0x34});
		boolean b = cpu.fetchExecute(m);
		short[] r = cpu.getRegisters();
		assertTrue(Arrays.equals(new short[] { (short) 0x04, (short) 0x1234, (short) 0x1234, (short) 0x0000 }, r));
		assertFalse(b);
	}

	@Test
	public void testRegInstr() {
		m.store((short) 0x0000, (byte) 0x18);
		boolean b = cpu.fetchExecute(m);
		short[] r = cpu.getRegisters();
		assertTrue(Arrays.equals(new short[] { (short) 0x18, (short) 0x0000, (short) 0x0001, (short) 0xFFFF }, r));
		assertFalse(b);
	}

	@Test
	public void testTwoOpInstr() {
		m.store((short) 0x0000, (byte) 0x70);
		m.store((short) 0x0001, new byte[]{0x00, 0x03});
		boolean b = cpu.fetchExecute(m);
		short[] r = cpu.getRegisters();
		assertTrue(Arrays.equals(new short[] { (short) 0x70, (short) 0x0003, (short) 0x0003, (short) 0x0003 }, r));
		assertFalse(b);
	}

	@Test
	public void testStopInstr() {
		boolean b = cpu.fetchExecute(m);
		short[] r = cpu.getRegisters();
		assertTrue(Arrays.equals(new short[] { (short) 0x0000, (short) 0x0000, (short) 0x0001, (short) 0x0000 }, r));
		assertTrue(b);
	}
}
