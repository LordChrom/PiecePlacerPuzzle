package puzzle;

import java.util.ArrayList;

import static puzzle.Dealer.typeCount;

public class Solver {
    public static final long rightWall =Dealer.rightRow;
    public static final Piece[][] pieces = Dealer.generateAllPieces();

    public static ArrayList<Long> solution = new ArrayList<>(typeCount);

    public static void solve(){
        solution.clear();
        solve(Dealer.boundaries, new boolean[typeCount],0);
    }

    private static boolean solve(long board, boolean[] used, int x){
        if(~board == 0) return true;

        while(((~board)&(rightWall<<x))==0) x++;
        int y = 0;
        for(long i = 1L<<x; (i&board)!=0; i=(1L<<x)<<(y*12))
            y++;

        for (int type = 0; type < typeCount; type++) {
            if(used[type])
                continue; //this just means "skip the rest of the loop"
            used[type]=true;

            for (int i = 0; i < pieces[type].length; i++) {
                Piece p = pieces[type][i];
                if ((y + p.heightAbove <= 5) && (y >= p.vPos)) {
                    long shiftedPiece = p.data<<(x+12*(y- p.vPos));
                    if ((board & shiftedPiece) == 0) {
                        if(solve(board | shiftedPiece,used,x)) {
                            solution.add(shiftedPiece);
                            return true;
                        }
                    }
                }
            }
            used[type]=false;
        }
        return false;
    }
}