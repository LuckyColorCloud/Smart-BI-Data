package com.yun.bifilemanage.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.text.csv.CsvData;
import cn.hutool.core.text.csv.CsvReader;
import cn.hutool.core.text.csv.CsvRow;
import cn.hutool.core.text.csv.CsvUtil;
import cn.hutool.core.util.ZipUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.poi.excel.ExcelUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yun.bidataconnmon.constant.CommonConstant;
import com.yun.bidataconnmon.vo.Result;
import com.yun.bidatastorage.api.DataStorageApiFeign;
import com.yun.bidatastorage.dto.DropTableDto;
import com.yun.bidatastorage.dto.SaveFileDto;
import com.yun.bifilemanage.dao.FileDao;
import com.yun.bifilemanage.entity.FileEntity;
import com.yun.bifilemanage.exception.FileManageException;
import com.yun.bifilemanage.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author Yun
 */
@Service("fileService")
@Slf4j
public class FileServiceImpl extends ServiceImpl<FileDao, FileEntity> implements FileService {

    @Value("${bi-fileManage.savePath}")
    private String domain;
    @Autowired
    private DataStorageApiFeign dataStorageApiFeign;

    /**
     * 保存文件 并 远程调用存储
     *
     * @param file     文件
     * @param md5      md5
     * @param saveName 保存名称
     * @param sourceId 数据源Id
     * @return
     */
    @SuppressWarnings("AlibabaSwitchStatement")
    @Override
    public Result<Object> saveAndStorage(MultipartFile file, String md5, String saveName, Integer sourceId) {
        Result<Object> result;
        try {
            //创建文件实体类存储
            FileEntity fileEntity = new FileEntity();
            fileEntity.setFileName(file.getOriginalFilename());
            fileEntity.setFilePath(domain + File.separator + file.getOriginalFilename());
            fileEntity.setSize(file.getSize());
            fileEntity.setFileMd5(md5);
            Integer type = typeJudgment(Objects.requireNonNull(FilenameUtils.getName(file.getOriginalFilename())));
            fileEntity.setFileType(type);
            fileEntity.setSourceId(sourceId);
            fileEntity.setSaveName(saveName);
            //保存文件到目录
            File saveFile = new File(fileEntity.getFilePath());
            file.transferTo(saveFile);
            //保存
            this.save(fileEntity);
            //存储结构
            List<Map> hashMaps = new ArrayList<>();
            //判断文件类型进行解析
            switch (type) {
                //csv
                case 0:
                    CsvReader reader = CsvUtil.getReader();
                    //从文件中读取CSV数据
                    CsvData data = reader.read(saveFile);
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
                    List<Map<String, Object>> maps = ExcelUtil.getReader(saveFile).readAll();
                    hashMaps.addAll(maps);
                    break;
                // json
                case 2:
                    String json = FileUtil.readString(saveFile, StandardCharsets.UTF_8);
                    if (JSONUtil.isTypeJSONObject(json)) {
                        hashMaps.add(JSONUtil.toBean(json, HashMap.class));
                    } else if (JSONUtil.isTypeJSONArray(json)) {
                        hashMaps.addAll(JSONUtil.toList(json, HashMap.class));
                    } else {
                        return Result.ERROR(Result.ResultEnum.JSON_TYPE_ERROR);
                    }
                    break;
            }
            //远程调用 存储服务
            SaveFileDto saveFileDto = new SaveFileDto();
            saveFileDto.setCharset(CommonConstant.UTF_8);
            saveFileDto.setSaveName(fileEntity.getSaveName());
            //数据压缩
            saveFileDto.setContext(ZipUtil.gzip(JSONUtil.toJsonStr(hashMaps), CommonConstant.UTF_8));
            //远程调用存储
            result = dataStorageApiFeign.saveFile(saveFileDto);
        } catch (IOException ioException) {
            ioException.printStackTrace();
            return Result.ERROR(Result.ResultEnum.FAILED_TO_SAVE_THE_FILE);
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
        FileEntity fileEntity = this.getById(id);
        if (fileEntity != null) {
            fileEntity.setStatus(true);
            this.updateById(fileEntity);
            DropTableDto dropTableDto = new DropTableDto();
            dropTableDto.setTableName(fileEntity.getSaveName());
            dropTableDto.setSourceId(fileEntity.getSourceId());
            return dataStorageApiFeign.dropTable(dropTableDto);
        }
        return Result.ERROR(Result.ResultEnum.FILE_DOES_NOT_EXIST);
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
}

