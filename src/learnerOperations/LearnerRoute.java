package learnerOperations;

import java.time.temporal.ValueRange;
import java.util.ArrayList;
import java.util.List;

import data.Data;
import models.BookingModel;
import models.CoachRatingModel;
import models.LearnerModel;
import models.LessonModel;
import util.MarkerCls;
import util.Utility;

public class LearnerRoute {

	private static Utility appUtility = new Utility();
	private static int bookingIdStart = 51;
	
	public Boolean isLearnerUsrExists( String learnerContactNo ) {
		
		if( Data.learnersRegDataMap.get(learnerContactNo) != null )
			return Boolean.TRUE;
		
		return Boolean.FALSE;		
	}
	
	public Boolean isPwdValid( String learnerContactNo, String pwd ) {
		
		if( Data.learnersRegDataMap.get(learnerContactNo) != null && Data.learnersRegDataMap.get(learnerContactNo).getPassword().equals(pwd) )
			return Boolean.TRUE;
		
		return Boolean.FALSE;		
	}
	
	public Boolean checkActiveBookingsForUsr( String usrContactNo ) {
		return isExistUsrActiveBookings( usrContactNo );
	}
	
	public void dispLearnerDashboard() {
		
		System.out.println( "\nWelcome Learner, please select one of the options listed below." );
		System.out.println( "1. Lesson Booking" );
		System.out.println( "2. Booking Cancellation" );
		System.out.println( "3. Change of Booking" );
		System.out.println( "4. Attend a Lesson" );
		System.out.println( "5. Logout and return to main menu\n" );
		
		System.out.println( "Please choose your option." );
	}
	
	public Boolean performOprByOption( int optionNo, String contactNo, MarkerCls flagObj ) {
		
		Boolean continueLearnerMenu = Boolean.FALSE;
		int option = 0;
		String bookingId = null;
		
		switch (optionNo) {
		case 1:						
//			Lesson Booking
			lessonBookingMenu();
			option = Integer.parseInt( appUtility.readUsrInputStr() );
			continueLearnerMenu = proceedLessonBooking( option, contactNo, flagObj );
			break;
		case 2:															
//			Booking Cancellation			
			if( !isExistUsrActiveBookings( contactNo ) ) {
				System.out.println("\nSorry, there is no active booking against your account.");
				continueLearnerMenu = Boolean.TRUE;
				break;
			}
			System.out.println("\nPlease enter booking Id you wish to cancel.");
			bookingId = appUtility.readUsrInputStr();
			continueLearnerMenu = proceedBookingCancellation(bookingId, contactNo);			
			break;
		case 3:
//			Change of Booking
			if( !isExistUsrActiveBookings( contactNo ) ) {
				System.out.println("\nSorry, there is no active booking against your account.");
				continueLearnerMenu = Boolean.TRUE;
				break;
			}
			System.out.println("\nPlease enter booking Id you wish to change.");
			bookingId = appUtility.readUsrInputStr();
			continueLearnerMenu = proceedBookingChange(bookingId, contactNo);				
			break;
		case 4:
//			Attend a Lesson
			if( !isExistUsrActiveBookings( contactNo ) ) {
				System.out.println("\nSorry, there is no active booking against your account.");
				continueLearnerMenu = Boolean.TRUE;
				break;
			}
			System.out.println("\nPlease enter booking Id you wish to attend.");
			bookingId = appUtility.readUsrInputStr();
			continueLearnerMenu = attendingLesson(bookingId, contactNo);
			break;
		case 5:			
			//Back to main menu
			continueLearnerMenu = Boolean.FALSE;
			break;	
		default:
			System.out.println("Sorry, Input is invalid. Returning to Main Menu.\n");
			continueLearnerMenu = Boolean.FALSE;
			break;
		}		
		return continueLearnerMenu;		
	}
	
	private void lessonBookingMenu() {
		
		System.out.println( "1. Booking through Day Name" );
		System.out.println( "2. Booking through Grade" );
		System.out.println( "3. Booking through Coach Name" );		
		System.out.println( "4. Back \n" );
		
		System.out.println( "Please select your preference." );
	}
	
	private Boolean proceedLessonBooking( int optionNo, String contactNo, MarkerCls flag ) {
		Boolean continueLearnerMenu = Boolean.FALSE;
		switch (optionNo) {
			case 1:
				System.out.println( "Please input day name in sentence case." );
				String day = appUtility.readUsrInputStr();				
				continueLearnerMenu = proceedBookingByDay( day, contactNo, flag );
				break;
			case 2:
				System.out.println( "Please enter grade/level, allowed range: 1-5:" );
				String grade = appUtility.readUsrInputStr();
				int gradeNo = Integer.parseInt(grade);
				while ( !ValueRange.of(1, 5).isValidIntValue(gradeNo) ) {
					System.out.println("Oops! Please enter grade between 1-5 only.");
					grade = appUtility.readUsrInputStr();
					gradeNo = Integer.parseInt(grade);
				}
				continueLearnerMenu = proceedBookingByGrade(grade, contactNo, flag );
				break;
			case 3:
				System.out.println( "Please enter coach name.\n" );
				String coachName = appUtility.readUsrInputStr();				
				continueLearnerMenu = proceedBookingByCoach(coachName, contactNo, flag );
				break;
			case 4:
				break;
			default:
				break;
		}
		
		return continueLearnerMenu;
		
	}
	
	private Boolean proceedBookingByDay( String nameOfDay, String usrContactNo, MarkerCls flagObj ) {
		
		String lessonIdInputStr = null;
		Boolean isRecordExists = Boolean.FALSE;	
		
		System.out.format("+--------------+-----------------+---------------------+--------------------+-------------+--------------------------+------------------+%n");
		System.out.format("| Lesson Id    |  Date           | Day                 | Time Slot          | Level       | Coach Name               | Seats Available  |%n");
		System.out.format("+--------------+-----------------+---------------------+--------------------+-------------+--------------------------+------------------+%n");	

		String leftAlignment = "| %-12s | %-15s | %-19s | %-19s| %-12s| %-25s| %-17s|%n";  
		for( LessonModel lessonModel : Data.lessonsDtls ) {
			if( lessonModel.getDay().equals(nameOfDay) && lessonModel.getAvlSeats() > 0  ) {
				isRecordExists = Boolean.TRUE;

				System.out.format(leftAlignment, lessonModel.getId(), lessonModel.getDate(), lessonModel.getDay(), 
						lessonModel.getTime(), lessonModel.getLevel(), lessonModel.getCoachName(), lessonModel.getAvlSeats() );
				System.out.format("+--------------+-----------------+---------------------+--------------------+-------------+--------------------------+------------------+%n");
			}
		}		
		
		if( !isRecordExists ) {
			System.out.println("\nSorry, No lesson found for the selected day.\n");
			return Boolean.TRUE;
		}else {
			System.out.println("");
			System.out.println("Please input Lesson Id to book.\n");
			lessonIdInputStr = appUtility.readUsrInputStr();
			return concludeBooking( lessonIdInputStr, usrContactNo, flagObj );
		}	
	}
	
	private Boolean proceedBookingByGrade( String grade, String usrContactNo, MarkerCls flagObj ) {
		
		String lessonIdInputStr = null;
		Boolean isRecordExists = Boolean.FALSE;	
		
		System.out.format("+--------------+-----------------+---------------------+--------------------+-------------+--------------------------+------------------+%n");
		System.out.format("| Lesson Id    |  Date           | Day                 | Time Slot          | Level       | Coach Name               | Seats Available  |%n");
		System.out.format("+--------------+-----------------+---------------------+--------------------+-------------+--------------------------+------------------+%n");	

		String leftAlignment = "| %-12s | %-15s | %-19s | %-19s| %-12s| %-25s| %-17s|%n";  
		for( LessonModel lessonModel : Data.lessonsDtls ) {
			if( lessonModel.getLevel() == Integer.parseInt(grade) && lessonModel.getAvlSeats() > 0  ) {
				isRecordExists = Boolean.TRUE;

				System.out.format(leftAlignment, lessonModel.getId(), lessonModel.getDate(), lessonModel.getDay(), 
						lessonModel.getTime(), lessonModel.getLevel(), lessonModel.getCoachName(), lessonModel.getAvlSeats() );
				System.out.format("+--------------+-----------------+---------------------+--------------------+-------------+--------------------------+------------------+%n");
			}
		}		
		
		if( !isRecordExists ) {
			System.out.println("\nSorry, No lesson found for the selected grade/level.\n");
			return Boolean.TRUE;
		}else {
			System.out.println("");
			System.out.println("Please input Lesson Id to book.\n");
			lessonIdInputStr = appUtility.readUsrInputStr();
			return concludeBooking( lessonIdInputStr, usrContactNo, flagObj );
		}
		
	}
	
	private Boolean proceedBookingByCoach( String coachName, String usrContactNo, MarkerCls flagObj ) {
		
		String lessonIdInputStr = null;
		Boolean isRecordExists = Boolean.FALSE;	
		
		System.out.format("+--------------+-----------------+---------------------+--------------------+-------------+--------------------------+------------------+%n");
		System.out.format("| Lesson Id    |  Date           | Day                 | Time Slot          | Level       | Coach Name               | Seats Available  |%n");
		System.out.format("+--------------+-----------------+---------------------+--------------------+-------------+--------------------------+------------------+%n");	

		String leftAlignment = "| %-12s | %-15s | %-19s | %-19s| %-12s| %-25s| %-17s|%n";  
		for( LessonModel lessonModel : Data.lessonsDtls ) {
			if( lessonModel.getCoachName().equals(coachName) && lessonModel.getAvlSeats() > 0  ) {
				isRecordExists = Boolean.TRUE;

				System.out.format(leftAlignment, lessonModel.getId(), lessonModel.getDate(), lessonModel.getDay(), 
						lessonModel.getTime(), lessonModel.getLevel(), lessonModel.getCoachName(), lessonModel.getAvlSeats() );
				System.out.format("+--------------+-----------------+---------------------+--------------------+-------------+--------------------------+------------------+%n");
			}
		}		
		
		if( !isRecordExists ) {
			System.out.println("\nSorry, No lesson found for the selected coach.\n");
			return Boolean.TRUE;
		}else {
			System.out.println("");
			System.out.println("Please input Lesson Id to book.\n");
			lessonIdInputStr = appUtility.readUsrInputStr();
			return concludeBooking( lessonIdInputStr, usrContactNo, flagObj );
		}
		
	}
	
	private Boolean concludeBooking( String lessonId, String usrContactNo, MarkerCls flagObj ) {
		int currAvlSeats = 0;		
		Boolean isValidLesson = Boolean.FALSE;
		BookingModel bookingModel = null;
		LearnerModel learnerModel = Data.learnersRegDataMap.get(usrContactNo);
		for( LessonModel lessonModel : Data.lessonsDtls ) {
			try {
				
				for( BookingModel bookingModelTemp : Data.bookingDataList ) {
					if( bookingModelTemp.getLessonId() == Integer.parseInt(lessonId) 
							&& bookingModelTemp.getContactNo().equals( learnerModel.getContactNo() ) 
							&& bookingModelTemp.getCurrentStatus().equals("Booked") ) {
						
						System.out.println("\nBooking for same lesson already exists against your account. No duplicate booking allowed.");
						return Boolean.TRUE;
					}
				}				
				if( lessonModel.getId() == Integer.parseInt(lessonId) ) {
					isValidLesson = Boolean.TRUE;
					if( lessonModel.getLevel() - learnerModel.getGrade() >=0 
							&& lessonModel.getLevel() - learnerModel.getGrade() <= 1  ) {
						currAvlSeats = lessonModel.getAvlSeats();
						if( currAvlSeats == 0 ) {
							System.out.println("\nSorry, All seats booked.");
							return Boolean.TRUE;
						}													
						if( flagObj != null && flagObj.getBookingModel() != null ) {
							bookingModel = flagObj.getBookingModel();
							
							flagObj.setStatus(Boolean.TRUE);
							bookingModel.setLessonId( Integer.parseInt(lessonId) );
							bookingModel.setDate( lessonModel.getDate() );
							bookingModel.setMonthNum( lessonModel.getMonthNum() );
							bookingModel.setDay( lessonModel.getDay() );
							bookingModel.setTime( lessonModel.getTime() );
							bookingModel.setLevel( lessonModel.getLevel() );
							bookingModel.setCoach( lessonModel.getCoachName() );
							bookingModel.setCurrentStatus("Booked");
						}else {
							bookingModel = new BookingModel();
							bookingModel.setContactNo( learnerModel.getContactNo() );
							bookingModel.setUsrName( learnerModel.getName() );
							bookingModel.setBookingId( bookingIdStart++ );
							bookingModel.setLessonId( lessonModel.getId() );
							bookingModel.setDate( lessonModel.getDate() );
							bookingModel.setMonthNum( lessonModel.getMonthNum() );
							bookingModel.setDay( lessonModel.getDay() );
							bookingModel.setTime( lessonModel.getTime() );
							bookingModel.setLevel( lessonModel.getLevel() );
							bookingModel.setCoach( lessonModel.getCoachName() );
							bookingModel.setCurrentStatus("Booked");
							
							Data.bookingDataList.add(bookingModel);
						}
						lessonModel.setAvlSeats(currAvlSeats-1);					
						System.out.println("\nLesson booked successfully, and the Booking Id is : " + bookingModel.getBookingId() );
					}else {
						System.out.println("\nSorry, you are not authorized to book this grade/level.");
						return Boolean.TRUE;
					}
											
					break;	
				}
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("An error occured. Please try again.\n");
			}
		}
		if(!isValidLesson) {
			System.out.println("\nInvalid Lesson Id. Please try again with valid input.");
			return Boolean.TRUE;
		}
		
		return Boolean.TRUE;
	}
	
	private Boolean isExistUsrActiveBookings( String usrContactNo ) {
		
		Boolean isActiveBookingExist = Boolean.FALSE;
		for( BookingModel bookingModel : Data.bookingDataList ) {
			if( bookingModel.getContactNo().equals(usrContactNo) 
					&& bookingModel.getCurrentStatus().equals("Booked")  ) {
				
				isActiveBookingExist = Boolean.TRUE;
				break;	
			}
		}		
		if( isActiveBookingExist ) {
			System.out.println("\nActive Bookings:\n ");			
			System.out.format("+--------------" + "+--------------+-----------------+---------------------+--------------------+-------------+--------------------------+%n");
			System.out.format("| Booking Id   " + "| Lesson Id    |  Date           | Day                 | Time Slot          | Level       | Coach Name               |%n");
			System.out.format("+--------------" + "+--------------+-----------------+---------------------+--------------------+-------------+--------------------------+%n");	
			String leftAlignment = "| %-12s " + "| %-12s | %-15s | %-19s | %-19s| %-12s| %-25s|%n"; 

			for( BookingModel bookingModel : Data.bookingDataList ) {
				if( bookingModel.getContactNo().equals(usrContactNo) 
						&& bookingModel.getCurrentStatus().equals("Booked")  ) {
					
					System.out.format(leftAlignment, bookingModel.getBookingId() ,bookingModel.getLessonId(), bookingModel.getDate(), bookingModel.getDay(), 
							bookingModel.getTime(), bookingModel.getLevel(), bookingModel.getCoach() );
					System.out.format("+--------------" + "+--------------+-----------------+---------------------+--------------------+-------------+--------------------------+%n");
				}
			}
		}
		return isActiveBookingExist;
	}
	
	private Boolean proceedBookingCancellation( String bookingId, String usrContactNo ) {
		Integer lessonId = null;
		Integer avlSeats = null;
		Boolean isBookingIdValid = Boolean.FALSE;
		
		for( BookingModel bookingModel : Data.bookingDataList ) {
			if( bookingModel.getBookingId() == Integer.parseInt(bookingId) ) {				
				isBookingIdValid = Boolean.TRUE;
				if( bookingModel.getCurrentStatus().equals("Booked") ) {
					lessonId = bookingModel.getLessonId();
					bookingModel.setCurrentStatus("Cancelled");
				}else if( bookingModel.getCurrentStatus().equals("Cancelled") ) {
					System.out.println("This booking is already cancelled.\n.");
					return Boolean.TRUE;
				}else if( bookingModel.getCurrentStatus().equals("Attended") ) {
					System.out.println("\nThe lesson was already attended.\n.");
					return Boolean.TRUE;
				}				
				for( LessonModel lessonModel : Data.lessonsDtls ) {
					if( lessonModel.getId() == lessonId ) {						
						avlSeats = lessonModel.getAvlSeats();
						lessonModel.setAvlSeats(avlSeats + 1);
						System.out.println("The booking has been cancelled successfully.");
						return Boolean.TRUE;
					}
				}				
			}
		}
		if(!isBookingIdValid)
			System.out.println("\nSorry, Invalid Booking Id.");
		
		return Boolean.TRUE;
	}
	
	private Boolean proceedBookingChange( String bookingId, String usrContactNo ) {
		Integer lessonId = null;
		Integer avlSeats = null;
		String newBookingOption = null;
		MarkerCls flagObj = null;
		Boolean isBookingValid = Boolean.FALSE;
		
		for( BookingModel bookingModel : Data.bookingDataList ) {
			if( bookingModel.getBookingId() == Integer.parseInt(bookingId) ) {				
				isBookingValid = Boolean.TRUE;
				if( bookingModel.getCurrentStatus().equals("Booked") ) {
					flagObj = new MarkerCls();
					lessonId = bookingModel.getLessonId();				
					System.out.println("\nPlease book a new lesson.\n");
					flagObj.setBookingModel(bookingModel);
					flagObj.setStatus(Boolean.FALSE);
					performOprByOption(1, usrContactNo, flagObj);					
					
					if( flagObj.getStatus() ) {
						for( LessonModel lessonModel : Data.lessonsDtls ) {
							if( lessonModel.getId() == lessonId ) {						
								avlSeats = lessonModel.getAvlSeats();
								lessonModel.setAvlSeats(avlSeats + 1);
								System.out.println("\nBooking has now been changed successfully.\n");
								return Boolean.TRUE;
							}
						}
					}					
				}else if( bookingModel.getCurrentStatus().equals("Cancelled") ) {
					System.out.println("\nThis booking was cancelled.\n");
					return Boolean.TRUE;
				}else if( bookingModel.getCurrentStatus().equals("Attended") ) {
					System.out.println("\nThe lesson was already attended through this booking.\n");
					return Boolean.TRUE;
				}		
			}
		}
		if(!isBookingValid)
			System.out.println("\nInvalid Booking Id, please try again with valid input.\n");
		
		return Boolean.TRUE;
	}
	
	private Boolean attendingLesson( String bookingId, String usrContactNo ) {		
		Integer lessonId = null;
		String coachRating = null;
		int learnersCurrGrade = 0;
		int coachAvgRatingRoundedOff = 0;
		int coachfinalRating = 0;
		Boolean isBookingValid = Boolean.FALSE;
		CoachRatingModel coachRatingModel = null;
		List<CoachRatingModel> coachRatingList = null;
		LearnerModel learnerModel = Data.learnersRegDataMap.get(usrContactNo);
		
		for( BookingModel bookingModel : Data.bookingDataList ) {
			if( bookingModel.getBookingId() == Integer.parseInt(bookingId) ) {				
				isBookingValid = Boolean.TRUE;
				if( bookingModel.getCurrentStatus().equals("Booked") ) {
					lessonId = bookingModel.getLessonId();
					learnersCurrGrade = learnerModel.getGrade();
					
					bookingModel.setCurrentStatus("Attended");
					if( learnersCurrGrade < bookingModel.getLevel() ) 
						learnerModel.setGrade(learnersCurrGrade + 1);
					
					System.out.println("\nPlease submit your review.\n");
					coachRating = appUtility.readUsrInputStr();
					System.out.println("\nPlease enter coach rating on a scale of 1-5.\n");
					coachRating = appUtility.readUsrInputStr();
					while ( !ValueRange.of(1, 5).isValidIntValue( Integer.parseInt(coachRating) ) ) {
						System.out.println("Invalid input, please input value between 1-5 only.");
						coachRating = appUtility.readUsrInputStr();
					}
					coachRatingList = Data.coachRatingMap.get( bookingModel.getMonthNum() );
					if( coachRatingList != null && coachRatingList.size() > 0 ) {
						for( CoachRatingModel coachRatingModelTemp : coachRatingList ) {
							if( coachRatingModelTemp.getCoachName().equals(bookingModel.getCoach()) ) {
								coachAvgRatingRoundedOff = coachRatingModelTemp.getCoachRating();
								coachRatingModel = coachRatingModelTemp;
							}
						}
					}
					coachfinalRating = coachAvgRatingRoundedOff != 0 ? Math.round( ( coachAvgRatingRoundedOff + Integer.valueOf(coachRating) )/2 ) : Integer.valueOf(coachRating);					
					if( coachRatingModel != null ) {
						coachRatingModel.setCoachRating(coachfinalRating);
					}else if( coachRatingList != null  ) {
						coachRatingModel = new CoachRatingModel();
						coachRatingModel.setCoachName( bookingModel.getCoach() );
						coachRatingModel.setCoachRating(coachfinalRating);
						coachRatingList.add(coachRatingModel);
					}else if( coachRatingList == null ) {
						coachRatingList = new ArrayList<>();
						coachRatingModel = new CoachRatingModel();
						coachRatingModel.setCoachName( bookingModel.getCoach() );
						coachRatingModel.setCoachRating(coachfinalRating);
						coachRatingList.add(coachRatingModel);
						
						Data.coachRatingMap.put(bookingModel.getMonthNum(), coachRatingList);
					} 
					System.out.println("\nLesson attended successfully.\n");
					return Boolean.TRUE;
				}else if( bookingModel.getCurrentStatus().equals("Cancelled") ) {
					System.out.println("Booking was cancelled.\n");
					return Boolean.TRUE;
				}else if( bookingModel.getCurrentStatus().equals("Attended") ) {
					System.out.println("\nLesson already attended against this Booking.\n");
					return Boolean.TRUE;
				}				
			}
		}		
		if( !isBookingValid )
			System.out.println("\nInvalid booking id.\n");
		
		return Boolean.TRUE;
	}

}
