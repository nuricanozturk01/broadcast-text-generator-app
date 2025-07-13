package com.nuricanozturk.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;

public record InfoClient(String address, int generatorPort, int port, int tryCount) {

  public void run() {
    try (final var ds = new DatagramSocket()) {
      final var buffer = ByteBuffer.allocate(Integer.BYTES).putInt(port).array();
      final var packet = new DatagramPacket(buffer, 0, buffer.length, InetAddress.getByName(address), generatorPort);

      for (int i = 0; i < tryCount; i++) {
        ds.send(packet);
      }

      final var server = new Server(port);
      server.run();
    } catch (IOException e) {
      System.err.println("IO Exception: " + e.getMessage());
    }
  }
}
