package com.gist.cithub.backend.Tools.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gist.cithub.backend.Tools.entity.ProjectsEntity;
import com.gist.cithub.backend.Tools.entity.TestSuitesEntity;
import com.gist.cithub.backend.Tools.service.TestSuitesService;
import com.gist.cithub.backend.common.utils.PageUtils;
import com.gist.cithub.backend.common.utils.R;
import com.gist.cithub.backend.Tools.entity.ModelsEntity;
import com.gist.cithub.backend.Tools.service.ModelsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("tools/")
public class ToolsController {

    @Autowired
    private ModelsService modelsService;

    @Autowired
    private TestSuitesService testSuitesService;

    @RequestMapping("/listByID")
    public R listBy(@RequestBody Map<String, Object> info) {
        Object id = info.get("searchkeywords");
        String column = (String) info.get("column");
        if (id instanceof Integer) {
            id = (Integer) id;
        } else if (id instanceof String) {
            // 如果是字符串，尝试将其转换为整数
            id = Integer.parseInt((String) id);
        } else {
            // 处理其他类型的情况，例如记录日志或抛出异常
            System.err.println("Unsupported type for strength: " + id.getClass());
            return R.ok().put("msg", "error");
        }
//        两种list

        switch (column) {
            //        一种是list By UserID 根据USERID获取对应用户所有的Model
            case "userid":
                QueryWrapper<ModelsEntity> queryWrapperModel = new QueryWrapper<>();
                queryWrapperModel.eq("userid", id);
                return R.ok().put("res", modelsService.list(queryWrapperModel));
            case "modelid":
                //        一种是list By ModelID 根据ModelID 获取对应测试模型的所有测试用例集
                QueryWrapper<TestSuitesEntity> queryWrapperTestSuite = new QueryWrapper<>();
                queryWrapperTestSuite.eq("modelid", id);
                return R.ok().put("res", testSuitesService.list(queryWrapperTestSuite));
        }

        return R.ok().put("res","listFail");

    }
}
