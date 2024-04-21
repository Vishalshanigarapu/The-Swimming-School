package adminOperations;

import java.time.temporal.ValueRange;
import java.util.List;

import data.Data;
import models.BookingModel;
import models.CoachRatingModel;
import models.LearnerModel;
import util.Utility;

public class AdminRoute {

	private Utility utilityObj = new Utility();
	
	public void learnerRegistration() {
		
		LearnerModel learnerModel = new LearnerModel();
		System.out.println("Please input Name:");
		String usrName = utilityObj.readUsrInputStr();
		learnerModel.setName(usrName);

		System.out.println("Please input gender:");
		String usrGender = utilityObj.readUsrInputStr();
		learnerModel.setGender(usrGender);
		
		System.out.println("Please enter age, allowed range: 4-11:");
		int usrAge = Integer.parseInt( utilityObj.readUsrInputStr() );

		while ( !ValueRange.of(4, 11).isValidIntValue(usrAge) ) {
			System.out.println("Oops! Please enter age between 4-11 only.");
			usrAge = Integer.parseInt( utilityObj.readUsrInputStr() );
		}
		learnerModel.setAge(usrAge);
		
		System.out.println("Please input Contact No:");
		String usrContactNo = utilityObj.readUsrInputStr();
		while( Data.learnersRegDataMap.get(usrContactNo) != null ) {
			System.out.println("The user with the provided contact number is already registered.");
			System.out.println("Please try again with different contact number.");
			usrContactNo = utilityObj.readUsrInputStr();
		}
		learnerModel.setContactNo(usrContactNo);
		
		System.out.println("Please input grade level in allowed range, 0-5:");
		int usrGrade = Integer.parseInt( utilityObj.readUsrInputStr() );

		while ( !ValueRange.of(0, 5).isValidIntValue(usrGrade) ) {
			System.out.println("Oops! Please input grade level between 0-5 only.");
			usrGrade = Integer.parseInt( utilityObj.readUsrInputStr() );
		}
		learnerModel.setGrade(usrGrade);
		
		System.out.println("Please set password to login");
		String usrPwd = utilityObj.readUsrInputStr();
		learnerModel.setPassword(usrPwd);
		
		Data.learnersRegDataMap.put(usrContactNo, learnerModel);
		System.out.println("Learner Successfully Registered.\n");				
	}
	
	public void createLearnerMonthlyReport() {
		
		System.out.println("Please provide month number, allowed range 1-12:");
		int inputMonthNum = Integer.parseInt( utilityObj.readUsrInputStr() );
		Boolean isRecordExists = Boolean.FALSE;
		
		while ( !ValueRange.of(1, 12).isValidIntValue(inputMonthNum) ) {
			System.out.println("\nInvalid month number. Please try again with valid inputs only.\n");
			inputMonthNum = Integer.parseInt( utilityObj.readUsrInputStr() );
		}

		for(BookingModel  bookingModel : Data.bookingDataList ) {
			if( bookingModel.getMonthNum() == inputMonthNum ) {
				isRecordExists = Boolean.TRUE;
			}
		}
		if(isRecordExists) {
			createLearnersList( inputMonthNum );
			showBookingList( inputMonthNum );
		}else
			System.out.println("Oops! No data exists for the selected month.\n");
					
	}
	
	public void createCoachMonthlyReport() {
		
		System.out.println("Please provide month number, allowed range 1-12:");
		int inputMonthNum = Integer.parseInt( utilityObj.readUsrInputStr() );
		Boolean isRecordExists = Boolean.FALSE;
		
		while ( !ValueRange.of(1, 12).isValidIntValue(inputMonthNum) ) {
			System.out.println("\nInvalid month number. Please try again with valid inputs only.\n");
			inputMonthNum = Integer.parseInt( utilityObj.readUsrInputStr() );
		}
		
		if( Data.coachRatingMap.get(inputMonthNum) != null ) {
			isRecordExists = Boolean.TRUE;
		}		
		if(isRecordExists) {
			showCoachRatingList( inputMonthNum );	
		}else
			System.out.println("Oops! No data found for the selected month.\n");
					
	}
	
	private void createLearnersList( int monthNum ) {

		System.out.println("Learners details for the input month number :" + monthNum + "\n");
		
		System.out.format("+----------------------+---------+-------+-------------------+--------+-----------------+---------------------+-------------------+%n");
		System.out.format("| Name                 | Gender  |  Age  | Contact No        | Grade  | Total Bookings  | Cancelled Bookings  | Attended Lessons  |%n");
		System.out.format("+----------------------+---------+-------+-------------------+--------+-----------------+---------------------+-------------------+%n");
		
		int usrTotalBookings = 0;
		int usrCancelledBookings = 0;
		int usrAttendedLessons = 0;
		
		String leftAlignment = "| %-20s | %-7s | %-5s | %-17s | %-7s| %-16s| %-20s| %-18s|%n";  		
		for( LearnerModel learnerModel : Data.learnersRegDataMap.values() ) {			
			
			usrTotalBookings = 0;
			usrCancelledBookings = 0;
			usrAttendedLessons = 0;
			
			for( BookingModel bookingModel : Data.bookingDataList ) {
				if( bookingModel.getMonthNum() == monthNum && bookingModel.getContactNo().equals(learnerModel.getContactNo()) ) {
					if( bookingModel.getCurrentStatus().equals("Booked") ) 
						usrTotalBookings = usrTotalBookings + 1;
					else if( bookingModel.getCurrentStatus().equals("Cancelled") ) {
						usrCancelledBookings = usrCancelledBookings + 1;
						usrTotalBookings ++;
					}else if( bookingModel.getCurrentStatus().equals("Attended") ) {
						usrAttendedLessons = usrAttendedLessons + 1;
						usrTotalBookings ++;
					}
				}
			}			
			System.out.format(leftAlignment, learnerModel.getName(), learnerModel.getGender(), learnerModel.getAge(), learnerModel.getContactNo(), learnerModel.getGrade(), usrTotalBookings, usrCancelledBookings, usrAttendedLessons );
			System.out.format("+----------------------+---------+-------+-------------------+--------+-----------------+---------------------+-------------------+%n");
		}
		System.out.println("");
		System.out.println("");
	}
	
	private void showBookingList( int monthNo ) {

		System.out.println("Learner wise booking details:");
		System.out.println("");
		
		System.out.format("+--------------+--------------+---------------------------+---------------------------+----------------+---------------+--------------+----------+--------------------+------------------------+%n");
		System.out.format("| Booking Id   | Lesson Id    |  Learner's Name           | Learner's Contact No      | Date           | Day           | Time         | Level    | Coach Name         | Current Status         |%n");
		System.out.format("+--------------+--------------+---------------------------+---------------------------+----------------+---------------+--------------+----------+--------------------+------------------------+%n");		
		
		String leftAlignment = "| %-12s | %-12s | %-25s | %-25s | %-15s| %-14s| %-13s| %-9s| %-19s|%-24s|%n";  		
		for( BookingModel bookingModel : Data.bookingDataList ) {			
			if( bookingModel.getMonthNum() == monthNo ) {
				System.out.format(leftAlignment, bookingModel.getBookingId(), bookingModel.getLessonId(), bookingModel.getUsrName(), 
						bookingModel.getContactNo(), bookingModel.getDate(), bookingModel.getDay(), bookingModel.getTime(), bookingModel.getLevel(),
						bookingModel.getCoach(), bookingModel.getCurrentStatus() );
				System.out.format("+--------------+--------------+---------------------------+---------------------------+----------------+---------------+--------------+----------+--------------------+------------------------+%n");
			}			
		}
		System.out.println("");
		System.out.println("");
	
	}
	
	private void showCoachRatingList( int monthNo ) {
		String coachRating = null;
		
		System.out.println("Coach Rating:\n");
		
		System.out.format("+---------------------------+---------------------------+%n");
		System.out.format("|  Coach Name               | Average Rating            |%n");
		System.out.format("+---------------------------+---------------------------+%n");		
		
		String leftAlignment = "| %-25s | %-25s |%n";  
		List<CoachRatingModel> coachRatingList = Data.coachRatingMap.get(monthNo);
		
		for( int i=0; i < Data.availableCoachesArr.length; i++ ) {			
			coachRating = "NA";
			for( CoachRatingModel coachRatingModel : coachRatingList ) {
				if( coachRatingModel.getCoachName().equals(Data.availableCoachesArr[i]) ) {
					coachRating = "" + coachRatingModel.getCoachRating();
				}
			}
			System.out.format( leftAlignment, Data.availableCoachesArr[i], coachRating );
			System.out.format("+---------------------------+---------------------------+%n");			
		}
		System.out.println("");
		System.out.println("");
	}

}
