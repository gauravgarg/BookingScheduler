package com.test.akqa.BookingScheduler.bean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.StringTokenizer;

import com.test.akqa.BookingScheduler.utility.BookingUtility;
import com.test.akqa.BookingScheduler.utility.TimeUtility;


public class BookingRequest implements Comparable<BookingRequest>{
	private  LocalDateTime requestSubmissionTime;
	private  String empId;
	private  LocalDate meetingDate;
	private  LocalTime startTime;
	private  LocalTime endTime;

	/**
	 * @param requestingData
	 * @param meetingTime
	 */
	public BookingRequest(final String requestingData, final String meetingTime) {
		StringTokenizer st = new StringTokenizer(requestingData, BookingUtility.BLANK);

		this.requestSubmissionTime = TimeUtility.getSubmissionTime(st.nextToken(), st.nextToken());
		this.empId = st.nextToken();

		st = new StringTokenizer(meetingTime, BookingUtility.BLANK);
		this.meetingDate = TimeUtility.getMeetingDate(st.nextToken());
		this.startTime = TimeUtility.getMeetingStartTime(st.nextToken());
		this.endTime = this.startTime.plusHours(Long.parseLong(st.nextToken()));
	}

	
	public LocalDateTime getRequestSubmissionTime() {
		return requestSubmissionTime;
	}



	public void setRequestSubmissionTime(LocalDateTime requestSubmissionTime) {
		this.requestSubmissionTime = requestSubmissionTime;
	}



	public String getEmpId() {
		return empId;
	}



	public void setEmpId(String empId) {
		this.empId = empId;
	}



	public LocalDate getMeetingDate() {
		return meetingDate;
	}



	public void setMeetingDate(LocalDate meetingDate) {
		this.meetingDate = meetingDate;
	}



	public LocalTime getStartTime() {
		return startTime;
	}



	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}



	public LocalTime getEndTime() {
		return endTime;
	}



	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}


	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(BookingRequest o) {
			final int BEFORE = -1;
			final int EQUAL = 0;
			final int AFTER = 1;

			if (this == o) {
				return EQUAL;
			}

			if (this.getRequestSubmissionTime().isBefore(o.getRequestSubmissionTime())) {
				return (this.getStartTime().isBefore(o.getStartTime()))? AFTER : BEFORE;
			}

			if (this.getRequestSubmissionTime().isAfter(o.getRequestSubmissionTime())) {
				return (this.getStartTime().isBefore(o.getStartTime()))? BEFORE : AFTER;
			}

			return EQUAL;
		}


	@Override
	public String toString() {
		return "BookingRequest [requestSubmissionTime=" + requestSubmissionTime
				+ ", empId=" + empId + ", meetingDate=" + meetingDate
				+ ", startTime=" + startTime + ", endTime=" + endTime + "]";
	}
	

}
