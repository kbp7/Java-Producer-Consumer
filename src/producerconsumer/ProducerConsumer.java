package producerconsumer;
public class ProducerConsumer
{
    public static void main(String[] args) throws InterruptedException
    {
        //System.out.println("Trial 1: Normal Operation ----------------- ");
        ProduceAndConsume pc1 = new ProduceAndConsume();
        Thread thread1 = new Thread(() -> {
            try
            {
                pc1.produce();
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }
        });
 
        // Create consumer thread
        Thread thread2 = new Thread(() -> {
            try
            {
                pc1.consume();
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }
        });
 
        thread1.start();
        thread2.start();
        //thread1.join();
        //thread2.join();
        
    }
    public static class ProduceAndConsume
    {
        int cap = 5;
        int[] array = new int[cap];
        String status = "EMPTY";
        int elements = 0;
        int value = 0;
        int index = 0;
        int first = 0;
        int last = 0;
        
        public void produce() throws InterruptedException
        {
            //initialize array elements to indicate empty
            array[0] = -1;
            array[1] = -1;
            array[2] = -1;
            while (true)
            {
                synchronized (this)
                {
                    while(elements==cap) {
                        //exceeded cap
                        status="FULL";
                        System.out.println(status);
                        wait();
                    }
                    array[last++] = value;
                    if(last==cap) {
                        last = last % cap; //wraparound
                    }
                    System.out.println("Produced: " + value++);
                    ++elements;
                    //System.out.println("Elements: " + ++elements);
                    
                    notify();
                    Thread.sleep(500);
                    
                }
            }
        }
 
        public void consume() throws InterruptedException
        {
            //System.out.println("Consuming...");
            while (true)
            {
                synchronized (this)
                {
                    while(elements==0) {
                        System.out.println("EMPTY");
                        status = "EMPTY";
                        wait();
                    }
                    System.out.println("Consumed: " + array[first]);
                    --elements;
                    //System.out.println("Elements: " + --elements);
                    array[first++] = -1;
                    if(first==cap) {
                        first = first % cap; //wraparound
                    }
                    notify();
                    Thread.sleep(500);
                }
            }
        }
    }
}
