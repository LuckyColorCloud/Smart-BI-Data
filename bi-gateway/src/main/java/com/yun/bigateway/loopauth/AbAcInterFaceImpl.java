package com.yun.bigateway.loopauth;

import com.sobercoding.loopauth.abac.face.AbacInterface;
import com.sobercoding.loopauth.abac.model.Policy;
import com.sobercoding.loopauth.model.LoopAuthHttpMode;
import com.yun.bidatacommon.vo.Result;
import com.yun.bisecurity.api.SecurityContextFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * @author Sober
 */
@Service
public class AbAcInterFaceImpl implements AbacInterface {

    @Autowired
    private SecurityContextFeign securityContextFeign;

    /**
     * 获取一个或多个路由/权限代码所属的 规则
     *
     * @param route            路由
     * @param loopAuthHttpMode 请求方式
     * @return 去重后的集合
     */
    @Override
    public Set<Policy> getPolicySet(String route, LoopAuthHttpMode loopAuthHttpMode) {
        CompletableFuture<Result<Set<Policy>>> result = CompletableFuture.supplyAsync(
                () -> securityContextFeign.getPolicySet(route, loopAuthHttpMode.name()
                )
        );
        return result.join().getResult();
    }
}
