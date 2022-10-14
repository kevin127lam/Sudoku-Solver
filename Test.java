package Assignment.Assignment3;

public class Test {
    public static void main(String[] args) {
        long time = 0;
        long endTime = 0;
        Board puzzle = new Board("sudoku.txt");
        System.out.println(puzzle);
        try {
            System.out.println("Solved!");
            time = System.nanoTime();
            puzzle.solve();
            endTime = System.nanoTime();
            System.out.println(puzzle);
        } catch (IllegalArgumentException e) {
            System.out.println("Illegal move");
        }
        System.out.println("Puzzle was executed in " + ((endTime - time)/1000000) + " milliseconds.");

    }
}
