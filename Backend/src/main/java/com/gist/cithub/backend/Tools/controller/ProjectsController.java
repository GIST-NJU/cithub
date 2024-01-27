package com.gist.cithub.backend.Tools.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gist.cithub.backend.common.utils.PageUtils;
import com.gist.cithub.backend.common.utils.R;
import com.gist.cithub.backend.Tools.entity.ProjectsEntity;
import com.gist.cithub.backend.Tools.service.ProjectsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * @author xiangjiantu
 * @email 1223564154@qq.com
 * @date 2022-08-28 17:02:35
 */
@RestController
@RequestMapping("tools/projects")
public class ProjectsController {
    @Autowired
    private ProjectsService projectsService;

    /**
     * 列表
     */
    @RequestMapping(value="/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = projectsService.queryPage(params);

        return R.ok().put("page", page);
    }

//    //Gateway的测试接口
//    @RequestMapping("/testGateway")
//    public R testGateWay() {
//        ProjectsEntity projectsEntity = new ProjectsEntity();
//        projectsEntity.setProjectid(1);
//        projectsEntity.setProjectdescriptions("testGateWayDesc");
//        projectsEntity.setProjectname("testGateWayName");
//        System.out.println("GateWay测试成功");
//        return R.ok().put("projects", projectsEntity);
//
//    }

    //   OpenFeign远程调用测试，由Users模块调用下面这个接口 ，该接口返回一个key为projects的projectsEntity
//    @RequestMapping("/getUserProjects")
//    public R getUserProjects() {
//        ProjectsEntity projectsEntity = new ProjectsEntity();
//        projectsEntity.setUserid(1);
//        projectsEntity.setProjectname("test");
//        projectsEntity.setProjectdescriptions("desc");
//        projectsEntity.setProjectname("testname");
//
//        CoveringarraysEntity coveringarraysEntity = new CoveringarraysEntity();
//        coveringarraysEntity.setCoveringarrayname("testCA");
//        coveringarraysEntity.setCoveringarraydescriptions("testdesc");
//        coveringarraysEntity.setCoveringarrayid(12);
//        coveringarraysEntity.setModelid(789);
//        return R.ok().put("coveringArray", coveringarraysEntity).put("projects", Arrays.asList(projectsEntity));
//    }


    @RequestMapping(value="/listProjectByUserID",method = RequestMethod.POST)
    public R listProjectByUserID(@RequestBody Map<String, Object> info) {
//        System.out.println("info是" + info);
        Integer userID = (Integer) info.get("userID");
//        System.out.println("listAllByProjectID" + projectid);
        List<ProjectsEntity> projectList = projectsService.listProjectByUserID(userID);
        return R.ok().put("projectList", projectList);

    }

    @RequestMapping(value="/updateProject",method = RequestMethod.POST)
    public R updatePaper(@RequestBody ProjectsEntity projectsEntity) {
//        System.out.println(listEntity.toString());
        Boolean updateFlag = projectsService.updateById(projectsEntity);
        if (updateFlag) return R.ok().put("UpdateStatus", "success!").put("UpdateResult", projectsEntity);
        else return R.ok().put("UpdateStatus", "failed!");

    }

    @RequestMapping(value="/DeleteByProjectID",method = RequestMethod.POST)
    public R deletePaper(@RequestBody Map<String,Object> info) {
//        System.out.println(info);
//        System.out.println(info.get("projectid"));
        QueryWrapper<ProjectsEntity> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("ProjectID",info.get("projectid"));
        Boolean deleteFlag=projectsService.remove(queryWrapper);
        if (deleteFlag) return R.ok().put("DeleteStatus", "success!");
        else return R.ok().put("DeleteStatus", "failed!");
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{projectid}")
    public R info(@PathVariable("projectid") Integer projectid) {
        ProjectsEntity projects = projectsService.getById(projectid);

        return R.ok().put("projects", projects);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody ProjectsEntity projects) {
        projectsService.save(projects);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody ProjectsEntity projects) {
        projectsService.updateById(projects);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] projectids) {
        projectsService.removeByIds(Arrays.asList(projectids));

        return R.ok();
    }

}
