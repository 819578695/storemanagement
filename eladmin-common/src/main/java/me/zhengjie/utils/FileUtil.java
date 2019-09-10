package me.zhengjie.utils;

import cn.hutool.core.util.IdUtil;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.*;
import java.text.DecimalFormat;

/**
 * File工具类，扩展 hutool 工具包
 * @author Zheng Jie
 * @date 2018-12-27
 */
public class FileUtil extends cn.hutool.core.io.FileUtil {

    /**
     * 定义GB的计算常量
     */
    private static final int GB = 1024 * 1024 * 1024;
    /**
     * 定义MB的计算常量
     */
    private static final int MB = 1024 * 1024;
    /**
     * 定义KB的计算常量
     */
    private static final int KB = 1024;

    /**
     * 格式化小数
     */
    private static final DecimalFormat DF = new DecimalFormat("0.00");

    /**
     * MultipartFile转File
     * @param multipartFile
     * @return
     */
    public static File toFile(MultipartFile multipartFile){
        // 获取文件名
        String fileName = multipartFile.getOriginalFilename();
        // 获取文件后缀
        String prefix="."+getExtensionName(fileName);
        File file = null;
        try {
            // 用uuid作为文件名，防止生成的临时文件重复
            file = File.createTempFile(IdUtil.simpleUUID(), prefix);
            // MultipartFile to File
            multipartFile.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * 删除
     * @param files
     */
    public static void deleteFile(File... files) {
        for (File file : files) {
            if (file.exists()) {
                file.delete();
            }
        }
    }

    /**
     * 获取文件扩展名
     * @param filename
     * @return
     */
    public static String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot >-1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return filename;
    }

    /**
     * Java文件操作 获取不带扩展名的文件名
     * @param filename
     * @return
     */
    public static String getFileNameNoEx(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot >-1) && (dot < (filename.length()))) {
                return filename.substring(0, dot);
            }
        }
        return filename;
    }

    /**
     * 文件大小转换
     * @param size
     * @return
     */
    public static String getSize(int size){
        String resultSize = "";
        if (size / GB >= 1) {
            //如果当前Byte的值大于等于1GB
            resultSize = DF.format(size / (float) GB) + "GB   ";
        } else if (size / MB >= 1) {
            //如果当前Byte的值大于等于1MB
            resultSize = DF.format(size / (float) MB) + "MB   ";
        } else if (size / KB >= 1) {
            //如果当前Byte的值大于等于1KB
            resultSize = DF.format(size / (float) KB) + "KB   ";
        } else {
            resultSize = size + "B   ";
        }
        return resultSize;
    }
    /**
     * 文件上传
     * @param multipartRequest
     * @param httpUrl 服务器文件地址
     * @param filePath 文件根路径
     * @param fileAttrName 文件属性名
     * @param folderName 文件夹名称(分层级例如:"/user/image",null或""代表默认目录)
     * @param contractNo 编号(不带@后缀)
     * @return String http地址
     */
    public static String uploadUtil(MultipartHttpServletRequest multipartRequest, String httpUrl, String filePath,
                                    String fileAttrName, String folderName, String contractNo){
        filePath = (filePath == null) ? "/" : filePath;
        /* 通过MultipartHttpServletRequest来获得MultipartFile对象 */
        MultipartFile multipartFile = multipartRequest.getFile(fileAttrName);
        /* 获得原始文件的名字 */
        String originalFileName = multipartFile.getOriginalFilename();
        /* 获得文件的后缀名 */
        String suffix = originalFileName.substring(originalFileName.lastIndexOf("."));
        /* 对文件重命名，设定为当前系统时间的毫秒数加邮箱名 */
        String newFileName = System.currentTimeMillis() + ((contractNo == null)?"":"."+contractNo) +suffix;
        if (!StringUtils.isEmpty(folderName)) {
            filePath += folderName;
        }else{
            folderName = "";
        }
        File folder = new File(filePath);
        if (!folder.exists()) {
            //　mkdirs(): 创建多层目录
            folder.mkdirs();
        }
        File file = new File(filePath + File.separator + newFileName);
        try {
            /* 文件不存在就创建文件 */
            if (!file.exists()) {
                file.createNewFile();
            }
            /* 进行上传文件 */
            InputStream input = multipartFile.getInputStream();
            OutputStream output = new FileOutputStream(file);
            IOUtils.copy(input, output);
            String url = httpUrl + folderName + "/" + newFileName;
            /* 关闭输入流和输出流 */
            input.close();
            output.close();
            return url;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
