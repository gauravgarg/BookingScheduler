package com.test.akqa.BookingScheduler.utility;


import java.util.StringTokenizer;

import com.test.akqa.BookingScheduler.bean.BookingRequest;
import com.test.akqa.BookingScheduler.bean.OfficeTimingHours;

/**
 * This singleton helper class encapsulates methods helpful to treat data pertaining to MeetingRequest and OfficeHours.
 * @author Daniel Wamara
 */
public class BookingUtility {
	/** An  of this class, it will uniquely exists */	

	/** The blank character */
	public static final String BLANK = " ";

	/**  The method checks if a BookingRequest is outside OfficeHours, so it could be ignored
	 * @param bookingRequest
	 * @param officeHours
	 * @return
	 */
	public static boolean isMeetingOutsideOfficeHours(BookingRequest bookingRequest, OfficeTimingHours officeHours) {
		return bookingRequest.getStartTime().isBefore(officeHours.getStartTime()) || bookingRequest.getEndTime().isAfter(officeHours.getEndTime());
	}

	/**
	 * This method creates an OfficeHours class with the given String read from the input
	 * @param officeHoursString the String passed from the input
	 * @return a new  of officeTimingHoursString
	 */
	public static OfficeTimingHours determineOfficeHours(final String officeTimingHoursString) {
		final StringTokenizer stringTokenizer = new StringTokenizer(officeTimingHoursString, BLANK);
		final OfficeTimingHours officeHours = new OfficeTimingHours(TimeUtility.extractHoursAndMinutesOfTime(stringTokenizer.nextToken()),
				TimeUtility.extractHoursAndMinutesOfTime(stringTokenizer.nextToken()));
		return officeHours;
	}

	/** This method checks if a BookingRequest overlaps with another one
	 * @param newBookingRequest
	 * @param previousBookingRequest
	 * @return
	 */
	public static boolean isNewMeetingOverlappingPreviousMeeting(BookingRequest newBookingRequest, BookingRequest previousBookingRequest) {
		return (newBookingRequest.getStartTime().isBefore(previousBookingRequest.getEndTime()) && previousBookingRequest.getStartTime().isBefore(newBookingRequest.getEndTime()))
			||
			((newBookingRequest.getStartTime() == previousBookingRequest.getStartTime() && newBookingRequest.getEndTime() == previousBookingRequest.getEndTime()));
	}
}
