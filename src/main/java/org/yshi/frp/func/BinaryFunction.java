package org.yshi.frp.func;

public interface BinaryFunction<A, B, C> {
	public C evaluate(A arg0, B arg1);
}
