package cn.gov.bjp.util;


import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FileParser {

    /**
     * 分隔符
     */
    private static final String SPLIT_TAG = ",";
    /**
     * 忽略标记
     */
    private static final String IGNORE_TAG = "#";

    /**
     * 获取文件内容
     *
     * @param fileBytes
     * @return
     */
    public List<String[]> loadItems(byte[] fileBytes) {
        List<String[]> itemList = new ArrayList<String[]>();
        BufferedReader reader = null;
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(new ByteArrayInputStream(fileBytes));
            reader = new BufferedReader(inputStreamReader);
            String tempString = null;
            // 一次读入一行，直到读入空为文件结束
            while ((tempString = reader.readLine()) != null) {
                if (tempString.indexOf(IGNORE_TAG) == 0) {
                    continue;
                }
                itemList.add(tempString.split(SPLIT_TAG));
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        // 剔除第一行标题信息
        itemList.remove(0);
        return itemList;
    }


}
