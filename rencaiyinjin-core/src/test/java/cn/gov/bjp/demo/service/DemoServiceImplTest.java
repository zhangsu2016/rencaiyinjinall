package cn.gov.bjp.demo.service;

import cn.gov.bjp.demo.DemoApp;
import cn.gov.bjp.demo.pojo.DemoModel;
import cn.gov.bjp.demo.pojo.DemoModelEditor;
import cn.gov.bjp.util.CommonModelIterator;
import org.apache.poi.util.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DemoApp.class)
public class DemoServiceImplTest {

    private final static Logger LOGGER= LoggerFactory.getLogger(DemoServiceImplTest.class);
    @Autowired
    private IDemoService demoService;

    public DemoServiceImplTest() {
    }


    @Test
    public void save() {
        DemoModel demoModel = new DemoModel();
        demoModel.setAge(20);
        demoModel.setName("zhangsan");
        demoModel.setId(new Long(1));
        demoService.saveDemoModel(demoModel);

    }

    @Test
    public void mergeDemoModel() {
        DemoModel demoModel = new DemoModel();
        demoModel.setAge(21);
        demoModel.setName("zhangsan");
        demoModel.setId(new Long(1));
        demoService.saveDemoModel(demoModel);
    }

    @Test
    public void saveManyDemoModel() {
        String xlsFileName=new String("test.xlsx");
        try {
            Resource resource = new ClassPathResource(xlsFileName);
            byte[] paramBytes = IOUtils.toByteArray(resource.getInputStream());
            CommonModelIterator<DemoModel> commonModelIterator = new CommonModelIterator<DemoModel>(paramBytes, "xlsx", new DemoModelEditor(), 1, CommonModelIterator.TEST);
            while (commonModelIterator.hasNext()) {
                DemoModel dm = commonModelIterator.next();
                LOGGER.info("开始保存对象[{}]......",dm.toString());
                demoService.saveDemoModel(dm);
                LOGGER.info("保存完成对象[{}].",dm.toString());
            }
        }catch (Exception e){
            e.printStackTrace();
            LOGGER.error("在测试批量保存Excel[{}]对象中的数据时出现错误[{}]",xlsFileName,e);
        }


    }


    @Test
    public void findByName() {
        DemoModel demoModel = demoService.findByName("zhangsan");
        Assert.assertEquals("zhangsan's age=20", 20, demoModel.getAge());
    }

    @Test
    public void findAll() {
        List<DemoModel> list = demoService.findAll();
        LOGGER.info("查询总数=[{}]",list.size());
        for (DemoModel dm : list) {
           LOGGER.info("dm=[{}]",dm.toString());
        }
    }
}