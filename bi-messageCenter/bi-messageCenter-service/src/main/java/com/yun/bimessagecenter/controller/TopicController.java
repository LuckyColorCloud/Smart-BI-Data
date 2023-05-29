package com.yun.bimessagecenter.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yun.bidatacommon.vo.Result;
import com.yun.bimessagecenter.entity.TopicEntity;
import com.yun.bimessagecenter.service.TopicService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


/**
 * 主题配置
 *
 * @author Yun
 */
@RestController
@RequestMapping("/topic")
@Tag(name = "主题配置")
public class TopicController {
    @Autowired
    private TopicService topicService;

    /**
     * 列表
     *
     * @param pageNo   当前页码
     * @param pageSize 每页显示条数
     * @return 结果集
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @Operation(summary = "列表")
    public Result<?> queryApiPathList(@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                      @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                      @RequestParam(name = "projectId", defaultValue = "1") Integer projectId) {
        return Result.OK(topicService.page(new Page(pageNo, pageSize), new QueryWrapper<TopicEntity>().lambda().eq(TopicEntity::getProjectId, projectId).orderByDesc(TopicEntity::getCreatedTime)));
    }

    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    @Operation(summary = "查询")
    public Result<TopicEntity> info(@PathVariable("id") Integer id) {
        return Result.OK(topicService.getById(id));
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @Operation(summary = "保存")
    public Result<String> save(@RequestBody TopicEntity topicEntity) {
        topicService.save(topicEntity);
        return Result.OK();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @Operation(summary = "更新")
    public Result<String> update(@RequestBody TopicEntity topicEntity) {
        topicEntity.setUpdatedTime(new Date());
        topicService.updateById(topicEntity);
        return Result.OK();
    }

    /**
     * 删除
     */
    @GetMapping("/delete/{id}")
    @Operation(summary = "删除")
    public Result<String> delete(@PathVariable("id") Integer id) {
        topicService.removeById(id);
        return Result.OK();
    }

}
