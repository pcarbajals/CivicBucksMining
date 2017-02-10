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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * This is a custom {@link ThreadPoolExecutor} for the CivicMining LLC.
 * challenge (read the README.txt file for more information.)
 *
 * This executor provides convenience methods for initiating the mining
 * computation based on arguments passed in the constructor and for retrieving
 * the mining results.
 *
 * @author Pablo A. Carbajal
 *
 */
public class MiningExecutor extends ThreadPoolExecutor {

	/**
	 * This list holds the future results of asynchronous mining computations.
	 * It is used for retrieving task information upon completion.
	 */
	final private List<Future<TaskResult>> mListOfFutureTaskResults;

	/**
	 * A {@link MiningStatistics} object for holding performance statistics
	 * about this {@link MiningExecutor}.
	 */
	final private MiningStatistics mStatistics = new MiningStatistics();

	/**
	 * The starting range of the block to mine.
	 */
	private final long mBlockStart;

	/**
	 * The ending range of the block to mine (inclusive).
	 */
	private final long mBlockEnd;

	/**
	 * Creates a {@link MiningExecutor} that reuses a fixed number of threads
	 * operating off a shared unbounded queue. At any point, at most
	 * <tt>numberOfThreads</tt> threads will be active processing tasks.
	 *
	 * The threads in the pool will exist until it is explicitly
	 * {@link ExecutorService#shutdown shutdown}.
	 *
	 * @param numberOfThreads
	 *            the number of threads in the pool
	 * @param blockStart
	 *            the starting range of the block to mine.
	 * @param blockEnd
	 *            the ending range of the block to mine (inclusive).
	 */
	public MiningExecutor(final int numberOfThreads, final long blockStart, final long blockEnd) {
		super(numberOfThreads, numberOfThreads, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());

		mBlockStart = blockStart;
		mBlockEnd = blockEnd;

		mListOfFutureTaskResults = new ArrayList<Future<TaskResult>>(numberOfThreads);
	}

	@Override
	protected void afterExecute(final Runnable r, final Throwable t) {
		super.afterExecute(r, t);

		if (r != null) {
			mStatistics.end(r.toString());
		}
	}

	@Override
	protected void beforeExecute(final Thread t, final Runnable r) {
		mStatistics.start(r.toString());
		super.beforeExecute(t, r);
	}

	/**
	 * Call this method after all tasks have been completed (see
	 * {@link #startMining()} to obtain the combined results of all executed
	 * tasks.
	 *
	 * @return An instance of {@link MiningExecutorResults} for accessing the
	 *         combined results of all executed tasks.
	 */
	public MiningExecutorResults calculateMiningResults() {
		int totalCivicBucks = 0;
		final StringBuilder miningOutput = new StringBuilder();

		/*
		 * Iterate over all future results of asynchronous mining computations,
		 * then add up the total number of CivicBucks each computation
		 * calculated and accumulate the mining output.
		 *
		 */
		for (final Iterator<Future<TaskResult>> iterator = mListOfFutureTaskResults.iterator(); iterator.hasNext();) {
			final Future<TaskResult> future = iterator.next();
			try {
				final TaskResult result = future.get();

				totalCivicBucks += result.getCivicBucksTotal();
				miningOutput.append(result.getMiningOutput());

			} catch (InterruptedException | ExecutionException e) {
				System.out.println(
						"Unable to retrieve information from a thread (error below). Printing partial results.");
				e.printStackTrace();
			}
		}

		return new MiningExecutorResults(totalCivicBucks, mStatistics, miningOutput.toString());
	}

	/**
	 * This method executes the mining computation of CivicBucks. It
	 * automatically creates the specified number of threads and submits the
	 * mining tasks. After submitting all tasks, this method calls
	 * {@link #shutdown()}.
	 *
	 * After calling this method, you may call
	 * {@link #awaitTermination(long, TimeUnit)} or {@link #isTerminated()} to
	 * determined if all the tasks have completed. The results can be collected
	 * by invoking {@link #calculateMiningResults()}.
	 */
	public void startMining() {
		/*
		 * The block to mine has to be split equally among all threads.
		 * Therefore, divide the total block size (mBlockEnd) by the number of
		 * threads and round up the value (Math.ceil()), the result will be the
		 * task size.
		 *
		 * The first task will mine from the start of the block to the end of
		 * the start plus the task size. All subsequent tasks will mine from
		 * where the previous task ended plus one until the task size.
		 */
		final int numberOfThreads = getMaximumPoolSize();
		final long taskSize = (long) Math.ceil((double) mBlockEnd / numberOfThreads);

		// set pointers for first task
		long taskStart = mBlockStart;
		long taskEnd = mBlockStart + taskSize;

		// create as many tasks as number of threads specified
		for (int threads = 0; threads < numberOfThreads; threads++) {
			final MiningTask minerTask = new MiningTask(taskStart, taskEnd);

			// submit the task and keep a reference of the future result
			final Future<TaskResult> futureResult = submit(minerTask);
			mListOfFutureTaskResults.add(futureResult);

			// update the starting and ending pointers for next task
			taskStart = taskEnd + 1;
			taskEnd = taskStart + taskSize;
		}

		// all tasks submitted, so shutdown orderly
		shutdown();
	}
}
