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

/**
 * A wrapper class for individual task results of mining computations. It
 * provides methods for retrieving result information.
 *
 * @author Pablo A. Carbajal
 *
 */
public class TaskResult {

	/**
	 * The total number of CivicBucks calculated by a task.
	 */
	private final int mCivicBucksTotal;

	/**
	 * The output generated by a task.
	 */
	private final StringBuilder mTaskOutput;

	/**
	 * Creates an instance of an object with task results from a mining
	 * computation.
	 *
	 * @param numberOfCivicBucks
	 *            The total number of CivicBucks calculated by a task.
	 * @param output
	 *            The output generated by a task.
	 */
	public TaskResult(final int numberOfCivicBucks, final StringBuilder output) {
		mCivicBucksTotal = numberOfCivicBucks;
		mTaskOutput = output;
	}

	/**
	 * Returns the total number of CivicBucks calculated by a mining
	 * computation.
	 */
	public int getCivicBucksTotal() {
		return mCivicBucksTotal;
	}

	/**
	 * Returns the output generated by a mining computation.
	 */
	public StringBuilder getMiningOutput() {
		return mTaskOutput;
	}

}
