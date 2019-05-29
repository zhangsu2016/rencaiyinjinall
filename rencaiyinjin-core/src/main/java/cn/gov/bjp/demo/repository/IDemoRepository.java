package cn.gov.bjp.demo.repository;

import cn.gov.bjp.demo.pojo.DemoModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "demoRepository")
public interface IDemoRepository extends CrudRepository<DemoModel,Long> {
    DemoModel findByName(String name);
}
