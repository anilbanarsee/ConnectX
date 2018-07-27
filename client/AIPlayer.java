package client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class AIPlayer{
    private Board board;

    AIPlayer(Board board){
        this.board = board;
    }
    int[] fullEvaluate(Symbol s){
        int[] eval = new int[board.win];

        for(int i=0; i<board.size; i++){
            for(int j = 0; j<board.size; j++) {
                //populate horizontally
                if(i+board.win <= board.size) {
                    Symbol[] segment = new Symbol[board.win];
                    for (int n = 0; n < board.win; n++) {
                        segment[n] = board.board[i+n][j];
                    }
                    int value = evaluateSegment(segment, s);
                    if(value>0){
                        eval[value-1]++;
                    }
                }
                //populate vertically
                if(j+board.win <= board.size) {
                    Symbol[] segment = new Symbol[board.win];
                    System.arraycopy(board.board[i], j + 0, segment, 0, board.win);

                    int value = evaluateSegment(segment, s);
                    if(value>0){
                        eval[value-1]++;
                    }
                }
                //populate diagonally right
                if(j+board.win <= board.size && i+board.win <= board.size){
                    Symbol[] segment = new Symbol[board.win];
                    for (int n = 0; n<board.win; n++){
                        segment[n] = board.board[i+n][j+n];
                        int value = evaluateSegment(segment, s);
                        if(value>0){
                            eval[value-1]++;
                        }
                    }
                }
                //populate diagonally left
                if(j+board.win <= board.size && i-board.win >= -1){
                    Symbol[] segment = new Symbol[board.win];
                    for (int n = 0; n<board.win; n++){
                        segment[n] = board.board[i-n][j+n];
                        int value = evaluateSegment(segment, s);
                        if(value>0){
                            eval[value-1]++;
                        }
                    }
                }
            }
        }

        for(int i=0; i<board.win; i++){
            if(eval[i]>1 && i!=(board.win-1)){
                eval[i+1]++;
                eval[i] -= 2;
            }
        }

        return eval;
    }
    int[] blockEvaluate(Symbol s){
        ArrayList<BlockValue> blocks = new ArrayList<BlockValue>();

        for(int i=0; i<board.size; i++){
            for(int j = 0; j<board.size; j++) {
                //populate horizontally
                if(i+board.win <= board.size) {
                    Symbol[] segment = new Symbol[board.win];
                    Coord[] squares = new Coord[board.win];
                    for (int n = 0; n < board.win; n++) {
                        segment[n] = board.board[i+n][j];
                        squares[i] = new Coord(i+n, j);
                    }
                    blocks.add(createValueBlock(segment, squares, s));
                }
                //populate vertically
                if(j+board.win <= board.size) {
                    Symbol[] segment = new Symbol[board.win];
                    Coord[] squares = new Coord[board.win];
                    for (int n = 0; n < board.win; n++) {
                        segment[n] = board.board[i][j+n];
                        squares[i] = new Coord(i, j+n);
                    }
                    blocks.add(createValueBlock(segment, squares, s));
                }
                //populate diagonally right
                if(j+board.win <= board.size && i+board.win <= board.size){
                    Symbol[] segment = new Symbol[board.win];
                    Coord[] squares = new Coord[board.win];
                    for (int n = 0; n<board.win; n++){
                        segment[n] = board.board[i+n][j+n];
                        squares[i] = new Coord(i+n, j+n);
                    }
                    blocks.add(createValueBlock(segment, squares, s));
                }
                //populate diagonally left
                if(j+board.win <= board.size && i-board.win >= -1){
                    Symbol[] segment = new Symbol[board.win];
                    Coord[] squares = new Coord[board.win];
                    for (int n = 0; n<board.win; n++){
                        segment[n] = board.board[i-n][j+n];
                        squares[i] = new Coord(i-n, j+n);
                    }
                    blocks.add(createValueBlock(segment, squares, s));
                }
            }
        }
        return null;
    }
    int[][] mapEvaluate(Symbol s){

        int[][] eval = new int[board.size][board.size];
        for(int i=0; i<board.size; i++){
            Arrays.fill(eval[i], 0);
        }

        for(int i=0; i<board.size; i++){
            for(int j = 0; j<board.size; j++) {
                //populate horizontally
                if(i+board.win <= board.size) {
                    Symbol[] segment = new Symbol[board.win];
                    for (int n = 0; n < board.win; n++) {
                        segment[n] = board.board[i+n][j];
                    }
                    int value = evaluateSegment(segment, s, eval);
                    insertValueSegment(i, j, value, segment.length, 1, 0, eval);
                }
                //populate vertically
                if(j+board.win <= board.size) {
                    Symbol[] segment = new Symbol[board.win];
                    System.arraycopy(board.board[i], j + 0, segment, 0, board.win);

                    int value = evaluateSegment(segment, s, eval);
                    insertValueSegment(i, j, value, segment.length, 0, 1, eval);
                }
                //populate diagonally right
                if(j+board.win <= board.size && i+board.win <= board.size){
                    Symbol[] segment = new Symbol[board.win];
                    for (int n = 0; n<board.win; n++){
                        segment[n] = board.board[i+n][j+n];
                        int value = evaluateSegment(segment, s, eval);
                        insertValueSegment(i, j, value, segment.length, 1, 1, eval);
                    }
                }
                //populate diagonally left
                if(j+board.win <= board.size && i-board.win >= -1){
                    Symbol[] segment = new Symbol[board.win];
                    for (int n = 0; n<board.win; n++){
                        segment[n] = board.board[i-n][j+n];
                        int value = evaluateSegment(segment, s, eval);
                        insertValueSegment(i, j, value, segment.length, -1, 1, eval);
                    }
                }
            }
        }
        return eval;
    }
    public int scoreEvaluation(int[][] eval){
        int[] d = new int[board.win];
        for (int[] anEval : eval) {
            for (int j = 0; j < eval[0].length; j++) {
                d[anEval[j] - 1]++;
            }
        }
        int highest = 0;
        for(int i=0; i<d.length; i++){
            int index = d[i]-1;
            if(d[index]>1 && index<board.win-1){
                d[index] -= 2;
                d[index+1] += 1;
            }
            if(d[i]>highest){
                highest = d[i];
            }
        }
        return highest;
    }
    private void insertValueSegment(int x, int y, int value, int length, int step_x, int step_y, int[][] eval){

        for(int i = 0; i<length; i++) {
            if (value > eval[x][y]){
                eval[x][y] = value;
            }
            x += step_x;
            y += step_y;
        }

    }
    private BlockValue createValueBlock(Symbol[] segment, Coord[] squares, Symbol s){
        boolean enemy_symb = false;
        int free_space = 0;
        int good_symbs = 0;

        for (int i =0; i<segment.length; i++) {

            Symbol aSegment = segment[i];

            if (aSegment == null) {
                free_space += 1;
            } else if (!aSegment.equals(s)) {
                enemy_symb = true;
                break;
            } else {
                good_symbs += 1;
            }
        }
        if(good_symbs+free_space<board.win || enemy_symb){
            return new BlockValue(new Coord[0], 0);
        }
        System.out.println(board.win+", "+free_space);
        return new BlockValue(squares, board.win-free_space);
    }
    private int evaluateSegment(Symbol[] segment, Symbol s){
        boolean enemy_symb = false;
        int free_space = 0;
        int good_symbs = 0;
        for (Symbol aSegment : segment) {
            if (aSegment == null) {
                free_space += 1;
            } else if (!aSegment.equals(s)) {
                enemy_symb = true;
                break;
            } else {
                good_symbs += 1;
            }
        }
        if(good_symbs+free_space<board.win || enemy_symb){
            return 0;
        }
        System.out.println(board.win+", "+free_space);
        return board.win-free_space;
    }
    private int evaluateSegment(Symbol[] segment, Symbol s, int[][] eval){
        boolean enemy_symb = false;
        int free_space = 0;
        int good_symbs = 0;
        for (Symbol aSegment : segment) {
            if (aSegment == null) {
                free_space += 1;
            } else if (!aSegment.equals(s)) {
                enemy_symb = true;
                break;
            } else {
                good_symbs += 1;
            }
        }
        if(good_symbs+free_space<board.win || enemy_symb){
            return 0;
        }
        System.out.println(board.win+", "+free_space);
        return board.win-free_space;
    }
    private class BlockMap{

        HashMap<Integer, HashMap<Integer, List<BlockValue>>> map;

        private BlockMap(int size){
            map = new HashMap<>();
            for(int i=0; i<size; i++){
                map.put(i, new HashMap<>());
            }
        }

        void addBlock(BlockValue block){

            for(Coord c: block.squares){
                List<BlockValue> list = map.get(c.x).get(c.y);
                boolean subset = false;
                for(BlockValue b: list){

                }
                boolean linked = false;
            }
        }

        boolean checkSubset(BlockValue block){
            for(Coord c: block.squares){
                List<BlockValue> list = map.get(c.x).get(c.y);
                for(BlockValue b: list){

                }
            }
        }
    }
}
