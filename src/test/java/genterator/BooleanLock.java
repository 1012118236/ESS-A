package genterator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * @author shenjiang
 * @Description:
 * @Date: 2019/3/23 10:31
 */
public class BooleanLock implements Lock{
    //当前拥有锁的线程
    private Thread currentThread;
    //false 无线程获得锁  true 又线程获得锁
    private boolean locked = false;
    //存储获得当前线程进入了阻塞状态的线程集合
    private final List<Thread> blockedList = new ArrayList<>();

    @Override
    public void lock() throws InterruptedException {
        synchronized (this){
            while(locked){
                blockedList.add(Thread.currentThread());
                this.wait();
            }
            blockedList.remove(Thread.currentThread());
            this.locked = true;
            this.currentThread = Thread.currentThread();
        }
    }

    @Override
    public void lock(long mills) throws InterruptedException, TimeoutException {
        synchronized (this){
            if(mills <= 0 ){
                this.lock();
            }else{
                long remainingMills = mills;
//                long endMills =
            }
        }
    }

    @Override
    public void unlock() {

    }

    @Override
    public List<Thread> getBlockedThreads() {
        return null;
    }
}
