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

import java.util.concurrent.TimeUnit;

/**
 * This is the entry point for the CivicMining LLC. challenge (read the
 * README.txt file for more information.)
 *
 *
 * @author Pablo A. Carbajal
 *
 */
public final class CivicBucksMiner {

	/**
	 * Blocks until all tasks in {@code executor} have completed execution after
	 * a shutdown request, or the timeout occurs, or the current thread is
	 * interrupted, whichever happens first.
	 *
	 * In case of a timeout, an error message is printed out to the standard
	 * output stream and attempts to stop all actively executing tasks (see
	 * {@link MiningExecutor#shutdownNow()}).
	 *
	 * In case of a thread interruption, just an error message is printed out to
	 * the standard output stream.
	 *
	 * @param executor
	 *            the {@link MiningExecutor} to wait for
	 * @param timeout
	 *            the maximum time to wait
	 * @param units
	 *            the time unit of the timeout argument
	 */
	private static void awaitExecutorTermination(final MiningExecutor executor, final int timeout,
			final TimeUnit units) {
		try {
			if (!executor.awaitTermination(timeout, units)) {
				System.out.println("Execution timed out, printing partial results.");
				executor.shutdownNow();
			}
		} catch (final InterruptedException e) {
			System.out.println("Thread interrupted (see error below). Printing partial results.");
			e.printStackTrace();
		}
	}

	/**
	 * Launches the program for mining CivicBucks with the following 4 mandatory
	 * arguments:
	 *
	 * <pre>
	 * java CivicBucksMiner [start] [end] [numberOfThreads] [timeout]
	 *
	 * Where:
	 *    start            - the start of the block to mine
	 *    end              - the end of the block to mine (inclusive)
	 *    numberOfThreads  - the number of concurrent threads to execute
	 *    timeout          - the timeout (in seconds) for long executions
	 * </pre>
	 *
	 * @param args
	 *            the program arguments as described above.
	 */
	public static void main(final String[] args) {
		// TODO implement more robust handling of arguments

		// TODO scale beyond limit of type long
		final long blockStart = Long.parseLong(args[0]);
		// TODO scale beyond limit of type long
		final long blockEnd = Long.parseLong(args[1]);
		final int numberOfThreads = Integer.parseInt(args[2]);
		final int timeout = Integer.parseInt(args[3]);
		final TimeUnit timeoutUnits = TimeUnit.SECONDS;

		// TODO Add more configuration parameters (e.g. poolSize, keepAliveTime)
		final MiningExecutor executor = new MiningExecutor(numberOfThreads, blockStart, blockEnd);

		System.out.println("Mining CivicBucks");
		System.out.println("block to mine:  " + blockStart + " to " + blockEnd);
		System.out.println("timeout: " + timeout + " " + timeoutUnits);

		final long executionStartTime = System.currentTimeMillis();

		executor.startMining();

		// wait until all threads are finished
		awaitExecutorTermination(executor, timeout, timeoutUnits);

		final long executionEndTime = System.currentTimeMillis();
		final String duration = String.valueOf(executionEndTime - executionStartTime);

		final MiningExecutorResults results = executor.calculateMiningResults();

		System.out.println("Palindromes:");
		System.out.print(results.getOutput());
		System.out.println("Performance (millis): max: " + results.getMaxPerformance() + ", mean: "
				+ results.getMeanPerformance());
		System.out.println("Palindromes computed: " + results.getTotalCivicBucks());
		System.out.println("Tasks run: " + numberOfThreads);
		System.out.println("Duration: " + duration + " millis.");
	}
}
