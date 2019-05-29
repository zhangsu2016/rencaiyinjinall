package cn.gov.bjp.demo.service;

import cn.gov.bjp.demo.pojo.DemoModel;

public interface IDemoService {

    DemoModel findByName(String name);

    void saveDemoModel(DemoModel demoModel);
}
