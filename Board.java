package client;

import static client.Board.Direction.*;

public class Board {

    Symbol[][] board;
    int[][] checks;

    static int WITH_COORDS = 1;

    int size;
    int win = 3;

    enum Direction{
        L, R, U, D, UL, UR, DL, DR;
    }

    static Direction[] directions = {L, R, U, D, UL, UR, DL, DR};

    public Board(int size){
        board = new Symbol[size][size];
        checks = new int[size][size];
        this.size = size;
    }
    public Board(int size, int wins){
        this(size);
        this.win = wins;
    }

    public void makeMove(int x, int y, Symbol s) throws InvalidMoveException{

        if(x >= size || x<0 || y >= size || y<0) throw new InvalidMoveException("Move ("+x+" ,"+(y+1)+") out of bounds");

        Symbol curr_s = board[x][y];

        if(curr_s != null) throw new InvalidMoveException("Space is already occupied by "+curr_s);

        board[x][y] = s;

    }
    public void makeMove(String st, Symbol sy) throws InvalidMoveException, InvalidInputException{
        int[] coords = parseInput(st);
        makeMove(coords[0], coords[1], sy);
    }

    public boolean checkWin(Symbol s){

        for(int i=0; i<size; i++)
            for(int j=0; j<size; j++)
                for(Direction d: directions)
                    if(checkWin(s, i, j, 0, d))
                        return true;

        return false;

    }
    private boolean checkWin(Symbol s, int x, int y, int c, Direction d){

        if(c == win) return true;

        if(x<0 || y<0 || x>=size || y>=size) return false;

        int[] i = parseDirection(d);

        if(board[x][y] == s)
            return checkWin(s, x+i[0], y+i[1], c+1,  d);
        else
            return false;

    }

    public int[] parseInput(String s) throws InvalidInputException{

        int[] coords = new int[2];
        char[] clist = s.toCharArray();
        String l = "";
        String n = "";
        for(int i=0; i<clist.length; i++){
            if(Character.isAlphabetic(clist[i])){
                if(n.length()!=0){
                    throw new InvalidInputException("Letter '"+clist[i]+"'found after number.");
                }
                l += clist[i];
            } else if(l.length()==0){
                throw new InvalidInputException("Non-letter '"+clist[i]+"' found before first letter.");
            } else {
                n += clist[i];
            }
        }

        coords[0] = letterToNum(l);
        try {
            coords[1] = Integer.parseInt(n)-1;
        } catch (NumberFormatException e){
            throw new InvalidInputException("Invalid number '"+n+"' given as input");
        }
        return coords;
    }
    public void printBoardWithEval(int[][] eval){
        StringBuilder sb = new StringBuilder();
        int gWidth = 1;
        String barrier = " | ";

        //calculate width of columns
        for(int i = 0; i<size; i++)
            for(int j = 0; j<size; j++)
                if(board[j][i]!=null) {
                    if (board[j][i].toString().length() > gWidth)
                        gWidth = board[j][i].toString().length();
                    if (Integer.toString(eval[j][i]).length()>gWidth)
                        gWidth = Integer.toString(eval[j][i]).length();
                }

        sb.append("   ");
        for(int i=0; i<size; i++){
            sb.append(getLetter(i));
            for(int j=0; j<barrier.length(); j++){
                sb.append(" ");
            }
        }

        for(int i = -1; i<size; i++){
            if(i!=-1)
                sb.append("\n");
            if(i == -1){
                sb.append("   ");
            } else {
                sb.append(" " + (i+1) + " ");
                for (int j = 0; j < size; j++) {
                    if (i != -1) {

                        if(board[j][i]!=null)
                            sb.append(board[j][i]);
                        else
                            sb.append(eval[j][i]);

                        if (j != size - 1)
                            sb.append(barrier);

                    } else {
                        sb.append(board[j][i]);
                        if (j != size - 1)
                            sb.append("   ");
                    }
                }
                if(i!=size-1) {
                    sb.append("\n   ");
                    for (int j = 0; j < (size-1)*barrier.length()+gWidth*size; j++) {
                        sb.append("-");
                    }
                }
            }
        }
        System.out.println(sb.toString());
    }
    public void printBoard(){

        StringBuilder sb = new StringBuilder();
        int gWidth = 1;
        String barrier = " | ";

        //calculate width of columns
        for(int i = 0; i<size; i++)
            for(int j = 0; j<size; j++)
                if(board[i][j]!=null)
                    if(board[i][j].toString().length()>gWidth)
                        gWidth = board[i][j].toString().length();

        sb.append("   ");
        for(int i=0; i<size; i++){
            sb.append(getLetter(i));
            for(int j=0; j<barrier.length(); j++){
                sb.append(" ");
            }
        }

        for(int i = -1; i<size; i++){
            if(i!=-1)
                sb.append("\n");
            if(i == -1){
                sb.append("   ");
            } else {
                sb.append(" " + (i+1) + " ");
                for (int j = 0; j < size; j++) {
                    if (i != -1) {

                        if(board[i][j]!=null)
                            sb.append(board[i][j]);
                        else
                            sb.append(" ");

                        if (j != size - 1)
                            sb.append(barrier);

                    } else {
                        sb.append(board[i][j]);
                        if (j != size - 1)
                            sb.append("   ");
                    }
                }
                if(i!=size-1) {
                    sb.append("\n   ");
                    for (int j = 0; j < (size-1)*barrier.length()+gWidth*size; j++) {
                        sb.append("-");
                    }
                }
            }


        }
        System.out.println(sb.toString());
    }
    private Character getLetter(int n){
        return Character.toChars(65+n)[0];
    }
    public int letterToNum(String s) throws InvalidInputException{
        int n = 0;
        int num_let = 26;

        boolean parsed = false;

        char[] clist = s.toCharArray();
        for(int i = clist.length-1; i>=0; i--){
            if(!Character.isAlphabetic(clist[i]))
                throw new InvalidInputException("Non-alphabetic letter "+clist[i]+"found when attempting to parse letter");
            int x = 1;
            for(int j = 0; j<(clist.length-1)-i; j++){
                x = x*num_let;
            }

            parsed = true;
            n += x*Character.codePointAt(clist, i)-65;
        }
        if(!parsed){
            throw new InvalidInputException("Nothing parsed, probably an empty string given");
        }
        return n;
    }
    private int[] parseDirection(Direction d) throws InvalidDirectionException {
        int x; int y;
        switch(d){
            case D: x = 0; y = -1; break;
            case U: x = 0; y = 1; break;
            case L: x = -1; y = 0; break;
            case R: x = 1; y = 0; break;
            case DL: x = -1; y = -1; break;
            case DR: x = 1; y = -1; break;
            case UL: x = -1; y = 1; break;
            case UR: x = 1; y = 1; break;
            default: throw new InvalidDirectionException();
        }
        int[] i = {x,y};
        return i;
    }
    public class InvalidMoveException extends Exception {

        String message;

        public InvalidMoveException(String message){
            super();
            this.message = message;
        }

        public String getMessage(){
            return message;
        }
    }

    public class InvalidDirectionException extends RuntimeException {
    }

    public class InvalidInputException extends Exception {
        String message;

        public InvalidInputException(String message){
            super();
            this.message = message;
        }

        public String getMessage(){
            return message;
        }
    }
}
