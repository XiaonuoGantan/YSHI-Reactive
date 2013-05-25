package org.yshi.frp.func;


public class RoundFloatToIntegerFunction implements UnaryFunction<Float, Integer> {
	public Integer evaluate(Float input) {
		return Math.round(input);
	}
}
