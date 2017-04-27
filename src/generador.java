/**
 * Created by calamarte on 27/04/2017.
 */
public class generador {
    public static void main(String[] args) {
        Token token = Token.tokNumber(3);
        Token token1 = Token.tokOp('+');
        System.out.println(token.toString()+token1.toString());
    }
}
