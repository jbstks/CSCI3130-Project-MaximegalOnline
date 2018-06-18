package com.example.peter.a3130project;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests for lengths of passwords and usernames
 *
 * 
 */
public class UserPasswordCheck {
    @Test
    public void userGood_passwordGood() {
	String username = "abc@dfe.comf";
	String password = "aewoifhaioefb";

	LoginRet ret_checker = LoginChecker.checkLogin(username,password);
	assertEquals(ret_checker, LoginRet.OK);

    }
    @Test 
    public void userInvalid_passwordGood() {
	String username = "abcdfecomfeoaihf";
	String password = "aewoifhaioefb";
	LoginRet ret_checker = LoginChecker.checkLogin(username,password);
	assertEquals(ret_checker, LoginRet.INVALID_EMAIL_FORMAT);
    }

    @Test 
    public void userShort_passwordGood() {
	String username = "abc@def";
	String password = "aewoifhaioefb";
	LoginRet ret_checker = LoginChecker.checkLogin(username,password);
	assertEquals(ret_checker, LoginRet.SHORT_PASSWORD);
    }

    @Test 
    public void userGood_passwordShort() {
	String username = "abcdefgh@ihd";
	String password = "aewo";
	LoginRet ret_checker = LoginChecker.checkLogin(username,password);
	assertEquals(ret_checker, LoginRet.SHORT_USER);
    }
    @Test 
    public void userEmpty_passwordGood() {
	String username = "";
	String password = "aewoeafuhea";
	LoginRet ret_checker = LoginChecker.checkLogin(username,password);
	assertEquals(ret_checker, LoginRet.EMPTY_USER);
    }
    @Test 
    public void userGood_passwordEmpty() {
	String username = "abauiefhiuaehwf@eoaofh";
	String password = "";
	LoginRet ret_checker = LoginChecker.checkLogin(username,password);
	assertEquals(ret_checker, LoginRet.EMPTY_PASSWORD);
    }


}
