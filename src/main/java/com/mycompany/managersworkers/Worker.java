
package com.mycompany.managersworkers;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author student
 */
public class Worker implements Runnable {
    private Thread worker;
    private static int num;
    private static int jobsDone = 1;
    private Object monitor;
    boolean isRunning = true;
    boolean isNextJob = true;
    

    public Worker(Object m) {
        worker = new Thread(this);
        this.monitor = m;
        worker.setName("Worker-" + num);
        num++;
    }

    @Override
    public void run() {
        
        System.out.println(Thread.currentThread().getName() + " is here");
        
        while (isRunning) {
            synchronized(monitor) {
                try {
                    monitor.wait();
                    if (isNextJob) {
                        System.out.println(Thread.currentThread().getName() + " job is done " + jobsDone + " times.");
                        jobsDone++;
                    }


                } catch (InterruptedException ex) {
                    Logger.getLogger(Worker.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    public void start() {
        worker.start();
    }
    
    public void join() throws InterruptedException {
        worker.join();
    }
    
    
}
