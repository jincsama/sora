package com.sora.mapper;


import com.sora.annotation.AutoFill;
import com.sora.entity.DishFlavor;
import com.sora.enumeration.OperationType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DishFlavorMapper {

    /**
     * 批量插入口味数据
     * @param flavor
     */

    void insertBatch(List<DishFlavor> flavor);

    /**
     * 根据菜品id批量删除
     * @param dishIds
     */
    void deleteByDishId(List<Long> dishIds);
}
