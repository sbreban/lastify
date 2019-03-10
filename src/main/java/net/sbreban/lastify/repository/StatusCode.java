package net.sbreban.lastify.repository;

public enum StatusCode {
  OK(200), TooManyRequests(429);

  private final int code;

  StatusCode(int code) {
    this.code = code;
  }

  public static boolean isOK(int statusCode) {
    return statusCode == OK.code;
  }

  public static boolean isTooManyRequests(int statusCode) {
    return statusCode == TooManyRequests.code;
  }
}
