package com.martinwj.test;

import org.apache.ibatis.io.Resources;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;

/**
 * @ClassName: SqlTest
 * @Description: TODO
 * @author: martin-wj
 * @createDate: 2020-12-01
 */
public class SqlTest {

    public static void main(String[] args) throws IOException {
        // 加载配置文件
        InputStream is = Resources.getResourceAsStream("mybatisConfig.xml");

    }

}
