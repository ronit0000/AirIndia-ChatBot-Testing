package com.demo;

import java.util.Scanner;

public class DriverClass {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n=== Air India ChatBot Testing ===");
            System.out.println("1. Air India Booking");
            System.out.println("2. Flight Status");
            System.out.println("3. Travel Guide");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("🔹 Running Air India Booking Test...");
                    AirIndiaBooking.main(null);
                    break;
                case 2:
                    System.out.println("🔹 Running Flight Status Test...");
                    FlightStatus.main(null);
                    break;
                case 3:
                    System.out.println("🔹 Running Travel Guide Test...");
                    TravelGuide.main(null);
                    break;
                case 0:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("⚠ Invalid choice! Try again.");
            }

        } while (choice != 0);

        sc.close();
    }
}
