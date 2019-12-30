package hello.jmeter.helloconfigor;

import java.util.Map;
import org.apache.jmeter.config.ConfigTestElement;
import org.apache.jmeter.engine.event.LoopIterationEvent;
import org.apache.jmeter.engine.event.LoopIterationListener;
import org.apache.jmeter.testbeans.TestBean;
import org.apache.jmeter.threads.JMeterContextService;
import org.apache.jmeter.threads.JMeterVariables;

// LoggingManager 在JMeter3.3已经弃用：https://jmeter.apache.org/api/deprecated-list.html
// LoggingManager is deprecated in JMeter 3.3: https://jmeter.apache.org/api/deprecated-list.html
// import org.apache.jorphan.logging.LoggingManager;
// import org.apache.log.Logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 创建一个Config Element: Hello Configor
 * 用户可以输入文字，然后JMeter读取文字，并且保存到参数
 *
 * Create a Config Element: Hello Configor
 *  User could input hello words, and JMeter read the words, and save to parameter
 */
public class HelloConfigor extends ConfigTestElement implements TestBean, LoopIterationListener {

  // private static final Logger log = LoggingManager.getLoggerForClass();
  private static final Logger log = LoggerFactory.getLogger(HelloConfigor.class);

  private String helloWords;
  private String variableName;

  // 生成新的JMeter迭代，并把数据传入JMeter
  // Start the new iteration, and pass the values to JMeter
  @Override
  public void iterationStart(LoopIterationEvent loopIterationEvent) {
    JMeterContextService.getContext().getVariables().put(getVariableName(), helloWords);
  }

  /**
   * @return 返回 variableName
   * 
   * @return the variableName
   */
  public String getVariableName() {
    return variableName;
  }

  /**
   * @param variableName 设置 variableName
   * 
   * @param variableName the variableName to set
   */
  public void setVariableName(String variableName) {
    this.variableName = variableName;
  }

  /**
   * @return 返回 helloWords
   *
   * @return the helloWords
   */
  public String getHelloWords() {
    return helloWords;
  }

  /**
   * @param helloWords 设置 helloWords
   * 
   * @param helloWords the helloWords to set
   */
  public void setHelloWords(String helloWords) {
    this.helloWords = helloWords;
  }

  /**
   * 本地调试
   * 
   * Helper for testing outside of JMeter
   */
  public static void main(String[] args) {
    HelloConfigor generator = new HelloConfigor();

    // 模拟JMeter环境
    // Mock out JMeter environment
    JMeterVariables variables = new JMeterVariables();
    JMeterContextService.getContext().setVariables(variables);
    generator.setHelloWords("Hello JMeter Plugin");
    generator.setVariableName("hello");

    generator.iterationStart(null);

    for (Map.Entry<String, Object> entry : variables.entrySet()) {
      System.out.println(entry.getKey() + " : " + entry.getValue());
    }
  }
}
