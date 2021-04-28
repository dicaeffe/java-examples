package demo.algorithm;

import java.util.GregorianCalendar;

public class TimeInMillis {
	public static void main(String[] args) {
		//Reference date is the 1st of Jan 2020 00:00:00
		GregorianCalendar refDate2020 = new GregorianCalendar(2020, 00, 01, 00, 00, 00);
		
		//Delta time in milliseconds from the refDate2020 to "now".
		long value = System.currentTimeMillis() - refDate2020.getTimeInMillis();
		System.out.println(value);
	}
}
