package cn.gtmap.landsale.bank.util;

import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author jiff on 14/12/21.
 */
public class ThreadPool {
    private static final ExecutorService EXECUTOR =
            new ThreadPoolExecutor(5, 2048, 60L, TimeUnit.SECONDS, new SynchronousQueue());

    public static void execute(Runnable task) {
        EXECUTOR.execute(task);
    }

    public static Thread getThread(String threadName){
        ThreadPoolExecutor threadPoolExecutor=(ThreadPoolExecutor) EXECUTOR;
        for (Iterator iter = threadPoolExecutor.getQueue().iterator(); iter.hasNext();) {
            Object obj = (Object)iter.next();
            if (obj instanceof  Thread){
                Thread cthread=(Thread)obj;
                if (cthread.getName().equals(threadName)){
                    return cthread;
                }
            }
        }
        return null;
    }


}
