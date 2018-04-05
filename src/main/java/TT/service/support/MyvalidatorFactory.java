package TT.service.support;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public class MyvalidatorFactory {
  public static Validator createValidator() {

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    return factory.getValidator();

  }
}
