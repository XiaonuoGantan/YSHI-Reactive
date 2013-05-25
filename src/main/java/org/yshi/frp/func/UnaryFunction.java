package org.yshi.frp.func;

public interface UnaryFunction<A, B> {
	public B evaluate(A input);
}
