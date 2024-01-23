package com.gist.cithub.repo.feign;

import com.gist.cithub.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("cithub-guitools")
public interface GuitoolsFeignService {
    @RequestMapping("/guitools/projects/getUserProjects")
    public R getUserProjects();
}
