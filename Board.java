package Assignment.Assignment3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Board {
    ArrayList<ArrayList<Integer>> board = new ArrayList<ArrayList<Integer>>();
    ArrayList<Integer> availableNumbers = new ArrayList<Integer>();
    private final int EMPTY = 0;

    /**
     * should initialize the two-dimensional ArrayList board to create a 9x9 grid,
     * setting each element to EMPTY (0) to start. This value indicates an empty
     * cell (one without an assigned value).
     * 
     * @param filename
     */
    public Board(String filename) {
        for (int i = 0; i < 9; i++) {
            availableNumbers.add(9);
        }
        for (int i = 0; i < 9; i++) {
            board.add(new ArrayList<Integer>());
            for (int j = 0; j < 9; j++) {
                board.get(i).add(EMPTY);
            }
        }

        readBoard(filename);
    }

    /**
     * reads the original text file and calls checkMove and isAvailable accordingly
     * as a digit is used, it also decrements its corresponding value in
     * availableNumbers
     * 
     * @param filename
     * @throws IllegalArgumentException
     */
    private void readBoard(String filename) throws IllegalArgumentException {
        File file = new File("sudoku.txt");
        int digit = 0;
        try {
            Scanner readFile = new Scanner(file);
            for (int i = 0; i < 9; i++) {
                board.add(new ArrayList<>());
                for (int j = 0; j < 9; j++) {
                    // creates digit which is the current element
                    digit = Integer.valueOf(readFile.next());
                    board.get(i).set(j, digit);
                    if (isAvailable(digit)) {
                        // decrements the value at index
                        switch (digit) {
                            case 1:
                                availableNumbers.set(0, availableNumbers.get(0) - 1);
                                break;
                            case 2:
                                availableNumbers.set(1, availableNumbers.get(1) - 1);
                                break;
                            case 3:
                                availableNumbers.set(2, availableNumbers.get(2) - 1);
                                break;
                            case 4:
                                availableNumbers.set(3, availableNumbers.get(3) - 1);
                                break;
                            case 5:
                                availableNumbers.set(4, availableNumbers.get(4) - 1);
                                break;
                            case 6:
                                availableNumbers.set(5, availableNumbers.get(5) - 1);
                                break;
                            case 7:
                                availableNumbers.set(6, availableNumbers.get(6) - 1);
                                break;
                            case 8:
                                availableNumbers.set(7, availableNumbers.get(7) - 1);
                                break;
                            case 9:
                                availableNumbers.set(8, availableNumbers.get(8) - 1);
                                break;
                            default:
                                break;
                        }
                    } else {
                        throw new IllegalArgumentException();
                    }
                }
            }
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if (checkMove(i, j) == false) {
                        throw new IllegalArgumentException();
                    }
                }
            }
            readFile.close();
        } catch (IllegalArgumentException e) {
            System.out.println("Illegal move detected");
        } catch (FileNotFoundException e) {
            System.out.println("File not sound");
        }
    }

    /**
     * determines if a value from availablenumbers is available
     * 
     * @param digit
     * @return
     */
    private boolean isAvailable(int digit) {
        for (int i = 1; i < 10; i++) {
            if (digit != 0) {
                if (digit == i) {
                    for (int j = 0; j < 9; j++) {
                        if (availableNumbers.get(digit - 1) < 0) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * returns true if there still numbers available within availablenumbers
     * 
     * @return
     */
    private boolean noNumbersLeft() {
        int zeroCount = 0;
        for (int i = 0; i < 9; i++) {
            if (availableNumbers.get(i) == 0) {
                zeroCount++;
            }
        }
        if (zeroCount >= 9) {
            return true;
        }
        return false;
    }

    /**
     * checks if inputting a value at a spot is valid
     * 
     * @param row
     * @param col
     * @return
     */
    private boolean checkMove(int row, int col) {
        int index = 0;

        // get the element at row/col
        index = board.get(row).get(col);
        if (index == 0) {
            return true;
        }
        for (int i = 0; i < 9; i++) {
            if (board.get(i).get(col) == index && (i != row)) {
                return false;
            }
        }
        for (int i = 0; i < 9; i++) {
            if (board.get(row).get(i) == index && (i != col)) {
                return false;
            }
        }
        // check box
        int r = row - row % 3;
        int c = col - col % 3;

        for (int i = r; i < r + 3; i++) {
            for (int j = c; j < c + 3; j++) {
                if (board.get(i).get(j) == index && (i != row) && (j != col)) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean solve() {
        return solve(0, 0);
    }

    /**
     * solves helper method
     * 
     * @param row
     * @param col
     * @return
     */
    public boolean solve(int row, int col) {
        // base case(s)
        if (noNumbersLeft() || row == 9) {
            return true;
        }
        if (col == 9) {
            return solve(row + 1, 0);
        }
        if (board.get(row).get(col) != 0) {
            return solve(row, col+1);
        }
        if (board.get(row).get(col) == 0) {
            for (int i = 1; i < 10; i++) {
                board.get(row).set(col, i);
                //recursive step
                if (checkMove(row, col) && solve(row, col)) {
                    availableNumbers.set((i-1), availableNumbers.get(i-1)-1);
                    return true;
                }
            }
            board.get(row).set(col, 0);
            return false;
        }
        return false;

    }

    /**
     * toString method that prints the new file
     */
    public String toString() {
        String out = "";
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                out += board.get(i).get(j) + " ";
            }
            out += "\n";
        }
        for (int i = 0; i < 9; i++) {
            out += availableNumbers.get(i) + ",";
        }
        return out;
    }

}