package com.hoodiv.javaluator.examples;

import com.hoodiv.javaluator.Parameters;
import com.hoodiv.javaluator.Operator;
import com.hoodiv.javaluator.AbstractEvaluator;
import com.hoodiv.javaluator.EvaluationContext;
import com.hoodiv.javaluator.Token;
import java.util.Iterator;


/** An example of how to implement an evaluator from scratch.
 */
public class SimpleBooleanEvaluator extends AbstractEvaluator<Boolean> {
	/** The negate unary operator.*/
	public final static Operator NEGATE = new Operator("!", 1, Operator.Associativity.RIGHT, 3);
	/** The logical AND operator.*/
	private static final Operator AND = new Operator("&&", 2, Operator.Associativity.LEFT, 2);
	/** The logical OR operator.*/
	public final static Operator OR = new Operator("||", 2, Operator.Associativity.LEFT, 1);

	private static final Parameters PARAMETERS;
	
	static {
		// Create the evaluator's parameters
		PARAMETERS = new Parameters();
		// Add the supported operators
		PARAMETERS.add(AND);
		PARAMETERS.add(OR);
		PARAMETERS.add(NEGATE);
	}

	public SimpleBooleanEvaluator() {
		super(PARAMETERS);
	}

	@Override
	protected Boolean toValue(Token literal, EvaluationContext evaluationContext) {
		return Boolean.valueOf(literal.getString());
	}

	@Override
	protected Boolean evaluate(Operator operator, Iterator<Boolean> operands, EvaluationContext evaluationContext) {
		if (operator == NEGATE) {
			return !operands.next();
		} else if (operator == OR) {
			Boolean o1 = operands.next();
			Boolean o2 = operands.next();
			return o1 || o2;
		} else if (operator == AND) {
			Boolean o1 = operands.next();
			Boolean o2 = operands.next();
			return o1 && o2;
		} else {
			return super.evaluate(operator, operands, evaluationContext);
		}
	}

	public static void main(String[] args) {
		SimpleBooleanEvaluator evaluator = new SimpleBooleanEvaluator();
		String expression = "tre && fase";
		System.out.println (expression+" = "+evaluator.evaluate(expression));
		expression = "true || (false";
		System.out.println (expression+" = "+evaluator.evaluate(expression));
		expression = "!true";
		System.out.println (expression+" = "+evaluator.evaluate(expression));
	}
}
