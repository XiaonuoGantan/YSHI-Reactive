package org.yshi.frp.test;

import static org.junit.Assert.*;
import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.yshi.frp.MutableInteger;
import org.yshi.frp.MutableValue;
import org.yshi.frp.func.UnaryFunction;


public class MutableIntegerTest extends TestCase {
	private MutableValue<Integer> mi0;
	private MutableValue<Integer> mi1;
	private MutableValue<Integer> mi2;
	
	
	@Before
	public void setUp() throws Exception {
		mi0 = new MutableInteger(0);
		mi1 = mi0.transform(new UnaryFunction<Integer, Integer>() {
			public Integer evaluate(Integer input) {
				return input + 4;
			}
		});
		mi2 = mi0.transform(new UnaryFunction<Integer, Integer>() {
			public Integer evaluate(Integer input) {
				return input + 1;
			}
		});
	}

	@Test
	public void testInitial() {
		mi0.set(0);
		assertEquals("mi0 initial must be 0", (int) mi0.get(), 0);
		assertEquals("mi1 initial must be 4", (int) mi1.get(), 4);
		assertEquals("mi2 initial must be 1", (int) mi2.get(), 1);
	}

	@Test
	public void testObservation() {
		assertEquals("mi0 initial", 0, (int) mi0.get());
		assertEquals("mi1 initial", 4, (int) mi1.get());
		assertEquals("mi2 initial", 1, (int) mi2.get());
		mi0.set(10);
		assertEquals("mi0 after change", 10, (int) mi0.get());
		assertEquals("mi1 after change", 14, (int) mi1.get());
		assertEquals("mi2 after change", 11, (int) mi2.get());
	}
}
