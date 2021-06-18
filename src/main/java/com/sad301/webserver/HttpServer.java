package com.sad301.webserver;

import spark.*;

@Deprecated
public class HttpServer {

  private final int port;
  private final String root;
  private Service service;

  public HttpServer(int port, String root) {
    this.port = port;
    this.root = root;
  }

  public void start() {
    service = Service.ignite()
      .externalStaticFileLocation(root)
      .port(port);
  }

  public void stop() {
    service.stop();
  }

}
