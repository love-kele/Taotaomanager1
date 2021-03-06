package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EUIDataGridResult;
import com.taotao.common.pojo.TbItemParam_fix;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.mapper.TbItemCatMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamMapper;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemParam;
import com.taotao.pojo.TbItemParamExample;
import com.taotao.service.ItemParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * ItemParamService包含对商品规格模板的管理
 */
@Service
public class ItemParamServiceImpl implements ItemParamService {
    @Autowired
    private TbItemParamMapper itemParamMapper;

    @Autowired
    private TbItemCatMapper itemCatMapper;

    //通过商品分类id获取一个商品规格模板
    @Override
    public TaotaoResult getItemParam(long itemcatid) {

        TbItemParamExample example = new TbItemParamExample();
        TbItemParamExample.Criteria criteria = example.createCriteria();
        criteria.andItemCatIdEqualTo(itemcatid);
          //s使用selectByExampleWithBLOBs()才可以从数据库中取出长文本数据
        List<TbItemParam> tbItemParams = itemParamMapper.selectByExampleWithBLOBs(example);

        if(tbItemParams!=null&&tbItemParams.size()>0){
            return TaotaoResult.ok(tbItemParams.get(0));
        }

        return TaotaoResult.ok();
    }


    //为商品添加规格模板
    @Override
    public TaotaoResult saveItemParam(long id,String itemcatname,String param) {

        TbItemParam itemParam = new TbItemParam();

        itemParam.setItemCatId(id);
        itemParam.setItemCatName(itemcatname);
        itemParam.setParamData(param);
        itemParam.setCreated(new Date());
        itemParam.setUpdated(new Date());
        itemParamMapper.insert(itemParam);

        return TaotaoResult.ok();
    }


    @Override
    public EUIDataGridResult selectItemParam(int page, int rows) {
        TbItemParamExample example = new TbItemParamExample();

        PageHelper.startPage(page, rows);

        List<TbItemParam> itemParamList = itemParamMapper.selectByExampleWithBLOBs(example);


        EUIDataGridResult result = new EUIDataGridResult();

        result.setRows(itemParamList);

        PageInfo<TbItemParam> info = new PageInfo<TbItemParam>(itemParamList);

        result.setTotal(info.getTotal());

        return result;
    }

    public void setItemParamMapper(TbItemParamMapper itemParamMapper) {
        this.itemParamMapper = itemParamMapper;
    }
}
