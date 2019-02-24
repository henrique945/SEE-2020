package federate;

import synchronizationPoint.SynchronizationPoint;

public class ShutdownTask implements Runnable {

	private MarsHabitatFederate federate = null;

	public ShutdownTask(MarsHabitatFederate federate) {
		this.federate = federate;
	}

	@Override
	public void run() {
		try {
			waitingForAnnouncement(SynchronizationPoint.MTR_SHUTDOWN);

			federate.getHLAModule().achieveSynchronizationPoint(SynchronizationPoint.MTR_SHUTDOWN);

			waitingForSynchronization(SynchronizationPoint.MTR_SHUTDOWN);

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
		federate.shudownExecution();

	}

	private void waitingForAnnouncement(SynchronizationPoint sp) throws InterruptedException {
		while (!sp.isAnnounced())
			Thread.sleep(10);
	}

	private void waitingForSynchronization(SynchronizationPoint sp) throws InterruptedException {
		while (!sp.federationIsSynchronized())
			Thread.sleep(10);
	}

}
