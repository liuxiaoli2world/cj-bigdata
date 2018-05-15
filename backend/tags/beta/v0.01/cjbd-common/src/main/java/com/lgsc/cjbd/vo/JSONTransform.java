package com.lgsc.cjbd.vo;

/**
 * @author zzq
 * @desc 将字符串转化成json字符串
 * @date 2017/3/14 10:53
 */
public interface JSONTransform<T> {
    public  T tranform(T str);
}
