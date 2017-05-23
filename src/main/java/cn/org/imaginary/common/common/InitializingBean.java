package cn.org.imaginary.common.common;

/**
 * Author    : imaginary
 * Date      : 2017/5/23 15 15
 * Version   : V1.0
 * Desc      :
 */
public interface InitializingBean {
    void afterPropertiesSet() throws Exception;
}

