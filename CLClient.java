package client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class CLClient implements Runnable {

    int players;

    ArrayList<Symbol> symbols;

    Board board;

    public CLClient(int num_players, int size, int wins){

        players = num_players;
        board = new Board(size, wins);
        symbols = new ArrayList<>();
        for(char char_symb: Symbol.SYMBOLS)
            symbols.add(new Symbol(char_symb));

    }

    @Override
    public void run() {
        Scanner sc = new Scanner(System.in);
        AIPlayer player = new AIPlayer(board);
        String input = "";
        boolean finished = false;
        board.printBoard();
        while(!input.equals("Q") && !finished) {
            for (int i = 0; i < players; i++) {
                System.out.print("Player " + (i + 1) + " " + symbols.get(i) + " Turn.\nInput: ");
                input = sc.nextLine();
                if (!input.equals("Q")) {
                    try {
                        board.makeMove(input, symbols.get(i));
                        int[][] eval = player.mapEvaluate(symbols.get(i));

                        board.printBoardWithEval(eval);
                        System.out.println(Arrays.toString(player.fullEvaluate(symbols.get(i))));
//                        for(int j = 0; j<board.size; j++){
//                            System.out.println(Arrays.toString(board.board[j]));
//                        }
//                        for(int j = 0; j<board.size; j++){
//                            System.out.println(Arrays.toString(eval[j]));
//
//}

                        if(board.checkWin(symbols.get(i))){
                            System.out.println("Player "+ (i + 1) +" Wins!");
                            finished = true;
                            break;
                        }
                    } catch (Board.InvalidMoveException e) {
                        System.out.println("Invalid move given: " + e.getMessage());
                    } catch (Board.InvalidInputException e) {
                        System.out.println("Invalid input given: " + e.getMessage());
                    }
                }
            }
        }
    }
}
