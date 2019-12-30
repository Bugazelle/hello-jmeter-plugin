package hello.jmeter.hellojavasampler;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Properties;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

// LoggingManager 在JMeter3.3已经弃用：https://jmeter.apache.org/api/deprecated-list.html
// LoggingManager is deprecated in JMeter 3.3: https://jmeter.apache.org/api/deprecated-list.html
// import org.apache.jorphan.logging.LoggingManager;
// import org.apache.log.Logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 创建一个Java Sampler，生成: Hello xxx, today is xxx
 * 
 * Create a Java Sampler to generate: Hello xxx, today is xxx
 */
public class HelloJavaSampler extends AbstractJavaSamplerClient {

  // private static final Logger log = LoggingManager.getLoggerForClass();
  private static final Logger log = LoggerFactory.getLogger(HelloJavaSampler.class);

  private static final String ENCODING = "UTF-8";
  private Properties props = new Properties();

  /**
   * hello words的参数
   * 
   * Parameter for the hello words
   */
  private static final String PARAMETER_HELLO_WORDS = "hello_words";


  /**
   * 测试前的准备工作，比如准备参数，初始化test client. JMeter对每一个thread只执行一次
   * 这里实现了读取当前日期
   * 
   * This is where you read test parameters and initialize your test client. JMeter calls this method only once for each test thread
   * Here is used to get the current date
   */
  @Override
  public void setupTest(JavaSamplerContext context) {
    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    Calendar cal = Calendar.getInstance();
    Date date = cal.getTime();
    String today = dateFormat.format(date);
    props.put("today", today);
  }

  /**
   * 测试完成后执行清理工作
   * 
   * Clear the mess after test
   */
  @Override
  public void teardownTest(JavaSamplerContext context) {
    props.remove("today");
  }

  /**
   * 设置参数的默认值，如果为空，则填null
   * 
   * Set the default values for parameters, if empty, please set null
   */
  @Override
  public Arguments getDefaultParameters() {
    Arguments defaultParameters = new Arguments();
    defaultParameters.addArgument(PARAMETER_HELLO_WORDS, "Hello JMeter Plugin");
    return defaultParameters;
  }

  /**
   * 实现测试逻辑
   * 
   * Fulfil the logic
   */
  @Override
  public SampleResult runTest(JavaSamplerContext context) {
    log.info("Start the Hello Java Sampler");
    SampleResult result = new SampleResult();
    result.sampleStart();
    try {
      result.setDataEncoding(ENCODING);
      result.setDataType(SampleResult.TEXT);
      String hello = context.getParameter(PARAMETER_HELLO_WORDS);
      String response = hello + ", today is " + props.get("today");
      result.setSamplerData(response);
      result.sampleEnd();
      result.setSuccessful(true);
      result.setResponseCodeOK();
      result.setResponseMessage(response);
      result.setResponseData(response, ENCODING);
    } catch (Exception e) {
      result.sampleEnd();
      result.setSuccessful(false);
      result.setResponseCode("500");
      result.setResponseMessage("Exception: " + e);
      result.setResponseData(getStackTrace(e), ENCODING);
    }
    return result;
  }

  /**
   * 返回 stack trace
   * Return the stack trace as a string.
   *
   * @param exception the exception containing the stack trace
   * @return the stack trace
   */
  private String getStackTrace(Exception exception) {
    StringWriter stringWriter = new StringWriter();
    exception.printStackTrace(new PrintWriter(stringWriter));
    return stringWriter.toString();
  }
}
