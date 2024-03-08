package com.sora.service;

import com.sora.dto.SetmealDTO;
import com.sora.dto.SetmealPageQueryDTO;
import com.sora.entity.Setmeal;
import com.sora.result.PageResult;
import com.sora.vo.DishItemVO;
import com.sora.vo.SetmealVO;
import java.util.List;

public interface SetmealService {

    /**
     * 条件查询
     * @param setmeal
     * @return
     */
    List<Setmeal> list(Setmeal setmeal);

    /**
     * 根据id查询菜品选项
     * @param id
     * @return
     */
    List<DishItemVO> getDishItemById(Long id);

}
