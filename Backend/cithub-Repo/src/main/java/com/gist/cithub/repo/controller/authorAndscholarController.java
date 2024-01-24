package com.gist.cithub.repo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gist.cithub.common.utils.R;
import com.gist.cithub.repo.entity.ListEntity;
import com.gist.cithub.repo.entity.authorAndscholarEntity;
import com.gist.cithub.repo.service.authorAndinstitutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("repo/author")
public class authorAndscholarController {

    @Autowired
    private authorAndinstitutionService authorAndinstitutionService;


    @RequestMapping("/testauthor")
    public R testAuthor() {
        System.out.println("testAuthortestAuthor");
        return R.ok();
    }

    @RequestMapping("/listallauthor")
    public R listAllauthor(@RequestBody Map<String, Object> map) {
        String project = map.get("obj").toString();
        QueryWrapper<authorAndscholarEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("DISTINCT authorname").eq("projects", project);
        return R.ok().put("authors", authorAndinstitutionService.list(queryWrapper));
    }

    @RequestMapping("/listAllInstitutions")
    public R listAllInstitutions(@RequestBody Map<String, Object> map) {
        String project = map.get("obj").toString();
        QueryWrapper<authorAndscholarEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("DISTINCT institution").eq("projects", project).isNotNull("institution");;
        return R.ok().put("Institutions", authorAndinstitutionService.list(queryWrapper));
    }

    @RequestMapping("/countEachCountry")
    public R countEachCountry(@RequestBody Map<String, Object> info) {
        String typeOfPaper = (String) info.get("info");
        QueryWrapper<authorAndscholarEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("Projects", typeOfPaper);
        queryWrapper.select("country", "COUNT(*) AS count")
                .isNotNull("country")
                .groupBy("country");
        System.out.println("authorService.listMaps(queryWrapper)" + authorAndinstitutionService.listMaps(queryWrapper));
        return R.ok().put("countEachCountry", authorAndinstitutionService.listMaps(queryWrapper));
    }

    @RequestMapping("/ListAuthorsByCountry")
    public R ListAuthorsByCountry(@RequestBody Map<String, Object> info) {
//        System.out.println("info"+info);
        String country = (String) info.get("country");
        String typeOfPaper = (String) info.get("typeofPapers");
        QueryWrapper<authorAndscholarEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("Projects", typeOfPaper);
        queryWrapper.eq("country", country);
        queryWrapper.select("authorname");
//        System.out.println("authorService.list(queryWrapper)"+ authorService.listMaps(queryWrapper));
        List<String> list = authorAndinstitutionService.listMaps(queryWrapper).stream().map(map -> map.get("authorname").toString()).collect(Collectors.toList());

        return R.ok().put("AuthorList",list);
    }

    @RequestMapping("/ListPapersByCountry")
    public R ListPapersByCountry(@RequestBody Map<String, Object> info) {
//        System.out.println("ListPapersByCountry info"+info);
        String country = (String) info.get("country");
        Map<String,Object> pageInfo= (Map<String, Object>) info.get("searchObj");
        Integer pagenum = (Integer) pageInfo.get("pagenum");
        Integer pagesize = (Integer) pageInfo.get("pagesize");
        String typerofPapers=(String) pageInfo.get("typerofPapers");
        QueryWrapper<authorAndscholarEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("Projects", typerofPapers);
        queryWrapper.eq("country", country);
        queryWrapper.select("authorname");
//        System.out.println("authorService.list(queryWrapper)"+ authorService.listMaps(queryWrapper));
        List<String> listOfAuthor = authorAndinstitutionService.listMaps(queryWrapper).stream().map(map -> map.get("authorname").toString()).collect(Collectors.toList());
        List<ListEntity> ListPapersByCountryResult = authorAndinstitutionService.listPaperByCountry(pagenum,pagesize,typerofPapers,listOfAuthor);
        return R.ok().put("ListPapersByCountryResult",ListPapersByCountryResult);
    }


}
