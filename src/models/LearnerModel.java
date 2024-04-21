package models;

public class LearnerModel {
	
	private String name;
	private String gender;
	private int age;
	private String contactNo;
	private int grade;
	private String password;
	private int totalBookings;
	private int cancelledBookings;
	private int attendedLessons;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getContactNo() {
		return contactNo;
	}
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getTotalBookings() {
		return totalBookings;
	}
	public void setTotalBookings(int totalBookings) {
		this.totalBookings = totalBookings;
	}
	public int getCancelledBookings() {
		return cancelledBookings;
	}
	public void setCancelledBookings(int cancelledBookings) {
		this.cancelledBookings = cancelledBookings;
	}
	public int getAttendedLessons() {
		return attendedLessons;
	}
	public void setAttendedLessons(int attendedLessons) {
		this.attendedLessons = attendedLessons;
	}
		
}
