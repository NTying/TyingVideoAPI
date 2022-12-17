package com.tying.controller;

import com.tying.domain.ResponseResult;
import com.tying.service.IInformationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Tying
 * @version 1.0
 */
@RestController
@RequestMapping("/app/information")
public class InformationController {

    @Resource
    private IInformationService informationService;

    @GetMapping("/list")
    public ResponseResult getVideoList(Integer pageNum, Integer limit) {

        return informationService.getVideoList(pageNum, limit);
    }
}
