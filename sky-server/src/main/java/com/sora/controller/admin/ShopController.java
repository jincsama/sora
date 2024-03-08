package com.sora.controller.admin;

import com.sora.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController("adminShopController")
@RequestMapping("/admin/shop")
@Api(tags = "店铺相关接口")
public class ShopController {

    @Autowired
    private RedisTemplate redisTemplate;

    public static final String SHOP_STATUS = "SHOP_STATUS";

    /**
     * 设置店铺状态
     *
     * @param status
     * @return
     */
    @PutMapping("/{status}")
    @ApiOperation("设置店铺状态")
    public Result setStatus(@PathVariable Integer status) {
        log.info("设置店铺状态为：{}", status == 1 ? "营业中" : "打烊");
        redisTemplate.opsForValue().set(SHOP_STATUS, status);

        return Result.success();
    }

    /**
     * 获取店铺营业状态
     * @return
     */
    @GetMapping("/status")
    @ApiOperation("获取店铺营业状态")
    public Result<Integer> getStatus(){
        Integer status = (Integer) redisTemplate.opsForValue().get(SHOP_STATUS);
        log.info("店铺状态为：{}", status == 1 ? "营业中" : "打烊");

        return Result.success(status);
    }
}
