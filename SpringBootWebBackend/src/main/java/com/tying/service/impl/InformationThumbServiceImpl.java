package com.tying.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tying.domain.ResponseResult;
import com.tying.domain.entity.InformationThumb;
import com.tying.mapper.IInformationMapper;
import com.tying.mapper.IInformationThumbMapper;
import com.tying.service.IInformationThumbService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (InformationThumb)表服务实现类
 *
 * @author makejava
 * @since 2022-12-12 15:39:42
 */
@Service("informationThumbService")
public class InformationThumbServiceImpl extends ServiceImpl<IInformationThumbMapper, InformationThumb> implements IInformationThumbService {

    @Resource
    private IInformationThumbMapper informationThumbMapper;
    @Override
    public ResponseResult getInformationThumb(Long infoId) {

        List<InformationThumb> informationThumbList = informationThumbMapper.selectList(
                new QueryWrapper<InformationThumb>().eq("information_id", infoId)
        );
        return ResponseResult.okResult(informationThumbList);
    }
}

