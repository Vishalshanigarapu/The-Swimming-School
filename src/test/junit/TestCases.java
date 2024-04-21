package test.junit;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import data.Data;
import learnerOperations.LearnerRoute;

public class TestCases {

	@BeforeClass
	 public static void setData() throws Exception {  

		Data.setLearnersRegData();
		Data.setBookingData();
		Data.setCoachRatingData();
		Data.setLessons();
	}
	
	@Test
	public void isLearnerExist() {
		LearnerRoute learnerRoute = new LearnerRoute();
		assertEquals(Boolean.FALSE, learnerRoute.isLearnerUsrExists("1234") );
		assertEquals(Boolean.TRUE, learnerRoute.isLearnerUsrExists("(333) 444-2266") );
		
	}
	
	@Test
	public void isPwdValid() {
		LearnerRoute learnerRoute = new LearnerRoute();
		assertEquals(Boolean.TRUE, learnerRoute.isPwdValid("(333) 444-2266", "password@1") );
		assertEquals(Boolean.FALSE, learnerRoute.isPwdValid("(333) 444-2266", "password@2") );
		
	}
	
	@Test
	public void isActiveBookingExistForUsr() {
		LearnerRoute learnerRoute = new LearnerRoute();
		assertEquals(Boolean.FALSE, learnerRoute.checkActiveBookingsForUsr("(333) 444-2266") );		
	}
}
