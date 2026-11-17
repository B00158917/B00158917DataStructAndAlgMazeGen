import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

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

    private static void GenerateMaze() throws InterruptedException{
        startPos[0] = 4;
        startPos[1] = 0;
        endPos = generateEdgePos();
        
        ArrayList<int[]> posStack = new ArrayList<int[]>();
        
        posStack.add(startPos);
        while(!posStack.isEmpty()){
            ArrayList<int[]> emptyAdjacencies = new ArrayList<int[]>();
            int[] stackTop = posStack.getLast();
            try{
                if(grid[stackTop[0] + 1][stackTop[1]] == 0){
                    emptyAdjacencies.add(new int []{stackTop[0] + 1, stackTop[1], 1});
                }
            }catch(ArrayIndexOutOfBoundsException e){
                
            }
            try{
                if(grid[stackTop[0] - 1][stackTop[1]] == 0){
                    emptyAdjacencies.add(new int []{stackTop[0] - 1, stackTop[1], 2});
                }
            }catch(ArrayIndexOutOfBoundsException e){
                
            }
            try{
                if(grid[stackTop[0]][stackTop[1] - 1] == 0){
                    emptyAdjacencies.add(new int []{stackTop[0], stackTop[1] - 1, 8});
                }
            }catch(ArrayIndexOutOfBoundsException e){
                
            }
            try{
                if(grid[stackTop[0]][stackTop[1] + 1] == 0){
                    emptyAdjacencies.add(new int []{stackTop[0], stackTop[1] + 1, 4});
                }
            }catch(ArrayIndexOutOfBoundsException e){
                
            }
            /*for(int[] x : emptyAdjacencies){
                System.out.print(x[1] +" "+ x[2] + " ");
            }*/
            System.out.println(emptyAdjacencies.size());
            if(emptyAdjacencies.isEmpty()){
                posStack.removeLast();
            }else{
                int[] chosenAdjacency = emptyAdjacencies.get(random.nextInt(emptyAdjacencies.size()));
                grid[stackTop[0]][stackTop[1]] += chosenAdjacency[2];
                posStack.add(new int[]{chosenAdjacency[0],chosenAdjacency[1]});
                stackTop = posStack.getLast();
                switch(chosenAdjacency[2]){
                    case 1:
                        grid[stackTop[0]][stackTop[1]] += 2;
                        break;
                    case 2:
                        grid[stackTop[0]][stackTop[1]] += 1;
                        break;
                    case 4:
                        grid[stackTop[0]][stackTop[1]] += 8;
                        break;
                    case 8:
                        grid[stackTop[0]][stackTop[1]] += 4;
                        break;
                }
            }
            stackTop = posStack.getLast();
            System.out.println(stackTop[0] + "" + stackTop[1]);
            Display.displayGrid(grid);
            TimeUnit.MILLISECONDS.sleep(500);
        }
    }

    private static int[] generateEdgePos() {
        int PosNum = App.random.nextInt(2 * (grid.length - 1) + 2 * (grid[0].length - 1)) - 2 * (grid.length - 1);
        int[] generatedPos = new int[2];
        if(PosNum < grid.length - 1){
            generatedPos[0] = PosNum;
            generatedPos[1] = 0;
        } else if (PosNum < (grid.length - 1) * 2){
            generatedPos[0] = PosNum + 1 - (grid.length - 1);
            generatedPos[1] = grid[0].length - 1;
        } else if (PosNum < (grid.length - 1) * 2 + (grid[0].length - 1)){
            generatedPos[1] = PosNum + 1 - (grid.length - 1) * 2;
            generatedPos[0] = 0;
        } else {
            generatedPos[1] = PosNum - ((grid.length - 1) * 2 + (grid[0].length - 1));
            generatedPos[0] = grid.length - 1;
        }
        return generatedPos;
    }

    private static int readInt(){
        int val = input.nextInt();
        input.nextLine();
        return val;
    }

    
}
