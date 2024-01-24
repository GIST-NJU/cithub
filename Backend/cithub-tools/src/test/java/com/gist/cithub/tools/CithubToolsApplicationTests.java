package com.gist.cithub.tools;

import com.gist.cithub.tools.entity.CoveringarraysEntity;
import com.gist.cithub.tools.service.CoveringarraysService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CithubToolsApplicationTests {

    @Autowired
    CoveringarraysService coveringarraysService;

    @Test
    void contextLoads() {
        for(int i=0;i<10;i++)
        {
            CoveringarraysEntity coveringarraysEntity= new CoveringarraysEntity();
            coveringarraysEntity.setCoveringarrayname(i+"test");
            coveringarraysEntity.setCoveringarraydescriptions(i+"test");
            coveringarraysEntity.setModelid(i+123);
            coveringarraysEntity.setCoveringarrayname(i+"test");
            coveringarraysService.save(coveringarraysEntity);
        }

    }

}
