package com.cobalt.tictactoe;

import java.security.Principal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

@Slf4j
public class SocketHandshakeHandler extends DefaultHandshakeHandler {

  public static class CustomPrincipal implements Principal {

    private String userID;

    public void setUserID(String userID) {
      this.userID = userID;
    }

    @Override
    public String getName() {
      return userID;
    }
  }

  @Override
  protected Principal determineUser(
      ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
    var allHeaders = request.getHeaders();
    var cookieString = allHeaders.getFirst(HttpHeaders.COOKIE);
    Objects.requireNonNull(cookieString);
    var cookies = getCookies(cookieString);
    CustomPrincipal principal = new CustomPrincipal();
    principal.setUserID(cookies.get("userid"));
    return principal;
  }

  private Map<String, String> getCookies(String cookieString) {
    Map<String, String> cookies = new HashMap<>();
    Arrays.stream(cookieString.split(";"))
        .map(String::trim)
        .forEach(
            kv -> {
              var kvArr = kv.split("=");
              cookies.put(kvArr[0], kvArr[1]);
            });
    return cookies;
  }
}
