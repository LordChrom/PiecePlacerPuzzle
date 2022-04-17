package puzzle;

import java.util.HashSet;

public class Dealer {
    public static final long boundaries = 0b1111100000000000100000000000100000000000100000000000100000000000L;
    public static final long bottomRow = 0b11111111111;
    public static final long rightRow = 0b000000000001000000000001000000000001000000000001000000000001L;

    public static final long[] pieceTypes = {
            0b000000000000000000000001000000000001000000000011L, //l  L
            0b000000000000000000000001000000000011000000000011L, //d  d
            0b000000000001000000000001000000000001000000000011L, //L  tall L
            0b000000000001000000000001000000000011000000000001L, //j  drunk L
            0b000000000001000000000001000000000011000000000010L, //hp harry potter scar
            0b000000000000000000000000000000000001000000000011L, //sc small corner
            0b000000000000000000000001000000000001000000000111L, //bc bigger corner
            0b000000000000000000000001000000000011000000000110L, //dl diagonal line
            0b000000000000000000000000000000000101000000000111L, //u  cup
            0b000000000001000000000001000000000001000000000001L, //ln |
            0b000000000000000000000000000000000011000000000011L, //sq square
            0b000000000000000000000010000000000111000000000010L, //pl +
    };
    public static final int typeCount = pieceTypes.length;
    //    public enum type{l,d,L,j,hp,sc,bc,dl,u,ln,sq,pl}

    public static long[] permutePiece(long piece){
        HashSet<Long> list = new HashSet<>();
        for (int i = 0; i < 4; i++) {
            list.add(piece);
            list.add(flipV(piece));
            piece = rotate(piece);
        }

        long[] ret = new long[list.size()];
        int i = 0;
        for(long l: list)
            ret[i++] = l;
        return ret;
    }

    public static Piece[][] generateAllPieces(){
        Piece[][] ret = new Piece[typeCount][];
        for (int type = 0; type < typeCount; type++) {
            long[] permutePieces = permutePiece(pieceTypes[type]);
            ret[type] = new Piece[permutePieces.length];
            for (int perm = 0; perm < permutePieces.length; perm++)
                ret[type][perm] = new Piece(permutePieces[perm]);
        }
        return ret;
    }

    public static long flipV(long piece){ //flips piece vertically
        long out = 0;
        for (int y = 0; y < 5; y++)
            out|=((piece>>(y*12))&bottomRow)<<((4-y)*12);
        while((out&bottomRow)==0) out>>=12; //aligns to bottom
        return out;
    }

    public static long rotate(long piece){ //rotates piece
        long out = 0;
        for (int y = 0; y < 5; y++)
            for (int x = 0; x < 5; x++) //iterates through each bit
                out|= (((piece & ((1L<<x)<<(12*y))) ==0?0L:1L)<<y)<<(12*(4-x)); //moves bit from (x,y) to (y,4-x)
        while((out&bottomRow)==0) out>>=12; //aligns to bottom
        return out;
    }
}