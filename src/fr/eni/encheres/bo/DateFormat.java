package fr.eni.encheres.bo;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;


public class DateFormat {

	static final String FORMAT = "uuuu-MM-dd HH:mm";

	static final String FORMAT_SHORT = "uuuu-MM-dd";

	public static String TOFORMAT(LocalDateTime date) {


		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMAT);
		//LocalDateTime dateTime = LocalDateTime.of(date);
		String formattedDateTime = date.format(formatter); // "1986-04-08 12:30"

		return formattedDateTime;


	}

	public static LocalDateTime TOFORMAT(String str) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMAT);
		LocalDateTime dateTime = LocalDateTime.parse(str, formatter);

		return dateTime;

	}

	public static String TOFORMAT_SHORT(LocalDateTime date) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMAT_SHORT);
		//LocalDateTime dateTime = LocalDateTime.of(date);
		String formattedDateTime = date.format(formatter); // "1986-04-08 12:30"

		return formattedDateTime;

	}

	public static LocalDateTime TOFORMAT_SHORT(String str) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMAT_SHORT);
		LocalDate date = LocalDate.parse(str, formatter);
		LocalDateTime dateTime = date.atStartOfDay();		
		return dateTime;

	}

	public static LocalDateTime TOFORMAT_SHORT(Date dateToConvert) {
		return dateToConvert.toInstant()
				.atZone(ZoneId.systemDefault())
				.toLocalDateTime();
	}

	public static LocalDateTime SqlDateTOLocalDateTime(java.sql.Date sqlDate) {
		return TOLOCALDATETIME(TODATE(TOLOCALDATE(sqlDate)));
	}

	private static LocalDate TOLOCALDATE(java.sql.Date sqlDate){
		return (sqlDate == null ? null : sqlDate.toLocalDate());
	}

	private static java.util.Date TODATE(LocalDate localDate){
		return java.util.Date.from(localDate.atStartOfDay()
				.atZone(ZoneId.systemDefault())
				.toInstant());
	}

	private static LocalDateTime TOLOCALDATETIME(java.util.Date date){
		return date.toInstant()
				.atZone(ZoneId.systemDefault())
				.toLocalDateTime();
	}

	public static java.sql.Date TOSQLDATE (LocalDateTime dateTime) {

		java.sql.Date sqlDate = java.sql.Date.valueOf(dateTime.toLocalDate());
		
		return sqlDate;

	}

	public static java.sql.Timestamp TOSQLDATETIME (LocalDateTime date) {

		return Timestamp.valueOf(date);

	}
	
	public static LocalDateTime TOSQLDATETIME (java.sql.Timestamp date) {

		return LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), 
                TimeZone.getDefault().toZoneId());  

	}


}
