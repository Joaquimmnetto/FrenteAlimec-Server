package br.alimec.server.main;

import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;

import br.alimec.server.commands.Command;

public class Log {

	private PrintWriter out;
	private PrintWriter err;
	private PrintWriter file;

	public Log(OutputStream mainOut, OutputStream mainError) {
		this.out = new PrintWriter(mainOut);

	}

	public Log(OutputStream mainOut, OutputStream mainError,
			OutputStream secondary) throws FileNotFoundException {
		this.out = new PrintWriter(mainOut);
		this.err = new PrintWriter(mainError);
		if (secondary != null) {
			this.file = new PrintWriter(secondary);
		}
	}

	public void println(String message) {
		out.println(message);
		if (file != null) {
			file.println(message);
		}
	}

	public void printlnErr(String message) {
		err.println(message);
		if (file != null) {
			file.println(message);
		}
	}

	public void printException(Exception e, Command c) {
		printlnErr("Erro enquanto executando: " + c.toString());
		printlnErr("");
		e.printStackTrace(getErrorWriter());
		e.printStackTrace(getSecondaryWriter());
	}

	public PrintWriter getMainPrintWriter() {
		return out;
	}

	public PrintWriter getErrorWriter() {
		return err;
	}

	public PrintWriter getSecondaryWriter() {
		return file;
	}

}
