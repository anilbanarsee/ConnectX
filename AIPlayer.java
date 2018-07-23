package client;

import java.util.Arrays;

public class AIPlayer{
    Board board;
    static final int HORIZONTAL = 0;
    static final int VERTICAL = 1;
    static final int DIAG_R = 2;
    static final int DIAG_L = 3;

    public AIPlayer(Board board){
        this.board = board;
    }

    public int[][] fullEvaluate(Symbol s){

        int[][] eval = new int[board.size][board.size];
        for(int i=0; i<board.size; i++){
            Arrays.fill(eval[i], board.win+1);
        }
        //horizontal
        for(int i=0; i<board.size; i++){
            for(int j = 0; j<board.size; j++) {
                if(i+board.win <= board.size) {
                    Symbol[] segment = new Symbol[board.win];
                    for (int n = 0; n < board.win; n++) {
                        segment[n] = board.board[i+n][j];
                    }
                    int value = evaluateSegment(segment, s, eval);
                    insertValueSegment(i, j, value, segment.length, 1, 0, eval);
                }
            }
        }

        //vertical
        for(int i=0; i<board.size; i++){
            for(int j = 0; j<board.size; j++) {
                if(j+board.win <= board.size) {
                    Symbol[] segment = new Symbol[board.win];
                    for (int n = 0; n < board.win; n++) {
                        segment[n] = board.board[i][j+n];
                    }
                    int value = evaluateSegment(segment, s, eval);
                    insertValueSegment(i, j, value, segment.length, 0, 1, eval);
                }
            }
        }

        //diagonal right
        for(int i=0; i<board.size; i++) {
            for (int j = 0; j < board.size; j++) {
                if(j+board.win <= board.size && i+board.win <= board.size){
                    Symbol[] segment = new Symbol[board.win];
                    for (int n = 0; n<board.win; n++){
                        segment[n] = board.board[i+n][j+n];
                        int value = evaluateSegment(segment, s, eval);
                        insertValueSegment(i, j, value, segment.length, 1, 1, eval);
                    }
                }
            }
        }
        //diag_left
        for(int i=0; i<board.size; i++) {
            for (int j = 0; j < board.size; j++) {
                if(j+board.win <= board.size && i-board.win >= -1){
                    Symbol[] segment = new Symbol[board.win]; //test comment
                    for (int n = 0; n<board.win; n++){
                        segment[n] = board.board[i-n][j+n];
                        int value = evaluateSegment(segment, s, eval);
                        insertValueSegment(i, j, value, segment.length, -1, 1, eval);
                        //jj
                    }
                }
            }
        }
//        for(int j=0; j<board.size; j++){
//            for(int i = 0; i<board.size; i++) {
//
//                Symbol[] segment = new Symbol[board.win];
//                if(i+board.win < board.size) {
//                    for (int n = 0; n < board.win; n++) {
//                        segment[n] = board.board[i + n][j];
//                    }
//                }
//                int value = evaluateSegment(segment, s, eval);
//                insertValueSegment(i, j, value, segment.length, 0, 1, eval);
//            }
//        }
        return eval;
        //diagonal
    }
    private void insertValueSegment(int x, int y, int value, int length, int step_x, int step_y, int[][] eval){

        for(int i = 0; i<length; i++) {
            if (value < eval[x][y]){
                eval[x][y] = value;
            }
            x += step_x;
            y += step_y;
        }

    }
    private int evaluateSegment(Symbol[] segment, Symbol s, int[][] eval){
        boolean enemy_symb = false;
        int free_space = 0;
        int good_symbs = 0;
        for (int n = 0; n < segment.length; n++) {
            if(segment[n] == null){
                free_space += 1;
            }
            else if(!segment[n].equals(s)){
                enemy_symb = true;
                break;
            }
            else {
                good_symbs += 1;
            }
        }
        if(good_symbs+free_space<board.win || enemy_symb){
            return board.win+1;
        }
        return free_space;
    }
}
