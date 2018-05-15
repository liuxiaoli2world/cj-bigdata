
package com.lgsc.cjbd.dao;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @desp   通用mapper,所有mapper都会集成它
 * @date   2017/3/7 10:54
 * @author zzq
 *
**/


public interface BaseMapper<T> extends Mapper<T>, MySqlMapper<T> {

}
