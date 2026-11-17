import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class App {
    static private Scanner input;
    private static byte[][] grid;
    public static int[] startPos = new int[2];
    public static int[] endPos = new int[2];
    public static Random random = new Random();
    public static void main(String[] args) throws Exception {
        input = new Scanner(System.in);
        System.out.println("Input Maze X Axis Size:");
        int x = readInt();
        System.out.println("Input Maze Y Axis Size:");
        int y = readInt();
        grid = new byte[y][x];
        for(byte[] b : grid)
            Arrays.fill(b, (byte)0);
        Display.displayGrid(grid);
        GenerateMaze();
    }

    public static void GenerateMaze(){
        int totalPossibleStartingPositions = 2 * (grid.length - 1) + 2 * (grid[0].length - 1);
        int startingPosNum = App.random.nextInt(totalPossibleStartingPositions) - 2 * (grid.length - 1);
        if(startingPosNum < grid.length - 1){
            startPos[0] = startingPosNum;
            startPos[1] = 0;
        } else if (startingPosNum < (grid.length - 1) * 2){
            startPos[0] = startingPosNum + 1 - (grid.length - 1);
            startPos[1] = grid[0].length - 1;
        } else if (startingPosNum < (grid.length - 1) * 2 + (grid[0].length - 1)){
            startPos[1] = startingPosNum + 1 - (grid.length - 1) * 2;
            startPos[0] = 0;
        } else {
            startPos[1] = startingPosNum - ((grid.length - 1) * 2 + (grid[0].length - 1));
            startPos[0] = grid.length - 1;
        }
    }

    

    private static int readInt(){
        int val = input.nextInt();
        input.nextLine();
        return val;
    }

    
}
