package client;

public class Symbol {

    public static final char[] SYMBOLS = {'O','X','V','B','£','$','#','@','€','V'};

    char symbol;
    String str_symb;

    public Symbol(char c){

        symbol = c;
        str_symb = getString(c);
    }

    @Override
    public String toString(){
        if(str_symb == null)
            return getString(symbol);

        return str_symb;
    }
//
//    private Symb getSymb(char c){
//        switch(c){
//            case 'O':
//                return Symb.O;
//            case 'X':
//                return Symb.X;
//            case 'A':
//                return Symb.A;
//            case 'B':
//                return Symb.B;
//            case 'C':
//                return Symb.C;
//        }
//        return Symb.D;
//    }

    private String getString(char c){
        switch(c){
            default:
                return Character.toString(c);
        }
    }
    public boolean equals(Symbol s) {
        return s.symbol == this.symbol;
    }
}
