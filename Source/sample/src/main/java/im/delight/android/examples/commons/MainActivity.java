package im.delight.android.examples.commons;

import android.os.Bundle;
import android.app.Activity;
import im.delight.android.commons.Strings;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		System.out.println("Strings.containsIgnoreCase(\"Hello world\", \"wo\") = "+Strings.containsIgnoreCase("Hello world", "wo"));
		System.out.println("Strings.containsIgnoreCase(\"Hello world\", \"Wo\") = "+Strings.containsIgnoreCase("Hello world", "Wo"));
		System.out.println("Strings.containsIgnoreCase(\"Hello World\", \"wo\") = "+Strings.containsIgnoreCase("Hello World", "wo"));
		System.out.println("Strings.containsIgnoreCase(\"Hello World\", \"Wo\") = "+Strings.containsIgnoreCase("Hello World", "Wo"));

		System.out.println("Strings.repeat(\"example\", 0) = "+Strings.repeat("example", 0));
		System.out.println("Strings.repeat(\"example\", 1) = "+Strings.repeat("example", 1));
		System.out.println("Strings.repeat(\"example\", 2) = "+Strings.repeat("example", 2));
	}

}
