package com.yun.bifilemanage.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.text.csv.CsvData;
import cn.hutool.core.text.csv.CsvReader;
import cn.hutool.core.text.csv.CsvRow;
import cn.hutool.core.text.csv.CsvUtil;
import cn.hutool.core.util.ZipUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.poi.excel.ExcelUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yun.bidatacommon.constant.CommonConstant;
import com.yun.bidatacommon.vo.Result;
import com.yun.bidatastorage.api.DataStorageApiFeign;
import com.yun.bidatastorage.dto.DropTableDto;
import com.yun.bidatastorage.dto.SaveFileDto;
import com.yun.bifilemanage.dao.FileStorageDao;
import com.yun.bifilemanage.entity.FileStorageEntity;
import com.yun.bifilemanage.exception.FileManageException;
import com.yun.bifilemanage.service.FileService;
import com.yun.bifilemanage.service.FileStorageService;
import com.yun.bifilemanage.vo.FileVo;
import com.yun.bifilemanage.vo.MinioFileVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author BlessingCR
 * @date 2022/11/03
 * @description
 */
@Service("fileStorageService")
@Slf4j
public class FileStorageServiceImpl extends ServiceImpl<FileStorageDao, FileStorageEntity> implements FileStorageService {

    @Autowired
    private DataStorageApiFeign dataStorageApiFeign;

    @Autowired
    private FileService fileService;

    /**
     * 保存文件 并 远程调用存储
     *
     * @param file     文件
     * @param saveName 保存名称
     * @param sourceId 数据源Id
     * @return
     */
    @SuppressWarnings("AlibabaSwitchStatement")
    @Override
    public Result<Object> saveAndStorage(MultipartFile file, String saveName, Integer sourceId) {
        Result<Object> result;
        try {
            //创建文件实体类存储
            FileStorageEntity fileStorageEntity = new FileStorageEntity();
            //调用minio统一存储 文件
            Long fileId = fileService.upload(file);
            fileStorageEntity.setSize(file.getSize());
            fileStorageEntity.setMinioId(fileId);
            Integer type = typeJudgment(Objects.requireNonNull(FilenameUtils.getName(file.getOriginalFilename())));
            fileStorageEntity.setFileType(type);
            fileStorageEntity.setSourceId(sourceId);
            fileStorageEntity.setSaveName(saveName);
            //保存
            this.save(fileStorageEntity);
            //存储结构
            List<Map> hashMaps = dataStorage(fileId, type);
            //远程调用 存储服务
            SaveFileDto saveFileDto = new SaveFileDto();
            saveFileDto.setCharset(CommonConstant.UTF_8);
            saveFileDto.setSaveName(fileStorageEntity.getSaveName());
            //数据压缩
            saveFileDto.setContext(ZipUtil.gzip(JSONUtil.toJsonStr(hashMaps), CommonConstant.UTF_8));
            //远程调用存储
            result = dataStorageApiFeign.saveFile(saveFileDto);
        } catch (FileManageException ex) {
            return Result.ERROR(ex.getMessage());
        } catch (NullPointerException nullPointerException) {
            return Result.ERROR(Result.ResultEnum.ERROR_GETTING_FILE_TYPE);
        } catch (Exception ex) {
            ex.printStackTrace();
            return Result.ERROR(Result.ResultEnum.UNKNOWN_EXCEPTION);
        }
        return result;
    }

    @Override
    public Result<Object> dropTable(Integer id) {
        FileStorageEntity fileStorageEntity = this.getById(id);
        if (fileStorageEntity != null) {
            fileStorageEntity.setStatus(true);
            this.updateById(fileStorageEntity);
            DropTableDto dropTableDto = new DropTableDto();
            dropTableDto.setTableName(fileStorageEntity.getSaveName());
            dropTableDto.setSourceId(fileStorageEntity.getSourceId());
            return dataStorageApiFeign.dropTable(dropTableDto);
        }
        return Result.ERROR(Result.ResultEnum.FILE_DOES_NOT_EXIST);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public Page<FileVo> list(Page<FileStorageEntity> params) {
        //查询分页结果
        Page<FileStorageEntity> page = this.page(params);
        //返回新的分页
        Page result = new Page();
        BeanUtil.copyProperties(params, result);
        //组成新的 vo对象
        List<FileVo> collect = page.getRecords().parallelStream().map(t -> {
            FileVo fileVo = new FileVo();
            MinioFileVO minioFileVO = fileService.queryById(t.getMinioId());
            BeanUtil.copyProperties(minioFileVO, fileVo);
            BeanUtil.copyProperties(t, fileVo);
            return fileVo;
        }).collect(Collectors.toList());
        result.setRecords(collect);
        return result;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void restart(FileStorageEntity fileStorageEntity) {
        this.save(fileStorageEntity);
        //清除全部数据
        dropTable(fileStorageEntity.getId());
        //重新读取数据
        List<Map> data = dataStorage(fileStorageEntity.getMinioId(), fileStorageEntity.getFileType());
        //远程调用 存储服务
        SaveFileDto saveFileDto = new SaveFileDto();
        saveFileDto.setCharset(CommonConstant.UTF_8);
        saveFileDto.setSaveName(fileStorageEntity.getSaveName());
        //数据压缩
        saveFileDto.setContext(ZipUtil.gzip(JSONUtil.toJsonStr(data), CommonConstant.UTF_8));
        //远程调用存储
        Result<Object> result = dataStorageApiFeign.saveFile(saveFileDto);
        if (!result.isSuccess()) {
            throw new FileManageException(result.getMessage());
        }
    }

    /**
     * 类型判断
     *
     * @param fileName 文件后缀
     * @return 文件类型
     */
    private Integer typeJudgment(String fileName) {
        switch (fileName.substring(fileName.lastIndexOf(CommonConstant.POINT))) {
            case "csv":
                return 0;
            case "xlsx":
                return 1;
            case "json":
                return 2;
            default:
                throw new FileManageException();
        }
    }

    @SuppressWarnings({"rawtypes", "AlibabaSwitchStatement"})
    private List<Map> dataStorage(Long fileId, Integer type) {
        List<Map> hashMaps = new ArrayList<>();
        InputStream inputStream = fileService.queryInputStreamById(fileId);
        //判断文件类型进行解析
        switch (type) {
            //csv
            case 0:
                CsvReader reader = CsvUtil.getReader();
                //从文件中读取CSV数据
                CsvData data = reader.read(IoUtil.getReader(inputStream, StandardCharsets.UTF_8));
                //获取头 作为 key
                List<String> header = data.getHeader();
                List<CsvRow> rows = data.getRows();
                //遍历行
                for (CsvRow csvRow : rows) {
                    HashMap<String, Object> map = new HashMap<>();
                    //getRawList返回一个List列表，列表的每一项为CSV中的一个单元格（既逗号分隔部分）
                    List<String> rawList = csvRow.getRawList();
                    for (int i = 0; i < header.size(); i++) {
                        try {
                            map.put(header.get(i), rawList.get(i));
                        } catch (Exception e) {
                            log.error("获取行内容出错 出错行数:{}", i);
                        }
                    }
                    hashMaps.add(map);
                }
                break;
            //xlsx
            case 1:
                List<Map<String, Object>> maps = ExcelUtil.getReader(inputStream).readAll();
                hashMaps.addAll(maps);
                break;
            // json
            case 2:
                String json = IoUtil.read(inputStream,StandardCharsets.UTF_8);
                if (JSONUtil.isTypeJSONObject(json)) {
                    hashMaps.add(JSONUtil.toBean(json, HashMap.class));
                } else if (JSONUtil.isTypeJSONArray(json)) {
                    hashMaps.addAll(JSONUtil.toList(json, HashMap.class));
                } else {
                    throw new FileManageException(Result.ResultEnum.JSON_TYPE_ERROR);
                }
                break;
        }
        return hashMaps;
    }
}
