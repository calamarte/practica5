import java.util.ArrayList;
import java.util.List;

public class Token {
    enum Toktype {
        NUMBER, OP, PAREN
    }

    // Pensa a implementar els "getters" d'aquests atributs
    private Toktype ttype;
    private int value;
    private char tk;

    // Constructor privat. Evita que es puguin construir objectes Token externament
    private Token() {
    }

    // Torna un token de tipus "NUMBER"
    static Token tokNumber(int value) {
        Token t = new Token();
        t.ttype = Toktype.NUMBER;
        t.value = value;
        return t;
    }

    // Torna un token de tipus "OP"
    static Token tokOp(char c) {//ojo al manojo if a tope
        Token t = new Token();
        t.ttype = Toktype.OP;
        t.tk = c;
        return t;
    }

    // Torna un token de tipus "PAREN"
    static Token tokParen(char c) {//mejorar if
        Token t = new Token();
        t.ttype = Toktype.PAREN;
        t.tk = c;
        return t;
    }

    // Mostra un token (conversió a String)
    public String toString() {
        if (ttype == Toktype.NUMBER)return (value+"");
        else return  (tk+"");
    }

    // Mètode equals. Comprova si dos objectes Token són iguals
    public boolean equals(Object o) {
        Token t = (Token) o;
        if (this.ttype == Toktype.NUMBER && t.ttype == Toktype.NUMBER ){
            if (this.value == t.value)return true;
            else return false;
        }else if (this.tk == t.tk)return true;
        else return false;
    }

    // A partir d'un String, torna una llista de tokens
    public static Token[] getTokens(String expr) {
        List<Token> tokens = new ArrayList<>();

        for (int i = 0; i <expr.length() ; i++) {
            if (expr.charAt(i) >= '0' && expr.charAt(i) <= '9') {
                StringBuilder n = new StringBuilder();
                n.append(expr.charAt(i));

                for (int j = 1; j + i < expr.length(); j++) {

                    if (expr.charAt(i + j) >= '0' && expr.charAt(i + j) <= '9') {
                        n.append(expr.charAt(i + j));
                    } else {
                            i = (i + j)-1;
                            break;
                    }
                }
                tokens.add(Token.tokNumber(Integer.parseInt(n.toString())));
                continue;
            }

            if (expr.charAt(i) == '(' || expr.charAt(i) == ')'){
                tokens.add(Token.tokParen(expr.charAt(i)));
            }else{
                tokens.add(Token.tokOp(expr.charAt(i)));
            }

        }
        return tokens.toArray(new Token[tokens.size()]);
    }
}
