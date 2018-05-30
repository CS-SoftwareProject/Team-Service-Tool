/*package TT.project;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import TT.dao.card.CardDAO;
import TT.dao.message.MessageDAO;
import TT.dao.project.ProjectDAO;
import TT.dao.user.UserDAO;
import TT.domain.assignee.Assignee;
import TT.domain.card.Card;
import TT.domain.message.Message;
import TT.domain.project.Image;
import TT.domain.project.Project;
import TT.domain.user.User;
import TT.service.support.Activitiy.BoardActivitivationFactory;
import TT.user.UserTest;

public class ProjectDAOTest {
  private static final Logger logger = LoggerFactory.getLogger(ProjectDAOTest.class);
  public static Project TEST_PROJECT = new Project("TEST_PROJECT");
  private ProjectDAO projectDAO;
  private UserDAO userDAO;

  @Before
  public void setup() throws SQLException {
    projectDAO = new ProjectDAO();
    userDAO = new UserDAO();
    projectDAO.addProject(ProjectDAOTest.TEST_PROJECT);
    projectDAO.addProject(new Project("TEST_PROJECT2"));
    projectDAO.addProject(new Project("TEST_PROJECT3"));
  }

  @After
  public void returns() throws SQLException {
    projectDAO.removeProject(ProjectDAOTest.TEST_PROJECT.getProjectName());
    projectDAO.removeProject("TEST_PROJECT2");
    projectDAO.removeProject("TEST_PROJECT3");
  }

  @Test
  public void projectCrud() throws SQLException {
    Project dbProject = projectDAO.getByProjectName(TEST_PROJECT.getProjectName());
    assertEquals(TEST_PROJECT.getProjectName(), dbProject.getProjectName());

    Project UpdateProject = new Project("UpdateProject");
    projectDAO.updateProject(UpdateProject.getProjectName(), dbProject.getProjectName());
    dbProject = projectDAO.getByProjectName(UpdateProject.getProjectName());
    assertEquals(dbProject.getProjectName(), UpdateProject.getProjectName());

    projectDAO.removeProject(UpdateProject.getProjectName());
  }

  @Test
  public void projectMemberCrud() throws SQLException {
    User user = UserTest.TEST_USER;
    user.setImage("/upload_image/default.png");

    List ProjectMemberlist = new ArrayList();
    List Projectlist = new ArrayList();

    userDAO.addUser(user);

    // invite Test
    projectDAO.InviteUser(user.getUserId(), TEST_PROJECT.getProjectName(), 0);
    projectDAO.InviteUser(user.getUserId(), "TEST_PROJECT2", 0);
    projectDAO.InviteUser(user.getUserId(), "TEST_PROJECT3", 0);
    User invitedUser = projectDAO.getProjectMember(TEST_PROJECT.getProjectName());
    assertEquals(user.getUserId(), invitedUser.getUserId());

    // getProjectMemberList Test
    ProjectMemberlist = projectDAO.getProjectMemberList(TEST_PROJECT.getProjectName());
    assertNotNull(ProjectMemberlist);
    logger.debug("projectMember : {}", ProjectMemberlist);

    // getProjectList Test
    Projectlist = projectDAO.getProjectList(user.getUserId());
    assertNotNull(Projectlist);
    logger.debug("projectList : {}", Projectlist);

    // remove Test
    projectDAO.removeProjectMember(invitedUser.getUserId(), TEST_PROJECT.getProjectName());
    invitedUser = projectDAO.getProjectMember(TEST_PROJECT.getProjectName());
    assertNull(invitedUser);

    userDAO.removeUser(user.getUserId());
  }

  @Test
  public void getUserList() throws SQLException {
    User user1 = new User("loginUser", "1234", "test", "2017-08-26", "test@test.com");
    user1.setImage("/upload_image/default.png");
    User user2 = new User("TestId1", "1234", "test", "2017-08-26", "test@test.com");
    user2.setImage("/upload_image/default.png");
    User user3 = new User("TestId2", "1234", "test", "2017-08-26", "test@test.com");
    user3.setImage("/upload_image/default.png");

    userDAO.addUser(user1);
    userDAO.addUser(user2);
    userDAO.addUser(user3);

    logger.debug("Users : {}", projectDAO.getUserList("te", "loginUser"));
    assertNotNull(projectDAO.getUserList("te", "loginUser"));

    userDAO.removeUser("loginUser");
    userDAO.removeUser("TestId1");
    userDAO.removeUser("TestId2");
  }

  @Test
  public void imageCrd() throws Exception {
    User user = UserTest.TEST_USER;
    Image image = new Image("TEST_PATH", user.getUserId());
    List list = new ArrayList();

    projectDAO.removeImage(image.getImagePath());
    projectDAO.addImage(image, TEST_PROJECT.getProjectName());

    image.setImageNum(projectDAO.getImageNum(TEST_PROJECT.getProjectName(), image.getAuthor()));

    Image dbimage = projectDAO.getByImageNum(image.getImageNum());
    logger.debug("image : {}", image);
    logger.debug("DBimage : {}", dbimage);
    assertEquals(image.getImagePath(), dbimage.getImagePath());
    assertEquals(image.getAuthor(), dbimage.getAuthor());

    list = projectDAO.getImageList(TEST_PROJECT.getProjectName());
    assertNotNull(list);
    logger.debug("imageList : {}", list);
  }

  @Test
  public void searchUserList() {
    List<User> list = new ArrayList<User>();
    list = projectDAO.getUserList("t", "A");
    Gson gson = new Gson();

    JsonElement el = gson.toJsonTree(1);
    JsonArray arr = new JsonArray();
    String invitedUserId;
    for (int i = 0; i < list.size(); i++) {
      invitedUserId = projectDAO.getInvitedUserId(list.get(i).getUserId());

      el = gson.toJsonTree(list.get(i));
      JsonObject obj = new JsonObject();
      obj.add("user", el);

      if (invitedUserId == null)
        obj.add("invited", gson.toJsonTree(false));
      else
        obj.add("invited", gson.toJsonTree(true));

      arr.add(obj);
    }
    logger.debug("debug arr : {} ", arr);
    logger.debug("debug : " + arr.get(0));
  }

  @Test
  public void getrealTimeSearch() throws Exception {
    List<User> list = new ArrayList<User>();
    MessageDAO msgDAO = new MessageDAO();
    list = msgDAO.getRealTimeUserList("t");
    logger.debug("list ? {}", list);
  }

  @Test
  public void getLastMessageList() throws Exception {
    MessageDAO msgDAO = new MessageDAO();
    UserDAO userDAO = new UserDAO();
    Gson gson = new Gson();
    JsonArray arr = new JsonArray();

    String userId = "vick";
    int notRead = 0;
    List<Message> list = new ArrayList<Message>();
    notRead = msgDAO.getNotReadMessage(userId);
    list = msgDAO.getLastMessageList("vick");
    logger.debug("0 : {}", list);
    for (int i = 0; i < list.size(); i++) {
      JsonObject obj = new JsonObject();
      obj.add("message", gson.toJsonTree(list.get(i)));
      obj.add("image", gson.toJsonTree(userDAO.getImageByUserId(list.get(i).getSender())));
      arr.add(obj);
    }
    JsonObject obj = new JsonObject();
    obj.add("notRead", gson.toJsonTree(notRead));
    arr.add(obj);
    logger.debug("last message arr ? {}", arr);
    String s = gson.toJson(arr);
  }

  @Test
  public void getUserProjectList() throws SQLException {
    Card card = new Card();
    CardDAO dao = new CardDAO();
    card = dao.findByCardNum(3);
    logger.debug("card : {}", card);
  }

  @Test
  public void boardActivityTest() {
    Card card = new Card();
    card.setSubject("댓냐");
    Assignee assignee = new Assignee();
    Assignee assignee2 = new Assignee();
    assignee.setUserId("test");
    assignee.setRoleName("매니저");
    assignee2.setUserId("test2");
    assignee2.setRoleName("매니저2");
    BoardActivitivationFactory factory = new BoardActivitivationFactory("테스터");
    logger.debug("" + factory.Activity(card, "삭제"));
    logger.debug("" + factory.activityInCard("TestCard", assignee, "삭제"));
    logger.debug("" + factory.updateActivityInCard("TestCard", assignee, assignee2));
    logger.debug("" + factory.updateActivity(card, "Old", "New"));
  }
}
*/