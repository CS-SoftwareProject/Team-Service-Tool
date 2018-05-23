package TT.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import TT.dao.user.UserDAO;
import TT.domain.user.User;

public class UserDAOTest {
  private UserDAO userDAO;
  private User user;

  @Before
  public void setUp() {
    userDAO = new UserDAO();
    user = UserTest.TEST_USER;
    user.setImage("/upload_image/default.png");
  }

  @After
  public void returns() throws SQLException {
    userDAO.removeUser(user.getUserId());
  }

  @Test
  public void crud() throws Exception {
    userDAO.addUser(user);

    User dbUser = userDAO.getByUserId(user.getUserId());
    assertEquals(dbUser, user);

    User updateUser = new User(user.getUserId(), "uPSW", "uName", "1992-10-08", "viser@visermail.net");
    updateUser.setImage("/upload_image/default.png");
    userDAO.updateUser(updateUser);

    dbUser = userDAO.getByUserId(updateUser.getUserId());
    assertEquals(updateUser, dbUser);

    userDAO.removeUser(user.getUserId());
    dbUser = null;
    dbUser = userDAO.getByUserId(updateUser.getUserId());
    assertNull(dbUser);
  }

  @Test
  public void getWhenNotExsitUser() throws Exception {
    User dbUser = userDAO.getByUserId(user.getUserId());
    assertNull(dbUser);
  }
}
