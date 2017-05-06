import java.util.ArrayList;
import java.util.List;

public class Token {
    enum Toktype {
        NUMBER, OP, PAREN, OPUNITER
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

    //Torna un token de tipus "OPUNITER"
    static Token tokOpuniter(char c){
        Token t = new Token();
        t.ttype = Toktype.OPUNITER;
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
        //Hay 4 tipos de tokens diferentes por ello lo que este en "expr" se ha de asignar
        //cada uno a el token que le corresponda
        for (int i = 0; i <expr.length() ; i++) {
            //Ignora los espacios
            if (expr.charAt(i) == ' ')continue;
            //Mira si es un numero
            if (expr.charAt(i) >= '0' && expr.charAt(i) <= '9') {
                StringBuilder n = new StringBuilder();
                n.append(expr.charAt(i));

                //Los numeros pueden estar compuestos por más de un caracter por ello se mira si el siguiente
                //char pertenece al numero
                while (i+1 < expr.length()){

                    if (expr.charAt(i+1) >= '0' && expr.charAt(i+1) <= '9') {
                        n.append(expr.charAt(i + 1));
                        i++;
                    }else break;
                }
                tokens.add(Token.tokNumber(Integer.parseInt(n.toString())));
                continue;
            }

            //comprueba si es un parentesis, en su defecto si es un signo unitario especial "-",
            // en su defecto se considera un signo normal
            if (expr.charAt(i) == '(' || expr.charAt(i) == ')')tokens.add(Token.tokParen(expr.charAt(i)));
            else if((expr.charAt(i) == '-' && i == 0) || (expr.charAt(i) == '-' && (expr.charAt(i-1) == '(' || expr.charAt(i-1) == '+' || expr.charAt(i-1) == '-'))){
            tokens.add(Token.tokOpuniter(expr.charAt(i)));
            }else tokens.add(Token.tokOp(expr.charAt(i)));

        }
        //Devuelve la lista de tokens en forma de array
        return tokens.toArray(new Token[tokens.size()]);
    }

    //Devuelve el ttype del objeto
    public Toktype getTtype(){
        Toktype ttype = this.ttype;
        return ttype;
    }

    //Devuelve el tk del objeto
    public char getTk(){
        char tk = this.tk;
        return tk;
    }

    //Devuelve el value
    public int getValue(){
        int value = this.value;
        return value;
    }

}
