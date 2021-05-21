package nguyenkhanh.backend.api.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class AppController {
	public Date stringToDate(String strDate) {
		Date date = null;
		if (isDateValid(strDate, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
			formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
			try {
				date = formatter.parse(strDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
			formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
			try {
				date = formatter.parse(strDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		return date;
	}

	public static boolean isDateValid(String date, String date_format) {
		try {
			DateFormat df = new SimpleDateFormat(date_format);
			df.setLenient(false);
			df.parse(date);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}
}
