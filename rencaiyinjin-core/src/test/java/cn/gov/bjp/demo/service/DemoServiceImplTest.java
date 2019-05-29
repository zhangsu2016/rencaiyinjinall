package cn.gov.bjp.demo.service;

import cn.gov.bjp.DemoApp;
import cn.gov.bjp.demo.pojo.DemoModel;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

//@RunWith(SpringJUnit4ClassRunner.class)
@RunWith(Parameterized.class)
@SpringBootTest( classes = DemoApp.class)
public class DemoServiceImplTest {
    @Autowired
    private IDemoService demoService;

    public DemoServiceImplTest() {
    }

//    //使用外部文件方式
//    @Parameterized.Parameters
//    public static Collection<String[]> caseData() {
//        return FileParser.loadCaseData("test.csv");
//    }


    @Test
    public void save(){
        DemoModel demoModel=new DemoModel();
        demoModel.setAge(20);
        demoModel.setName("zhangsan");
        demoModel.setId(new Long(1));
        demoService.saveDemoModel(demoModel);

    }

    @Test
    public void testParameter(){
        Assert.assertEquals("Kaishi",1L,1L);
    }

    @Test
    public void findByName() {
        DemoModel demoModel=demoService.findByName("zhangsan");
        Assert.assertEquals("zhangsan's age=20",20,demoModel.getAge());
    }
}