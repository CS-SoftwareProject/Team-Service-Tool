package TT.service.support;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebFilter(urlPatterns = {"/*"})
public class CharacterEncodingFilter implements Filter {
  private static final Logger logger = LoggerFactory.getLogger(CharacterEncodingFilter.class);


  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    logger.info("Character encoding filter initing -> UTF-8");
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    request.setCharacterEncoding("UTF-8");
    response.setCharacterEncoding("UTF-8");
    chain.doFilter(request, response);
  }

  @Override
  public void destroy() {

  }

}
