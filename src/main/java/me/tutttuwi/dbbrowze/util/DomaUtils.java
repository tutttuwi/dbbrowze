package me.tutttuwi.dbbrowze.util;

import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.data.domain.Pageable;

// TODO: 要実装
/**
 * Doma関連ユーティリティ.
 */
public class DomaUtils {

  /**
   * SearchOptionsを作成して返します.
   *
   * @return
   */
  public static SelectOptions createSelectOptions() {
    return SelectOptions.get();
  }

  /**
   * SearchOptionsを作成して返します.
   *
   * @param pageable Pageable
   * @return
   */
  public static SelectOptions createSelectOptions(Pageable pageable) {
    int page = pageable.getPageNumber();
    int perpage = pageable.getPageSize();
    return createSelectOptions(page, perpage);
  }

  /**
   * SearchOptionsを作成して返します.
   *
   * @param page int
   * @param perpage int
   * @return
   */
  public static SelectOptions createSelectOptions(int page, int perpage) {
    int offset = (page - 1) * perpage;
    return SelectOptions.get().offset(offset).limit(perpage);
  }
}
