package com.nuricanozturk.generator;

import org.csystem.util.string.StringUtil;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.random.RandomGenerator;

import static com.nuricanozturk.generator.ServerUtil.SERVERS;
import static com.nuricanozturk.generator.ServerUtil.SYNC_LOCK;
import static com.nuricanozturk.generator.ServerUtil.THREAD_POOL;

public class TextGenerator {
  private final RandomGenerator randomGenerator;


  private static void setInActive(ServerInfo serverInfo) {
    synchronized (SYNC_LOCK) {
      serverInfo.setActive(false);
    }
  }

  public TextGenerator() {
    randomGenerator = new Random();
  }

  public void run() {
    var text = StringUtil.getRandomTextEN(randomGenerator, randomGenerator.nextInt(5, 15));
    System.out.println("Generated Text: " + text);

    synchronized (SYNC_LOCK) {
      SERVERS.stream().filter(ServerInfo::isActive)
              .forEach(serverInfo -> THREAD_POOL.execute(() -> sendTextAsync(serverInfo, text)));
    }
  }

  private void sendTextAsync(final ServerInfo serverInfo, final String text) {
    try (final var socket = new Socket(serverInfo.getHost(), serverInfo.getPort());
         final var os = socket.getOutputStream();
         final var dos = new DataOutputStream(os)) {
      final var bytes = text.getBytes(StandardCharsets.UTF_8);

      dos.writeInt(bytes.length);
      dos.write(bytes);
      dos.flush();
    } catch (final IOException e) {
      System.err.println("IOException: " + e.getMessage());
      setInActive(serverInfo);
    } catch (final Throwable t) {
      System.err.println("Exception: " + t.getMessage());
      setInActive(serverInfo);
    }
  }
}
