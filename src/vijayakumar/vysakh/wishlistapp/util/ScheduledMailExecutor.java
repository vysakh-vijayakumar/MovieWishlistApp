package vijayakumar.vysakh.wishlistapp.util;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledMailExecutor {
	
	public static void executeMail() {
		ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
		WeeklyMailThread task = new WeeklyMailThread();
		service.scheduleAtFixedRate(task, 0, 1, TimeUnit.HOURS);   			// executing the thread periodically on the basic of TimeUnit
	}
}
