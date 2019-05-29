package cn.gov.bjp.util;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.util.List;

public class FileParserTest {

    private final static Logger LOGGER = LoggerFactory.getLogger(FileParserTest.class);
    private FileParser fileParser = new FileParser();

    @Test
    public void loadCaseData() {
        Assert.assertEquals("0=0", 1L, 1L);
        ;
        try {
            Resource resource = new ClassPathResource("test.csv");
            List<String[]> list = fileParser.loadItems(IOUtils.toByteArray(resource.getInputStream()));
            for (String[] sa : list) {
                for (String s : sa) {
                    LOGGER.info("s:{}", s);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("在测试读取csv文件时发生错误", e);
        }


    }

}