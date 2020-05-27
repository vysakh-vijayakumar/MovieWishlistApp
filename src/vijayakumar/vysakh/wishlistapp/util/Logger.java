package vijayakumar.vysakh.wishlistapp.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import vijayakumar.vysakh.wishlistapp.data.Constants;

public class Logger {
	private String path;
	private boolean logToConsole;
	private static Logger obj = null;
	private Logger() {
		this.path = "log/log.txt";
	}
	
	public static synchronized Logger getInstance() {		// to avoid race condition, that to avoid two threads executing the methd simultaneously
		if(obj == null)
			obj = new Logger();
		return obj;
	}
	
	public void SetLogtoConsole(boolean option) {
		this.logToConsole = option;
	}
	public void log(String data) {
		BufferedWriter bw = null;
		
		try {
			bw = new BufferedWriter(new FileWriter(path, true));
			Date dt = new Date();
			bw.write(dt+":"+data);
			if(logToConsole == true)
				System.out.println("Logger:"+data);
			bw.newLine();
		} 
		catch (IOException e) {
			
			e.printStackTrace();
		}
		finally
		{
			if(obj != null)
				try {
					bw.close();
				} catch (IOException e) {

					e.printStackTrace();
				}
		}
	}
	
	public void emptyLog() {									// clear log file before new execution.
		
		BufferedWriter bw = null;
		
		try {
			bw = new BufferedWriter(new FileWriter(path));
			bw.write("");
			bw.newLine();
		} catch (IOException e) {

			e.printStackTrace();
		}finally {
			if(bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void addLine() {

		BufferedWriter bw = null;
		
		try {
			bw = new BufferedWriter(new FileWriter(path,true));
			bw.write("------------------------------------------------------------------------------------");
			bw.newLine();
		} catch (IOException e) {

			e.printStackTrace();
		}finally {
			if(bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
