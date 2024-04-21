import adminOperations.AdminRoute;
import data.Data;
import learnerOperations.LearnerRoute;
import util.Utility;


public class SwimAppMain {

	private static Boolean isProgRun = null;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String inputStr = null;
		SwimAppMain swimAppMainObj = new SwimAppMain();
		Utility appUtility = new Utility();		
		AdminRoute adminRoute = null;
		LearnerRoute learnerRoute = null;
		setDummyData();
		isProgRun = Boolean.TRUE;

		while( isProgRun ) {
			try {
				int userInputNo = 0;
				swimAppMainObj.appHomeOptions();
				inputStr = appUtility.readUsrInputStr();
				userInputNo = Integer.parseInt(inputStr);
				
				switch (userInputNo) {
				case 1:
//					Learner Registration
					adminRoute = new AdminRoute();
					adminRoute.learnerRegistration();
					break;
				case 2:															
//					Monthly Learner Report
					adminRoute = new AdminRoute();
					adminRoute.createLearnerMonthlyReport();
					break;
				case 3:
//					Monthly Coach Report
					adminRoute = new AdminRoute();
					adminRoute.createCoachMonthlyReport();
					break;
				case 4:
//					Login as a Learner
					Boolean continueLearnerMenu = Boolean.TRUE;
					learnerRoute = new LearnerRoute();
					String inputPwdStr = null;
					int optionNo = 0;
					System.out.println("Please enter your contact number.");
					inputStr = appUtility.readUsrInputStr();
					if( !learnerRoute.isLearnerUsrExists( inputStr ) ) {
						System.out.println("\nOops! User with the input contact number does not exist.\n");
						break;
					}
					System.out.println("Please enter password to login.");
					inputPwdStr = appUtility.readUsrInputStr();
					if( !learnerRoute.isPwdValid( inputStr, inputPwdStr ) ) {
						System.out.println("\nSorry! The password entered is incorrect.\n");
						break;
					}					
					while( continueLearnerMenu ) {
						learnerRoute.dispLearnerDashboard();
						optionNo = Integer.parseInt( appUtility.readUsrInputStr() );
						continueLearnerMenu = learnerRoute.performOprByOption( optionNo, inputStr, null );
					}					
					break;
				case 5:
					isProgRun = Boolean.FALSE;
					System.out.println("\nApplication terminated.\n");
					break;	
				default:
					isProgRun = Boolean.TRUE;
					System.out.println("\nPlease select valid option.\n");
					break;
				}								
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("\nThe process encountered an error, the application has been restarted.\nPlease try again with proper inputs.\n");
				isProgRun = Boolean.TRUE;
			}
		}
		
	}
	
	private void appHomeOptions() {
		System.out.println( "Welcome User.\n" + "Please select your option.\n" );
		System.out.println( "1. Learner Registration" );
		System.out.println( "2. Monthly Learner Report" );
		System.out.println( "3. Monthly Coach Report" );
		System.out.println( "4. Login as a Learner User" );
		System.out.println( "5. Exit Application" );
		System.out.println( "" );
	}
	
	private static void setDummyData() {
		Data.setLearnersRegData();
		Data.setBookingData();
		Data.setCoachRatingData();
		Data.setLessons();
	}

}
