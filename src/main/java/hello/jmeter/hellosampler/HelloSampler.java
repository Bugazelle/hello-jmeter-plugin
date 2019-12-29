package hello.jmeter.hellosampler;

import org.apache.jmeter.samplers.AbstractSampler;
import org.apache.jmeter.samplers.Entry;
import org.apache.jmeter.samplers.SampleResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 创建一个Sampler，生成: Hello xxx, today is xxx
 * 更多关于怎么开发Sampler，请参考：https://github.com/apache/jmeter/tree/master/src/examples/src/main/java/org/apache/jmeter/examples
 * 
 * Create a Sampler to generate: Hello xxx, today is xxx
 * For more info about how to create sampler: https://github.com/apache/jmeter/tree/master/src/examples/src/main
 */
public class HelloSampler extends AbstractSampler {

    private static final long serialVersionUID = 240L;
    private static final Logger log = LoggerFactory.getLogger(HelloSampler.class);
    public static final String HELLOINPUT = "Input";

    public HelloSampler() {
        super();
    }

    @Override
    public SampleResult sample(Entry e) {
        SampleResult res = new SampleResult();
        boolean isOK = false;
        String hello = getHello();
        res.setSampleLabel(getTitle());

        // 开始计时
        // Start timing
        res.sampleStart(); 

        try {

            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            Calendar cal = Calendar.getInstance();
            Date date = cal.getTime();
            String today = dateFormat.format(date);
            String response = hello + ", today is " + today;

            /*
             * 设置sampler的显示结果
             *
             * Set up the sampler result details
             */
            res.setSamplerData(hello);
            res.setResponseData(response, null);
            res.setDataType(SampleResult.TEXT);

            res.setResponseCodeOK();
            res.setResponseMessage("OK");
            isOK = true;
        } catch (Exception ex) {
            log.debug("", ex);
            res.setResponseCode("500");// $NON-NLS-1$
            res.setResponseMessage(ex.toString());
        }
        res.sampleEnd(); // End timing

        res.setSuccessful(isOK);

        return res;
    }

    /**
     * @return sampleResult 的 标题
     * 
     * @return a string for the sampleResult Title
     */
    private String getTitle() {
        return this.getName();
    }

    /**
     * @return 填入Sampler中的数据
     * 
     * @return the data for the sampler
     */
    public String getHello() {
        return getPropertyAsString(HELLOINPUT);
    }
}
