package cn.gov.bjp.demo.repository;

import cn.gov.bjp.demo.pojo.DemoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "demoRepository")
public interface IDemoRepository extends JpaRepository<DemoModel,Long>, JpaSpecificationExecutor<DemoModel > {
    DemoModel findByName(String name);
}
