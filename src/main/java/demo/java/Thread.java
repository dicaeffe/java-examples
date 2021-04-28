package demo.java;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class Thread {
	public static void main(String[] args) {
		/* A thread can be define with a class extending Thread and overriding the "run" method. */

		/** CREATE a THREAD **/
		MyThread myThread = new MyThread();
		/* The "start" method do not execute the thread but record it in the Thread Scheduler (in the OS or JVM).
		 * Now the thread can be elected for the execution. */
		myThread.start();
		/* Note: the Thread Scheduler defines which thread can be executed on which CPU among those available.
		 * The thread is executed when the Thread Scheduler select it from the queue of threads changing its status from "Ready" to "Running".
		 * The execution will run the "run" method. */

		/** RUNNABLE INTERFACE **/
		/* Is an alternative to the "Thread" class. */
		AlterThread alterThread = new AlterThread();
		java.lang.Thread thread = new java.lang.Thread(alterThread);
		thread.start();

		/** Manage Threads with EXECUTORs **/
		/* Executors are a higher level of management of threads and allows the execution of asynchronous tasks and of pool of threads. */
		exampleOfExecutor();
	}
	
	/** Example of Thread class extending Thread. */
	public static class MyThread extends java.lang.Thread {
		@Override
		public void run(){
			System.out.println("Code of MyThread");
		}
	}

	/** Example of Thread class implementing Runnable. */
	public static class AlterThread implements Runnable {
		public void run(){
			System.out.println("Code of AlterThread");
		}
	}
	
	/** Example of Thread management with an Executor. */
	private static void exampleOfExecutor() {
		/* There are many type of Executors:
		 * 1- newSingleThreadExecutor(): for a single-thread executor.
		 * 		ExecutorService executor = Executors.newSingleThreadExecutor()
		 * 2- newCachedThreadPool(): for a pool of thread that will grow dynamically re-using those previously created.
		 * 3- newFixedThreadPool(): for a foxed size pool of thread that can be re-used by executor.
		 * 		ExecutorService executor = Executors.newFixedThreadPool(2)
		 *  	executor.submit(new Runnable(){...})
		 *  	executor.submit(new Runnable(){...})
		 * 		
		 * 4- newScheduledThreadPool(): for a pool of thread that execute a task periodically or after a fixed time.
		 * 	  The "schedule" method will setup the schedunling time for the task execution.
		 *  	ScheduledExecutorService executor = Executors.newScheduledThreadPool(1)
		 *  	ScheduledFuture<?> future = executor.schedule(new Runnable(){...}, 3, TimeUnit.SECONDS)
		 *  	long timeLeft = 0
		 *  	while((timeLeft = future.getDelay(TimeUnit.MILLISECONDS)) > 0){
		 *  		System.out.printf("Time left before execution: %sms ", timeLeft)
		 *  		System.out.println()
		 *  	} */
		ExecutorService executor = Executors.newSingleThreadExecutor();
		/* Add a thread created with an anonymous class of Runnable interface */
		executor.submit(new Runnable(){
			public void run(){
				String nomeThread = java.lang.Thread.currentThread().getName();
				System.out.println("Executor with only one Thread: " + nomeThread);
			}
		});
		
		try {
			System.out.println("Executor shutdown attempt");
			executor.shutdown();
			executor.awaitTermination(10, TimeUnit.SECONDS);
			
		} catch (InterruptedException e) {
			System.err.println(e.getMessage());
			
		} finally {
			if (!executor.isTerminated()) {
				System.err.println("Normal shutdown not executed on the expected time");
				System.err.println("Invoke the shutdownNow()");
			}
			executor.shutdownNow();
			System.out.println("Shutdown completed");
		}
	}
	
	/** Example of Thread management with an Executor. */
	private static void exampleOfExecutorWithCallable() {
		/* Instead of Runnable, the Callable interface returns a value from the "call" method. */
		ExecutorService executor = Executors.newSingleThreadExecutor();
		Future future = executor.submit(new Callable(){
			public Integer call() throws Exception {
				String threadName = java.lang.Thread.currentThread().getName();
				System.out.println("Executor con un solo Thread utilizzando Callable: " + threadName);
				TimeUnit.SECONDS.sleep(1);
				return 1;
			}
		});
		
		/* The "isDone" method of "future" allows the monitoring of the thread execution. */
		while(!future.isDone()){
			System.out.println("Polling sul Thread: " + future.isDone());
		}
		
		try {
			System.out.println("Il Thread ha completato il suo lavoro: "+future.get());
			
		} catch (InterruptedException e1) {
			e1.printStackTrace();
			
		} catch (ExecutionException e1) {
			e1.printStackTrace();
		}
	}
}
