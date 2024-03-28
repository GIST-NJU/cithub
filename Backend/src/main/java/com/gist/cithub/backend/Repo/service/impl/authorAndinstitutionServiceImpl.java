package com.gist.cithub.backend.Repo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gist.cithub.backend.Repo.entity.ListEntity;
import com.gist.cithub.backend.Repo.entity.authorAndscholarEntity;
import com.gist.cithub.backend.Repo.service.authorAndinstitutionService;
import com.gist.cithub.backend.Repo.dao.ListDao;
import com.gist.cithub.backend.Repo.dao.authorAndscholarDao;
import com.github.yulichang.base.MPJBaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service("authorService")
//public class authorAndinstitutionServiceImpl extends ServiceImpl<authorAndscholarDao, authorAndscholarEntity> implements authorAndinstitutionService {
public class authorAndinstitutionServiceImpl extends MPJBaseServiceImpl<authorAndscholarDao, authorAndscholarEntity> implements authorAndinstitutionService {

    @Autowired
    private authorAndscholarDao authorAndscholarDao;
    @Autowired
    private ListDao listDao;

    @Override
    public List<ListEntity> listPaperByCountry(Integer pagenum, Integer pagesize, String typerofPapers, List<String> listOfAuthor) {
        List<ListEntity> result = new ArrayList<>();
        for (String authorname : listOfAuthor) {
            QueryWrapper<ListEntity> wrapper = new QueryWrapper<>();
            wrapper.like("author", authorname);
            wrapper.eq("Projects", typerofPapers);
            IPage<ListEntity> listEntityPage = listDao.selectPage(new Page<>(pagenum, pagesize), wrapper);
            result.addAll(listEntityPage.getRecords());
        }
        List<ListEntity> listWithoutDuplicates = result.stream().distinct().collect(Collectors.toList());
        List<ListEntity> listSortedByAge = listWithoutDuplicates.stream().sorted(Comparator.comparing(ListEntity::getYear).reversed()).collect(Collectors.toList());
        return listSortedByAge;
    }
}
