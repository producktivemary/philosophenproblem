package com.school;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Semaphore;

public class Runner {

    public void run() {

        // Run the entire thingy here :)


        // Instantiate 5 philosophers and 5 forks
        final int N = 5; // Number of forks for both of them


        // Create the forks, 1 fork per philosopher
        /***
         * Semaphore-class:  used to restrict the number of other threads than can access this resource (=> fork)
         * every Philosoph has one of the Semaphore forks
         */
        // Set size of semaphore list
        Semaphore[] fork = new Semaphore[N];
        // create a Semaphore for list size, this semaphore is supposed to depict a *FORK*
        for (int f = 0; f < N; f++) {
            fork[f] = new Semaphore(1, true);
        }


        // Create a philosoph list of size N (=5)
        Philosoph[] philosophs = new Philosoph[N];

        // Assign the forks. The fork on the left is always the Nr. X's philosoph fork.
        // On the right always belongs to neighbor.
        for (int me = 0; me < N; me++) {
            int forkLeftIndex = me; //Fork on the left is always the own
            int forkRightIndex = me + 1; //Fork on the right is the neighbors own fork
            if (forkRightIndex == N) {
                forkRightIndex = 0;
            }
            philosophs[me] = new Philosoph(me, fork[forkLeftIndex], fork[forkRightIndex]);

        }

        // Make a thread to wrap up the philosophs, which implement Runnable, and need a thread to be run.
        // Running the threads will start the program.
        for (int i = 0; i < N; i++) {
            Thread thread = new Thread(philosophs[i]);
            thread.start();

        }



    }

    public static void main(String[] args) {
        // Main to step into program
     new Runner().run();
    }

}
