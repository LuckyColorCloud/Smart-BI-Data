package com.yun.bisecurity.group.account.model.convert;

import com.yun.bisecurity.group.account.model.entity.AccountEntity;
import com.yun.bisecurity.group.account.model.param.AccountAddParam;
import com.yun.bisecurity.group.account.model.param.AccountEditParam;
import com.yun.bisecurity.group.account.model.vo.AccountVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author Sober
 */
@Mapper
public interface AccountConvert {

    AccountConvert INSTANCE = Mappers.getMapper(AccountConvert.class);

    /**
     * 对象转换
     *
     * @param entity
     * @return
     */
    AccountVo convert(AccountEntity entity);

    /**
     * 对象转换
     *
     * @param param
     * @return
     */
    AccountEntity convert(AccountAddParam param);

    /**
     * 对象转换
     *
     * @param param
     * @return
     */
    AccountEntity convert(AccountEditParam param);

    /**
     * 对象转换
     *
     * @param entitys
     * @return
     */
    List<AccountVo> convertList(List<AccountEntity> entitys);

}
