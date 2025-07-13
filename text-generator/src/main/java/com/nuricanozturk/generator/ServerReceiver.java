package com.nuricanozturk.generator;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.ByteBuffer;

public record ServerReceiver(int port, int size) {

  public void run() {
    try (final var datagramSocket = new DatagramSocket(port)) {
      System.out.println("Server listening on port " + port);
      while (true) {
        receive(datagramSocket);
      }
    } catch (final IOException e) {
      System.err.println("IOException: " + e.getMessage());
    } catch (final Exception e) {
      System.err.println("Exception: " + e.getMessage());
      System.exit(1);
    }
  }

  private void receive(final DatagramSocket datagramSocket) {
    try {
      final var buffer = new byte[size];
      final var datagramPacket = new DatagramPacket(buffer, size);
      datagramSocket.receive(datagramPacket);

      final var port = ByteBuffer.wrap(datagramPacket.getData(), 0, 4).getInt();
      final var host = datagramPacket.getAddress().getHostAddress();

      synchronized (ServerUtil.SYNC_LOCK) {
        final var si = new ServerInfo(host, port);
        if (!ServerUtil.SERVERS.contains(si)) {
          ServerUtil.SERVERS.add(si);
          System.out.println("Server received host: " + si);
        }
      }
    } catch (IOException e) {
      System.err.println("IOException: " + e.getMessage());
    }
  }
}
