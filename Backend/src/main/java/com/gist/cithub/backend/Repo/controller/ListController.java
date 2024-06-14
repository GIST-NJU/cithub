package com.gist.cithub.backend.Repo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gist.cithub.backend.Repo.dao.ListDao;
import com.gist.cithub.backend.Repo.entity.authorAndscholarEntity;
import com.gist.cithub.backend.Repo.service.authorAndinstitutionService;
import com.gist.cithub.backend.Tools.entity.ModelsEntity;
import com.gist.cithub.backend.common.utils.R;
import com.gist.cithub.backend.Repo.entity.ListEntity;
import com.gist.cithub.backend.Repo.entity.view.count_field_annualEntity;
import com.gist.cithub.backend.Repo.service.ListService;
import com.gist.cithub.backend.Repo.dao.viewDao.count_field_annualDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.stream.Collectors;


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
    private count_field_annualDao countFieldAnnualDao;

    @Autowired
    private ListDao listDao;

    @Autowired
    private authorAndinstitutionService authorAndinstitutionService;


    /**
     * 列表
     */


    //    @Operation(summary = "列出所有文献")
    @RequestMapping(value = "/listAll", method = RequestMethod.POST)
    public R listAll(@RequestBody Map<String, Object> info) {
/*
pagenum当前是第几页
pagesize每页有几项
* */
        System.out.println("pageinfo是" + info);
        String column = (String) info.get("column");
        switch (column) {
            case "Papers":
                Integer pagenum = (Integer) info.get("pagenum");
                Integer pagesize = (Integer) info.get("pagesize");
                Page<ListEntity> listEntityPage = listService.listAllPapers(pagenum, pagesize);
//        System.out.println("success");
                return R.ok().put("listEntityPage", listEntityPage);

            case "Scholars":
                QueryWrapper<authorAndscholarEntity> queryWrapperScholars = new QueryWrapper<>();
                queryWrapperScholars.select("DISTINCT name");
                return R.ok().put("authors", authorAndinstitutionService.list(queryWrapperScholars));

            case "Institutions":
                QueryWrapper<authorAndscholarEntity> queryWrapperInstitutions = new QueryWrapper<>();
                queryWrapperInstitutions.select("institution", "category").isNotNull("institution");

                List<authorAndscholarEntity> resultList = authorAndinstitutionService.list(queryWrapperInstitutions);

                // 使用 Java 8 Stream API 去重
                List<authorAndscholarEntity> distinctInstitutions = resultList.stream()
                        .collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(authorAndscholarEntity::getInstitution))),
                                ArrayList::new));

                return R.ok().put("Institutions", distinctInstitutions);

//            case "Venues":
//                QueryWrapper queryWrapperVenues = new QueryWrapper<ListEntity>();
//                return R.ok().put("res", listService.list(queryWrapperVenues));

            case "Country":
                QueryWrapper<authorAndscholarEntity> queryWrapperCountry = new QueryWrapper<>();
                queryWrapperCountry.select("DISTINCT country");
                return R.ok().put("country", authorAndinstitutionService.list(queryWrapperCountry));

//            case "Tags":
//                QueryWrapper queryWrapperTags = new QueryWrapper<ListEntity>();
//                queryWrapperTags.isNotNull("tag");
//                queryWrapperTags.select("DISTINCT tag"); // 添加条件，确保tag字段不为空
//                return R.ok().put("res", listService.list(queryWrapperTags));
        }
        return R.ok();

    }

    @RequestMapping(value = "/countTotal", method = RequestMethod.POST)
    public R CountPaperTotal(@RequestBody Map<String, Object> pageinfo) {

//        获取文章总数
        QueryWrapper<ListEntity> queryWrapperPaper = new QueryWrapper<>();
        long totalPapers = listService.count(queryWrapperPaper);

//        获取 institutions 总数
        QueryWrapper<authorAndscholarEntity> queryWrapperInstitution = new QueryWrapper<>();
        queryWrapperInstitution.select("DISTINCT institution");
        long totalInstitutions = authorAndinstitutionService.count(queryWrapperInstitution);

//        获取学者总数
        QueryWrapper<authorAndscholarEntity> queryWrapperScholars = new QueryWrapper<>();
        queryWrapperScholars.select("DISTINCT name");
        long totalScholars = authorAndinstitutionService.count(queryWrapperScholars);
        return R.ok().put("totalPapers", totalPapers).put("totalInstitutions", totalInstitutions).put("totalScholars", totalScholars);

    }


    // @Operation(summary = "通过关键字分页搜索文献")
//通过输入的关键词模糊搜索
    @RequestMapping(value = "/searchPapers", method = RequestMethod.POST)
    public R searchPapers(@RequestBody Map<String, Object> searchinfo) {
        System.out.println(searchinfo);
        Integer pagenum = (Integer) searchinfo.get("pagenum");
        Integer pagesize = (Integer) searchinfo.get("pagesize");
        String searchkeywords = (String) searchinfo.get("searchkeywords");
        Page<ListEntity> res = listService.searchByKeywords(pagenum, pagesize, searchkeywords);
        return R.ok().put("res", res);
    }


    @RequestMapping(value = "/searchBy", method = RequestMethod.POST)
    public R searchBy(@RequestBody Map<String, Object> searchinfo) {
        System.out.println("searchBy中的" + searchinfo);
        Integer pagenum = (Integer) searchinfo.get("pagenum");
        Integer pagesize = (Integer) searchinfo.get("pagesize");
        String searchkeywords = (String) searchinfo.get("searchkeywords");
        String column = (String) searchinfo.get("column");
        if (column.length() != 0) {
            if (column.equals(("institution"))) {
                //        原始的查询，无分页
//        List<ListEntity> res = listDao.searchByInstitution(institution);
//        return R.ok().put("res", res);
                Page<ListEntity> res = listService.searchByInstitutionPages(pagenum, pagesize, searchkeywords, column);
                return R.ok().put("res", res);
            }

//            country和tag是通过自定义的sql语句查询的，特殊处理
            else if (column.equals(("country")) || column.equals(("tag"))) {
                List<ListEntity> dataList = null;

                if (column.equals(("country"))) {
                    dataList = listDao.searchByCountry(searchkeywords);
                }
                if (column.equals(("tag"))) {
                    dataList = listDao.searchByTag(searchkeywords);
                }

                int total = dataList.size(); // 总记录数
                // 在dataList的基础上执行分页查询，手动分页
                int fromIndex = (pagenum - 1) * pagesize;

                int toIndex = Math.min(pagenum * pagesize, total);

                List<ListEntity> pageDataList = dataList.subList(fromIndex, toIndex);
                // 创建一个 Map 对象
                Map<String, Object> resMap = new HashMap<>();

                // 将 Records 数组放入 res 对象中
                resMap.put("records", pageDataList);

                // 向 res 对象中添加 current 键值对
                resMap.put("current", pagenum);


                return R.ok().put("res", resMap).put("total", total);
            } else {
                Page<ListEntity> res = listService.searchBy(pagenum, pagesize, searchkeywords, column);
                return R.ok().put("res", res);
            }

        } else {
            Page<ListEntity> res = listService.listAllPapers(pagenum, pagesize);
            return R.ok().put("res", res);
        }

    }

    //    @Operation(summary = "通过作者名字搜索文献")
    @RequestMapping(value = "/searchByAuthor", method = RequestMethod.POST)
    public R searchByAuthor(@RequestBody Map<String, Object> searchInfo) {
//        System.out.println("searchInfo是" + searchInfo);
        Integer pagenum = (Integer) searchInfo.get("pagenum");
        Integer pagesize = (Integer) searchInfo.get("pagesize");
        String searchkeywords = (String) searchInfo.get("searchkeywords");
        String typeofPapers = (String) searchInfo.get("typerofPapers");
        Page<ListEntity> res = listService.searchByAuthor(pagenum, pagesize, searchkeywords, typeofPapers);
        return R.ok().put("res", res);
    }

    @RequestMapping(value = "/searchByInstitution", method = RequestMethod.POST)
    public R searchByInstitution(@RequestBody Map<String, Object> searchInfo) {
        String searchkeywords = (String) searchInfo.get("searchkeywords");
        Integer pagenum = (Integer) searchInfo.get("pagenum");
        Integer pagesize = (Integer) searchInfo.get("pagesize");
        String column = (String) searchInfo.get("column");

        Page<ListEntity> res = listService.searchByInstitutionPages(pagenum, pagesize, searchkeywords, column);
        return R.ok().put("res", res);


    }

    @RequestMapping(value = "/searchByCountry", method = RequestMethod.POST)
    public R searchByCountry(@RequestBody Map<String, Object> searchInfo) {
        String searchkeywords = (String) searchInfo.get("searchkeywords");
        Integer pagenum = (Integer) searchInfo.get("pagenum");
        Integer pagesize = (Integer) searchInfo.get("pagesize");


        List<ListEntity> dataList = listDao.searchByCountry(searchkeywords);
        int total = dataList.size(); // 总记录数
        // 在dataList的基础上执行分页查询
        int fromIndex = (pagenum - 1) * pagesize;
        int toIndex = Math.min(pagenum * pagesize, total);

        List<ListEntity> pageDataList = dataList.subList(fromIndex, toIndex);

        return R.ok().put("res", pageDataList).put("total", total);


    }

    @RequestMapping(value = "/searchByTag", method = RequestMethod.POST)
    public R searchByTag(@RequestBody Map<String, Object> searchInfo) {
        String tag = (String) searchInfo.get("searchkeywords");
        List<ListEntity> res = listDao.searchByTag(tag);
        return R.ok().put("res", res);
    }

    @RequestMapping(value = "/searchByVenue", method = RequestMethod.POST)
    public R searchByVenue(@RequestBody Map<String, Object> searchInfo) {
        String venue = (String) searchInfo.get("searchkeywords");
        QueryWrapper<ListEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("abbr", venue);
        List<ListEntity> res = listService.list(queryWrapper);
        return R.ok().put("res", res);
    }

    //    @Operation(summary = "通过Field搜索文献")
    @RequestMapping(value = "/searchByField", method = RequestMethod.POST)
    public R searchByField(@RequestBody Map<String, Object> searchInfo) {
//        System.out.println("searchInfo是" + searchInfo);
        Integer pagenum = (Integer) searchInfo.get("pagenum");
        Integer pagesize = (Integer) searchInfo.get("pagesize");
        String searchkeywords = (String) searchInfo.get("searchkeywords");
        Page<ListEntity> res = listService.searchByField(pagenum, pagesize, searchkeywords);
        return R.ok().put("res", res);
    }

    @RequestMapping(value = "/listRecentPapers", method = RequestMethod.POST)
    public R listRecentPapers(@RequestBody Map<String, Object> searchInfo) {
        QueryWrapper<ListEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("year");
        // 获取最新的5篇paper
        queryWrapper.last("LIMIT 5");
        return R.ok().put("res", listService.list(queryWrapper));
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

    //    @Operation(summary = "通过Bibtex文件上传文献")
    @RequestMapping(value = "/uploadByBib", method = RequestMethod.POST)
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

    //    @Operation(summary = "通过Form表单上传单篇文献")
    @RequestMapping(value = "/uploadOnePaper", method = RequestMethod.POST)
    public R uploadOnePaper(@RequestBody ListEntity listEntity) {
        if (listService.save(listEntity)) {
            return R.ok().put("SaveResult", "success!");

        } else return R.ok().put("UploadResult", "Failed!");
    }

    //    @Operation(summary = "通过Form表单修改单篇已有文献")
    @RequestMapping(value = "/updatePaper", method = RequestMethod.POST)
    public R updatePaper(@RequestBody ListEntity listEntity) {
//        System.out.println(listEntity.toString());
        Boolean updateFlag = listService.updateById(listEntity);
        if (updateFlag) return R.ok().put("UpdateStatus", "success!").put("UpdateResult", listEntity);
        else return R.ok().put("UpdateStatus", "failed!");

    }

    //    @Operation(summary = "删除单篇文献")
    @RequestMapping(value = "/deletePaper", method = RequestMethod.POST)
    public R deletePaper(@RequestBody ListEntity listEntity) {
        Boolean deleteFlag = listService.removeById(listEntity);
        if (deleteFlag) return R.ok().put("DeleteStatus", "success!");
        else return R.ok().put("DeleteStatus", "failed!");
    }





    //    @Operation(summary = "通过Abbr搜索文献")
    @RequestMapping(value = "/searchByAbbr", method = RequestMethod.POST)
    public R searchByAbbr(@RequestBody Map<String, Object> infos) {


        Integer pagenum = (Integer) infos.get("pagenum");
        Integer pagesize = (Integer) infos.get("pagesize");
        String searchkeywords = (String) infos.get("searchkeywords");
        String typeofPapers = (String) infos.get("typerofPapers");

        Page<ListEntity> res = listService.searchByAbbr(pagenum, pagesize, searchkeywords, typeofPapers);
        return R.ok().put("res", res);
    }


    //    @Operation(summary = "通过BookTitle搜索文献")
    @RequestMapping(value = "/searchByBooktitle", method = RequestMethod.POST)
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
//        queryWrapper.select("DISTINCT year").orderByAsc("year");
//        return R.ok().put("YearsCategory",listService.list(queryWrapper));
//    }

    //    @Operation(summary = "统计每年出版的文献数量")
    @RequestMapping(value = "/countEachYear", method = RequestMethod.POST)
    public R countEachYear(@RequestBody Map<String, Object> info) {
        String typeOfPaper = (String) info.get("info");
        QueryWrapper<ListEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("year", "COUNT(*) AS RecordCount").groupBy("year").orderByAsc("year");
        return R.ok().put("countEachYear", listService.listMaps(queryWrapper));
    }

    @RequestMapping(value = "/countEachYearScholars", method = RequestMethod.POST)
    public R countEachYearScholars(@RequestBody Map<String, Object> info) {
        String author = (String) info.get("author");
        QueryWrapper<ListEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("author", author);
        queryWrapper.select("year", "COUNT(*) AS RecordCount").groupBy("year").orderByAsc("year");
        return R.ok().put("res", listService.listMaps(queryWrapper));
    }

    //    通过关键词list 出全部符合要求的记录
    @RequestMapping(value = "/listBy", method = RequestMethod.POST)
    public R listBy(@RequestBody Map<String, Object> info) {
        System.out.println("info是" + info);

        String column = (String) info.get("column");
        String value = (String) info.get("searchkeywords");
        List<ListEntity> res = listService.listBy(column, value);
        return R.ok().put("res", res);
    }

    @RequestMapping(value = "/listPaperInfoByPaperID", method = RequestMethod.POST)
    public R listPaperInfoByPaperID(@RequestBody Map<String, Object> info) {
//        System.out.println("info是"+info);

        Object PaperID = info.get("PaperID");
        if (PaperID instanceof Integer) {
            QueryWrapper<ListEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id", PaperID);
            ListEntity res = listService.getOne(queryWrapper);
            return R.ok().put("res", res);
        } else if (PaperID instanceof String) {
            QueryWrapper<ListEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id", Integer.parseInt((String) PaperID));
            ListEntity res = listService.getOne(queryWrapper);
            return R.ok().put("res", res);
        }
        return R.ok().put("res", "No match");
    }


    //    @Operation(summary = "统计每个国家的文献数量")
    @RequestMapping(value = "/countEachCountry", method = RequestMethod.POST)
    public R countEachCountry(@RequestBody Map<String, Object> info) {
        String typeOfPaper = (String) info.get("info");
        QueryWrapper<ListEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("year", "COUNT(*) AS RecordCount").groupBy(
                "year").orderByAsc("year");
        return R.ok().put("countEachCountry", listService.listMaps(queryWrapper));
    }


    @RequestMapping(value = "/countField", method = RequestMethod.POST)
    public R countField(@RequestBody Map<String, Object> info) {
        System.out.println("info是" + info);
        String typeOfPaper = (String) info.get("info");
        QueryWrapper<ListEntity> queryWrapper = new QueryWrapper<>();
//        queryWrapper.select("field", "COUNT(field) AS RecordCount").groupBy("field").orderByDesc("year");
        queryWrapper.select("field", "COUNT(field) AS RecordCount", "MAX(year) AS maxYear").groupBy("field").orderByDesc("maxYear");

        return R.ok().put("countEachField", listService.listMaps(queryWrapper));
    }

    //    @Operation(summary = "统计每个Field每年的文献数量")
    @RequestMapping(value = "/countFieldEachYear", method = RequestMethod.POST)
    public R countFieldeachYear(@RequestBody Map<String, Object> info) {
        System.out.println("info是" + info);
        String typeOfPaper = (String) info.get("info");

        QueryWrapper<ListEntity> queryWrapper = new QueryWrapper<>();
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


    @RequestMapping(value = "/listCountFieldAnnual", method = RequestMethod.POST)
    public R listCountFieldAnnual() {
        QueryWrapper<count_field_annualEntity> queryWrapper = new QueryWrapper<>();
        List<count_field_annualEntity> result = countFieldAnnualDao.selectList(queryWrapper);

        return R.ok().put("result", result);

    }


}
