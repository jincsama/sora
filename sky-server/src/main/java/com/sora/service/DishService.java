package com.sora.service;

import com.sora.dto.DishDTO;
import com.sora.dto.DishPageQueryDTO;
import com.sora.result.PageResult;
import com.sora.vo.DishVO;

import java.util.List;

public interface DishService {

    /**
     * 新增菜品及口味
     * @param dishDTO
     */
    public void saveWithFlavor(DishDTO dishDTO);

    PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);

    void delete(List<Long> ids);

    DishVO getByIdWithFlavor(Long id);

    void updateWithFlavor(DishDTO dishDTO);
}
