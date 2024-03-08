package com.sora.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sora.constant.MessageConstant;
import com.sora.constant.StatusConstant;
import com.sora.dto.SetmealDTO;
import com.sora.dto.SetmealPageQueryDTO;
import com.sora.entity.Dish;
import com.sora.entity.Setmeal;
import com.sora.entity.SetmealDish;
import com.sora.exception.DeletionNotAllowedException;
import com.sora.exception.SetmealEnableFailedException;
import com.sora.mapper.DishMapper;
//import com.sora.mapper.SetmealDishMapper;
import com.sora.mapper.SetmealMapper;
import com.sora.result.PageResult;
import com.sora.service.SetmealService;
import com.sora.vo.DishItemVO;
import com.sora.vo.SetmealVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * 套餐业务实现
 */
@Service
@Slf4j
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealMapper setmealMapper;
//    @Autowired
//    private SetmealDishMapper setmealDishMapper;
    @Autowired
    private DishMapper dishMapper;

    /**
     * 条件查询
     * @param setmeal
     * @return
     */
    public List<Setmeal> list(Setmeal setmeal) {
        List<Setmeal> list = setmealMapper.list(setmeal);
        return list;
    }

    /**
     * 根据id查询菜品选项
     * @param id
     * @return
     */
    public List<DishItemVO> getDishItemById(Long id) {
        return setmealMapper.getDishItemBySetmealId(id);
    }
}
