package genterator;

import java.util.concurrent.TimeUnit;

/**
 * @author shenjiang
 * @Description:
 * @Date: 2019/3/20 16:48
 */
public class EventClient {
    public static void main(String[] args) {
        final EventQueue eventQueue = new EventQueue();

        new Thread(()->{
          for(;;){
              eventQueue.offer(new EventQueue.Event());
          }
        },"Producer").start();


        new Thread(()->{
            for(;;){
                eventQueue.take();
                try {
                    TimeUnit.MINUTES.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"Consumer").start();
    }
}
