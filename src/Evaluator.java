import java.util.LinkedList;
import java.util.Queue;

public class Evaluator {


    public static int calculate(String expr) {
        // Convertim l'string d'entrada en una llista de tokens
        Token[] tokens = Token.getTokens(expr);
        // Efectua el procediment per convertir la llista de tokens en notació RPN
        LinkedList<Token> pila = new LinkedList<>();
        Queue<Token> resultado = new LinkedList<>();

        for (int i = 0; i < tokens.length; i++) {
            if (tokens[i].getTtype() == Token.Toktype.NUMBER){
                resultado.add(tokens[i]);
                continue;
            }

            if (tokens[i].getTtype() == Token.Toktype.OP){
                if (pila.isEmpty()){
                    pila.push(tokens[i]);
                    continue;
                }

                if (priority(pila.peek()) < priority(tokens[i])){
                    pila.push(tokens[i]);
                    continue;
                }

                while (!pila.isEmpty() || priority(pila.peek()) >= priority(tokens[i]))resultado.add(pila.pop());

                pila.push(tokens[i]);
                continue;
            }

            if (tokens[i].getTtype() == Token.Toktype.PAREN){
                if (tokens[i].getTk() == '(')pila.push(tokens[i]);
                else {
                    while (pila.peek().getTk() != '(')resultado.add(pila.pop());

                    pila.pop();
                }
            }

            if (i == tokens.length-1){
                while (!pila.isEmpty()){
                    resultado.add(pila.pop());
                }
            }
        }
        // Finalment, crida a calcRPN amb la nova llista de tokens i torna el resultat
        return calcRPN(resultado.toArray(new Token[resultado.size()]));
    }

    public static int calcRPN(Token[] list) {
        // Calcula el valor resultant d'avaluar la llista de tokens
        return 0;
        
    }

    private static int priority(Token t){
        char signo = t.getTk();

        switch (signo) {
            case '+':
            case '-':
                return 1;
            case '/':
            case '*':
                return 2;
            case '^':return 3;
            default: return 0;
        }
    }


}
