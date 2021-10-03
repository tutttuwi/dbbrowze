package me.tutttuwi.dbbrowze.util;

import java.sql.Timestamp;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import me.tutttuwi.dbbrowze.entity.Numbering;

@Component
public class AppUtil {

    /**
     * パスワード検証.
     *
     * @param password String
     * @return
     */
    public boolean checkPasswordRequirement(String password) {
        boolean isValid = false;
        return isValid;

    }

    /**
     * 採番テーブルの情報を基に次の番号を採番し、キー文字列を返却する
     *
     * @param numbering
     * @return
     * @throws Throwable
     */
    public String getNextNumStr(Numbering nextNumbering) throws Throwable {
        long nextNum = nextNumbering.getNumIssued();
        String prefixChar = StringUtils.trim(nextNumbering.getPrefixChar());
        String nextNumStr =
                StringUtils.leftPad(String.valueOf(nextNum), nextNumbering.getNumDigits(), "0");
        if (StringUtils.isNotEmpty(prefixChar)) {
            nextNumStr = prefixChar + nextNumStr.substring(prefixChar.length());
        }
        return nextNumStr;
    }

    /**
     * 採番テーブルの情報を基に次の番号を採番する
     *
     * @param numbering
     * @return
     * @throws Throwable
     */
    public Numbering getNextNum(Numbering numbering) throws Throwable {
        long nextNum = numbering.getNumIssued() + 1; // increment
        long maxNum = numbering.getNumMax();
        long minNum = numbering.getNumMin();
        if (nextNum < minNum && nextNum > maxNum) {
            throw new Exception(); // TODO: 採番エラー
        }
        numbering.setNumIssued(nextNum);
        numbering.setUpdateDt(new Timestamp(System.currentTimeMillis()));
        return numbering;
    }

    /**
     * 現在時刻取得<br/>
     * DBやAPIから時刻を取得するケースを鑑みてメソッドをUTILに分ける
     *
     * @return
     */
    public Timestamp getTimeNow() {
        return new Timestamp(System.currentTimeMillis());
    }

}
