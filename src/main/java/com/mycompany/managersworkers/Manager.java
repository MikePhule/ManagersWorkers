
package com.mycompany.managersworkers;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author student
 */
public class Manager implements Runnable{
    ArrayList<Worker> workers = new ArrayList<>();
    ConcurrentLinkedQueue<Object> jobs = new ConcurrentLinkedQueue<>();
    private int workersCount;
    Thread self;
    private boolean isNextJob = true;
    Object monitor = new Object();

    public Manager(int workersCount) {
        self = new Thread(this);
        
        this.workersCount = workersCount;
        for (int i = 0; i < workersCount; i++) {
            workers.add(new Worker(monitor));
        }
    }
    
    

    public Manager() {
        
        this(Runtime.getRuntime().availableProcessors());
    }
    
    public void submit(Object job) {
        jobs.add(job);
    }

    @Override
    public void run() {
        System.out.println("Manager stared your project");
        for (Worker worker : workers) {
            worker.start();
        }

        while (isNextJob) {
            for (Object job : jobs) {
                jobs.remove(job);
                synchronized(monitor) {
                    monitor.notify();
                }
                if (jobs.isEmpty()) {
                    try {
                        Thread.sleep(5);
                        System.out.println("All jobs are done!");
                    } catch (InterruptedException ex) {
                        
                    }
                    
                }
//                Уточнить зачем это здесь!!!!!
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ex) {
                    
                }
            }
            
        }
    }
    
    public void start() throws InterruptedException {
        
        self.start();
    }
    
    public void join() throws InterruptedException {
        if (jobs.isEmpty()) {
            isNextJob = false;
            for (Worker worker : workers) {
                worker.isNextJob = false;
                worker.isRunning = false;
            }

            synchronized(monitor) {
                    monitor.notifyAll();
                }

            for (Worker worker : workers) {

                worker.join();

            }
            System.out.println("Have a nice day!;)");
            self.join();
        } else {
            Thread.sleep(10);
            this.join();
        }
    }
    
    
    
}
