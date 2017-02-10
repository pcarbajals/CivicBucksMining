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
 * Instances of this class hold the results of a {@link MiningExecutor}.
 *
 * @author Pablo A. Carbajal
 *
 */
public class MiningExecutorResults {

	/**
	 * The total number of CivicBucks computed by all tasks completed by the
	 * {@link MiningExecutor}.
	 */
	private final int mTotalCivicBucks;

	/**
	 * The max time taken by a single task (in millis).
	 */
	private final long mMaxPerformance;

	/**
	 * The mean computation time of all tasks (in millis).
	 */
	private final long mMeanPerformance;

	/**
	 * The output generated by all tasks.
	 */
	private final String mOutput;

	/**
	 * Creates a new instance of class that holds the results of a
	 * {@link MiningExecutor}.
	 *
	 * @param totalCivicBucks
	 *            The total number of CivicBucks computed by all tasks
	 * @param statistics
	 *            The statistics generated by the {@link MiningExecutor}
	 * @param output
	 *            The output generated by all the task completed by the
	 *            {@link MiningExecutor}
	 */
	public MiningExecutorResults(final int totalCivicBucks, final MiningStatistics statistics, final String output) {
		mTotalCivicBucks = totalCivicBucks;
		mMaxPerformance = statistics.getMaxTime();
		mMeanPerformance = statistics.getMeanTime();
		mOutput = output;
	}

	/**
	 * Returns the time taken by the longest running task (in millis).
	 */
	public long getMaxPerformance() {
		return mMaxPerformance;
	}

	/**
	 * Returns the mean computation time of all tasks (in millis).
	 */
	public long getMeanPerformance() {
		return mMeanPerformance;
	}

	/**
	 * Returns the output generated by all the task completed by the
	 * {@link MiningExecutor}
	 */
	public String getOutput() {
		return mOutput;
	}

	/**
	 * Returns the total number of CivicBucks computed by all tasks.
	 */
	public int getTotalCivicBucks() {
		return mTotalCivicBucks;
	}

}