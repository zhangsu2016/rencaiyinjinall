package cn.gov.bjp.demo.service;

import cn.gov.bjp.demo.pojo.DemoModel;
import cn.gov.bjp.demo.repository.IDemoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "demoService")
public class DemoServiceImpl implements IDemoService {

    @Autowired
    private IDemoRepository demoRepository;


    public DemoServiceImpl() {
    }

    @Override
    public DemoModel findByName(String name) {
        return demoRepository.findByName(name);
    }

    @Override
    public void saveDemoModel(DemoModel demoModel) {

        demoRepository.save(demoModel);
    }
}
