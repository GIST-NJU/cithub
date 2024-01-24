package com.gist.cithub.repo.service.impl;

import com.alibaba.nacos.shaded.com.google.gson.Gson;
import com.alibaba.nacos.shaded.com.google.gson.JsonArray;
import com.alibaba.nacos.shaded.com.google.gson.JsonElement;
import com.alibaba.nacos.shaded.com.google.gson.JsonParser;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gist.cithub.common.utils.R;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gist.cithub.common.utils.PageUtils;
import com.gist.cithub.common.utils.Query;

import com.gist.cithub.repo.dao.ListDao;
import com.gist.cithub.repo.entity.ListEntity;
import com.gist.cithub.repo.service.ListService;
import org.springframework.web.multipart.MultipartFile;


@Service("listService")
public class ListServiceImpl extends ServiceImpl<ListDao, ListEntity> implements ListService {
    @Autowired
    private ListDao listDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ListEntity> page = this.page(
                new Query<ListEntity>().getPage(params),
                new QueryWrapper<ListEntity>()
        );

        return new PageUtils(page);
    }

    @Override

    public Page<ListEntity> listAllPapers(Integer pagenum, Integer pagesize, String typerofPapers) {
        QueryWrapper<ListEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("Projects", typerofPapers).orderByDesc("year");
        Page<ListEntity> listEntityPage = listDao.selectPage(new Page<>(pagenum, pagesize), queryWrapper);

        return listEntityPage;
    }

    @Override
    public Page<ListEntity> listBy(Integer pagenum, Integer pagesize, String typerofPapers,String listByKey, String listByValue) {
        QueryWrapper<ListEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("Projects",typerofPapers);
        queryWrapper.eq(listByKey,listByValue);
        Page<ListEntity> listEntityPage = listDao.selectPage(new Page<>(pagenum, pagesize), queryWrapper);
        return listEntityPage;

    }

    @Override
    public List<Map<String, Object>> getAllTypeofPapers() {
        QueryWrapper<ListEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("DISTINCT Projects");
        List<Map<String, Object>> res = listDao.selectMaps(queryWrapper);
        return res;
    }

    @Override
    public Boolean writeToMysql(String filePath) {
        XSSFWorkbook workbook = null;
        try {
            FileInputStream stream = new FileInputStream(filePath);
            // 创建工作簿
            workbook = new XSSFWorkbook(stream);
            XSSFSheet sheet = workbook.getSheetAt(0);
            Integer maxRow = sheet.getLastRowNum();
            ListEntity[] listEntities = new ListEntity[maxRow];
            for (int i = 1; i <= maxRow; i++) {
                ListEntity tempListentity = new ListEntity();
                XSSFRow row = sheet.getRow(i);

                if (row.getCell(1) != null) {
                    // System.out.println("time_stamp:" + row.getCell(1).toString());
                    tempListentity.setTimeStamp(row.getCell(1).toString());
                } else {
                    tempListentity.setTimeStamp("");
                }

                if (row.getCell(2) != null) {
                    // System.out.println("year" + row.getCell(2).toString());
                    tempListentity.setYear(Integer.valueOf(row.getCell(2).getRawValue()));
                } else {
                    tempListentity.setYear(999);
                }

                if (row.getCell(3) != null) {
                    // System.out.println("Type" + row.getCell(3).toString());
                    tempListentity.setType(row.getCell(3).toString());
                } else {
                    tempListentity.setType("");
                }

                if (row.getCell(4) != null) {
                    // System.out.println("Author" + row.getCell(4).toString());
                    tempListentity.setAuthor(row.getCell(4).toString());
                } else {
                    tempListentity.setAuthor("");
                }


                if (row.getCell(5) != null) {
                    // System.out.println("title" + row.getCell(5).toString());
                    tempListentity.setTitle(row.getCell(5).toString());
                } else {
                    tempListentity.setTitle("");
                }

                if (row.getCell(6) != null) {
                    //  System.out.println("Field" + row.getCell(6).toString());
                    tempListentity.setField(row.getCell(6).toString());
                } else {
                    tempListentity.setField("");
                }

                if (row.getCell(7) != null) {
                    //  System.out.println("Tag" + row.getCell(7).toString());
                    tempListentity.setTag(row.getCell(7).toString());
                } else {
                    tempListentity.setTag("");
                }

                if (row.getCell(8) != null) {
                    // System.out.println("Booktitle" + row.getCell(8).toString());

                    tempListentity.setBooktitle(row.getCell(8).toString());
                } else {
                    tempListentity.setTag("");
                }

                if (row.getCell(9) != null) {
                    // System.out.println("abbr" + row.getCell(9).toString());

                    tempListentity.setAbbr(row.getCell(9).toString());
                } else {
                    tempListentity.setAbbr("");
                }

                if (row.getCell(10) != null) {
                    // System.out.println("vol" + row.getCell(10).toString());

                    tempListentity.setVol(row.getCell(10).toString());
                } else {
                    tempListentity.setVol("");
                }

                if (row.getCell(11) != null) {
                    // System.out.println("no" + row.getCell(11).toString());

                    tempListentity.setNo(row.getCell(11).toString());
                } else {
                    tempListentity.setNo("");
                }

                if (row.getCell(12) != null) {
                    // System.out.println("pages" + row.getCell(12).toString());

                    tempListentity.setPages(row.getCell(12).toString());
                } else {
                    tempListentity.setPages("");
                }

                if (row.getCell(13) != null) {
                    // System.out.println("publisher" + row.getCell(13).toString());

                    tempListentity.setPublisher(row.getCell(13).toString());
                } else {
                    tempListentity.setPublisher("");
                }

                if (row.getCell(14) != null) {
                    // System.out.println("doi" + row.getCell(14).toString());

                    tempListentity.setDoi(row.getCell(14).toString());
                } else {
                    tempListentity.setDoi("");
                }

                if (row.getCell(15) != null) {
//                        System.out.println("project" + row.getCell(15).toString());
                    tempListentity.setProjects(row.getCell(15).toString());
                } else {
                    tempListentity.setProjects("");
                }
                listEntities[i - 1] = tempListentity;
            }
//            for (ListEntity item : listEntities) {
//                System.out.println(item.toString());
//            }

//            插入新的文献
            for (int i = 0; i < listEntities.length; i++) {
                listDao.insert(listEntities[i]);
            }
            return true;

        } catch (IOException ex) {
            System.out.println("读取文件失败" + ex.toString());
            ex.printStackTrace();
            return false;
        }


    }

    @Override
    public ArrayList<ListEntity> saveUploadFileByBib(String JsonString) {
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        JsonArray jsonArray = parser.parse(JsonString).getAsJsonArray();
        ArrayList<ListEntity> listEntities = new ArrayList<>();
        //加强for循环遍历JsonArray
        for (JsonElement user : jsonArray) {
            //使用GSON，直接转成Bean对象
            ListEntity listEntity = gson.fromJson(user, ListEntity.class);
            listEntities.add(listEntity);
        }
//        for(ListEntity item : listEntities)
//        {
//            System.out.println("当前item：");
//            System.out.println(item);
//        }
        if(listEntities!=null) return listEntities;
        else return null;


    }

    @Override
    public String savaUploadFile(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String prefix = fileName.lastIndexOf(".") != -1 ? fileName.substring(fileName.lastIndexOf(".")) : ".jpg";
        String path = UUID.randomUUID().toString().replace("-", "") + prefix;
        String uploadFileDir = "/uploadFile";
        try {
            File filePath = new File(uploadFileDir, path);
            if (!filePath.getParentFile().exists()) {   // 如果存放路径的父目录不存在，就创建它。
                filePath.getParentFile().mkdirs();
            }
            file.transferTo(filePath.getAbsoluteFile());
            return filePath.getAbsolutePath();

        } catch (IOException e) {
            e.printStackTrace();
            return "保存失败";
        }
    }

    @Override
    public Page<ListEntity> searchByKeywords(Integer pagenum, Integer pagesize, String searchKeywords,String typeofPapers) {
        QueryWrapper<ListEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("author", searchKeywords).or().like("title", searchKeywords).or().like("booktitle", searchKeywords).or().like("tag", searchKeywords).or().like("abbr", searchKeywords).or().like("year", searchKeywords);
        queryWrapper.orderByDesc("year");
        queryWrapper.orderByAsc("author");
        queryWrapper.eq("Projects",typeofPapers);
        Page<ListEntity> selectPage = listDao.selectPage(new Page<>(pagenum, pagesize), queryWrapper);
        return selectPage;

    }

    @Override
    public Page<ListEntity> searchByAuthor(Integer pagenum, Integer pagesize, String searchKeywords, String typeofPapers) {
        QueryWrapper<ListEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("author",searchKeywords);
        queryWrapper.eq("Projects",typeofPapers);
        queryWrapper.orderByDesc("year");
        Page<ListEntity> selectPage = listDao.selectPage(new Page<>(pagenum, pagesize), queryWrapper);
        return selectPage;
    }

    @Override
    public Page<ListEntity> searchByField(Integer pagenum, Integer pagesize, String searchKeywords, String typeofPapers) {
        QueryWrapper<ListEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("field",searchKeywords);
        queryWrapper.eq("Projects",typeofPapers);
        queryWrapper.orderByDesc("year");
        Page<ListEntity> selectPage = listDao.selectPage(new Page<>(pagenum, pagesize), queryWrapper);
        return selectPage;
    }

    @Override
    public Page<ListEntity> searchByInstitutions(Integer pagenum, Integer pagesize, String searchKeywords, String typeofPapers) {
        QueryWrapper<ListEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("institution",searchKeywords);
        queryWrapper.eq("Projects",typeofPapers);
        queryWrapper.orderByDesc("year");
        Page<ListEntity> selectPage = listDao.selectPage(new Page<>(pagenum, pagesize), queryWrapper);
        return selectPage;
    }

    @Override
    public Page<ListEntity> searchByAbbr(Integer pagenum, Integer pagesize, String searchKeywords, String typeofPapers) {
        QueryWrapper<ListEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("abbr",searchKeywords);
        queryWrapper.eq("Projects",typeofPapers);
        queryWrapper.orderByDesc("year");
        Page<ListEntity> selectPage = listDao.selectPage(new Page<>(pagenum, pagesize), queryWrapper);
        return selectPage;
    }

    @Override
    public Page<ListEntity> searchByBooktitle(Integer pagenum, Integer pagesize, String searchKeywords, String typeofPapers) {
        QueryWrapper<ListEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("booktitle",searchKeywords);
        queryWrapper.eq("Projects",typeofPapers);
        queryWrapper.orderByDesc("year");
        Page<ListEntity> selectPage = listDao.selectPage(new Page<>(pagenum, pagesize), queryWrapper);
        return selectPage;
    }


}