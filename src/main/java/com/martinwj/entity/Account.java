package com.martinwj.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: Account
 * @Description: TODO 账户信息类（测试用）
 * @author: martin-wj
 * @createDate: 2020-12-01
 * 注释：@Data：作用于类上，是以下注解的集合：@ToString @EqualsAndHashCode @Getter @Setter @RequiredArgsConstructor
 */
@Data
public class Account implements Serializable {
    private Integer id;
    private String name;
    private Double money;
}
