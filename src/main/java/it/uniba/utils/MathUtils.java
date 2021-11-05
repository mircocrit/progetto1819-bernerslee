package it.uniba.utils;

import java.time.DateTimeException;
import java.time.LocalDate;

/**
 * MathUtils.java ||noECB|| This class implements date controls , and includes
 * sum and divide operands
 * 
 */
public class MathUtils {
	private static final int NUM13 = 13;

	/**
	 * Returns the arithmetic sum of two integers.
	 * 
	 * @param addend1 First integer to sum.
	 * @param addend2 Second integer to sum.
	 * @return The arithmetic sum of <code>a</code> and <code>b</code>.
	 */
	public int add(final int addend1, final int addend2) {
		return addend1 + addend2;
	}

	/**
	 * Returns the arithmetic division of two integers.
	 * 
	 * @param num The division numerator.
	 * @param div The division denominator.
	 * @return The result of the division as a <code>float</code> number.
	 * @throws ArithmeticException If <code>div</code> is zero.
	 */
	public float divide(final int num, final int div) throws ArithmeticException {
		float x = (float) num / div;
		return x;
	}

	/**
	 * Returns the boolean result if the date is correct
	 * 
	 * @param year  Year of the input date
	 * @param month Month of the input date
	 * @param day   Day of the input date
	 * @return The result of the check as a <code>boolean</code> value.
	 */
	public boolean isData(final int year, final int month, final int day) {
		boolean dateIsValid = true;
		try {
			LocalDate.of(year, month, day);
		} catch (DateTimeException e) {
			dateIsValid = false;
		}
		return dateIsValid;

	}

	/**
	 * Returns the boolean result if the date is correct
	 * 
	 * @param year  Year of the input date
	 * @param month Month of the input date
	 * @return The result of the check as a <code>boolean</code> value.
	 */
	public boolean isDataNoDay(final int year, final int month) {
		if (month <= 0 || month >= NUM13) {
			return false;
		}
		if (year < 0) {
			return false;
		}
		return true;
	}
}
