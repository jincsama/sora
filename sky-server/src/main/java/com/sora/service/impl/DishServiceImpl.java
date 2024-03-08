package com.sora.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sora.constant.MessageConstant;
import com.sora.constant.StatusConstant;
import com.sora.dto.DishDTO;
import com.sora.dto.DishPageQueryDTO;
import com.sora.entity.Dish;
import com.sora.entity.DishFlavor;
import com.sora.exception.DeletionNotAllowedException;
import com.sora.mapper.DishFlavorMapper;
import com.sora.mapper.DishMapper;
import com.sora.mapper.setMealDishMapper;
import com.sora.result.PageResult;
import com.sora.service.DishService;
import com.sora.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private DishFlavorMapper dishFlavorMapper;
    @Autowired
    private setMealDishMapper setMealDishMapper;

    /**
     * 新增菜品及口味
     *
     * @param dishDTO
     */

    @Transactional
    public void saveWithFlavor(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);

        dishMapper.insert(dish);
        Long id = dish.getId();

        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors != null && flavors.size() > 0) {
            for (DishFlavor flavor : flavors) {
                flavor.setDishId(id);
            }
            dishFlavorMapper.insertBatch(flavors);

        }

    }

    /**
     * 菜品分页查询
     *
     * @param dishPageQueryDTO
     * @return
     */
    public PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize());
        Page<DishVO> page = dishMapper.pageQuery(dishPageQueryDTO);

        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 菜品批量删除
     *
     * @param ids
     */
    @Transactional
    public void delete(List<Long> ids) {
        //判断删除
        for (Long id : ids) {
            Dish dish = dishMapper.getById(id);
            if (dish.getStatus() == StatusConstant.ENABLE) {
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        }
        List<Long> setMealIds = setMealDishMapper.getsetMealIdsDishIds(ids);
        if (setMealIds != null && setMealIds.size() > 0) {
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }
        //删除
            dishMapper.delete(ids);
            dishFlavorMapper.deleteByDishId(ids);
    }
}