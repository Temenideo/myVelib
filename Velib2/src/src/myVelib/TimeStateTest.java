package myVelib;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.junit.Test;

public class TimeStateTest {

	@Test
	public void testTimeState() throws ParseException {
		String string = "2018.03.25 AD at 12:08:56 PDT";
		DateFormat format = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z", Locale.ENGLISH);
		Date datestart = format.parse(string);
		TimeState t=new TimeState(true,datestart);
		assertEquals(datestart, t.getStart());
	}

}
