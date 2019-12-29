package hello.jmeter.hellosampler.gui;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import hello.jmeter.hellosampler.HelloSampler;
import org.apache.jmeter.samplers.gui.AbstractSamplerGui;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.util.JMeterUtils;

// import kg.apc.jmeter.JMeterPluginsUtils;

/**
 * 这里是JMeter界面控制的代码
 *
 * This is the GUI class which contains necessary methods
 * to make GUI component suitable for execution in JMeter.
 */
public class HelloSamplerGui extends AbstractSamplerGui {

    private static final long serialVersionUID = 240L;
    private JTextArea helloInput;
    public HelloSamplerGui() {
        init();
    }

    @Override
    public String getStaticLabel() {
        return "Hello Sampler";
    }

    @Override
    public String getLabelResource() {
        return this.getClass().getSimpleName();
    }

    @Override
    public void configure(TestElement element) {
        helloInput.setText(element.getPropertyAsString(HelloSampler.HELLOINPUT));
        super.configure(element);
    }

    @Override
    public TestElement createTestElement() {
        HelloSampler sampler = new HelloSampler();
        modifyTestElement(sampler);
        return sampler;
    }

    @Override
    public void modifyTestElement(TestElement te) {
        te.clear();
        configureTestElement(te);
        te.setProperty(HelloSampler.HELLOINPUT, helloInput.getText());
    }

    /*
     * 设置JMeter GUI界面
     *
     * Helper method to set up the GUI screen
     */
    private void init() { 
        // 标准设置
        // Standard setup
        setLayout(new BorderLayout(0, 5));
        setBorder(makeBorder());
        add(makeTitlePanel(), BorderLayout.NORTH); 

        // 特殊设置 
        // Specific setup
        add(createDataPanel(), BorderLayout.CENTER);
    }

    /*
     * 为helloInput创建一个数据输入框
     * @return 返回界面输入的数据
     * 
     * Create a helloInput input text field
     * @return the panel for entering the helloInput
     */
    private Component createDataPanel() {
        JLabel label = new JLabel("Input Hello Words: ");

        helloInput = new JTextArea();
        helloInput.setName(HelloSampler.HELLOINPUT);
        label.setLabelFor(helloInput);

        JPanel dataPanel = new JPanel(new BorderLayout(5, 0));
        dataPanel.add(label, BorderLayout.WEST);
        dataPanel.add(helloInput, BorderLayout.CENTER);

        return dataPanel;
    }
    
    @Override
    public void clearGui() {
        super.clearGui();
        helloInput.setText(""); 
    }
}
