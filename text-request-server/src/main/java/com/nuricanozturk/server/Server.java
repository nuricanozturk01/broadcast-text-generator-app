package com.nuricanozturk.server;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
  private final int port;
  private final ExecutorService threadPool;

  public Server(int port) {
    this.port = port;
    threadPool = Executors.newCachedThreadPool();
  }

  public void run() {
    try (final var serverSocket = new ServerSocket(port)) {
      while (true) {
        final var clientSocket = serverSocket.accept();
        threadPool.execute(() -> handleClient(clientSocket));
      }
    } catch (final IOException e) {
      System.err.println("Could not listen on port: " + port);
    }
  }

  private void handleClient(final Socket clientSocket) {
    try (clientSocket; final var is = clientSocket.getInputStream();
         final var dis = new DataInputStream(is)) {
      final var length = dis.readInt();
      final var buffer = new byte[length];
      dis.readFully(buffer);
      final var text = new String(buffer, StandardCharsets.UTF_8);
      System.out.println("Text is: " + text);
    } catch (final IOException e) {
      System.err.println("I/O error: " + e.getMessage());
    } catch (final Throwable t) {
      System.err.println("Unexpected error: " + t.getMessage());
    }
  }
}
