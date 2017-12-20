package shit.helper.scheduler;

import shit.helper.ShitSchedulerHelper.Scheduler;

/**
 * 新线程执行
 * @author GongTengPangYi
 *
 */
public class NewThreadScheduler implements Scheduler{

	@Override
	public void execute(Runnable runnable) {
		new Thread(runnable).start();
	}

}
