package TT.service.support;

import javax.servlet.http.HttpSession;

public class SessionUtils {

  public static boolean isEmpty(HttpSession session, String key) {
    Object obj = session.getAttribute(key);
    if (obj == null) {
      return true;
    }
    return false;
  }

  public static String getStringValue(HttpSession session, String key) {
    if (isEmpty(session, key)) {
      return null;
    }
    return (String) session.getAttribute(key);
  }

  public static Integer getIntegerValue(HttpSession session, String key) {
    if (isEmpty(session, key)) {
      return null;
    }
    return (Integer) session.getAttribute(key);
  }

  public static Object getObjectValue(HttpSession session, String key) {
    if (isEmpty(session, key)) {
      return null;
    }
    return session.getAttribute(key);
  }

}
