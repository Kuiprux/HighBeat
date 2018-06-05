package cf.kuiprux.spbeat.logging;

import java.util.logging.Level;
import java.util.logging.Logger;

//Logger�� �����ϰ� ������ �α׸� ���Ϸ� ���
public class LogManager {
	
	public static final String LOG_DIRECTORY = "logs";
	
	private Logger logger;

	public LogManager(String name) {
		this.logger = Logger.getLogger(name);
	}
	
	public Logger getLogger() {
		return logger;
	}

	public void log(Level level, String msg, Throwable thrown) {
		getLogger().log(level, msg, thrown);
	}

}
