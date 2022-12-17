package com.tying.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tying.domain.ResponseResult;
import com.tying.domain.entity.Information;
import com.tying.domain.entity.Video;
import com.tying.domain.vo.InformationVo;
import com.tying.domain.vo.VideoVo;
import com.tying.mapper.IInformationMapper;
import com.tying.service.IInformationService;
import com.tying.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Information)表服务实现类
 *
 * @author makejava
 * @since 2022-12-12 14:37:20
 */
@Service("informationService")
public class InformationServiceImpl extends ServiceImpl<IInformationMapper, Information> implements IInformationService {

    @Resource
    private IInformationMapper informationMapper;

    @Override
    public ResponseResult getVideoList(Integer pageNum, Integer limit) {

        IPage<Information> page = new Page<>();
        // 设置每页条数
        page.setSize(limit);
        // 设置查询第几页
        page.setCurrent(pageNum);
        IPage<Information> informationIPage = informationMapper.selectPage(page, null);
        List<Information> informationList = informationIPage.getRecords();
        List<InformationVo> informationVoList = BeanCopyUtils.copyBeanList(informationList, InformationVo.class);

        return ResponseResult.okResult(informationVoList);
    }
}

