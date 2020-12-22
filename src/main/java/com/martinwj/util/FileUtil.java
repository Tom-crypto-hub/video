package com.martinwj.util;

import java.io.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * @ClassName: FileUtil
 * @Description: TODO
 * @author: martin-wj
 * @createDate: 2020-12-22
 */
public class FileUtil {

    /**
     * 获取路径下的所有文件
     * @param directoryPath 需要遍历的文件夹路径
     * @return
     */
    public static List<String> getAllFile(String directoryPath) {
        return getAllFile(directoryPath, false, null);
    }

    /**
     * 获取路径下的所有文件
     * @param directoryPath 需要遍历的文件夹路径
     * @param format 正则，需要以什么后缀结尾的文件
     * @return
     */
    public static List<String> getAllFile(String directoryPath, String format) {
        return getAllFile(directoryPath, false, format);
    }

    /**
     * 获取路径下的所有文件/文件夹
     * @param directoryPath 需要遍历的文件夹路径
     * @param isAddDirectory 是否将子文件夹的路径也添加到list集合中
     * @return
     */
    public static List<String> getAllFile(String directoryPath, boolean isAddDirectory) {
        return getAllFile(directoryPath, isAddDirectory, null);
    }

    /**
     * 获取路径下的所有以 **为后缀 的文件/文件夹（如.jpeg, .png等等)
     * @param directoryPath 需要遍历的文件夹路径
     * @param isAddDirectory 是否将子文件夹的路径也添加到list集合中
     * @param format 正则，需要以什么后缀结尾的文件
     * @return
     */
    public static List<String> getAllFile(String directoryPath, boolean isAddDirectory, String format) {
        List<String> list = new ArrayList<String>();
        File baseFile = new File(directoryPath);
        if (baseFile.isFile() || !baseFile.exists()) {
            return list;
        }
        File[] files = baseFile.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                if(isAddDirectory){
                    list.add(file.getAbsolutePath());
                }
                list.addAll(getAllFile(file.getAbsolutePath(),isAddDirectory));
            } else {
                // 如果要匹配固定格式的文件的话，只添加需要的，否则添加所有文件
                if(format != null && !"".equals(format)) {
                    if(file.getName().endsWith(format)) {
                        list.add(file.getAbsolutePath());
                    }
                } else {
                    list.add(file.getAbsolutePath());
                }
            }
        }
        return list;
    }

    /**
     * 文件转换base64
     */
    public static String fileToBase64(String path) {
        String base64 = null;
        InputStream in = null;
        try {
            File file = new File(path);
            in = new FileInputStream(file);
            byte[] bytes = new byte[(int) file.length()];
            in.read(bytes);
            base64 = Base64.getEncoder().encodeToString(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return base64;
    }

    /**
     * base64转文件
     */
    public static void base64ToFile(String base64, String destPath, String fileName) throws IOException {
        File file = null;
        //创建文件目录
        String filePath = destPath;
        File dir = new File(filePath);
        if (!dir.exists() && !dir.isDirectory()) {
            dir.mkdirs();
        }
        BufferedOutputStream bos = null;
        java.io.FileOutputStream fos = null;
        try {
            byte[] bytes = Base64.getDecoder().decode(base64);
            file = new File(filePath + "/" + fileName);
            fos = new java.io.FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bytes);
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 复制文件
     * @param filePath   被复制的文件路径
     * @param toFilePath 粘贴的路径
     */
    public static void copyFile(String filePath, String toFilePath) {
        try {
            //给被复制文件的路径，创建一个文件
            File file = new File(filePath);
            //创建粘贴的文件路径
            File copyFile = new File(toFilePath);
            //新建一个文件
            copyFile.createNewFile();

            //创建输入、输出流
            FileInputStream fis = new FileInputStream(file);
            FileOutputStream fos = new FileOutputStream(copyFile);
            //定义一个byte数组
            byte[] bytes = new byte[512];
            int len = 0;
            //判断有没有读取完成
            while ((len = fis.read(bytes)) != -1) {
                //写入数据
                fos.write(bytes, 0, len);
            }
            //释放资源
            fis.close();
            fos.flush();
            fos.close();
            //复制完成
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
