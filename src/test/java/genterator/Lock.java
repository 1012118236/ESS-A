package genterator;

import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * @author shenjiang
 * @Description:
 * @Date: 2019/3/23 10:32
 */
public interface Lock {
    void lock() throws InterruptedException;

    void lock(long mills)throws InterruptedException,TimeoutException;

    void unlock();

    List<Thread> getBlockedThreads();
}
