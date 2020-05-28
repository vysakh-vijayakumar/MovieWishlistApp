package vijayakumar.vysakh.wishlistapp.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator {						// regex validation
	
		private static final String EMAIL_REGEX = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";		// Email Regex java
		private static Pattern pattern;					// static Pattern object, since pattern is fixed
		private Matcher matcher;						// non-static Matcher object because it's created from the input String

		public EmailValidator() {
														// initialize the Pattern object
			pattern = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);
		}

		public boolean validateEmail(String email) {			// email validating method
			matcher = pattern.matcher(email);
			return matcher.matches();
		}
}
