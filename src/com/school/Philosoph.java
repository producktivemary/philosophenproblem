package com.school;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

/***
 * Zwei Möglichkeiten: Philosoph isst nur, wenn Nachbar nicht isst (also jeder 2.). Philosoph lässt Gabel fallen,
 * sobald eine benutzt ist. Problem: Philosophen würden gabel nicht wiederhergeben - hier gelöst.
 */
public class Philosoph implements Runnable {


    // Random number for how long to think/sleep
    Random randomNumber = new Random();

    Semaphore leftFork;
    Semaphore rightFork;
    // me = Position in Array zur "Selbstauskunft"
    int me;
    int numberOfMealsAvailable = 3;
    int numberOfMealsHad = 0;


    public Philosoph(int me, Semaphore forkLeft, Semaphore forkRight) {
        this.me = me;
        leftFork = forkLeft;
        rightFork = forkRight;
    }


    private void tryEating() {

        // if one fork is used, release both.
        if (!leftFork.tryAcquire() || !rightFork.tryAcquire()) {
            leftFork.release();
            rightFork.release();
            System.out.println("Forks cannot be picked up for " + me);
            return;
            }
        // otherwise eat with both
        System.out.println(me + " is eating Meal Nr. #" + numberOfMealsHad );
        try {
            System.out.println(Thread.currentThread());
            Thread.sleep(Math.abs(randomNumber.nextInt(30)));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Puts down forks
        leftFork.release();
        rightFork.release();

        numberOfMealsHad++;

    }



    private void think() {
        try {
            Thread.sleep(Math.abs(randomNumber.nextInt(30)));
            System.out.println(me + "thinks");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (numberOfMealsHad <= numberOfMealsAvailable) {
            think();
            tryEating();
        }


    }
}
