package com.nuricanozturk.generator;

import java.util.Objects;

public class ServerInfo {
  private final String host;
  private final int port;
  private boolean active;

  public ServerInfo(String host, int port) {
    this.host = host;
    this.port = port;
    active = true;
  }

  public String getHost() {
    return host;
  }

  public int getPort() {
    return port;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  @Override
  public String toString() {
    return "%s:%d".formatted(host, port);
  }

  @Override
  public boolean equals(Object other) {
    return other instanceof ServerInfo si && si.host.equals(host) && si.port == port;
  }

  @Override
  public int hashCode() {
    return Objects.hash(host, port);
  }
}
