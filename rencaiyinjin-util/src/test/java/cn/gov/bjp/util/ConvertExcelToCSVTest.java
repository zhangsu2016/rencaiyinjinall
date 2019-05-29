package cn.gov.bjp.util;

import org.apache.poi.util.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.FileOutputStream;

import static org.junit.Assert.*;

public class ConvertExcelToCSVTest {
    private final static Logger LOGGER= LoggerFactory.getLogger(ConvertExcelToCSVTest.class);
    @Test
    public void convert() {
        Assert.assertEquals(true,true);
        ConvertExcelToCSV convertExcelToCSV=new ConvertExcelToCSV();
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("ceshi20190529.csv");
            Resource resource=new ClassPathResource("test.xlsx");
            byte[] paramBytes= IOUtils.toByteArray(resource.getInputStream());
            byte[] bakBytes=convertExcelToCSV.convert(paramBytes,1,"xlsx");
            fileOutputStream.write(bakBytes);
            fileOutputStream.close();
            LOGGER.info("转换完{}为csv文件","xlsx");
        }catch (Exception e){
            e.printStackTrace();
            LOGGER.error("在测试xlsx文件转换为csv文件时出现错误",e);
        }
    }
}