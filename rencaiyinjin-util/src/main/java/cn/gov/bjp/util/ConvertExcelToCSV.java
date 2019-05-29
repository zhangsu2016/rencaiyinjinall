package cn.gov.bjp.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 把Excel转换为csv文件
 */
public class ConvertExcelToCSV {

    public ConvertExcelToCSV() {
        // TODO Auto-generated constructor stub
    }

    /**
     * 进行校验excel转换为csv文件
     *
     * @param inputBytes excel文件的字节流
     * @param startRow    从第几行起
     * @param suffix      可以是xls 或xlsx格式
     * @return  byte[] 返回处理形成的字节数组，可以写入csv文件或直接使用
     */
    public byte[] convert(byte[] inputBytes,int startRow,String suffix) {

        BufferedInputStream bufferedInputStream = null;

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        //返回值
        int ret = 0;
        try {
            Workbook wb = null;
            bufferedInputStream = new BufferedInputStream(new ByteArrayInputStream(inputBytes));
            if ("xls".equalsIgnoreCase(suffix)) {
                wb = new HSSFWorkbook(bufferedInputStream);
            } else {
                wb = new XSSFWorkbook(bufferedInputStream);
            }
            //日期格式
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Sheet sheet = wb.getSheetAt(0); // 第一个工作表
            Row row = sheet.getRow(0);
            Cell cell = null;
            //excel格式表格的列数
            int columns = row.getLastCellNum();
            //行数，当然不完全需要
            int rows = sheet.getLastRowNum();
            //形成字符串
            StringBuffer buf = new StringBuffer();

            for (int k = startRow; k <= rows; k++) {
                row = sheet.getRow(k);
                // 空行的时候直接continue 防止下面getCellType()空指针
                if (row.getCell(0) == null) {
                    continue;
                }
                columns = row.getLastCellNum();
                //这里先判断第一行不是数字类型的序号的话，就退出
                if (row.getCell(0).getCellType() != Cell.CELL_TYPE_NUMERIC) {
                    break;
                }
                for (int j = 0; j < columns; j++) {
                    try {
                        cell = row.getCell(j);
                        if (cell != null) {
                            if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                                buf.append(cell.getStringCellValue().trim());
                            } else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                                if (DateUtil.isCellDateFormatted(cell)) {
                                    Date theDate = cell.getDateCellValue();
                                    buf.append(df.format(theDate));
                                } else {
                                    buf.append((int) Math.ceil(cell
                                            .getNumericCellValue()));
                                }
                            } else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
                                //下面的用数字来代替有些许问题 ，在测试警衔变动的模版时因为有公式，好像不能使用
                                //故而改进处理方式 章苏
                                //公式获取，如果是数字的进行数字处理，否则进行字符串处理
                                FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();
                                int geshi = evaluator.evaluateFormulaCell(cell);
                                if (geshi == Cell.CELL_TYPE_NUMERIC) {
                                    buf.append((int) Math.ceil(cell.getNumericCellValue()));
                                } else {
                                    buf.append(cell.getStringCellValue());
                                }


                            } else {
                                buf.append("0");
                            }
                            buf.append(",");
                        }
                    } catch (Exception e) {
                        throw new RuntimeException("row=" + k + ",column=" + j
                                + " is error:" + e.getMessage());
                    }

                }
                if (buf.length() > 0) {
                    buf.append("\r\n");
                }
                outputStream.write(buf.toString().getBytes());
                buf = null;
                buf = new StringBuffer();

            }
            bufferedInputStream.close();
            //1-代表处理成功，0-代表失败，-1代表excel格式不一致
            return outputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("在转换成csv格式时发生错误。" + e.getMessage(), e.getCause());
        }
    }

}
