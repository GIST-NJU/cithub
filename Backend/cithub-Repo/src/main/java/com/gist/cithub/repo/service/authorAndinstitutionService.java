package com.gist.cithub.repo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gist.cithub.repo.entity.ListEntity;
import com.gist.cithub.repo.entity.authorAndscholarEntity;

import java.util.List;

public interface authorAndinstitutionService extends IService<authorAndscholarEntity> {
    List<ListEntity> listPaperByCountry(Integer pagenum,Integer pagesize,String typerofPapers, List<String> listOfAuthor);

}
