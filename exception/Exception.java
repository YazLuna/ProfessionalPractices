package exception;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class Exception {
	public boolean log(Throwable exception) {
		boolean wrote = false;
		try {
			Files.createFile(Paths.get("log.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			BufferedWriter bufferedWriter =
					new BufferedWriter(new FileWriter("log.txt", true));
			bufferedWriter.write(exception.getMessage());
			bufferedWriter.newLine();
			for(StackTraceElement line: exception.getStackTrace()){
				bufferedWriter.write(String.valueOf(line));
				bufferedWriter.newLine();
			}
			bufferedWriter.newLine();
			bufferedWriter.flush();
			bufferedWriter.close();
			wrote = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return wrote;
	}
}
