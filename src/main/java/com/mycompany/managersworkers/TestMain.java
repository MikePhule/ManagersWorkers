
package com.mycompany.managersworkers;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 * @author student
 */
public class TestMain {
    public static void main(String[] args) throws InterruptedException {
        Manager mgr = new Manager();
        ConcurrentLinkedQueue<Object> jobs = new ConcurrentLinkedQueue<>();
        
        mgr.start();
        

        for (int i = 0; i < 50; i++) {
            mgr.submit("");

        }

        Thread.sleep(6000);
        for (int i = 0; i < 10; i++) {
            mgr.submit("");
        }
//        Thread.sleep(5000);
        mgr.join();
    }
}
