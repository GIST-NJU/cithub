package com.gist.cithub.backend.Repo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gist.cithub.backend.common.utils.R;
import com.gist.cithub.backend.Repo.entity.ListEntity;
import com.gist.cithub.backend.Repo.service.ListService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @author xiangjiantu
 * @Tag(name = "文献接口")
 * @email 1223564154@qq.com
 * @date 2022-09-08 21:44:17
 */
//@Tag(name = "文献接口")
@RestController
@RequestMapping(value = "repo/list", method = RequestMethod.POST)
public class ListController {
    @Autowired
    private ListService listService;

    @Autowired

    /**
     * 列表
     */

//    @Operation(summary = "获取当前库内所有论文的种类")
    @RequestMapping(value = "/getAllTypeOfPapers", method = RequestMethod.POST)
    public R getAllTypeOfPapers() {
        List<Map<String, Object>> res = listService.getAllTypeofPapers();
//        System.out.println("res是"+res);
        return R.ok().put("res", res);
    }

//    @Operation(summary = "列出所有文献")
    @RequestMapping(value = "/listAllpapers", method = RequestMethod.POST)
    public R listAllpapers(@RequestBody Map<String, Object> pageinfo) {
/*
pagenum当前是第几页
pagesize每页有几项
* */
        System.out.println("pageinfo是" + pageinfo);
        Integer pagenum = (Integer) pageinfo.get("pagenum");
        Integer pagesize = (Integer) pageinfo.get("pagesize");
        String typeofPaper = (String) pageinfo.get("typerofPapers");
        Page<ListEntity> listEntityPage = listService.listAllPapers(pagenum, pagesize, typeofPaper);
//        System.out.println("success");
        return R.ok().put("listEntityPage", listEntityPage);


    }


//    @Operation(summary = "通过关键字分页搜索文献")
//    @Operation(summary = "列出所有文献")
    @RequestMapping(value = "/searchPapers", method = RequestMethod.POST)
    public R searchPapers( @RequestBody Map<String, Object> searchinfo) {
//        System.out.println(searchinfo);
        Integer pagenum = (Integer) searchinfo.get("pagenum");
        Integer pagesize = (Integer) searchinfo.get("pagesize");
        String searchkeywords = (String) searchinfo.get("searchkeywords");
        String typeofPapers = (String) searchinfo.get("typerofPapers");
        Page<ListEntity> res = listService.searchByKeywords(pagenum, pagesize, searchkeywords, typeofPapers);
        return R.ok().put("res", res);
    }

//    @Operation(summary = "通过作者名字搜索文献")
    @RequestMapping(value = "/searchByAuthor", method = RequestMethod.POST)
    public R searchByAuthor( @RequestBody Map<String, Object> searchInfo) {
//        System.out.println("searchInfo是" + searchInfo);
        Integer pagenum = (Integer) searchInfo.get("pagenum");
        Integer pagesize = (Integer) searchInfo.get("pagesize");
        String searchkeywords = (String) searchInfo.get("searchkeywords");
        String typeofPapers = (String) searchInfo.get("typerofPapers");
        Page<ListEntity> res = listService.searchByAuthor(pagenum, pagesize, searchkeywords, typeofPapers);
        return R.ok().put("res", res);
    }

//    @Operation(summary = "通过Insitutions搜索文献")
    @RequestMapping(value = "/searchByInstitutions", method = RequestMethod.POST)
    public R searchByInstitutions( @RequestBody Map<String, Object> searchInfo) {
//        System.out.println("searchInfo是" + searchInfo);
        Integer pagenum = (Integer) searchInfo.get("pagenum");
        Integer pagesize = (Integer) searchInfo.get("pagesize");
        String searchkeywords = (String) searchInfo.get("searchkeywords");
        String typeofPapers = (String) searchInfo.get("typerofPapers");
        Page<ListEntity> res = listService.searchByInstitutions(pagenum, pagesize, searchkeywords, typeofPapers);
        return R.ok().put("res", res);
    }

//    @Operation(summary = "通过Field搜索文献")
    @RequestMapping(value = "/searchByField", method = RequestMethod.POST)
    public R searchByField( @RequestBody Map<String, Object> searchInfo) {
//        System.out.println("searchInfo是" + searchInfo);
        Integer pagenum = (Integer) searchInfo.get("pagenum");
        Integer pagesize = (Integer) searchInfo.get("pagesize");
        String searchkeywords = (String) searchInfo.get("searchkeywords");
        String typeofPapers = (String) searchInfo.get("typerofPapers");
        Page<ListEntity> res = listService.searchByField(pagenum, pagesize, searchkeywords, typeofPapers);
        return R.ok().put("res", res);
    }

//    @RequestMapping(value="/testRPC")
//    public R testRPC() {
//        R res = guitoolsFeignService.getUserProjects();
//        return R.ok().put("res", res);
//    }


//    /**
//     * 信息
//     */
//    @RequestMapping("/info/{id}")
//    public R info(@PathVariable("id") Integer id) {
//        ListEntity list = listService.getById(id);
//
//        return R.ok().put("list", list);
//    }
//
//    /**
//     * 保存
//     */
//    @RequestMapping("/save")
//    public R save(@RequestBody ListEntity list) {
//        listService.save(list);
//
//        return R.ok();
//    }
//
//    /**
//     * 修改
//     */
//    @RequestMapping("/update")
//    public R update(@RequestBody ListEntity list) {
//        listService.updateById(list);
//
//        return R.ok();
//    }
//
//    /**
//     * 删除
//     */
//    @RequestMapping("/delete")
//    public R delete(@RequestBody Integer[] ids) {
//        listService.removeByIds(Arrays.asList(ids));
//
//        return R.ok();
//    }

//    @RequestMapping("/testRepo")
//    public R testRepo() {
//        System.out.println("收到请求");
//        return R.ok().put("success", "success");
//    }

//    @Operation(summary = "通过xlsx文件上传文献")
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public R upload( @RequestParam("uploadFile") MultipartFile file) throws FileNotFoundException, UnsupportedEncodingException {
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

//    @Operation(summary = "通过Bibtex文件上传文献")
    @RequestMapping(value = "/uploadByBib", method = RequestMethod.POST)
    public R uploadByBib( @RequestBody Map<String, String> bibObjArrayJson) {
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

//    @Operation(summary = "通过Form表单上传单篇文献")
    @RequestMapping(value = "/uploadOnePaper", method = RequestMethod.POST)
    public R uploadOnePaper(@RequestBody ListEntity listEntity) {
        if (listService.save(listEntity)) {
            return R.ok().put("SaveResult", "success!");

        } else return R.ok().put("UploadResult", "Failed!");
    }

//    @Operation(summary = "通过Form表单修改单篇已有文献")
    @RequestMapping(value = "/updatePaper", method = RequestMethod.POST)
    public R updatePaper( @RequestBody ListEntity listEntity) {
//        System.out.println(listEntity.toString());
        Boolean updateFlag = listService.updateById(listEntity);
        if (updateFlag) return R.ok().put("UpdateStatus", "success!").put("UpdateResult", listEntity);
        else return R.ok().put("UpdateStatus", "failed!");

    }

//    @Operation(summary = "删除单篇文献")
    @RequestMapping(value = "/deletePaper", method = RequestMethod.POST)
    public R deletePaper( @RequestBody ListEntity listEntity) {
        Boolean deleteFlag = listService.removeById(listEntity);
        if (deleteFlag) return R.ok().put("DeleteStatus", "success!");
        else return R.ok().put("DeleteStatus", "failed!");
    }

//    @Operation(summary = "列举所有Venue")
    @RequestMapping(value = "/listallVenue", method = RequestMethod.POST)
    public R listallVenue( @RequestBody Map<String, Object> infos) {
        String project = (String) infos.get("obj");
        QueryWrapper queryWrapper = new QueryWrapper<ListEntity>();
        queryWrapper.eq("Projects", project);
//        queryWrapper.select("abbr");
        return R.ok().put("res", listService.list(queryWrapper));
    }

//    @Operation(summary = "通过Abbr搜索文献")
    @RequestMapping(value = "/searchByAbbr", method = RequestMethod.POST)
    public R searchByAbbr( @RequestBody Map<String, Object> infos) {


        Integer pagenum = (Integer) infos.get("pagenum");
        Integer pagesize = (Integer) infos.get("pagesize");
        String searchkeywords = (String) infos.get("searchkeywords");
        String typeofPapers = (String) infos.get("typerofPapers");

        Page<ListEntity> res = listService.searchByAbbr(pagenum, pagesize, searchkeywords, typeofPapers);
        return R.ok().put("res", res);
    }


//    @Operation(summary = "通过BookTitle搜索文献")
    @RequestMapping(value = "/searchByBooktitle", method = RequestMethod.POST)
    public R searchByBooktitle( @RequestBody Map<String, Object> infos) {
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

//    @Operation(summary = "统计每年出版的文献数量")
    @RequestMapping(value = "/countEachYear", method = RequestMethod.POST)
    public R countEachYear( @RequestBody Map<String, Object> info) {
        String typeOfPaper = (String) info.get("info");
        QueryWrapper<ListEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("Projects", typeOfPaper);
        queryWrapper.select("year", "COUNT(*) AS RecordCount").groupBy("year").orderByAsc("year");
        return R.ok().put("countEachYear", listService.listMaps(queryWrapper));
    }

//    @Operation(summary = "通过关键字列举该关键字相关的所有文献")
    @RequestMapping(value = "/listBy", method = RequestMethod.POST)
    public R listBy( @RequestBody Map<String, Object> info) {
//        System.out.println("info是"+info);
        Map<String, Object> pageInfo = (Map<String, Object>) info.get("pageInfo");
        Integer pagenum = (Integer) pageInfo.get("pagenum");
        Integer pagesize = (Integer) pageInfo.get("pagesize");
        String typerofPapers = (String) pageInfo.get("typerofPapers");
        String listByKey = (String) info.get("listByKey");
        String listByValue = (String) info.get("listByValue");
        Page<ListEntity> res = listService.listBy(pagenum, pagesize, typerofPapers, listByKey, listByValue);
        return R.ok().put("res", res);
    }


//    @Operation(summary = "统计每个国家的文献数量")
    @RequestMapping(value = "/countEachCountry", method = RequestMethod.POST)
    public R countEachCountry( @RequestBody Map<String, Object> info) {
        String typeOfPaper = (String) info.get("info");
        QueryWrapper<ListEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("Projects", typeOfPaper);
        queryWrapper.select("year", "COUNT(*) AS RecordCount").groupBy(
                "year").orderByAsc("year");
        return R.ok().put("countEachCountry", listService.listMaps(queryWrapper));
    }


    @RequestMapping(value = "/countField", method = RequestMethod.POST)
    public R countField( @RequestBody Map<String, Object> info) {
        System.out.println("info是" + info);
        String typeOfPaper = (String) info.get("info");
        QueryWrapper<ListEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("Projects", typeOfPaper);
//        queryWrapper.select("field", "COUNT(field) AS RecordCount").groupBy("field").orderByDesc("year");
        queryWrapper.select("field", "COUNT(field) AS RecordCount").groupBy("field").orderByDesc("year");
        return R.ok().put("countEachField", listService.listMaps(queryWrapper));
    }

//    @Operation(summary = "统计每个Field每年的文献数量")
    @RequestMapping(value = "/countFieldEachYear", method = RequestMethod.POST)
    public R countFieldeachYear( @RequestBody Map<String, Object> info) {
        System.out.println("info是" + info);
        String typeOfPaper = (String) info.get("info");

        QueryWrapper<ListEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("Projects", typeOfPaper);
        queryWrapper.lt("year", 2023);// 添加年份小于2023的条件
        queryWrapper.ne("field", "application");
        queryWrapper.select("field", "year", "COUNT(*) AS RecordCount");
        queryWrapper.groupBy("field", "year");

        List<Map<String, Object>> result = listService.listMaps(queryWrapper);
        return R.ok().put("countFieldEachYear", result);
    }

//    @Operation(summary = "找到论文数前十的机构")
    @RequestMapping(value = "/topInstitutions", method = RequestMethod.POST)
    public R findTopOrganizations() {
        QueryWrapper<ListEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("institution", "COUNT(*) AS paperCount");
        queryWrapper.groupBy("institution");
        queryWrapper.orderByDesc("paperCount");
        queryWrapper.last("LIMIT 10");

        List<Map<String, Object>> result = listService.listMaps(queryWrapper);
        return R.ok().put("topInstitutions", result);
    }


}
