package cn.gov.bjp.util;

import java.beans.PropertyEditorSupport;
import java.io.*;
import java.util.Iterator;

/**
 * excel转换为对象文件
 * @param <T>
 */
public class CommonModelIterator<T> implements Iterator<T> {
    /**
     * 生产环境 0
     */
    public final static int PROD=0;
    /**
     * 测试环境 1
     */

    public final static int TEST=1;
    /**
     * CSV阅读器
     */
    private LineNumberReader reader;

    /**
     * 读文件的一行返回的值
     */
    private String last;
    /**
     * 调用来源 ，0-正式环境 ，1测试- 缺省是0
     * 增加这个属性的原因主要是因为在测试环境和正式环境下，next()方法返回的值如果是数组的，测试能用但生产环境不能使用；
     * next()方法返回的值如果是T型对象的，测试不能使用，但生产环境能使用，折中考虑增加了本属性 。
     * 章苏
     * 2019-5-27
     */
    private int invokeSource=0;
    /**
     * 为了获取属性编辑器，符合 javabean 规范
     */
    private PropertyEditorSupport propertyEditorSupport;
    /**
     * excel转换为csv工具
     */
    private ConvertExcelToCSV convertExcelToCSV=new ConvertExcelToCSV();

    /**
     * 缺省的构造器  Iterator对象，以的生产环境为准 0
     * @param xlsStreamBytes excel文件转换为字节数组
     * @param suffix excle文件后缀
     * @param propertyEditorSupport T对象的属性编辑器
     * @param startRow  从第几行开始
     */
    public CommonModelIterator(byte[] xlsStreamBytes,String suffix,PropertyEditorSupport propertyEditorSupport,int startRow) {
        this(xlsStreamBytes,suffix,propertyEditorSupport,startRow,TEST);
    }
    /**
     * 重新构造的一个Iterator对象
     * @param xlsStreamBytes excel文件转换为字节数组
     * @param suffix  excle文件后缀
     * @param propertyEditorSupport 对象的属性编辑器
     * @param startRow  从第几行开始
     * @param invokeSource  调用来源 ，0-正式环境，1-测试 缺省是0
     */
    public CommonModelIterator(byte[] xlsStreamBytes,String suffix,PropertyEditorSupport propertyEditorSupport,int startRow ,int invokeSource) {
        // TODO Auto-generated constructor stub
        this.propertyEditorSupport=propertyEditorSupport;
        byte[] retBytes=convertExcelToCSV.convert(xlsStreamBytes,startRow,suffix);
        InputStreamReader isr=new InputStreamReader(new ByteArrayInputStream(retBytes));
        reader=new LineNumberReader(isr);
        this.invokeSource=invokeSource;
    }



    @Override
    public boolean hasNext() {
        // TODO Auto-generated method stub
        try {
            last=reader.readLine();
            if (last==null){
                reader.close();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        }

        return last!=null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T next() {
        // TODO Auto-generated method stub
        try{
            String next=null;
            if (last!=null){
                next=last;
            }else{
                next=reader.readLine();
            }
            last=null;
            //解析字符串数组,
            //需要注意空行的解决问题
            PropertyEditorSupport pes=propertyEditorSupport.getClass().newInstance();
            pes.setAsText(next);
            //生产的情况下就回传数组 否则回传T对象
            return (invokeSource==PROD) ? (T)new Object[]{pes.getValue()} : (T)pes.getValue();

        }catch(Exception e){
            throw new RuntimeException("在转成【"+propertyEditorSupport.getClass().getName()+"】发生错误,", e.getCause());
        }
    }

    @Override
    public void remove() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException();
    }
}
