package util;

import java.util.Scanner;

public class Utility {

	public String readUsrInputStr() {
		Scanner scanner = null;		
		scanner = new Scanner(System.in);		
		
		return scanner.nextLine();
	}
}
