package com.eastros.echo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientSocketProcessor implements Runnable {
	private Server server;
	private Socket socket;
	private PrintWriter writer;
	private BufferedReader reader;
	
	public ClientSocketProcessor(Socket socket, Server server) {
		this.socket = socket;
		this.server = server;
	}

	@Override
	public void run() {
		try {
			System.out.println("Setting up processor for client");
			writer = new PrintWriter(socket.getOutputStream(), true);
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			while ( true ) {
				String line = reader.readLine();
				System.out.println("Client says: "+line);
				if ( "exit".equals(line) ) {
					server.shutdown();
				} else {
					writer.println(line);
				}				
			}
			
		} catch ( Exception e ) {
			throw new RuntimeException("An error occurred while processing client socket", e);
		} finally {
			try {writer.close(); reader.close(); socket.close(); } catch (Exception e) {};
		}
	}
}
