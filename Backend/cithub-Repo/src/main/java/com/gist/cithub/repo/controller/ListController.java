package com.gist.cithub.repo.controller;

import java.io.*;
import java.util.*;

import com.alibaba.nacos.shaded.com.google.gson.JsonArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gist.cithub.repo.feign.GuitoolsFeignService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gist.cithub.repo.entity.ListEntity;
import com.gist.cithub.repo.service.ListService;
import com.gist.cithub.common.utils.PageUtils;
import com.gist.cithub.common.utils.R;
import org.springframework.web.multipart.MultipartFile;


/**
 * @author xiangjiantu
 * @email 1223564154@qq.com
 * @date 2022-09-08 21:44:17
 */
@RestController
@RequestMapping("repo/list")
public class ListController {
    @Autowired
    private ListService listService;

    @Autowired
    private GuitoolsFeignService guitoolsFeignService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = listService.queryPage(params);

        return R.ok().put("page", page);
    }

    @RequestMapping("/getAllTypeOfPapers")
    public R getAllTypeOfPapers() {
        List<Map<String, Object>> res = listService.getAllTypeofPapers();
//        System.out.println("res是"+res);
        return R.ok().put("res", res);
    }

    @RequestMapping("/listAllpapers")
    public R listAllCtpapers(@RequestBody Map<String, Object> pageinfo) {
/*
pagenum当前是第几页
pagesize每页有几项
* */

        System.out.println("pageinfo是" + pageinfo);
        Integer pagenum = (Integer) pageinfo.get("pagenum");
        Integer pagesize = (Integer) pageinfo.get("pagesize");
        String typeofPaper = (String) pageinfo.get("typerofPapers");
        Page<ListEntity> listEntityPage = listService.listAllPapers(pagenum, pagesize, typeofPaper);
        System.out.println("success");
        return R.ok().put("listEntityPage", listEntityPage);


    }

    @RequestMapping("/searchPapers")
    public R searchPapers(@RequestBody Map<String, Object> searchinfo) {
//        System.out.println(searchinfo);
        Integer pagenum = (Integer) searchinfo.get("pagenum");
        Integer pagesize = (Integer) searchinfo.get("pagesize");
        String searchkeywords = (String) searchinfo.get("searchkeywords");
        String typeofPapers = (String) searchinfo.get("typerofPapers");
        Page<ListEntity> res = listService.searchByKeywords(pagenum, pagesize, searchkeywords, typeofPapers);
        return R.ok().put("res", res);
    }

    @RequestMapping("/searchByAuthor")
    public R searchByAuthor(@RequestBody Map<String, Object> searchInfo) {
//        System.out.println("searchInfo是" + searchInfo);
        Integer pagenum = (Integer) searchInfo.get("pagenum");
        Integer pagesize = (Integer) searchInfo.get("pagesize");
        String searchkeywords = (String) searchInfo.get("searchkeywords");
        String typeofPapers = (String) searchInfo.get("typerofPapers");
        Page<ListEntity> res = listService.searchByAuthor(pagenum, pagesize, searchkeywords, typeofPapers);
        return R.ok().put("res", res);
    }

    @RequestMapping("/searchByInstitutions")
    public R searchByInstitutions(@RequestBody Map<String, Object> searchInfo) {
//        System.out.println("searchInfo是" + searchInfo);
        Integer pagenum = (Integer) searchInfo.get("pagenum");
        Integer pagesize = (Integer) searchInfo.get("pagesize");
        String searchkeywords = (String) searchInfo.get("searchkeywords");
        String typeofPapers = (String) searchInfo.get("typerofPapers");
        Page<ListEntity> res = listService.searchByInstitutions(pagenum, pagesize, searchkeywords, typeofPapers);
        return R.ok().put("res", res);
    }

    @RequestMapping("/searchByField")
    public R searchByField(@RequestBody Map<String, Object> searchInfo) {
//        System.out.println("searchInfo是" + searchInfo);
        Integer pagenum = (Integer) searchInfo.get("pagenum");
        Integer pagesize = (Integer) searchInfo.get("pagesize");
        String searchkeywords = (String) searchInfo.get("searchkeywords");
        String typeofPapers = (String) searchInfo.get("typerofPapers");
        Page<ListEntity> res = listService.searchByField(pagenum, pagesize, searchkeywords, typeofPapers);
        return R.ok().put("res", res);
    }

    @RequestMapping("/testRPC")
    public R testRPC() {
        R res = guitoolsFeignService.getUserProjects();
        return R.ok().put("res", res);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id) {
        ListEntity list = listService.getById(id);

        return R.ok().put("list", list);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody ListEntity list) {
        listService.save(list);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody ListEntity list) {
        listService.updateById(list);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids) {
        listService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    @RequestMapping("/testRepo")
    public R testRepo() {
        System.out.println("收到请求");
        return R.ok().put("success", "success");
    }

    @RequestMapping("/upload")
    public R upload(@RequestParam("uploadFile") MultipartFile file) throws FileNotFoundException, UnsupportedEncodingException {
        String saveResultPath = listService.savaUploadFile(file);
        Boolean saveResultFlag = false;
        if (saveResultPath != "保存失败") {
            System.out.println("文件保存路径是：" + saveResultPath);
            saveResultFlag = listService.writeToMysql(saveResultPath);
            if (saveResultFlag) {
                return R.ok().put("SaveResult", "success!");
            } else return R.ok().put("SaveResult", "Failed!");
        } else {
            return R.ok().put("UploadResult", "Failed!");
        }


    }

    @RequestMapping("/uploadByBib")
    public R uploadByBib(@RequestBody Map<String, String> bibObjArrayJson) {
//        选择项目那里，改一下，改成公用的
        ArrayList<ListEntity> paperList = null;
        paperList = listService.saveUploadFileByBib(bibObjArrayJson.get("bibObjArrayJson"));
        if (paperList == null) {
            return R.ok().put("UploadResult", "Failed!");
        } else {
            Boolean flag = listService.saveBatch(paperList);
            if (flag) return R.ok().put("SaveResult", "success!");
            else return R.ok().put("UploadResult", "Failed!");

        }
    }

    @RequestMapping("/uploadOnePaper")
    public R uploadOnePaper(@RequestBody ListEntity listEntity) {
        if (listService.save(listEntity)) {
            return R.ok().put("SaveResult", "success!");

        } else return R.ok().put("UploadResult", "Failed!");
    }

    @RequestMapping("/updatePaper")
    public R updatePaper(@RequestBody ListEntity listEntity) {
//        System.out.println(listEntity.toString());
        Boolean updateFlag = listService.updateById(listEntity);
        if (updateFlag) return R.ok().put("UpdateStatus", "success!").put("UpdateResult", listEntity);
        else return R.ok().put("UpdateStatus", "failed!");

    }

    @RequestMapping("/deletePaper")
    public R deletePaper(@RequestBody ListEntity listEntity) {
        Boolean deleteFlag = listService.removeById(listEntity);
        if (deleteFlag) return R.ok().put("DeleteStatus", "success!");
        else return R.ok().put("DeleteStatus", "failed!");
    }

    @RequestMapping("/listallVenue")
    public R listallVenue(@RequestBody Map<String, Object> infos) {
        String project = (String) infos.get("obj");
        QueryWrapper queryWrapper = new QueryWrapper<ListEntity>();
        queryWrapper.eq("Projects", project);
//        queryWrapper.select("abbr");
        return R.ok().put("res", listService.list(queryWrapper));
    }

    @RequestMapping("/searchByAbbr")
    public R searchByAbbr(@RequestBody Map<String, Object> infos) {


        Integer pagenum = (Integer) infos.get("pagenum");
        Integer pagesize = (Integer) infos.get("pagesize");
        String searchkeywords = (String) infos.get("searchkeywords");
        String typeofPapers = (String) infos.get("typerofPapers");

        Page<ListEntity> res = listService.searchByAbbr(pagenum, pagesize, searchkeywords, typeofPapers);
        return R.ok().put("res", res);
    }


    @RequestMapping("/searchByBooktitle")
    public R searchByBooktitle(@RequestBody Map<String, Object> infos) {
        Integer pagenum = (Integer) infos.get("pagenum");
        Integer pagesize = (Integer) infos.get("pagesize");
        String searchkeywords = (String) infos.get("searchkeywords");
        String typeofPapers = (String) infos.get("typerofPapers");

        Page<ListEntity> res = listService.searchByBooktitle(pagenum, pagesize, searchkeywords, typeofPapers);
        return R.ok().put("res", res);
    }

//    @RequestMapping("/getYearsCategory")
//    public R getYearsCategory(@RequestBody Map<String,Object> info) {
//        String typeOfPaper=(String) info.get("info");
//        QueryWrapper<ListEntity> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("Projects",typeOfPaper);
//        queryWrapper.select("DISTINCT year").orderByAsc("year");
//        return R.ok().put("YearsCategory",listService.list(queryWrapper));
//    }

    @RequestMapping("/countEachYear")
    public R countEachYear(@RequestBody Map<String,Object> info){
        String typeOfPaper=(String) info.get("info");
        QueryWrapper<ListEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("Projects",typeOfPaper);
        queryWrapper.select("year", "COUNT(*) AS RecordCount").groupBy("year").orderByAsc("year");
        return R.ok().put("countEachYear",listService.listMaps(queryWrapper));
    }

    @RequestMapping("/listBy")
    public R listBy(@RequestBody Map<String,Object> info)
    {
//        System.out.println("info是"+info);
        Map<String,Object> pageInfo= (Map<String, Object>) info.get("pageInfo");
        Integer pagenum = (Integer) pageInfo.get("pagenum");
        Integer pagesize = (Integer) pageInfo.get("pagesize");
        String typerofPapers=(String) pageInfo.get("typerofPapers");
        String listByKey = (String) info.get("listByKey");
        String listByValue = (String) info.get("listByValue");
        Page<ListEntity> res = listService.listBy(pagenum, pagesize, typerofPapers,listByKey, listByValue);
        return  R.ok().put("res",res);
    }



    @RequestMapping("/countEachCountry")
    public R countEachCountry(@RequestBody Map<String,Object> info){
        String typeOfPaper=(String) info.get("info");
        QueryWrapper<ListEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("Projects",typeOfPaper);
        queryWrapper.select("year", "COUNT(*) AS RecordCount").groupBy(
                "year").orderByAsc("year");
        return R.ok().put("countEachYear",listService.listMaps(queryWrapper));
    }

    @RequestMapping("/countField")
    public R countField(@RequestBody Map<String,Object> info){
        System.out.println("info是"+info);
        String typeOfPaper=(String) info.get("info");
        QueryWrapper<ListEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("Projects",typeOfPaper);
//        queryWrapper.select("field", "COUNT(field) AS RecordCount").groupBy("field").orderByDesc("year");
        queryWrapper.select("field", "COUNT(field) AS RecordCount").groupBy("field").orderByDesc("year");
        return R.ok().put("countEachField",listService.listMaps(queryWrapper));
    }



}
