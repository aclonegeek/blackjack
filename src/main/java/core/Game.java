package core;

import java.util.Scanner;

public class Game {
	public static void main(String[] args) {
		System.out.println("Enter c for console input and f for file input.");

		String option = new String();
		Scanner input = new Scanner(System.in);
		option = input.nextLine();
	
		if (option == "c") {
			playWithConsole();
		} else if (option == "f") {
			playWithFile();
		}

		input.close();
	}

	public static void playWithConsole() {
		
	}

	public static void playWithFile() {
		
	}
}
