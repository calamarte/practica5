import java.util.Arrays;

/**
 * Created by calamarte on 27/04/2017.
 */
public class generador {
    public static void main(String[] args) {
        Token token = Token.tokParen('(');
        Token token1 = Token.tokParen('(');
        System.out.println(token.equals(token1));
        Token[] t = Token.getTokens("111/1541654*)(44+5666");
        for (int i = 0; i < t.length; i++) {
            System.out.println(t[i].toString());
        }
    }
}
