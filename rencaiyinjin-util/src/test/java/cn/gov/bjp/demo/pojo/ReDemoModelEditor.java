package cn.gov.bjp.demo.pojo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.PropertyValue;
import org.springframework.util.StringUtils;

import java.beans.PropertyEditorSupport;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReDemoModelEditor extends PropertyEditorSupport {

    private final static Logger LOGGER = LoggerFactory.getLogger(ReDemoModelEditor.class);
    private final static String DATE_FORMAT = "yyyy-MM-dd";

    /**
     * 进行解析使用的模型对象列表
     */
    private final static String[] needToConvertNames = new String[]{"id", "age", "name"};

    public ReDemoModelEditor() {
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        // 简单判断设置的值
        if (text == null || text.indexOf(",") < 0) {
            throw new IllegalArgumentException("DemoModel 对象设置的参数格式设置错误。");
        }
        //拆分数据为字符串的数组

        LOGGER.info("text={}", text);

        String[] ardaray = StringUtils.commaDelimitedListToStringArray(text);
        ReDemoModel rcm = new ReDemoModel();
        //进行绑定对象
        BeanWrapper bw = new BeanWrapperImpl(rcm);
        /**
         * 加入注解日期对象
         */
        registerDate(bw);

        for (int i = 0; i < ardaray.length - 1; i++) {
            //完全依赖属性进行处理 章苏 2017-2-22
            Class requiredType = bw.getPropertyType(needToConvertNames[i]);
            PropertyValue pv = new PropertyValue(needToConvertNames[i],
                    bw.convertIfNecessary(ardaray[i], requiredType));
            bw.setPropertyValue(pv);

        }
        //真正转换为 DemoModel 对象
        setValue(rcm);
    }

    /**
     * 注册日期对象
     *
     * @param bw
     */
    private void registerDate(BeanWrapper bw) {
        bw.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            public void setAsText(String value) {
                try {
                    setValue(new SimpleDateFormat(DATE_FORMAT).parse(value));
                } catch (Exception e) {
                    setValue(null);
                }
            }

            public String getAsText() {
                return new SimpleDateFormat(DATE_FORMAT).format((Date) getValue());
            }

        });
    }
}
