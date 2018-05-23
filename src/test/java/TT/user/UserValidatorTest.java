package TT.user;

import static org.junit.Assert.assertEquals;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.BeforeClass;
import org.junit.Test;

import TT.domain.user.User;

public class UserValidatorTest {

  private static Validator validator;

  @BeforeClass
  public static void setUp() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  public void UserIdIsNull() throws Exception {
    User user = new User(null, "password", "name", "1992-10-08", "email@visermail.net");

    Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
    assertEquals(1, constraintViolations.size());
    System.out.println(constraintViolations.iterator().next().getMessage());
  }

  @Test
  public void UserIdLength() throws Exception {
    User user = new User("us", "password", "name", "1992-10-08", "email@visermail.net");

    Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
    assertEquals(1, constraintViolations.size());
    System.out.println(constraintViolations.iterator().next().getMessage());

    user = new User("userIddddddddddd", "password", "name", "1992-10-08", "email@visermail.net");
    constraintViolations = validator.validate(user);
    assertEquals(1, constraintViolations.size());
    System.out.println(constraintViolations.iterator().next().getMessage());
  }

  @Test
  public void UserPasswordLength() throws Exception {
    User user = new User("userId", "pd", "name", "1992-10-08", "email@visermail.net");
    Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

    assertEquals(1, constraintViolations.size());
    System.out.println(constraintViolations.iterator().next().getMessage());

    user = new User("userId", "pdddddddddddd", "name", "1992-10-08", "email@visermail.net");
    assertEquals(1, constraintViolations.size());
    System.out.println(constraintViolations.iterator().next().getMessage());
  }

  @Test
  public void UserEmailCheck() throws Exception {
    User user = new User("userId", "password", "name", "1992-10-08", "");
    Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

    assertEquals(1, constraintViolations.size());
    System.out.println(constraintViolations.iterator().next().getMessage());
  }

}
