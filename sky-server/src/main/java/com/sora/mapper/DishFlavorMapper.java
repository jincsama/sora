package com.sora.mapper;


import com.sora.entity.DishFlavor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishFlavorMapper {

    /**
     * 批量插入口味数据
     * @param flavor
     */

    void insertBatch(List<DishFlavor> flavor);
    @Delete("delete from dish_flavor where id = #{dishId}")
    void deleteByDishIds(Long dishId);

    /**
     * 根据菜品id批量删除
     * @param dishIds
     */
    void deleteByDishId(List<Long> dishIds);

    /**
     * 菜品id查询
     * @param dishId
     */
    @Select("select * from dish_flavor where dish_id = #{dishId}")
    List<DishFlavor> getByDishId(Long dishId);
}
