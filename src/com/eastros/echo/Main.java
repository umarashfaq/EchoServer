package com.eastros.echo;

public class Main {
	public static void main(String[] args) {
		Server server = new Server(4444);
		server.run();
	}
}
