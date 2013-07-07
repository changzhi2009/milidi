package frontEnd;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

public class Log {
	static boolean debug = true;
	public static void setDebug(boolean b){
		debug = b;
	}
	public static boolean getDebug(){
		return debug;
	}
	static PrintWriter out;
	public static void enableLogs(File file) throws FileNotFoundException{
		out = new PrintWriter(file);
		
	}
	public static void disableLogs() throws IOException{
		out.close();
	}
	public static void log(String s) throws IOException{
		if(debug)
			out.write(s+"\n");
	}
}
