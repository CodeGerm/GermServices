/**
 * 
 */
package org.cg.services.core.util;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * A Joda DateTime wrapper that can be formated to any desired pattern
 * @author WZ
 * 
 */
public class CGDateTime {

	private final boolean isFormated;
	private final DateTime time;
	private final DateTimeFormatter dateTimeFormatter;

	public CGDateTime(DateTime time, boolean isFormated) {
		this.time = time;
		this.isFormated = isFormated;
		this.dateTimeFormatter = DateTimeFormat.forPattern("yyyy/MM/dd HH:mm:ss z");
	}
	
	public CGDateTime(DateTime time, boolean isFormated, String pattern) {
		this.time = time;
		this.isFormated = isFormated;
		this.dateTimeFormatter = DateTimeFormat.forPattern(pattern);
	}
	
	public long getAsLong() {
		return time.getMillis();
	}
	
	public String getAsString() {
		return dateTimeFormatter.print(time);
	}

	@Override
	public String toString() {
		return isFormated ? dateTimeFormatter.print(time) : String.valueOf(time.getMillis());
	}
	
}
