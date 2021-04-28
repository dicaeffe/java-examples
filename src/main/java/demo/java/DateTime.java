package demo.java;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateTime {
	
	public static void main(String[] args) {
		OffsetDateTime myDateTime = OffsetDateTime.now();
		System.out.println(myDateTime);
		System.out.println(toString(myDateTime, DateTimeFormatter.ofPattern("'ACN'-yyMM-ddHHmmss")));
		System.out.println(toString(myDateTime, DateTimeFormatter.ofPattern("dd-MM-yyyy-HH:mm+ss")));
	}

	private static String toString(OffsetDateTime myDateTime, DateTimeFormatter formatter) {
		return myDateTime.format(formatter);
	}
	
	private static void manageTimeZone() {
		String timestampFormat = "dd-MM-yyyy-HH:mm+ss z";
		DateTimeFormatter timestampFormatter = DateTimeFormatter.ofPattern(timestampFormat);

		String zone = "UTC";

		System.out.println(ZonedDateTime.now(ZoneId.of("America/Los_Angeles")).format(timestampFormatter));
		System.out.println(ZonedDateTime.now(ZoneId.of("GMT")).format(timestampFormatter));
		System.out.println(ZonedDateTime.now(ZoneId.of("UT")).format(timestampFormatter));
		System.out.println(ZonedDateTime.now(ZoneId.of("+2")).format(timestampFormatter));
		System.out.println(ZonedDateTime.now(ZoneId.of(zone)).format(timestampFormatter));
		OffsetDateTime adhesionDateTime = OffsetDateTime.now();
		System.out.println(adhesionDateTime.atZoneSameInstant(ZoneId.of(zone)).format(timestampFormatter));
		System.out.println(adhesionDateTime.atZoneSimilarLocal(ZoneId.of(zone)).format(timestampFormatter));
	}
}
