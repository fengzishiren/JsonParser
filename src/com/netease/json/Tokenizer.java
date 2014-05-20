package com.netease.json;
import java.util.ArrayList;

/**
 * 
 * 标记/令牌生成器
 * 
 * 
 * { \"font_size\": "14", \"font_face\": \"Courier 10 Pitch\",
 * \"ignored_packages\": [ \"Vintage\" ] }
 * 
 * @author lunatic
 */
public class Tokenizer {
    // public static final String TEST_TEXT =
    // "     {\"font_size\": \"14\", \"font_face\": \"Courier 10 Pitch\", \"ignored_packages\": [ \"Vintage\"]}";

    private char[] sens;

    public TokenList tokens = new TokenList(1000);

    private int index = 0;

    public Tokenizer(String text) {
        this.sens = text.toCharArray();
    }

    /**
     * 
     * 跳到下一个token 空行、空白字符、注释
     */
    public void nextToken() {

        for (; index < sens.length;) {
            char c = sens[index];
            if (c == ' ' || c == '\t' || c == '\r' || c == '\n') {
                index++;
            }
            // skip '// xxx
            else if (c == '/' && sens[index + 1] == '/') {
                while (sens[++index] != '\n')
                    ;
            }
            // skip '/* xxx */'
            else if (c == '/' && sens[index + 1] == '*') {
                while (sens[++index] != '*' || sens[++index] != '/')
                    ;
            } else
                break;
        }
    }

    /**
     * 
     * 解析当前token
     */
    public void parseToken() {
        char elem = sens[index];
        int start = index;
        byte type = -1;
        int length = -1;

        switch (elem) {
        case '{':
            type = TokenType.CURLY_BRACKET_LEFT;
            length = 1;
            index += length;
            break;
        case '}':
            type = TokenType.CURLY_BRACKET_RIGHT;
            length = 1;
            index += length;
            break;
        case '[':
            type = TokenType.SQUARE_BRACKET_LEFT;
            length = 1;
            index += length;
            break;
        case ']':
            type = TokenType.SQUARE_BRACKET_RIGHT;
            length = 1;
            index += length;
            break;
        case '\"':
            // Note:Quote skip
            // K, V Or ["", "", ""]
            type = TokenType.STRING_TOKEN;
            start = ++index; // skip '"'
            while (sens[index++] != '\"')
                ;
            length = index - start - 1;
            break;
        case ':':
            type = TokenType.COLON;
            length = 1;
            index += length;
            break;
        case ',':
            type = TokenType.COMMA;
            length = 1;
            index += length;
            break;

        default:// deal with: number, boolean, null

            while (sens[++index] != ' ' && sens[index] != ',' && sens[index] != ']' && sens[index] != '}')
                ;
            // index++;
            // endsWith ' '
            // ;
            length = index - start;
            if (length == 0)
                throw new RuntimeException("0");
            String special = new String(sens, start, length);
            if (special.equals("true") || special.equals("false"))
                type = TokenType.BOOLEAN_TOKEN;
            else if (special.equals("null"))
                type = TokenType.NULL_TOKEN;
            else
                type = TokenType.NUMBER_TOKEN;

            // number:13 boolean true/false
            break;
        }

        Token token = new Token(new String(sens, start, length), type);
        Log.d("token: " + token.content + " len:" + token.content.length() + " type:" + token.type);
        tokens.append(token);
    }

    public TokenList parseTokens() {
        try {
            for (; index < sens.length;) {
                nextToken();
                parseToken();
            }
            return tokens;
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new SyntaxException("提前遇到EOF");
        }
    }

    // public static void main(String[] args) {
    // Tokenizer tokenizer = new Tokenizer();
    //
    // for (; tokenizer.index < tokenizer.sens.length;) {
    // tokenizer.nextToken();
    // tokenizer.parseToken();
    // }
    // //
    // for (Token token : tokenizer.tokens) {
    // Log.d("start: " + token.start);
    // Log.d("Length: " + token.length);
    // Log.d("Type: " + token.type);
    // Log.d("String: "
    // + new String(tokenizer.sens, token.start, token.length));
    // Log.d("----------------------------------------");
    // }
    //
    // Log.d(tokenizer.tokens.size());
    // }
}

class TokenList extends ArrayList<Token> {
    private static final long serialVersionUID = 1L;

    public TokenList() {
        super(1000);
    }

    public TokenList(int capacity) {
        super(capacity);
    }

    public void append(Token tk) {
        super.add(tk);
    }
}

class Token {
    String content;

    byte type;

    public Token(String content, byte type) {
        super();
        this.content = content;
        this.type = type;
    }

}

interface TokenType {

    public static final byte CURLY_BRACKET_LEFT = 1; // {

    public static final byte CURLY_BRACKET_RIGHT = 2; // }

    public static final byte SQUARE_BRACKET_LEFT = 3; // [

    public static final byte SQUARE_BRACKET_RIGHT = 4; // ]

    public static final byte STRING_TOKEN = 5; //

    public static final byte NUMBER_TOKEN = 7; //

    public static final byte BOOLEAN_TOKEN = 8; //

    public static final byte NULL_TOKEN = 9; // null

    public static final byte COLON = 10; // :

    public static final byte COMMA = 11; // ,

}