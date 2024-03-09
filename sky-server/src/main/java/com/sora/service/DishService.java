package com.sora.service;

import com.sora.dto.DishDTO;
import com.sora.dto.DishPageQueryDTO;
import com.sora.entity.Dish;
import com.sora.entity.Setmeal;
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

    List<DishVO> listWithFlavor(Dish dish);

    List<Dish> list(Long categoryId);

    void startOrStop(Integer status, Long id);
}
