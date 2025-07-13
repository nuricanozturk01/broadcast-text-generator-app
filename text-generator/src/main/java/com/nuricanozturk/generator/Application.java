package com.nuricanozturk.generator;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.karandev.io.util.console.CommandLineArgs.checkLengthEquals;
import static com.nuricanozturk.generator.ServerUtil.SERVERS;

public class Application {


  public static void run(final String[] args) {
    checkLengthEquals(2, args.length, "./app <port> <size>");

    try {
      final var threadPool = Executors.newScheduledThreadPool(2);
      final var serverReceiver = new ServerReceiver(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
      final var textGenerator = new TextGenerator();

      ServerUtil.THREAD_POOL.execute(serverReceiver::run);
      threadPool.scheduleAtFixedRate(textGenerator::run, 0, 500, TimeUnit.MILLISECONDS);
      threadPool.scheduleAtFixedRate(Application::removeInactiveSchedulers, 0, 100, TimeUnit.MILLISECONDS);

    } catch (final NumberFormatException e) {
      System.err.println("Invalid port number: " + e.getMessage());
    } catch (final Throwable e) {
      System.err.println("Error: " + e.getMessage());
    }
  }

  private static void removeInactiveSchedulers() {
    synchronized (ServerUtil.SYNC_LOCK) {
      final var inactives = SERVERS.stream().filter(server -> !server.isActive()).toList();
      if (!inactives.isEmpty()) {
        inactives.forEach(si -> System.err.println(si + " is inactive"));
      }
      SERVERS = SERVERS.stream().filter(ServerInfo::isActive).collect(Collectors.toSet());
    }
  }
}
