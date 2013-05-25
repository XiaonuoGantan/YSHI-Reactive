package org.yshi.frp;

import org.yshi.frp.func.BinaryFunction;

public class MutableInteger extends MutableValue<Integer> {

	public MutableInteger(Integer initial) {
		super(initial);
	}

	public MutableInteger(MutableValue<Integer> source) {
		super(source);
	}

	public <PT> MutableInteger(MutableValue<PT> source1,
			MutableValue<PT> source2, BinaryFunction<PT, PT, Integer> join) {
		super(source1, source2, join);
	}

	public void add(int value) {
		this.set(this.get() + value);
	}
	
	public void multiply(int value) {
		this.set(this.get() * value);
	}
}
