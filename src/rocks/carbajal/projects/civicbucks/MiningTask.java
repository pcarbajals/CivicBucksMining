/**
 * MIT License
 *
 * Copyright (c) 2017 Pablo Alejandro Carbajal Siller
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package rocks.carbajal.projects.civicbucks;

import java.util.concurrent.Callable;

/**
 * A {@link Callable} task for performing mining computations. This class
 * executes the algorithm for finding CivicBucks (Palindromes).
 *
 * @author Pablo A. Carbajal
 *
 */
public class MiningTask implements Callable<TaskResult> {

	/**
	 * Constant to the system's line separator.
	 */
	final private String END_OF_LINE = System.getProperty("line.separator");

	/**
	 * The starting range of the block to mine.
	 */
	final private long mStartBlock;

	/**
	 * The end range of the block to mine (inclusive).
	 */
	final private long mEndBlock;

	/**
	 * Creates a new task for mining CivicBucks from a specified block.
	 *
	 * @param start
	 *            the starting point of the block to mine.
	 * @param end
	 *            the ending point of the block to mine (inclusive).
	 */
	public MiningTask(final long start, final long end) {
		mStartBlock = start;
		mEndBlock = end;
	}

	@Override
	public TaskResult call() throws Exception {
		final TaskResult result = mineBlock(mStartBlock, mEndBlock);
		return result;
	}

	/**
	 * Finds out if the specified number is a palindrome.
	 *
	 * @param number
	 *            the number to check
	 * @return <code>true</code> if the number is a palindrome,
	 *         <code>false</code> otherwise.
	 */
	private boolean isPalindrome(final long number) {
		/*
		 * This algorithm for finding if a number is a palindrome converts the
		 * number into a string, then checks if the "string" is a palindrome.
		 */
		// TODO: performance test with Integer.toString()
		final String stringToCheck = String.valueOf(number);
		return isPalindrome(stringToCheck);
	}

	/**
	 * Finds out if the specified {@link String} is a palindrome.
	 *
	 * @param string
	 *            the string to check
	 * @return <code>true</code> if the string is a palindrome,
	 *         <code>false</code> otherwise.
	 */
	private boolean isPalindrome(final String string) {
		/*
		 * Finding if a string is a palindrome requires to reverse the string
		 * and compared both for equality. If both strings (original and
		 * reversed) are identical, then the string is a palindrome.
		 */
		final String reversedString = new StringBuilder(string).reverse().toString();

		if (string.equals(reversedString)) {
			return true;
		}

		return false;
	}

	/**
	 * This method initiates the mining computation for the specified block
	 * range.
	 *
	 * @param startBlock
	 *            the starting point of the block to mine.
	 * @param endBlock
	 *            the ending point of the block to mine (inclusive).
	 * @return a {@link TaskResult} with information about the result of the
	 *         mining computation.
	 */
	private TaskResult mineBlock(final long startBlock, final long endBlock) {
		int numberOfCivicBucks = 0;
		// TODO replace StringBuilder by its own data type
		final StringBuilder output = new StringBuilder();

		/*
		 * Iterate over each number in the block. First, check if the number in
		 * turn is a palindrome, if so, then convert the number to its binary
		 * equivalent and check if the binary equivalent is a palindrome too. If
		 * both are palindrome, then we got a CivicBucket! Increase the count
		 * and append the output.
		 *
		 * If the thread gets interrupted, then just return partial results.
		 */
		for (long number = startBlock; number <= endBlock; number++) {
			if (Thread.currentThread().isInterrupted()) {
				// Interruptions? return with partial results
				return new TaskResult(numberOfCivicBucks, output);
			}

			if (isPalindrome(number)) {
				final String binary = Long.toBinaryString(number);
				if (isPalindrome(binary)) {
					numberOfCivicBucks++;
					output.append("\t" + number + "\tbinary: " + binary + END_OF_LINE);
				}
			}
		}

		return new TaskResult(numberOfCivicBucks, output);
	}

}
