#CivicMining LLC. challenge

We have just created a new company to mine the hottest new cryptocurrency called CivicBucks. CivicBucks are 
going to take over the world and replace all other currencies in existence. The future is now! And we're right 
there at the beginning. Everyone will be uber rich when we're done. We hired you to write software for us to 
efficiently mine this currency.

We need a Java program that can compute a numeric palindromes for a range of values that will be defined when 
the computation is invoked. Initially we will be computing the first block of values from 0 to 100,000,000. 
Later, we will want to compute additional blocks. Each palindrome we mine will net us a CivicBuck. The software 
must perform multi-threaded computations and be configurable for the machine it will run on. Initially, we will 
use 8 threads to execute the computations concurrently. Because we don't yet know how this will perform, we 
need to limit the entire execution time to a maximum of 30 seconds, after which the program must exit with a 
time-out and produce the output of whatever computations were performed. We will ultimately test this on 
various hardware configurations to achieve the best possible computation times, so design it to be tunable as 
required. Make the program as fast and efficient as you can. We're counting on you making us all rich!

After execution is completed, the program must output the following (see expected output):
* The sorted list of numeric palindromes computed and their binary representation.
* The count of numeric palindromes computed.
* The number of tasks executed by the thread pool.
* Performance statistics for the run, including:
  * The max time taken by a computation in millis.
  * The mean computation time of all computations in millis.
  * The total computation time taken.

A palindrome is a number that is numerically the same when read forwards or backwards. Furthermore, CivicBucks 
are not that simple to mine, there is also the requirement that the binary representation of said number also be 
a palindrome. Once these conditions are met, we have mined ourselves a CivicBuck!

An example of a CivicBuck palindrome that is both numeric and binary is the number 99 with a binary 
representation of 1100011.

Numbers being computed will never be proceeded by one or more zeroes. A number such as 050 is not valid.

##The Rules
* You must make do with the included JDK libraries without the use of external libraries of any kind.
* You may NOT use any of the Java 8's new stream APIs such as foreach, map or filter, including the entirety
   of java.util.stream.
* You may NOT adjust the heap size for your program; it must use the default VM heap size.

##Expected output

	Palindromes:
	...
	     99 binary: 1100011
	...
	Performance (millis): max: #, mean: #
	Palindromes computed: #
	Tasks run: #
	Duration: # millis.


##Regarding the Exercise

The focus of this exercise is not the palimdrome computation, but the implementation of the other requirements. 
The palimdrome computation is a simple drop in for a computation that could run for minutes, hours, and even days 
to compute actual crypto-currency. Do not focus solely on the computation, though we are interested in your 
implementation and whatever creativity you are able to incorporate into the entire solution.
 

##Questions

Along with your implementation of the requirements, provide answers to the following:

1. What would you do to make the program better had you more time?
2. How would you prove that your solution is correct?

