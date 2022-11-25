package com.yun.bimessagecenter.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yun.bidatacommon.vo.Result;
import com.yun.bimessagecenter.entity.MqConfigEntity;
import com.yun.bimessagecenter.service.MqConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


/**
 * MQ消息配置
 *
 * @author Yun
 */
@RestController
@RequestMapping("/mqConfig")
@Api(tags ="MQ消息配置")
public class MqConfigController {
    @Autowired
    private MqConfigService mqConfigService;


    /**
     * 列表
     *
     * @param pageNo   当前页码
     * @param pageSize 每页显示条数
     * @return 结果集
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ApiOperation("列表")
    public Result<?> queryApiPathList(@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                      @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                      @RequestParam(name = "projectId", defaultValue = "1") Integer projectId) {
        return Result.OK(mqConfigService.page(new Page(pageNo, pageSize), new QueryWrapper<MqConfigEntity>().lambda().eq(MqConfigEntity::getProjectId, projectId).orderByDesc(MqConfigEntity::getCreatedTime)));
    }

    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    @ApiOperation("查询信息")
    public Result<MqConfigEntity> info(@PathVariable("id") Integer id) {
        return Result.OK(mqConfigService.getById(id));
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @ApiOperation("保存")
    public Result<String> save(@RequestBody MqConfigEntity mqConfig) {
        mqConfigService.save(mqConfig);
        return Result.OK();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @ApiOperation("更新")
    public Result<String> update(@RequestBody MqConfigEntity mqConfig) {
        mqConfig.setUpdatedTime(new Date());
        mqConfigService.updateById(mqConfig);
        return Result.OK();
    }

    /**
     * 删除
     */
    @GetMapping("/delete/{id}")
    @ApiOperation("删除")
    public Result<String> delete(@PathVariable("id") Integer id){
        mqConfigService.removeById(id);
        return Result.OK();
    }

}
