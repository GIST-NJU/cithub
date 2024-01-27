package com.gist.cithub.backend.Repo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gist.cithub.backend.Repo.entity.ListEntity;
import com.gist.cithub.backend.Repo.entity.authorAndscholarEntity;

import java.util.List;

public interface authorAndinstitutionService extends IService<authorAndscholarEntity> {
    List<ListEntity> listPaperByCountry(Integer pagenum, Integer pagesize, String typerofPapers, List<String> listOfAuthor);

}
