package com.sora.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface setMealDishMapper {


    /**
     * 查询菜品id对应的套餐id
     * @param ids
     * @return
     */
    List<Long> getsetMealIdsDishIds(List<Long> ids);


}
