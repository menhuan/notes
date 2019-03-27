package com.infervision.controller;

import com.infervision.service.ProfessorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName fruiqi
 * @Description
 * @Author frq
 * @Date 2019/3/27 23:36
 * @Version 1.0
 */
@RestController
public class ProfessorControler {


    private static final Logger logger = LoggerFactory.getLogger(ProfessorControler.class);

    @Autowired
    ProfessorService professorService;

    @GetMapping("start")
    public void start() {
        logger.info("[INFO] read message");
        professorService.ProfessorService("https://api.zsxq.com/v1.10/groups/721455121/topics?scope=digests&count=20");
    }

}

