public final class Display {
    public static void displayGrid(byte[][] grid){
        for(byte[] a : grid){
            for(byte b : a){
                System.out.printf(" %x ", b);
            }
            System.out.println();
        }
    }
}
