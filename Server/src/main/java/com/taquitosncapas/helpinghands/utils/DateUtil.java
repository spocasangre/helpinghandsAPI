package com.taquitosncapas.helpinghands.utils;

import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class DateUtil {

    private static final String FORMAT_DATE = "dd/MM/yyyy";
    static DateTimeFormatter date = DateTimeFormatter.ofPattern(FORMAT_DATE);

    public LocalDate convertDate(String dateString) {

        Boolean dateValid=isValid(dateString);

        if(dateValid){
            LocalDate convertDate = LocalDate.parse(dateString, date);
            return convertDate;
        }else{
            return null;
        }

    }

    public static boolean isValid(String strDate) {
        if(strDate==null || strDate.isEmpty()){
            return false;
        }
        DateFormat sdf = new SimpleDateFormat(FORMAT_DATE);
        sdf.setLenient(false);
        try {
            sdf.parse(strDate);
        } catch (ParseException e) {
            return false;
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}
