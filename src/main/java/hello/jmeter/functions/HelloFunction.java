package hello.jmeter.functions;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.jmeter.engine.util.CompoundVariable;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.samplers.Sampler;
import org.apache.jmeter.threads.JMeterVariables;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jmeter.functions.AbstractFunction;
import org.apache.jmeter.functions.InvalidVariableException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 创建一个自定义函数__Hello, 实现: Hello xxx, today is xxx
 * 
 * Create a custom function __Hello to: Hello xxx, today is xxx
 */
public class HelloFunction extends AbstractFunction {

    private static final List<String> desc = new LinkedList<>();
    private static final String KEY = "__hello";

    static {
        desc.add("Hello Words");
        desc.add("Name of variable in which to store the result (optional)");
    }

    private CompoundVariable varName;
    private CompoundVariable helloWords;

    public HelloFunction() {}

    /**
     * 核心功能
     * 更多关于execute：https://jmeter.apache.org/api/org/apache/jmeter/functions/Function.html#execute()
     * 
     * Core features
     * More info about the execute: https://jmeter.apache.org/api/org/apache/jmeter/functions/Function.html#execute()
     */
    @Override
    public String execute(SampleResult previousResult, Sampler currentSampler)
            throws InvalidVariableException {


        String hello = helloWords.execute().trim();
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        String today = dateFormat.format(date);
        String response = hello + ", today is " + today;

        if (varName != null) {
            JMeterVariables vars = getVariables();
            final String varTrim = varName.execute().trim();
            if (vars != null && varTrim.length() > 0){
                vars.put(varTrim, response);
            }
        }

        return response;
    }

    /**
     * 为所有的参数保存值
     * 更多关于setParameters：http://jmeter.apache.org/api/org/apache/jmeter/functions/Function.html#setParameters()
     * 
     * Save all the values for the parameters in JMeter
     * More info about the setParameters: http://jmeter.apache.org/api/org/apache/jmeter/functions/Function.html#setParameters()
     */
    @Override
    public void setParameters(Collection<CompoundVariable> parameters) throws InvalidVariableException {
        checkParameterCount(parameters, 1, 2);
        Object[] values = parameters.toArray();

        helloWords = (CompoundVariable) values[0];
        if (values.length>1){
            varName = (CompoundVariable) values[1];
        } else {
            varName = null;
        }
    }

    /**
     * 在 Function Helper 中显示自定义的函数名
     * 更多关于getReferenceKey：https://jmeter.apache.org/api/org/apache/jmeter/functions/Function.html#getReferenceKey()
     * 
     * Show the custom function name in Function Helper
     * More info about the getReferenceKey: https://jmeter.apache.org/api/org/apache/jmeter/functions/Function.html#getReferenceKey()
     */
    @Override
    public String getReferenceKey() {
        return KEY;
    }

    /**
     * 在 Function Helper 中显示每个参数的描述
     * 更多关于getArgumentDesc：https://jmeter.apache.org/api/org/apache/jmeter/functions/Function.html#getReferenceKey()
     * 
     * Show the parameter descripton in Function Helper
     * More info about the getArgumentDesc: https://jmeter.apache.org/api/org/apache/jmeter/functions/Function.html#getReferenceKey()
     */
    @Override
    public List<String> getArgumentDesc() {
        return desc;
    }

}
