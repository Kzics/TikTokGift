// 
// Decompiled by Procyon v0.5.36
// 

package pl.jordii.mctiktokgiftactions.util;

import org.bukkit.plugin.Plugin;
import pl.jordii.mctiktokgiftactions.McTiktokGiftActions;
import org.bukkit.Bukkit;
import java.util.concurrent.CountDownLatch;

public class ServerMainThread
{
    public static class WaitForCompletion<T>
    {
        private CountDownLatch startSignal;
        private CountDownLatch doneSignal;
        private Retrievable<T> retrievable;
        private Object result;
        
        private WaitForCompletion(final Retrievable<T> retrievable) {
            this.retrievable = retrievable;
            this.result = null;
            this.startSignal = new CountDownLatch(1);
            this.doneSignal = new CountDownLatch(1);
        }
        
        public static <T> T result(final Retrievable<T> retrievable) {
            final WaitForCompletion<T> task = new WaitForCompletion<T>(retrievable);
            return (T)task.catchResult();
        }
        
        private Object catchResult() {
            Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)McTiktokGiftActions.getPlugin((Class)McTiktokGiftActions.class), () -> {
                try {
                    this.startSignal.await();
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                this.result = this.retrievable.retrieve();
                this.doneSignal.countDown();
                return;
            });
            this.startSignal.countDown();
            try {
                this.doneSignal.await();
            }
            catch (InterruptedException e2) {
                e2.printStackTrace();
            }
            return this.result;
        }
    }
    
    public static class RunParallel
    {
        public static void run(final Runnable runnable) {
            if (Bukkit.isPrimaryThread()) {
                runnable.run();
                return;
            }
            Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)McTiktokGiftActions.getPlugin((Class)McTiktokGiftActions.class), runnable);
        }
    }
}
