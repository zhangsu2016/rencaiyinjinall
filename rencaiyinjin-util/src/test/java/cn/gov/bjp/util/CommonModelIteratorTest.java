package cn.gov.bjp.util;

import cn.gov.bjp.demo.pojo.ReDemoModel;
import cn.gov.bjp.demo.pojo.ReDemoModelEditor;
import org.apache.poi.util.IOUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class CommonModelIteratorTest {

    private final static Logger LOGGER = LoggerFactory.getLogger(CommonModelIteratorTest.class);

    @Test
    public void next() {
        try {
            Resource resource = new ClassPathResource("test.xlsx");
            byte[] paramBytes = IOUtils.toByteArray(resource.getInputStream());
            CommonModelIterator<ReDemoModel> commonModelIterator = new CommonModelIterator<ReDemoModel>(paramBytes, "xlsx", new ReDemoModelEditor(), 1,CommonModelIterator.TEST);
           while (commonModelIterator.hasNext()) {
                ReDemoModel demoModel = commonModelIterator.next();
                LOGGER.info("demoModel:{}", demoModel.toString());
           }
        } catch (Exception e) {
            LOGGER.error("在测试用excel文件[{}]转换为[ ReDemoModel] 时发生错误", "test.xlsx", e);
        }

    }
}