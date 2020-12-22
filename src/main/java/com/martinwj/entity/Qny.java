package com.martinwj.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName: Tencent
 * @Description: TODO 七牛云配置表
 * @author: martin-wj
 * @createDate: 2020-12-18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("serial")
public class Qny implements Serializable {
    private String id;		// 主键
    private String type;	// 类型
    private String name;	// 名称
    private String domain;	// 空间绑定域名
    private String ak;		// 密钥ak
    private String sk;		// 密钥sk
    private String bucket;	// 空间名称
    private String width;	// 自动裁剪后的宽度（0为不裁剪）
    private String height;	// 自动裁剪后的高度（0为不裁剪）
    private String compress;// 压缩率（0为不压缩）
}
