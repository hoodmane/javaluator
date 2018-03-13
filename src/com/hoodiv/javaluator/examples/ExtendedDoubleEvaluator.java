package com.hoodiv.javaluator.examples;

import java.util.Iterator;

import com.hoodiv.javaluator.DoubleEvaluator;
import com.hoodiv.javaluator.Function;
import com.hoodiv.javaluator.Parameters;

/** A subclass of DoubleEvaluator that supports SQRT function.
 */
public class ExtendedDoubleEvaluator extends DoubleEvaluator {
	/** Defines the new function (square root).*/
	private static final Function SQRT = new Function("sqrt", 1);
	private static final Parameters PARAMS;
	
	static {
		// Gets the default DoubleEvaluator's parameters
		PARAMS = DoubleEvaluator.getDefaultParameters();
		// add the new sqrt function to these parameters
		PARAMS.add(SQRT);
	}

	public ExtendedDoubleEvaluator() {
		super(PARAMS);
	}

	@Override
	protected Double evaluate(Function function, Iterator<Double> arguments, Object evaluationContext) {
		if (function == SQRT) {
			// Implements the new function
			return Math.sqrt(arguments.next());
		} else {
			// If it's another function, pass it to DoubleEvaluator
			return super.evaluate(function, arguments, evaluationContext);
		}
	}
	
	public static void main(String[] args) {
		// Test that all this stuff is ok
		String expression = "sqrt(abs(-2))^2";
		System.out.println (expression+" = "+new ExtendedDoubleEvaluator().evaluate(expression));
	}

}
