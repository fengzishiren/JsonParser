import java.util.HashMap;
import java.util.Map;

/**
 * @author lunatic 2014年7月26日
 */
public class Lexer {

    private static Map<String, Character> escapeMap = new HashMap<String, Character>();

    static {
        escapeMap.put("\\\"", '\"');
        escapeMap.put("\\\\", '\\');
        escapeMap.put("\\/", '/');
        escapeMap.put("\\b", '\b');
        escapeMap.put("\\f", '\f');
        escapeMap.put("\\n", '\n');
        escapeMap.put("\\r", '\r');
        escapeMap.put("\\t", '\t');
        // escape.put("\\u", '\u5845')
}

    private static final int UNICODE_WIDTH = 5, ESCAPE_WIDTH = 1;

    private String text;

    private int offset;

    private int row, col;

    public void load(String s) {
        text = s;
        offset = row = col = 0;
    }

    private Character escape(String s) {
        Character c = null;
        if ((c = escapeMap.get(s)) == null && s.startsWith("\\u")) {
            int i = Integer.parseInt(s.substring(2), 16);
            if (i <= Character.MAX_VALUE)
                c = (char) i;
        }
        return c;
    }

    private void skipSpace() {
        while (text.length() != offset
            && Character.isWhitespace(text.charAt(offset)))
            forward();
    }

    private void forward() {
        if (text.charAt(offset++) == '\n') {
            row++;
            col = 0;
        } else
            col++;
    }

    public Token scan() {
        skipSpace();
        if (text.length() == offset)
            return null;

        int start = offset;
        byte type = -1;
        char c = text.charAt(offset);
        switch (c) {
            case '{':
                type = Tag.OPEN_BRACE;
                break;
            case '}':
                type = Tag.CLOSE_BRACE;
                break;
            case '[':
                type = Tag.OPEN_BRACKET;
                break;
            case ']':
                type = Tag.CLOSE_BRACKET;
                break;
            case ':':
                type = Tag.COLON;
                break;
            case ',':
                type = Tag.COMMA;
                break;
        }
        if (type != -1) {
            forward();
            return new Token(text.substring(start, offset), type, row, col);
        }
        // ////
        // 类型推断部分
        // 'STRING', 'NUMBER', 'NULL', 'TRUE', 'FALSE',
        if (c == '"') {
            forward();
            StringBuilder sb = new StringBuilder();
            while (offset != text.length()) {
                if (text.charAt(offset) == '\\') {
                    int step = ESCAPE_WIDTH;
                    if (offset + 1 == text.length()) {
                        S.syntaxError("Expect \"\"\"", row, col);
                    }
                    if (text.charAt(offset + 1) == 'u') {
                        if (offset + UNICODE_WIDTH + 1 >= text.length())
                            S.syntaxError("Expect unicode character", row, col);
                        step = UNICODE_WIDTH;
                    }
                    Character character = escape(text.substring(offset, offset
                        + (step == UNICODE_WIDTH ? UNICODE_WIDTH + 1
                            : ESCAPE_WIDTH + 1)));
                    if (character == null) {
                        S.syntaxError("Unsupport escape character", row, col);
                    } else {
                        sb.append(character);
                        while (step-- != 0)
                            forward();
                    }
                } else if (text.charAt(offset) == '"') {
                    break;
                } else {
                    sb.append(text.charAt(offset));
                }
                forward();
            }
            if (offset == text.length()) {
                S.syntaxError("Expect \"\"\"", row, col);
            }
            int thisRow = row;
            int thisCol = col;
            forward();// skip "
            skipSpace();
            if (offset == text.length()) {
                S.syntaxError("Expect \":\" or \",\" or \"}\" or \"]\"", row,
                    col);
            }
            type = text.charAt(offset) == ':' ? Tag.KEY_STRING : Tag.STRING;
            return new Token(sb.toString(), type, thisRow, thisCol);
        } else {
            while (text.length() != offset
                && (Character.isLetterOrDigit(text.charAt(offset))
                    || text.charAt(offset) == '.' || text.charAt(offset) == '+' || text
                    .charAt(offset) == '-')) {
                forward();
            }
            if (start == offset)
                S.syntaxError(
                    String.format("Unrecognized \"%c\"", text.charAt(offset)),
                    row, col);
            if (offset == text.length()) {
                S.syntaxError("Expect \",\" or \"}\" or \"]\"", row, col);
            }
            int thisRow = row, thisCol = col;
            String content = text.substring(start, offset);

            Object val = null;
            if ("null".equals(content)) {
                type = Tag.NULL;
            } else if ("true".equals(content)) {
                type = Tag.BOOLEAN;
                val = Boolean.TRUE;
            } else if ("false".equals(content)) {
                type = Tag.BOOLEAN;
                val = Boolean.FALSE;
            } else {
                type = Tag.NUMBER;
                boolean ok = false;
                if (content.indexOf('.') == content.indexOf('E')) {
                    try {
                        val = Integer.parseInt(content);
                        ok = true;
                    } catch (Exception e) {
                        try {
                            val = Long.parseLong(content);
                            ok = true;
                        } catch (Exception e1) {
                            ok = false;
                        }
                    }
                } else {
                    try {
                        val = Float.parseFloat(content);
                        ok = true;
                    } catch (Exception e) {
                        try {
                            val = Double.parseDouble(content);
                            ok = true;
                        } catch (Exception e1) {
                            ok = false;
                        }
                    }
                }
                if (!ok)
                    S.syntaxError(
                        String.format("Unrecognized \"%s\"", content), thisRow,
                        thisCol);
            }
            return new Token(val, type, thisRow, thisCol);
        }
    }

}
