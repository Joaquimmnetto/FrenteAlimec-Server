package br.alimec.server.main;

import java.awt.SecondaryLoop;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import br.alimec.server.commands.Command;

public class Log {

	private PrintStream out;
	private PrintStream err;
	private PrintStream file;

	private static Log standardLog;

	public static Log getStandardLog() {
		if (standardLog == null) {
			standardLog = new Log(System.out, System.err);
		}
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
		out.println(message);
		if (file != null) {
			file.println(message);
		}
	}

	public synchronized void printlnErr(String message) {
		err.println(message);
		if (file != null) {
			file.println(message);
		}
	}

	public synchronized void printException(Exception e, Command c) {
		if (c != null) {
			printlnErr("Erro enquanto executando: " + c.toString());
			printlnErr("");
		}

		e.printStackTrace(getErrorWriter());
		if(file != null){
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

}
