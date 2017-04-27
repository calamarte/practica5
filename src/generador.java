import java.util.Arrays;

/**
 * Created by calamarte on 27/04/2017.
 */
public class generador {
    public static void main(String[] args) {
        Token token = Token.tokNumber(3);
        Token token1 = Token.tokOp('+');
        Token[] t = Token.getTokens("1231+32");
        for (int i = 0; i < t.length; i++) {
            System.out.println(t[i].toString());
        }
    }
}
