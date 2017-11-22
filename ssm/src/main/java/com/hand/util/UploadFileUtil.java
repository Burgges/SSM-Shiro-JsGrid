package com.hand.util;

import com.alibaba.fastjson.JSON;

import java.io.*;
import java.util.List;

/**
 * Created by Nick on 2017/11/6.
 *
 */
public class UploadFileUtil {

//    private static final String PATH = "C://Users/Nick/Desktop/fileTest.txt";
    private static final String OPEN_FILE_STYLE = "r";
    private static final String FIELD_LIMIT_CHAR = ",";
    private static final int FIELD_ALL_COUNT = 6;
    private int count;

    /*
     * 功能：解析文本文件
     */
    public void loadFile(String path) {
        try {
            RandomAccessFile raf = new RandomAccessFile(path, OPEN_FILE_STYLE);
            String line_record = raf.readLine();
            while (line_record != null) {
                // 解析每一条记录
                parseRecord(line_record);
                line_record = raf.readLine();
            }
            System.out.println("共有合法的记录" + count + "条");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
 * 功能：具体解析每一条记录，这里可以增加很多对记录的解析判断条件，如是否为字母、
* 数字、email等。
     */
    private void parseRecord(String line_record) throws Exception {
        String userName;
        String password;
        String sex;
        String email;
        String roleId;
        String enabledFlag;

        //拆分记录
        String[] fields = line_record.split(FIELD_LIMIT_CHAR);
        if (fields.length == FIELD_ALL_COUNT) {
            userName = tranStr(fields[0]);
            password = tranStr(fields[1]);
            sex = tranStr(fields[2]);
            email = tranStr(fields[3]);
            roleId = tranStr(fields[4]);
            enabledFlag = tranStr(fields[5]);
            System.out.println(userName + " " + password + " " + sex + " "
                    + email + " " + roleId + " " + enabledFlag );
            count++;
        }
    }

    private String tranStr(String oldStr) {
        String newStr = "";
        try {
            newStr = new String(oldStr.getBytes("ISO-8859-1"), "GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return newStr;
    }

    /**
     * 读取文件中的全部内容
     * @param filePath 文件路径
     * @return 文件内容
     */
    public String readToString(String filePath) {
        String encoding = "ISO-8859-1";
        File file = new File(filePath);
        Long filelength = file.length();
        byte[] filecontent = new byte[filelength.intValue()];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(filecontent);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return new String(filecontent, encoding);
        } catch (UnsupportedEncodingException e) {
            System.err.println("The OS does not support " + encoding);
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args){
        UploadFileUtil uploadFileUtil = new UploadFileUtil();
        String fileContent = uploadFileUtil.readToString("C://Users/Nick/Desktop/fileTest2.txt");

        System.out.println(fileContent);
        UserUtil userUtil = new UserUtil();
        List<Users> userList = userUtil.getUserList();

    }
}
