package com.yun.bisecurity.group.account.model.convert;

import com.yun.bisecurity.group.account.model.entity.AccountEntity;
import com.yun.bisecurity.group.account.model.vo.AccountVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Sober
 */
@Mapper
public interface AccountConvert {

    AccountConvert INSTANCE = Mappers.getMapper(AccountConvert.class);

    AccountVo convert(AccountEntity entity);

}
