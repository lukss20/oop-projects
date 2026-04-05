import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SudokuTest {

    //this method tests solving of hardgrid its solutions number and output of the first solution
    @Test
    public void testSolve() {
        Sudoku sudoku = new Sudoku(Sudoku.hardGrid);
        int solutions = sudoku.solve();
        assertEquals(1,solutions);
        String solution = sudoku.getSolutionText();
        String mysolution = "3 7 5 1 6 2 4 8 9 \n"+
        "8 6 1 4 9 3 5 2 7 \n" +
        "2 4 9 7 8 5 1 6 3 \n" +
        "4 9 3 8 5 7 6 1 2 \n" +
        "7 1 6 2 4 9 8 3 5 \n" +
        "5 2 8 3 1 6 7 9 4 \n" +
        "6 5 7 9 2 1 3 4 8 \n" +
        "1 8 2 5 3 4 9 7 6 \n" +
        "9 3 4 6 7 8 2 5 1 \n";
        assertEquals(mysolution, solution);
    }
    //this method tests a grid with no answer because of matching numbers in rows and columns
    @Test
    public void testNoAnswer() {
        String noanswer = "550000001234500000220000123400012000012345600066000000000440001234567000880012340";
        Sudoku sudoku = new Sudoku(noanswer);
        int solutions = sudoku.solve();
        assertEquals(0, solutions);
    }
    //this method should throw a runtime exception because of invalid input
    @Test
    public void testInvalidInput() {
        String invalidInput = "12345";
        Exception exception = assertThrows(RuntimeException.class, () -> {
            new Sudoku(invalidInput);
        });
        String expectedMessage = "Needed 81 numbers, but got:5";
        assertEquals(expectedMessage, exception.getMessage());

    }
    //this method checks if your recursion stops on many solutions
    @Test
    public void testMaxSolution() {
        String maxSolution = "000000000000000000000000000000000000000000000000000000000000000000000000000000000";
        Sudoku sudoku = new Sudoku(maxSolution);
        int solutions = sudoku.solve();
        long time  = sudoku.getElapsed();
        assertTrue(time > 0);
        assertEquals(100,solutions);
    }
    @Test
    public void testAlreadySolved() {
        String solved  = "534678912672195348198342567859761423426853791713924856961537284287419635345286179";
        Sudoku sudoku = new Sudoku(solved);
        int solutions = sudoku.solve();
        assertEquals(1,solutions);
    }
}
