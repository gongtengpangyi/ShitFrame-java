package shit.helper.scheduler;

import shit.helper.ShitSchedulerHelper.Scheduler;

/**
 * 当前线程执行
 * @author GongTengPangYi
 *
 */
public class ImmediateScheduler implements Scheduler {

	@Override
	public void execute(Runnable runnable) {
		runnable.run();
	}

}
