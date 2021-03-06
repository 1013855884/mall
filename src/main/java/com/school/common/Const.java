package com.school.common;

import com.google.common.collect.Sets;

import java.util.Set;

/**
 * 常量类
 */
public class Const {
    //CURRENT_USER当前用户
    public static final String CURRENT_USER = "currentUser";
    public static final String EMAIL = "email";
    public static final String USERNAME= "username";

    public interface ProductListOrderBy{
        Set<String> PRICE_ASC_DESC = Sets.newHashSet("price_desc","price_asc");
    }

    public interface Cart{
        int CHECKED = 1;//购物车的选中状态
        int UN_CHECKED = 0;//购物车未选中的状态

        String LIMIT_NUM_FAIL = "LIMIT_NUM_FAIL";
        String LIMIT_NUM_SUCCESS = "LIMIT_NUM_SUCCESS";
    }
    public interface Role{
        int ROLE_CUSTOMER = 0;//普通用户
        int ROLE_ADMIN =1;//管理员
    }

    public enum ProductStatusEnum{
        ON_SALE("在线",1);

        private String value;
        private Integer code;

        ProductStatusEnum(String value, Integer code) {
            this.value = value;
            this.code = code;
        }

        public String getValue() {
            return value;
        }

        public Integer getCode() {
            return code;
        }
    }

}
