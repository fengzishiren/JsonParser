import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Test;

public class LexerTest {

	@Test
	public void testLexer() throws FileNotFoundException, IOException {

		Lexer lexer = new Lexer();

		String s = UTest.readJsonFromFile("test.json");
		Log.d(s);
		lexer.load(s);
		Token token;
		while ((token = lexer.scan()) != null) {
			Log.d(token);
		}
		Log.i("The end!");
	}
}
