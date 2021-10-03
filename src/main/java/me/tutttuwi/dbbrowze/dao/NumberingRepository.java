package me.tutttuwi.dbbrowze.dao;

import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.SelectOptions;
import me.tutttuwi.dbbrowze.entity.Numbering;

@ConfigAutowireable
@Dao
public interface NumberingRepository {

    @Select
    Numbering selectByNumKey(String numKey, SelectOptions options);

    @Insert
    int insert(Numbering numbering);

    @Update
    int update(Numbering numbering);
}
