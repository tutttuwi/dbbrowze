package me.tutttuwi.dbbrowze.dao;

import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.boot.ConfigAutowireable;
import me.tutttuwi.dbbrowze.entity.AccountHistory;

@ConfigAutowireable
@Dao
public interface AccountHistoryRepository {
  // @Select
  // List<Account> selectAll();

  // @Select
  // AccountHistory selectBySeq(Long seq);

  @Insert
  int insert(AccountHistory accountHistory);

}
