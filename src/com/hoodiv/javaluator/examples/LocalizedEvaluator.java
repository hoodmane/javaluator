package com.hoodiv.javaluator.examples;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Locale;

import com.hoodiv.javaluator.DoubleEvaluator;
import com.hoodiv.javaluator.Parameters;
import com.hoodiv.javaluator.Token;

/** An example of how to localize an existing evaluator to match French locale.
 * <br>As a French, I prefer "moyenne" to "avg" and "somme" to "sum".
 * <br>As the default argument function (',') is used as decimal separator in France,
 * I may also change it to ';'.
 * <br> Here is how I can do that very easily.
 */
public class LocalizedEvaluator extends DoubleEvaluator {
	/** Defines the new function (square root).*/
	private static final Parameters PARAMS;
	
	static {
		// Gets the default DoubleEvaluator's parameters
		PARAMS = DoubleEvaluator.getDefaultParameters();
		// adds the translations
		PARAMS.setTranslation(DoubleEvaluator.SUM, "somme");
		PARAMS.setTranslation(DoubleEvaluator.AVERAGE, "moyenne");
		// Change the default function separator
		PARAMS.setFunctionArgumentSeparator(';');
	}

	private DecimalFormat format;

	public LocalizedEvaluator() {
		super(PARAMS);
		// Create a French number formatter
		format = (DecimalFormat) DecimalFormat.getInstance(Locale.FRENCH);
		format.setGroupingUsed(true);
	}

	@Override
	protected Double toValue(Token literalTok, Object evaluationContext) {
		// Override the method that converts a literal to a number, in order to match with
		// the French decimal separator
                String literal = literalTok.getString();
		try {
			// For a strange reason, Java thinks that only non breaking spaces are French thousands
			// separators. So, we will replace spaces in the literal by non breaking spaces
			literal = literal.replace(' ', (char)0x00A0); 
			return format.parse(literal).doubleValue();
		} catch (ParseException e) {
			// If the number has a wrong format, throw the right exception.
			throw literalTok.getError(literal+" is not a number");
		}
	}
	
	public static void main(String[] args) {
		// Test that all this stuff is ok
		LocalizedEvaluator evaluator = new LocalizedEvaluator();
		String expression = "3 000 +moyenne(3 ; somme(1,5 ; 7 ; -3,5))";
		System.out.println (expression+" = "+evaluator.format.format(evaluator.evaluate(expression)));
	}
}
