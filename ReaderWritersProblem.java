import java.util.concurrent.Semaphore;
import java.io.*;

class ReaderWritersProblem {

    static Semaphore rsem = new Semaphore(1);
    static Semaphore wsem = new Semaphore(1);
    static int readCount = 0;

    static class Read implements Runnable {
        @Override
        public void run() {
            try {
                
                rsem.acquire();
                readCount++;
                if (readCount == 1) {
                    wsem.acquire();
                }
                rsem.release();

                
                System.out.println(Thread.currentThread().getName() + " is READING");
                Thread.sleep(1500);
                System.out.println(Thread.currentThread().getName() + " has FINISHED READING");

                
                rsem.acquire();
                readCount--;
                if(readCount == 0) {
                    wsem.release();
                }
                rsem.release();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    static class Write implements Runnable {
        @Override
        public void run() {
            try {
                wsem.acquire();
                System.out.println(Thread.currentThread().getName() + " is WRITING");
                Thread.sleep(2500);
                System.out.println(Thread.currentThread().getName() + " has finished WRITING");
                wsem.release();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Read read = new Read();
        Write write = new Write();
        Thread t1 = new Thread(read);
        t1.setName("Process 1");
        Thread t2 = new Thread(read);
        t2.setName("Process 2");
        Thread t3 = new Thread(write);
        t3.setName("Process 3");
        Thread t4 = new Thread(read);
        t4.setName("Process 4");
        Thread t5 = new Thread(write);
        t5.setName("Process 5");
        Thread t6 = new Thread(read);
        t6.setName("Process 6");
        Thread t7 = new Thread(write);
        t7.setName("Process 7");
        t1.start();
        t3.start();
        t2.start();
        t4.start();
        t5.start();
        t6.start();
        t7.start();
    }
}
