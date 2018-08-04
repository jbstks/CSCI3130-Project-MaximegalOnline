package com.example.peter.a3130project;

import static com.example.peter.a3130project.LoginRet.*;

/** LoginChecker
 * Business class for checking if an email is valid.
 *
 * @author Peter Lee
 * @author Aecio Cavalcanti
 */
public class LoginChecker {
    /**
     * Function: precheckLogin
     * ------------
     * Parameters:
     *
     * @param email:    String
     * @param password: String
     * @return LoginRet enumeration based on its evaluation
     */

    public static LoginRet checkLogin (String email, String password) {
		if ( email.isEmpty()){
			return EMPTY_USER;
		}
		if (password.isEmpty()) {
			return EMPTY_PASSWORD;
		}
		if (email.length() < 8) {
			return SHORT_USER;
		}
		if (password.length() < 8 ) {
			return SHORT_PASSWORD;
		}
		if (!email.matches("^.+@.+\\..+$")) {
			return INVALID_EMAIL_FORMAT;
		}

		return OK;
    }
}
