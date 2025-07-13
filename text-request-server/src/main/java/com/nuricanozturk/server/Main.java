package com.nuricanozturk.server;


import com.karandev.io.util.console.CommandLineArgs;

public class Main {
  public static void main(String[] args) {
    CommandLineArgs.checkLengthEquals(4, args.length, "./app <address> <generator_port> <port> <try_count>");

    try {
      final var address = "%s.255".formatted(args[0]);
      final var generatorPort = Integer.parseInt(args[1]);
      final var port = Integer.parseInt(args[2]);
      final var tryCount = Integer.parseInt(args[3]);

      final var infoClient = new InfoClient(address, generatorPort, port, tryCount);
      infoClient.run();
    } catch (final NumberFormatException e) {
      System.err.println("Invalid port number: " + e.getMessage());
    } catch (final Throwable t) {
      System.err.println("Error: " + t.getMessage());
    }
  }
}