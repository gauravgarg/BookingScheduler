package com.test.akqa.BookingScheduler.bean;

import java.time.LocalTime;

/**
 *
 */
public class OfficeTimingHours {
	private final LocalTime startTime;
	private final LocalTime endTime;

	/**
	 *
	 * @param startValue
	 * @param endValue
	 */
	public OfficeTimingHours(Integer[] startValue, Integer[] endValue){
		this.startTime = LocalTime.of(startValue[0], startValue[1]);
		this.endTime = LocalTime.of(endValue[0], endValue[1]);
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	@Override
	public String toString() {
		return "OfficeHours [startTime=" + startTime + ", endTime=" + endTime+ "]";
	}

}
