package com.tying.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tying.domain.ResponseResult;
import com.tying.domain.entity.InformationThumb;
import com.tying.service.IInformationThumbService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Tying
 * @version 1.0
 */
@RestController
@RequestMapping("/app/information/thumb")
public class InformationThumbController {

    @Resource
    private IInformationThumbService informationThumbService;

    @GetMapping
    public ResponseResult getInformationThumb(Long infoId) {

        return informationThumbService.getInformationThumb(infoId);
    }
}
