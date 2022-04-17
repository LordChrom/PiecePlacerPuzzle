package puzzle;

import java.util.ArrayList;

public class Main {
    public static final char[] flavors = "█▓▒░X+".toCharArray();

    public static void main(String[] args) {
        timeSolves(10000 );
    }

    public static void timeSolves(int count){
        System.out.println("doing "+count+" Solves");
        long time = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            Solver.solve();
        }
        long timeTaken = System.currentTimeMillis()-time;
        System.out.println("completed in " + timeTaken+"ms, with an average of "+(double)timeTaken/count+"ms");
        printSolution();
    }

    private static void printSolution(){printSolution(Solver.solution);}
    private static void printSolution(ArrayList<Long> solution){
        char[][] board = new char[5][11];
        for (int i = 0; i < solution.size(); i++) {
            long l = solution.get(i);
            for (int x = 0; x < 11; x++) {
                for (int y = 0; y < 5; y++) {
                    if((l&((1L<<x)<<(y*12)))!=0)
                        board[y][x]=flavors[i%flavors.length];
                }
            }
        }
        for (char[] chars : board) System.out.println(chars);
    }
}