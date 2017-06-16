package com.test.akqa.BookingScheduler.main;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import com.test.akqa.BookingScheduler.bean.BookingRequest;
import com.test.akqa.BookingScheduler.bean.OfficeTimingHours;
import com.test.akqa.BookingScheduler.utility.BookingUtility;

/**
 * This is the main class called to process for booking
 */
public class BookingProcessor {

	private final Map<LocalDate, Set<BookingRequest>> bookingMap = new TreeMap<LocalDate, Set<BookingRequest>>();
	private String bookingMapAsString;

	/**
	 * This method processes the scheduling
	 * @param meetingsBatch the input to be processed
	 */
	public void process(final List<String> listOfBookingObject) {
		boolean firstLine = true;

		OfficeTimingHours officeHours = null;
		final Set<BookingRequest> meetingRequestsSet = new TreeSet<BookingRequest>();
		int incrementCounter = 0;
		while(incrementCounter < listOfBookingObject.size()){
			if (firstLine) {
			officeHours = BookingUtility.determineOfficeHours(listOfBookingObject.get(incrementCounter));
			incrementCounter = incrementCounter + 1;
			firstLine = false;
		} else {
			BookingRequest meetingRequest = new BookingRequest(listOfBookingObject.get(incrementCounter),listOfBookingObject.get(incrementCounter+1));
			addBookingToSet(meetingRequest, officeHours, meetingRequestsSet);
			incrementCounter = incrementCounter +2;
		}
		}
		
		cleanOverlappingBookings(bookingMap, meetingRequestsSet);
		bookingMapAsString = createFormatedOutput(bookingMap);
		printOutputToConsole(bookingMapAsString);
	}


	/**
	 * This method dumps the result of the processing to the console
	 * @param meetingsScheduling the result of the processing
	 */
	private void printOutputToConsole(final String meetingsScheduling) {
		System.out.println(meetingsScheduling);
	}

	/**
	 * This method create a String representation of the result of the processing.
	 * @param bookingsMapValue
	 */
	private  String createFormatedOutput(final Map<LocalDate, Set<BookingRequest>> bookingsMapValue) {
		final StringBuilder sb = new StringBuilder();
		for (Map.Entry<LocalDate, Set<BookingRequest>> bookingEntry : bookingsMapValue.entrySet()) {
			final LocalDate bookingDate = bookingEntry.getKey();

			sb.append(bookingDate).append("\n");

			final Set<BookingRequest> bookings = bookingEntry.getValue();
			for (BookingRequest booking : bookings) {
				sb.append(booking.getStartTime()).append(" ");
				sb.append(booking.getEndTime()).append(" ");
				sb.append(booking.getEmpId()).append("\n");
			}

		}
		return sb.toString();
	}

	/** This method add the BookingRequest to the set by eliminating any overlapping bookings
	 * @param bookingMap
	 * @param bokingRequestsSet
	 */
	private  void cleanOverlappingBookings(final Map<LocalDate, Set<BookingRequest>> bookingMap, final Set<BookingRequest> bokingRequestsSet) {
		for (BookingRequest bookingRequestInSet : bokingRequestsSet) {
			if (!bookingMap.containsKey(bookingRequestInSet.getMeetingDate())) {
				bookingMap.put(bookingRequestInSet.getMeetingDate(), new TreeSet<BookingRequest>());
			}

			if (bookingMap.get(bookingRequestInSet.getMeetingDate()).isEmpty()) {
				bookingMap.get(bookingRequestInSet.getMeetingDate()).add(bookingRequestInSet);
			} else {
				@SuppressWarnings("rawtypes")
				BookingRequest lastMeetingRequestInSet = (BookingRequest)((TreeSet)bookingMap.get(bookingRequestInSet.getMeetingDate())).last();
				if (!BookingUtility.isNewMeetingOverlappingPreviousMeeting(bookingRequestInSet, lastMeetingRequestInSet)) {
					bookingMap.get(bookingRequestInSet.getMeetingDate()).add(bookingRequestInSet);
				}
			}
		}
	}

	/** We add the BookingRequest to the Set that will contain all the BookingRequest. We don't need to care about BookingRequest falling outside
	 * of the office hours, so they are here put aside.
	 * @param bookingRequest
	 * @param officeHours
	 * @param bookingReqSet
	 */
	private  void addBookingToSet(final BookingRequest bookingRequest, final OfficeTimingHours officeHours, final Set<BookingRequest> bookingReqSet) {
		if (!BookingUtility.isMeetingOutsideOfficeHours(bookingRequest, officeHours)) {			
			bookingReqSet.add(bookingRequest);
		}
	}

	/**
	 * This method returns the map containing the BookingRequest
	 * @return the map set during the process
	 */
	public Map<LocalDate, Set<BookingRequest>> getMeetingsMap() {
		return bookingMap;
	}

	/**
	 * This method returns a String representation of the processing
	 * @return
	 */
	public String getMeetingMapAsString() {
		return bookingMapAsString;
	}
}
