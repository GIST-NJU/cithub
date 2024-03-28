package com.gist.cithub.backend.Repo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gist.cithub.backend.Repo.entity.ListEntity;
import com.gist.cithub.backend.Repo.entity.authorAndscholarEntity;
import com.github.yulichang.base.MPJBaseService;

import java.util.List;

//public interface authorAndinstitutionService extends IService<authorAndscholarEntity> {
public interface authorAndinstitutionService extends MPJBaseService<authorAndscholarEntity> {
    List<ListEntity> listPaperByCountry(Integer pagenum, Integer pagesize, String typerofPapers, List<String> listOfAuthor);

}
