package com.yun.bisecurity.loopauth;

import com.sobercoding.loopauth.abac.face.AbacInterface;
import com.sobercoding.loopauth.abac.model.Policy;
import com.sobercoding.loopauth.model.LoopAuthHttpMode;
import com.yun.bidatacommon.vo.Result;
import com.yun.bisecurity.entity.AbacPolicyEntity;
import com.yun.bisecurity.service.AbacPolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * @author Sober
 */
@Component
public class AbAcInterFaceImpl implements AbacInterface {

    @Resource
    private AbacPolicyService abacPolicyService;

    /**
     * 获取一个或多个路由/权限代码所属的 规则
     *
     * @param route            路由
     * @param loopAuthHttpMode 请求方式
     * @return 去重后的集合
     */
    @Override
    public Set<Policy> getPolicySet(String route, LoopAuthHttpMode loopAuthHttpMode) {
        List<AbacPolicyEntity> abacPolicyEntityList = abacPolicyService
                .getPolicySet(route, loopAuthHttpMode.name());

        return new HashSet<>();
    }
}
