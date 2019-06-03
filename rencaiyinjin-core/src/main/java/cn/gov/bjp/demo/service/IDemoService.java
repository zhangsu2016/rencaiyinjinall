package cn.gov.bjp.demo.service;

import cn.gov.bjp.demo.pojo.DemoModel;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IDemoService {

    DemoModel findByName(String name);

    void saveDemoModel(DemoModel demoModel);

    List<DemoModel> findAll();

    Page<DemoModel> findDemo(DemoModel condition);

}
