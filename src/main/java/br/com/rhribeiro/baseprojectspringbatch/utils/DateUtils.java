package br.com.rhribeiro.baseprojectspringbatch.utils;

import lombok.extern.log4j.Log4j2;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Renan Ribeiro
 * @date 07/07/21
 */

@Log4j2
public class DateUtils {

    public static Date stringToDate_yyyy_MM_dd__HH_mm_ss(String date) {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date formatted = null;
        try {
            formatted = formato.parse(date);
        } catch (ParseException e) {
            log.error(e.getMessage());
        }
        return formatted;
    }
}
