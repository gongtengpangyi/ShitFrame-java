package shit.socket;

public abstract class RunnableLifeCycle implements LifeCycle, Runnable {
	
	/**
	 * 线程
	 */
	Thread threadSelf;

	/**
	 * 启停标志
	 */
	private boolean flag = false;
	
	public RunnableLifeCycle() {
		super();
		init();
	}

	@Override
	public void run() {
		while (flag) {
			runInternal();
		}
	}

	@Override
	public void close() {
		flag = false;
		closeInternal();
	}

	@Override
	public void start() {
		flag = true;
		startInternal();
		threadSelf.start();
	}

	@Override
	public void init() {
		threadSelf = new Thread(this);
		initInternal();
	}

	@Override
	public void stop() {
		flag = false;
		stopInternal();
	}

	protected abstract void startInternal();

	protected abstract void stopInternal();

	protected abstract void closeInternal();

	protected abstract void initInternal();

	protected abstract void runInternal();
}
