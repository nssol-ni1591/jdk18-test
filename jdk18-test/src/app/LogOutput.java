package app;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.inject.Instance;
import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;
import javax.inject.Inject;

import util.logging.StdErr;
import util.logging.StdOut;

public class LogOutput {

	@Inject private Logger log;
	@Inject @StdOut private Logger out;
	@Inject @StdErr private Logger err;

	public LogOutput() {
	}
	
	public void run() {

		log.log(Level.INFO, "StandardOutput.run: start ...");

		// ログ出力
		out.finest("ログ出力テスト： finest");
		out.fine("ログ出力テスト： fine");
		out.info("ログ出力テスト： info");
		out.warning("ログ出力テスト： warning");
		out.severe("ログ出力テスト： severe");
		
		// ログ出力
		err.finest("エラー出力テスト： finest");
		err.fine("エラー出力テスト： fine");
		err.info("エラー出力テスト： info");
		err.warning("エラー出力テスト： warning");
		err.severe("エラー出力テスト： severe");

		log.log(Level.INFO, "StandardOutput.run: end.");
	}

	public static void main(String[] args) {
		try(SeContainer container = SeContainerInitializer.newInstance().initialize()) {
	        // start the container, retrieve a bean and do work with it
			Instance<LogOutput> instance = container.select(LogOutput.class);
			LogOutput application = instance.get();
			application.run();
	    }
		catch (Exception ex) {
			System.err.println(ex);
		}
	}
}
