package data;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import models.BookingModel;
import models.CoachRatingModel;
import models.LearnerModel;
import models.LessonModel;

public class Data {

	public static Map<String,LearnerModel> learnersRegDataMap = new HashMap<>();
	public static List<BookingModel> bookingDataList = new ArrayList<>();
	public static Map<Integer, List<CoachRatingModel>> coachRatingMap = new HashMap<>();
	public static String availableCoachesArr[] = { "Toren", "Ava", "Charlie", "Isla", "Charlotte" };
	public static List<LessonModel> lessonsDtls = new ArrayList<>();

	public static void setLearnersRegData() {
		
		LearnerModel learnerModel = null;
		String learnerNamesArr[] = {"Raina", "Konika", "Rikin", "Korn", "Jinko", "Teer","Anam", "Razzaq", "Angel", "Jack", "Bruce", "Jackie", "Panther", "William", "Leo"};
		String learnerGenderArr[] = {"MALE", "FEMALE", "MALE", "FEMALE", "MALE", "FEMALE", "MALE", "FEMALE", "FEMALE", "FEMALE", "MALE", "FEMALE", "MALE", "MALE", "FEMALE"};
		int ageArr[] = {6, 7, 9, 5, 10, 9, 6, 7, 9, 5, 10, 9, 8,8,7};
		String contactNoArr[] = {"(333) 444-2266", "(333) 224-21555", "(333) 434-535322", "(333) 433-335577", "(333) 224-522599", "(333) 411-511591", "(333) 411-510712", "(333) 411-711500", "(333) 411-000588", "(777) 211-511596",
								"(666) 411-511521", "(555) 411-511531", "(555) 411-510032", "(888) 421-511545", "(666) 211-226665"};
		
		String pwdArr[] = {"password@1", "password@2", "password@3", "password@4", "password@5", "password@6", "password@7", "password@8", "password@9", "password@10",
				"password@11", "password@12", "password@13", "password@14", "password@15"};
		
		int gradeArr[] = {0, 4, 3, 0, 4, 3, 0, 4, 3, 0, 4, 3, 4,3,3};
		int totalBookingsArr[] = {2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,0,1};
		int cancelledBookingsArr[] = {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,0,0};
		int attendedLessonsArr[] = {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,0,1};
		
		for( int i=0; i<=14; i++ ) {
			learnerModel = new LearnerModel();
			learnerModel.setName(learnerNamesArr[i]);
			learnerModel.setGender(learnerGenderArr[i]);
			learnerModel.setAge(ageArr[i]);
			learnerModel.setContactNo(contactNoArr[i]);
			learnerModel.setGrade(gradeArr[i]);
			learnerModel.setPassword(pwdArr[i]);
			learnerModel.setTotalBookings(totalBookingsArr[i]);
			learnerModel.setCancelledBookings(cancelledBookingsArr[i]);
			learnerModel.setAttendedLessons(attendedLessonsArr[i]);
			
			learnersRegDataMap.put(contactNoArr[i], learnerModel);			
		}
		
	}
	
	public static void setBookingData() {
		
		BookingModel bookingModel = null;
		String contactNosArr[] = { "(333) 444-2266", "(333) 444-2266", "(333) 224-21555", "99887767" };
		String usrNamesArr[] = { "Raina", "Raina", "Konika", "Leo" };
		int bookingIdsArr[] = { 10, 20, 30, 40 };
		int lessonIdsArr[] = { 1, 2, 2, 3 };
		String datesArr[] = { "01-03-2024", "01-03-2024", "01-03-2024", "02-03-2024" };
		int monthNumArr[] = { 3, 3, 3, 3 };
		String daysArr[] = { "Friday", "Friday", "Friday", "Saturday" };
		String timeSlotsArr[] = { "4-5 PM", "5-6 PM", "5-6 PM", "2-3 PM" };
		int levelsArr[] = { 0, 0, 4, 3 };
		String coachesArr[] = { "Toren", "Ava", "Charlie", "Isla" };
		String currStatusArr[] = { "Cancelled", "Attended", "Cancelled", "Attended" }; //Booked, Cancelled, Attended
		
		for( int i=0; i < contactNosArr.length; i++ ) {
			bookingModel = new BookingModel();
			
			bookingModel.setContactNo(contactNosArr[i]);
			bookingModel.setUsrName(usrNamesArr[i]);
			bookingModel.setBookingId(bookingIdsArr[i]);
			bookingModel.setLessonId(lessonIdsArr[i]);
			bookingModel.setDate(datesArr[i]);
			bookingModel.setMonthNum(monthNumArr[i]);
			bookingModel.setDay(daysArr[i]);
			bookingModel.setTime(timeSlotsArr[i]);
			bookingModel.setLevel(levelsArr[i]);
			bookingModel.setCoach(coachesArr[i]);
			bookingModel.setCurrentStatus(currStatusArr[i]);			
			bookingDataList.add(bookingModel);
		}
		
	}
	
	public static void setCoachRatingData() {

		CoachRatingModel coachRatingModel = null;
		List<CoachRatingModel> coachRatingList = new ArrayList<>();
		Random random = new Random();
		int monthNo = 3;
		String coachNamesArr[] = { "Ava", "Isla" };
		
		for ( int i = 0; i <= 1; i++ ) {
			coachRatingModel = new CoachRatingModel();
			coachRatingModel.setCoachName( coachNamesArr[i] );
			coachRatingModel.setCoachRating( random.nextInt(4) + 1 );
			
			coachRatingList.add(coachRatingModel);
		}
		coachRatingMap.put(monthNo, coachRatingList);		
	}
	
	public static void setLessons() {
		
		Random random = new Random();
		Calendar calenderObj = Calendar.getInstance();
		LessonModel lessonModel = null;		
		int lessonIdStartNum = 4;
		
		String weekDaysSlotsArr[] = { "4-5 PM", "5-6 PM", "6-7 PM" };
		String satSlotsArr[] = { "2-3 PM", "3-4 PM" };
		String swimmingDaysArr[] = { "Monday", "Wednesday", "Friday", "Saturday" };
		int gradesArr[] = { 1,2,3,4,5 };
		int monthNumArr[] = {3,4,5,6};
		
		for(int i=1; i<=50; i++) {
			
			String lessonTime = null;
			String lessonDate = null;
			int lessonMonth = 0;
			String lessonYear = null;
			String lessonDayName = null;
			String lessonCoach = null;
			Integer lessonGrade = null;
			
			lessonModel = new LessonModel();
			lessonMonth = monthNumArr[random.nextInt(monthNumArr.length)];
			lessonCoach = availableCoachesArr[random.nextInt(availableCoachesArr.length)];
			lessonGrade = gradesArr[random.nextInt(gradesArr.length)];		
			lessonDayName = swimmingDaysArr[random.nextInt(swimmingDaysArr.length)];
			lessonTime = weekDaysSlotsArr[random.nextInt(weekDaysSlotsArr.length)];
			if( lessonDayName.equals(swimmingDaysArr[3]) ) {
				lessonTime = satSlotsArr[random.nextInt(satSlotsArr.length)];
			}
			int dayNum = findCalenderDayEq(lessonDayName);
			calenderObj.set(Calendar.DAY_OF_WEEK, dayNum);
			calenderObj.set(Calendar.MONTH, lessonMonth);

			lessonDate = String.valueOf(calenderObj.get(Calendar.DATE));
			lessonMonth = calenderObj.get(Calendar.MONTH) + 1;
			lessonYear = String.valueOf(calenderObj.get(Calendar.YEAR));
			
			lessonModel.setId(lessonIdStartNum++);
			lessonModel.setDate( String.format("%02d", Integer.parseInt(lessonDate)) + "-" + String.format("%02d", lessonMonth) + "-" + lessonYear );
			lessonModel.setMonthNum(lessonMonth);
			lessonModel.setYear(lessonYear);
			lessonModel.setDay(lessonDayName);
			lessonModel.setTime(lessonTime);
			lessonModel.setLevel(lessonGrade);
			lessonModel.setCoachName(lessonCoach);
			lessonModel.setAvlSeats(4);

			lessonsDtls.add(lessonModel);			
		}		
	}
	
	private static int findCalenderDayEq(String day) {
		switch (day) {
		case "Monday":
			return Calendar.MONDAY;
		case "Wednesday":
			return Calendar.WEDNESDAY;
		case "Friday":
			return Calendar.FRIDAY;
		case "Saturday":
			return Calendar.SATURDAY;
		default:
			return Calendar.SUNDAY;
		}
	}
}
