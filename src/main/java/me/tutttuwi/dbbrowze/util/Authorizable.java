package me.tutttuwi.dbbrowze.util;

public interface Authorizable {

  /**
   * 認可を必要とする機能の場合はtrueを返します.
   *
   * @return
   */
  boolean authorityRequired();
}
