import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.net.MalformedURLException;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import com.sun.tools.javac.Main;

public class MainTest {

	@Test
	void GivenInvalidUrl_WhenMainIsCalled_ThenExceptionIsThrown() {
		var e = assertThrows(MalformedURLException.class, () ->
		Main.main(new String[] {"sadadsdada"}));
		assertTrue(e.getClass() == MalformedURLException.class);
	}
}
