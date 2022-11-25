package com.yun.bimessagecenter.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yun.bidatacommon.vo.Result;
import com.yun.bimessagecenter.entity.SocketConfigEntity;
import com.yun.bimessagecenter.service.SocketConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


/**
 * socket配置
 *
 * @author Yun
 */
@RestController
@RequestMapping("/socketConfig")
@Api(tags = "socket配置")
public class SocketConfigController {
    @Autowired
    private SocketConfigService socketConfigService;


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
        return Result.OK(socketConfigService.page(new Page(pageNo, pageSize), new QueryWrapper<SocketConfigEntity>().lambda().eq(SocketConfigEntity::getProjectId, projectId).orderByDesc(SocketConfigEntity::getCreatedTime)));
    }

    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    @ApiOperation("查询")
    public Result<SocketConfigEntity> info(@PathVariable("id") Integer id) {
        return Result.OK(socketConfigService.getById(id));
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @ApiOperation("保存")
    public Result<String> save(@RequestBody SocketConfigEntity socketConfigEntity) {
        socketConfigService.save(socketConfigEntity);
        return Result.OK();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @ApiOperation("更新")
    public Result<String> update(@RequestBody SocketConfigEntity socketConfigEntity) {
        socketConfigEntity.setUpdatedTime(new Date());
        socketConfigService.updateById(socketConfigEntity);
        return Result.OK();
    }

    /**
     * 删除
     */
    @GetMapping("/delete/{id}")
    @ApiOperation("删除")
    public Result<String> delete(@PathVariable("id") Integer id) {
        socketConfigService.removeById(id);
        return Result.OK();
    }

    /**
     * 上线
     */
    @GetMapping("/online/{id}")
    @ApiOperation("上线")
    public Result<String> online(@PathVariable("id") Integer id) {
        SocketConfigEntity socketConfigEntity = socketConfigService.getById(id);
        if (socketConfigEntity != null) {
            socketConfigEntity.setStatus(true);
            socketConfigService.online(socketConfigEntity);
        }
        return Result.OK();
    }

    /**
     * 下线
     */
    @GetMapping("/offline/{id}")
    @ApiOperation("下线")
    public Result<String> offline(@PathVariable("id") Integer id) {
        SocketConfigEntity socketConfigEntity = socketConfigService.getById(id);
        if (socketConfigEntity != null) {
            socketConfigEntity.setStatus(false);
            socketConfigService.offline(socketConfigEntity);
            socketConfigService.updateById(socketConfigEntity);
        }
        return Result.OK();
    }
}
