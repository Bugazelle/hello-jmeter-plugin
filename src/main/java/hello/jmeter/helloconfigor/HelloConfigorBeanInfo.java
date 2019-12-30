package hello.jmeter.helloconfigor;

import java.beans.PropertyDescriptor;
import org.apache.jmeter.testbeans.BeanInfoSupport;
import org.apache.jmeter.util.JMeterUtils;

/**
 * 为 {@link HelloConfigor} 准备JMeter GUI 
 * 
 * Creates the fields for the {@link HelloConfigor} GUI in JMeter
 */
public class HelloConfigorBeanInfo extends BeanInfoSupport {

  private static final String HELLOWORDS = "helloWords";
  private static final String VARIABLE_NAME = "variableName";

  public HelloConfigorBeanInfo() {
    super(HelloConfigor.class);

    createPropertyGroup("hello_configor", new String[] {
        HELLOWORDS, VARIABLE_NAME
    });

    PropertyDescriptor p = property(HELLOWORDS);
    p.setValue(NOT_UNDEFINED, Boolean.TRUE);
    p.setValue(DEFAULT, "Hello JMeter Plugin");
    p.setValue(NOT_EXPRESSION, Boolean.TRUE);

    p = property(VARIABLE_NAME);
    p.setValue(NOT_UNDEFINED, Boolean.TRUE);
    p.setValue(DEFAULT, "");
    p.setValue(NOT_EXPRESSION, Boolean.TRUE);
  }
}
