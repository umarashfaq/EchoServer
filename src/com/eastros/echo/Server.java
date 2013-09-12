package com.eastros.echo;

import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {
	private int port;
	private ServerSocket s;
	private boolean running = true;
	
	public Server(int port) {
		this.port = port;
	}

	@Override
	public void run() {
		System.out.println("Server starting up ...");		
		try {
			s = new ServerSocket(port);
			System.out.println("Server listening on port "+port);
			
			while ( running ) {
				Socket cs = s.accept();
				(new Thread(new ClientSocketProcessor(cs, this))).start();
			}
			
		} catch ( Exception e ) {
			if ( running ) {
				System.out.println(e);
			}
		}
	}
	
	public void shutdown() {
		System.out.println("Closing the loop ...");
		running = false;
		
		System.out.println("Closing opened resources ...");
		try { s.close(); } catch(Exception e) {}
		
		System.out.println("Interrupting the thread ...");
		this.interrupt();
		
		System.out.println("Shutdown is complete");
	}
}
