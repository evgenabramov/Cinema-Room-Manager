package cinema;

import java.util.Arrays;
import java.util.Scanner;

public class Cinema {
    static void PrintLayout(int numRows, int numSeatsInRow, char[][] layout) {
        System.out.println("\nCinema:");
        System.out.print("  ");
        for (int i = 1; i <= numSeatsInRow; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        for (int row = 1; row <= numRows; row++) {
            System.out.print(row + " ");
            for (int seat = 1; seat <= numSeatsInRow; seat++) {
                System.out.print(layout[row - 1][seat - 1] + " ");
            }
            System.out.println();
        }
    }

    static int FindTotalIncome(int numRows, int numSeatsInRow) {
        int totalNumSeats = numSeatsInRow * numRows;
        if (totalNumSeats <= 60) {
            return 10 * totalNumSeats;
        }
        int numRowsInFirstHalf = numRows / 2;
        int numRowsInSecondHalf = numRows - numRowsInFirstHalf;
        return numSeatsInRow * (10 * numRowsInFirstHalf + 8 * numRowsInSecondHalf);
    }

    static int FindPlacePrice(int numRows, int numSeatsInRow, int row, int seatInRow) {
        int totalNumSeats = numSeatsInRow * numRows;
        if (totalNumSeats <= 60) {
            return 10;
        }
        final int firstHalfPrice = 10;
        final int secondHalfPrice = 8;
        return row <= numRows / 2 ? firstHalfPrice : secondHalfPrice;
    }

    static void PrintOptions() {
        System.out.print("""

                1. Show the seats
                2. Buy a ticket
                3. Statistics
                0. Exit
                >\s"""
        );
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the number of rows:");
        System.out.print("> ");
        int numRows = scanner.nextInt();

        System.out.println("Enter the number of seats in each row");
        System.out.print("> ");
        int numSeatsInRow = scanner.nextInt();

        char[][] layout = new char[numRows][numSeatsInRow];
        for (char[] chars : layout) {
            Arrays.fill(chars, 'S');
        }

        while (true) {
            PrintOptions();
            int option = scanner.nextInt();
            int row, seatInRow;
            switch (option) {
                case 1:
                    PrintLayout(numRows, numSeatsInRow, layout);
                    break;
                case 2:
                    while (true) {
                        System.out.println("Enter a row number:");
                        System.out.print("> ");
                        row = scanner.nextInt();

                        System.out.println("Enter a seat number in that row:");
                        System.out.print("> ");
                        seatInRow = scanner.nextInt();

                        if (row < 1 || row > numRows || seatInRow < 1 || seatInRow > numSeatsInRow) {
                            System.out.println("Wrong input!");
                        } else if (layout[row - 1][seatInRow - 1] == 'B') {
                            System.out.println("That ticket has already been purchased!");
                        } else {
                            break;
                        }
                    }
                    layout[row - 1][seatInRow - 1] = 'B';

                    int placePrice = FindPlacePrice(numRows, numSeatsInRow, row, seatInRow);
                    System.out.print("Ticket price: $");
                    System.out.println(placePrice);
                    break;
                case 3:
                    int currentPrice = 0;
                    int purchasedCount = 0;
                    for (row = 1; row <= numRows; row++) {
                        for (seatInRow = 1; seatInRow <= numSeatsInRow; seatInRow++) {
                            if (layout[row - 1][seatInRow - 1] == 'B') {
                                purchasedCount++;
                                currentPrice += FindPlacePrice(numRows, numSeatsInRow, row, seatInRow);
                            }
                        }
                    }

                    System.out.printf("Number of purchased tickets: %d\n", purchasedCount);
                    System.out.printf("Percentage: %.2f%%\n", (float)purchasedCount / (numRows * numSeatsInRow) * 100);
                    System.out.printf("Current income: $%d\n", currentPrice);
                    System.out.printf("Total income: $%d\n", FindTotalIncome(numRows, numSeatsInRow));
                    break;
                case 0:
                    return;
                default:
                    break;
            }
        }
    }
}