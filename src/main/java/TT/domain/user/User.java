package TT.domain.user;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import TT.dao.user.UserDAO;

public class User {
  @NotNull(message = "아이디를 입력하세요.")
  @Size(min = 4, max = 12, message = "아이디는 4자 이상 12자 이하여야 합니다.")
  private String userId;

  @NotNull(message = "패스워드를 입력하세요.")
  @Size(min = 4, max = 12, message = "비밀번호는 4자 이상 12자 이하여야 합니다.")
  private String password;

  @NotNull(message = "이름을 입력하세요.")
  @Size(min = 2, max = 12, message = "이름은 2자 이상 10자 이하여야 합니다.")
  private String name;

  @NotNull(message = "생년월일을 선택하세요.")
  @Size(min = 10, max = 10, message = "생년월일은 YYYY-MM-DD 형식입니다.")
  private String birth;

  @NotNull
  @NotEmpty(message = "이메일을 입력하세요.")
  @Email
  private String email;

  private String image;

  public User() {

  }

  public User(String userId) {
    this.userId = userId;
  }

  public User(String userId, String password, String name, String birth, String email) {
    this.userId = userId;
    this.password = password;
    this.name = name;
    this.birth = birth;
    this.email = email;
  }

  public User(String userId, String password, String name, String birth, String email, String image) {
    this.userId = userId;
    this.password = password;
    this.name = name;
    this.birth = birth;
    this.email = email;
    this.image = image;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((birth == null) ? 0 : birth.hashCode());
    result = prime * result + ((email == null) ? 0 : email.hashCode());
    result = prime * result + ((image == null) ? 0 : image.hashCode());
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    result = prime * result + ((password == null) ? 0 : password.hashCode());
    result = prime * result + ((userId == null) ? 0 : userId.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    User other = (User) obj;
    if (birth == null) {
      if (other.birth != null)
        return false;
    } else if (!birth.equals(other.birth))
      return false;
    if (email == null) {
      if (other.email != null)
        return false;
    } else if (!email.equals(other.email))
      return false;
    if (image == null) {
      if (other.image != null)
        return false;
    } else if (!image.equals(other.image))
      return false;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    if (password == null) {
      if (other.password != null)
        return false;
    } else if (!password.equals(other.password))
      return false;
    if (userId == null) {
      if (other.userId != null)
        return false;
    } else if (!userId.equals(other.userId))
      return false;
    return true;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getBirth() {
    return birth;
  }

  public void setBirth(String birth) {
    this.birth = birth;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  @Override
  public String toString() {
    return "User [userId=" + userId + ", password=" + password + ", name=" + name + ", birth=" + birth + ", email=" + email + ", image=" + image + "]";
  }

  public boolean matchPassword(String newPassword) {
    return this.password.equals(newPassword);
  }

  public static boolean login(String userId, String password) throws Exception {
    UserDAO userDAO = new UserDAO();
    User user = userDAO.getByUserId(userId);
    if (user == null) {
      throw new UserNotFoundException();
    }

    if (!user.matchPassword(password)) {
      throw new PasswordMismatchException();
    }
    return true;
  }

}
