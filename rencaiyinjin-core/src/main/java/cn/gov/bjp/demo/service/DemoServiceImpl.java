package cn.gov.bjp.demo.service;

import cn.gov.bjp.demo.pojo.DemoModel;
import cn.gov.bjp.demo.repository.IDemoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

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

    @Override
    public List<DemoModel> findAll(){
        return demoRepository.findAll();
    }

    @Override
    public Page<DemoModel> findDemo(DemoModel condition) {
        return null;
    }

}
