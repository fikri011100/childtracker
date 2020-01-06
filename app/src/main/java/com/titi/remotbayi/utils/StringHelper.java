package com.titi.remotbayi.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class StringHelper {

  public static String pattern2 = "###,###";

  public static String convertFormatPrice(long priceValue, String pattern) {
    try {
      DecimalFormat decimalFormat = new DecimalFormat(pattern);
      decimalFormat.applyPattern(pattern);
      return "Rp " + decimalFormat.format(priceValue);
    } catch (Exception e) {
      return "Rp 0";
    }
  }

  public static String convertFormatPrice(String priceValue) {
    return convertFormatPrice(Long.parseLong(priceValue), pattern2);
  }

  public static String convertFormatPriceWithoutRp(long priceValue, String pattern) {
    try {
      DecimalFormat decimalFormat = new DecimalFormat(pattern);
      decimalFormat.applyPattern(pattern);
      return "" + decimalFormat.format(priceValue);
    } catch (Exception e) {
      return "0";
    }
  }

  public static String convertFormatPriceWithoutRp(String priceValue) {
    return convertFormatPriceWithoutRp(Long.parseLong(priceValue), pattern2);
  }

  public static String money(String val) {
    if (val != null ) {
      return "Rp. " + NumberFormat
          .getNumberInstance(Locale.US).format(Integer.parseInt(val)).replace(",", ".");
    } else {
      return "Rp. 0";
    }

  }

  public static String unMoney(String val) {
    String set1 = val.replace("Rp. ", "");
    String set2 = set1.replace(".", "");
    return set2;
  }

  public static String formatDate(String value) {
    return formatingDateFromString("yyyy-MM-dd HH:mm:ss", " dd MMM yyyy", value);
  }

  public static String formatDateWithTime(String value) {
    return formatingDateFromString("yyyy-MM-dd HH:mm:ss", "dd MMM yyyy 'at' HH:mm", value);
  }

  public static String formatDateSimpleDate(Boolean withYear, String value) {
    if (withYear) {
      //become 01 Feb 2017
      return formatingDateFromString("yyyy-MM-dd HH:mm:ss", "dd MMM yyyy", value);
    } else {
      //become 01 Feb
      return formatingDateFromString("yyyy-MM-dd HH:mm:ss", "dd MMM", value);
    }
  }

  public static String formatDateSplitter(String value) {
    return formatingDateFromString("yyyy-MM-dd HH:mm:ss", "yyyyMMdd", value);
  }

  public static String formatingDateFromString(String fromFormat, String toFormat,
      String stringDate) {
    //yyyy-MM-dd'T'HH:mm:ss.SSSZ
    SimpleDateFormat format = new SimpleDateFormat(fromFormat, Locale.getDefault());
    try {
      Date newDate = format.parse(stringDate);
      format = new SimpleDateFormat(toFormat, Locale.getDefault());
      return format.format(newDate);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return stringDate;
  }

  public static String initialText(String text) {
    String initialText = "";

    if (text != null) {
      String[] imageText = text.trim().split(" ");

      if (imageText.length > 0 && !imageText[0].isEmpty()) {
        initialText = imageText[0].substring(0, 1).toUpperCase();
        if (imageText.length > 1) {
          // prevent error when the index 1 of imageText is null
          if (!imageText[1].isEmpty()) {
            initialText += imageText[1].substring(0, 1).toUpperCase();
          }
        } else {
          // prevent error when the index 0 of imageText just only has 1 character of text
          if (imageText[0].length() > 1) {
            initialText += imageText[0].substring(1, 2).toLowerCase();
          }

        }

      }
    }
    return initialText;
  }

}
