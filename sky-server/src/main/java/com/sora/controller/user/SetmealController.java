package com.sora.controller.user;

import com.sora.constant.StatusConstant;
import com.sora.dto.SetmealDTO;
import com.sora.dto.SetmealPageQueryDTO;
import com.sora.entity.Setmeal;
import com.sora.result.PageResult;
import com.sora.result.Result;
import com.sora.service.SetmealService;
import com.sora.vo.DishItemVO;
import com.sora.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("userSetmealController")
@RequestMapping("/user/setmeal")
@Api(tags = "C端-套餐浏览接口")
public class SetmealController {
    @Autowired
    private SetmealService setmealService;

    /**
     * 条件查询
     *
     * @param categoryId
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("根据分类id查询套餐")
    @Cacheable(cacheNames = "setmealCache",key = "#categoryId")
    public Result<List<Setmeal>> list(Long categoryId) {
        Setmeal setmeal = new Setmeal();
        setmeal.setCategoryId(categoryId);
        setmeal.setStatus(StatusConstant.ENABLE);

        List<Setmeal> list = setmealService.list(setmeal);
        return Result.success(list);
    }

    /**
     * 根据套餐id查询包含的菜品列表
     *
     * @param setmealId
     * @return
     */
    @GetMapping("/dish/{id}")
    @ApiOperation("根据套餐id查询包含的菜品列表")
    @Cacheable(cacheNames = "setmealCache",key = "#setmealId")
    public Result<List<DishItemVO>> dishList(@PathVariable("id") Long setmealId) {
        List<DishItemVO> list = setmealService.getDishItemById(setmealId);
        return Result.success(list);
    }

}
