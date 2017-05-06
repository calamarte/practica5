import java.util.LinkedList;
import java.util.Queue;

public class Evaluator {

    //Se encarga de cambiar el orden de los tokens para formar expresiones
    //en polaca inversa y luego calcular el resultado
    public static int calculate(String expr) {
        // Convertim l'string d'entrada en una llista de tokens
        Token[] tokens = Token.getTokens(expr);
        // Efectua el procediment per convertir la llista de tokens en notació RPN
        LinkedList<Token> pila = new LinkedList<>();
        Queue<Token> resultado = new LinkedList<>();

        for (int i = 0; i < tokens.length; i++) {

            //Al ser un entero se introduce en la cola
            if (tokens[i].getTtype() == Token.Toktype.NUMBER){
                resultado.add(tokens[i]);
                continue;
            }

            //Al ser un operador entrara en la pila liberando otros operadores
            //dependiendo de el orden de prioridad
            if (tokens[i].getTtype() == Token.Toktype.OP || tokens[i].getTtype() == Token.Toktype.OPUNITER){
                //Si la pila esta vacia se introduce siempre
                if (pila.isEmpty()){
                    pila.push(tokens[i]);
                    continue;
                }

                //Si el que esta en la pila tiene menor prioridad se introduce
                if (priority(pila.peek()) < priority(tokens[i])){
                    pila.push(tokens[i]);
                    continue;
                }

                //Si el que esta en la pila es de mayor prioridad expulsará a todos los que sean
                //de menor prioridad de la pila y se introduce el operador
                while (!pila.isEmpty() && priority(pila.peek()) >= priority(tokens[i]))resultado.add(pila.pop());
                pila.push(tokens[i]);
                continue;
            }
            //Si es un parentesis se mira si es el que abre para introducirlo en la pila
            //o si es el que cierra para sacar lo que se incluya entre ellos dos.
            if (tokens[i].getTtype() == Token.Toktype.PAREN){
                if (tokens[i].getTk() == '(')pila.push(tokens[i]);
                else {
                    while (pila.peek().getTk() != '(')resultado.add(pila.pop());

                    pila.pop();
                }
            }
        }

        while (!pila.isEmpty())resultado.add(pila.pop());

        // Finalment, crida a calcRPN amb la nova llista de tokens i torna el resultat
        return calcRPN(resultado.toArray(new Token[resultado.size()]));
    }

    //Se encarga de resolver operciones en polaca inversa
    public static int calcRPN(Token[] list) {
        LinkedList<Token> pila = new LinkedList<>();
        // Calcula el valor resultant d'avaluar la llista de tokens
        for (int i = 0; i < list.length; i++) {
            //Se analiza cada token del array para averiguar de que tipo son
            //para así poder realizar las diferentes operaciones
            if (list[i].getTtype() == Token.Toktype.NUMBER)pila.push(list[i]);
            else if (priority(list[i]) <= 3)pila.push(operar(pila.pop(),pila.pop(),list[i]));
            else if(priority(list[i]) == 4) pila.push(operar(pila.pop(),list[i]));
            else pila.push(operar(pila.pop(),list[i]));
        }

        return pila.pop().getValue();
    }

    //Determina la prioridad de un token que se un operador
    private static int priority(Token t){
        if (t.getTtype() == Token.Toktype.OPUNITER)return 5;
        char signo = t.getTk();

        switch (signo) {
            case '+':
            case '-':
                return 1;
            case '/':
            case '*':
                return 2;
            case '^':return 3;
            case '!':return 4;
            default: return 0;
        }
    }

    //Hace operaciones que necesitan de 2 valores
    private static Token operar(Token value2,Token value1,Token operator){
        //Es un switch que devuelde el resultado de 5 posibles operación entre 2 valores
        //definidas por el token "operator"
        switch (operator.getTk()){
            case '+': return Token.tokNumber(value1.getValue() + value2.getValue());
            case '-': return Token.tokNumber(value1.getValue() - value2.getValue());
            case '*': return Token.tokNumber(value1.getValue() * value2.getValue());
            case '/': return Token.tokNumber(value1.getValue() / value2.getValue());
            case '^': return Token.tokNumber((int) Math.pow((double) value1.getValue(),(double) value2.getValue()));
            default: return null;
        }
    }

    //Hace operaciones que necesitan de un valor
    private static Token operar(Token value,Token operator){
        //Realiza las 2 posibles operaciones que requieren solo de un valor para
        //llevarse a cabo
        if (operator.getTk() == '!') {
            int resultado = value.getValue();
            for (int i = value.getValue() - 1; i >= 1; i--) resultado *= i;
            return Token.tokNumber(resultado);
        }
        else return Token.tokNumber(value.getValue()*(-1));
    }


}
