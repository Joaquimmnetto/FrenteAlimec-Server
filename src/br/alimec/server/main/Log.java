package br.alimec.server.main;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import br.alimec.server.commands.Command;

public class Log {

	private PrintStream out;
	private PrintStream err;
	private PrintStream file;

	private static Log standardLog;

	private static final String LOG_FILE_NAME_PREFIX = "Log_";
	private static final String LOG_FILE_NAME_SUFIX = ".log";

	public static void generateNewStandardLog() {

		try {
			Calendar current = Calendar.getInstance();
			String logFileName = LOG_FILE_NAME_PREFIX
					+ current.get(Calendar.DATE) + "-"
					+ (current.get(Calendar.MONTH) + 1) + "-"
					+ current.get(Calendar.YEAR) + "_"
					+ current.get(Calendar.HOUR_OF_DAY) + "-"
					+ (current.get(Calendar.MINUTE)) + "-"
					+ current.get(Calendar.SECOND) + LOG_FILE_NAME_SUFIX;
			standardLog = new Log(System.out, System.err, new PrintStream(
					logFileName));
		} catch (FileNotFoundException e) {
			// TODO: vish, e ai? Stack trace no stdout mesmo?
			e.printStackTrace();
		}
	}

	public static Log getStandardLog() {
		return standardLog;
	}

	public Log(PrintStream mainOut, PrintStream mainError) {
		this.out = mainOut;
		this.err = mainError;

	}

	public Log(PrintStream mainOut, PrintStream mainError, PrintStream secondary)
			throws FileNotFoundException {
		this.out = mainOut;
		this.err = mainError;
		if (secondary != null) {
			this.file = secondary;

		}
	}

	public synchronized void println(String message) {
		message = getTimestamp() + message;
		out.println(message);
		if (file != null) {
			file.println(message);
		}

	}

	public synchronized void printlnErr(String message) {
		message = getTimestamp() + message;
		err.println(message);
		if (file != null) {
			file.println(message);
		}
	}

	public synchronized void printException(Exception e) {
		printException(e, null);
	}

	public synchronized void printException(Exception e, Command c) {
		if (c != null) {
			printlnErr("Erro enquanto executando: " + c.toString());
			printlnErr("");
		}
		e.printStackTrace(getErrorWriter());
		if (file != null) {
			e.printStackTrace(getSecondaryWriter());
		}

	}

	public PrintStream getMainPrintStream() {
		return out;
	}

	public PrintStream getErrorWriter() {
		return err;
	}

	public PrintStream getSecondaryWriter() {
		return file;
	}

	private String getTimestamp() {

		Date current = new Date();

		StringBuilder timestamp = new StringBuilder();

		timestamp.append('[').append(current).append(']');
		timestamp.append('\t');

		return timestamp.toString();
	}

}
