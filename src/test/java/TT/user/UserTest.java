package TT.user;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import TT.dao.user.UserDAO;
import TT.domain.user.PasswordMismatchException;
import TT.domain.user.User;
import TT.domain.user.UserNotFoundException;

public class UserTest {
  public static User TEST_USER = new User("TestId", "PSW", "Name", "Birth", "test@viser.com", "");
  private UserDAO userDAO;

  @Before
  public void setUp() throws Exception {
    userDAO = new UserDAO();
  }

  @After
  public void returns() throws SQLException{
    userDAO.removeUser(TEST_USER.getUserId());
  }
  
  @Test
  public void matchPassword() {
    assertTrue(TEST_USER.matchPassword("PSW"));
  }

  @Test
  public void notMatchPassword() {
    assertFalse(TEST_USER.matchPassword("AnyPSW"));
  }

  @Test(expected = UserNotFoundException.class)
  public void loginWhenNotExsitedUser() throws Exception {
    User.login("AnyId", TEST_USER.getPassword());
  }

  @Test(expected = PasswordMismatchException.class)
  public void loginWhenPasswordMismatch() throws Exception {
    userDAO.addUser(TEST_USER);
    User.login(TEST_USER.getUserId(), "AnyPSW");
  }

  @Test
  public void login() throws Exception {
    userDAO.addUser(TEST_USER);
    assertTrue(User.login(TEST_USER.getUserId(), TEST_USER.getPassword()));
  }
}
