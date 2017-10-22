package shit.helper;

import shit.helper.scheduler.ImmediateScheduler;
import shit.helper.scheduler.NewThreadScheduler;

/**
 * 线程调度控制，用于操作Runnable实现类具体的运行线程的控制
 * 
 * @author GongTengPangYi
 *
 */
public class ShitSchedulerHelper {
	
	/**
	 * 获取当前线程
	 * @return
	 */
	public static Scheduler immediate() {
		return new ImmediateScheduler();
	}
	
	/**
	 * 获取新线程
	 * @return
	 */
	public static Scheduler newThread() {
		return new NewThreadScheduler();
	}
	

	/**
	 * 线程接口
	 * @author Fang
	 *
	 */
	public interface Scheduler {
		
		/**
		 * 执行Runnable
		 * @param runnable
		 */
		public void execute(Runnable runnable);
	}
}
