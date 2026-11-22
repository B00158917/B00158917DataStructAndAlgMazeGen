import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class App {
    static private Scanner input;
    private static int[][] grid;
    public static int[] startPos = new int[2];
    public static int[] endPos = new int[2];
    public static Random random = new Random();
    public static JFrame frame;
    public static void main(String[] args) throws Exception {
        frame = new JFrame();
        frame.setSize(720, 720);
        new Thread() {
            public void run(){
                input = new Scanner(System.in);
                System.out.println("Input Maze X Axis Size:");
                int x = readInt();
                System.out.println("Input Maze Y Axis Size:");
                int y = readInt();
                grid = new int[y][x];
                for(int[] b : grid)
                    Arrays.fill(b, (byte)0);
                try {
                    displayGrid(grid);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                try {
                    GenerateMaze();
                } catch (InvocationTargetException | InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }.start();;
    }

    private static void GenerateMaze() throws InterruptedException, InvocationTargetException{
        for(int i = 0; i < 255; i++){
            startPos = generateEdgePos();
        }
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
            displayGrid(grid);
        }
    }

    private static int[] generateEdgePos() {
        int posNum = App.random.nextInt(2 * (grid.length - 1) + 2 * (grid[0].length - 1));
        int[] generatedPos = new int[2];
        if(posNum < grid.length - 1){
            generatedPos[0] = posNum;
            generatedPos[1] = 0;
        } else if (posNum < (grid.length - 1) * 2){
            generatedPos[0] = posNum + 1 - (grid.length - 1);
            generatedPos[1] = grid[0].length - 1;
        } else if (posNum < (grid.length - 1) * 2 + (grid[0].length - 1)){
            generatedPos[1] = posNum + 1 - (grid.length - 1) * 2;
            generatedPos[0] = 0;
        } else {
            generatedPos[1] = posNum - ((grid.length - 1) * 2 + (grid[0].length - 1));
            generatedPos[0] = grid.length - 1;
        }
        return generatedPos;
    }

    private static int readInt(){
        int val = input.nextInt();
        input.nextLine();
        return val;
    }

    public static void displayGrid(int[][] grid) throws InterruptedException {
        String gridString = "";
        for(int[] a : grid){
            for(int b : a){
                gridString = gridString.concat(String.format(" %x ", b));
            }
            gridString = gridString.concat(String.format("\n"));
        }
        System.out.println(gridString);
        
        frame.getContentPane().add(new JComponent() {
            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                for(int y = 0; y < grid.length; y++){
                    for(int x = 0; x < grid[1].length; x++){
                        int xMult = x*8;
                        int yMult = y*8;
                        g2.setColor(Color.BLACK);
                        g2.drawRect(xMult, yMult, 8, 8);
                        g2.setColor(Color.WHITE);
                        int currSquare = grid[y][x];
                        if(currSquare % 2 == 1){
                            g2.drawLine(xMult, yMult+8, xMult+8, yMult+8);
                        }
                        currSquare /= 2;
                        if(currSquare % 2 == 1){
                            g2.drawLine(xMult, yMult, xMult+8, yMult);
                        }
                        currSquare /= 2;
                        if(currSquare % 2 == 1){
                            g2.drawLine(xMult+8, yMult, xMult+8, yMult+8);
                        }
                        currSquare /= 2;
                        if(currSquare % 2 == 1){
                            g2.drawLine(xMult, yMult, xMult, yMult+8);
                        }
                    }
                }
            }
        });
        frame.setVisible(true);
        
        TimeUnit.MILLISECONDS.sleep(500);
    }
}
