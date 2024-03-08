package com.sora.mapper;

import com.github.pagehelper.Page;
import com.sora.annotation.AutoFill;
import com.sora.dto.DishPageQueryDTO;
import com.sora.entity.Dish;
import com.sora.enumeration.OperationType;
import com.sora.vo.DishVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishMapper {

    /**
     * 根据分类id查询菜品数量
     *
     * @param categoryId
     * @return
     */
    @Select("select count(id) from dish where category_id = #{categoryId}")
    Integer countByCategoryId(Long categoryId);


    @AutoFill(value = OperationType.INSERT)
    void insert(Dish dish);

    /**
     * 分页查询
     * @param dishPageQueryDTO
     * @return
     */
    Page<DishVO> pageQuery(DishPageQueryDTO dishPageQueryDTO);

    /**
     * 主键查询
     * @param id
     * @return
     */
    @Select("select * from dish where id = #{id}")
    Dish getById(Long id);


    /**
     * 主键批量删除
     * @param ids
     */
    void delete(List<Long> ids);

    @AutoFill(value = OperationType.UPDATE)
    void update(Dish dish);
}

