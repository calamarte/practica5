import java.util.Arrays;
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

                while (!pila.isEmpty() && priority(pila.peek()) >= priority(tokens[i]))resultado.add(pila.pop());

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
        }

        while (!pila.isEmpty()){
            resultado.add(pila.pop());
        }
        System.out.println(Arrays.toString(resultado.toArray(new Token[resultado.size()])));
        // Finalment, crida a calcRPN amb la nova llista de tokens i torna el resultat
        return calcRPN(resultado.toArray(new Token[resultado.size()]));
    }

    public static int calcRPN(Token[] list) {
        LinkedList<Token> pila = new LinkedList<>();
        // Calcula el valor resultant d'avaluar la llista de tokens
        for (int i = 0; i < list.length; i++) {

            if (list[i].getTtype() == Token.Toktype.NUMBER)pila.push(list[i]);
            else pila.push(operar(pila.pop(),pila.pop(),list[i]));
        }
        
        return pila.pop().getValue();
        
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

    private static Token operar(Token value2,Token value1,Token operator){
        switch (operator.getTk()){
            case '+': return Token.tokNumber(value1.getValue() + value2.getValue());
            case '-': return Token.tokNumber(value1.getValue() - value2.getValue());
            case '*': return Token.tokNumber(value1.getValue() * value2.getValue());
            case '/': return Token.tokNumber(value1.getValue() / value2.getValue());
            default: return null;
        }
    }



}
