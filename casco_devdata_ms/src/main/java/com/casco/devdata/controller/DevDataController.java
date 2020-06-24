package com.casco.devdata.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.casco.devdata.aspect.BaseController;
import com.casco.devdata.common.dto.R;
import com.casco.devdata.controller.vo.CollectionVo;
import com.casco.devdata.controller.vo.SysDevMainTypeDefVo;
import com.casco.devdata.controller.vo.SysDevTypeDefVo;
import com.casco.devdata.entity.*;
import com.casco.devdata.mapper.*;
import com.casco.devdata.redis.AnalogRedisUtils;
import com.casco.devdata.redis.DigitalRedisUtils;
import com.casco.devdata.redis.EnumRedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/*-
 * 后续需要改成统一SQL
 */
@RestController
@Slf4j
public class DevDataController extends BaseController{

    @Autowired
    private SysDevMainTypeDefMapper sysDevMainTypeDefMapper;
    @Autowired
    private SysDevTypeDefMapper sysDevTypeDefMapper;
    @Autowired
    private SysDevListMapper sysDevListMapper;
    @Autowired
    private SysRelateCollectionDefMapper sysRelateCollectionDefMapper;


    @Autowired
    private SysDigitTypeDefMapper sysDigitTypeDefMapper;
    @Autowired
    private SysAnalogTypeDefMapper sysAnalogTypeDefMapper;
    @Autowired
    private SysEnumTypeDefMapper sysEnumTypeDefMapper;
    @Autowired
    private SysKeyVisibleMapper sysKeyVisibleMapper;
    @Autowired
    private SysInterfaceTypeDefMapper sysInterfaceTypeDefMapper;

    @Autowired
    private DigitalRedisUtils digitalRedisUtils;
    @Autowired
    private EnumRedisUtils enumRedisUtils;
    @Autowired
    private AnalogRedisUtils analogRedisUtils;

    @RequestMapping(value = "/devdata/listdevtype", method = RequestMethod.POST)
    @ResponseBody
    public R listDevType() {

        R<List<SysDevMainTypeDefVo>> res = new R<>();

        List<SysDevMainTypeDefVo> devTypeVoList = new ArrayList<>();

        QueryWrapper<SysDevMainTypeDef> mquery = new QueryWrapper<>();
        List<SysDevMainTypeDef> mList = sysDevMainTypeDefMapper.selectList(mquery);

        for(SysDevMainTypeDef item: mList){
            SysDevMainTypeDefVo devTypeVo = new SysDevMainTypeDefVo();
            BeanUtils.copyProperties(item, devTypeVo);
            QueryWrapper<SysDevTypeDef> query = new QueryWrapper<>();
            query.lambda().eq(SysDevTypeDef::getDevMainTypeId, item.getDevMainTypeId());
            List<SysDevTypeDef> typeList = sysDevTypeDefMapper.selectList(query);

            for(SysDevTypeDef type : typeList){

                SysDevTypeDefVo sysDevTypeDefVo = new SysDevTypeDefVo();
                BeanUtils.copyProperties(type, sysDevTypeDefVo);
                devTypeVo.getSysDevTypeDefVoList().add(sysDevTypeDefVo);
            }

            devTypeVoList.add(devTypeVo);

        }

        res.setCode(R.SUCCESS);
        res.setData(devTypeVoList);
        return res;
    }

    @RequestMapping(value = "/devdata/listdev", method = RequestMethod.POST)
    @ResponseBody
    public R listDev(String typeid) {

        //http://localhost:7002/devdata/listdev?typeid=401

        R<List<SysDevList>> res = new R<>();

        //1. 查找所有的设备
        QueryWrapper<SysDevList> devquery = new QueryWrapper<>();
        devquery.lambda().eq(SysDevList::getDevTypeId, typeid);

        List<SysDevList> list = sysDevListMapper.selectList(devquery);

        res.setCode(R.SUCCESS);
        res.setData(list);
        return res;
    }

    @RequestMapping(value = "/devdata/getdevattr", method = RequestMethod.POST)
    @ResponseBody
    public R getDevAttr(String major, String devid, Integer start, Integer limit) {

        /*-
         * 1. 根据DEVID（设备ID）查询SYS_RELATE_COLLECTION_DEF（采集关联表）
         * 2. 根据 SYS_RELATE_COLLECTION_DEF 中的 COLLECT_TYPE_ID 获取详细采集项类型信息
         * 主要是 SYS_DIGIT_TYPE_DEF/SYS_ENUM_TYPE_DEF/SYS_ANALOG_TYPE_DEF 三张
         * 这里通过 DATA_TYPE 区分，即数字 枚举以及模拟
         * 3. 根据 SYS_RELATE_COLLECTION_DEF 中的 INTERFACE_TYPE_ID 去 SYS_INTERFACE_TYPE_DEF
         * 查 INTERFACE_SOURCE_NAME 作为来源
         */

        R<List<CollectionVo>> res = new R<>();

        List<CollectionVo> result = new ArrayList<>();

        //1. 查找设备采集项目
        List<SysRelateCollectionDef> list = null;

        if(major.equals("VEHICLE")){

            //车辆的话需要分页
            QueryWrapper<SysRelateCollectionDef> collectQuery = new QueryWrapper<>();
            collectQuery.lambda().
                    eq(SysRelateCollectionDef::getDevId, devid);

            Page<SysRelateCollectionDef> page = new Page<>(start,limit);

            list = sysRelateCollectionDefMapper.selectPage(page, collectQuery).getRecords();

        }else {

            QueryWrapper<SysRelateCollectionDef> collectQuery = new QueryWrapper<>();
            collectQuery.lambda().eq(SysRelateCollectionDef::getDevId, devid);

            list = sysRelateCollectionDefMapper.selectList(collectQuery);
        }


        for(SysRelateCollectionDef item: list){

            CollectionVo collectionVo = new CollectionVo();
            collectionVo.setDataType(item.getDataType());

            if(item.getDataType() == 0){ //1. 数字

                //1.1 获取类型名称
                QueryWrapper<SysDigitTypeDef> dtquery
                        = new QueryWrapper<SysDigitTypeDef>();
                dtquery.lambda().eq(SysDigitTypeDef::getTypeId, item.getCollectTypeId());

                SysDigitTypeDef def = sysDigitTypeDefMapper.selectOne(dtquery);

                collectionVo.setCollectionName(def.getTypeName());

                //1.2 获取类型来源和key
                QueryWrapper<SysInterfaceTypeDef> iquery
                        = new QueryWrapper<SysInterfaceTypeDef>();
                iquery.lambda().eq(SysInterfaceTypeDef::getInterfaceTypeId, item.getInterfaceTypeId());
                SysInterfaceTypeDef idef = sysInterfaceTypeDefMapper.selectOne(iquery);

                collectionVo.setSrc(idef.getInterfaceSourceName());

                result.add(collectionVo);

            }else if(item.getDataType() == 1){ //enum

                //1.1 获取类型名称
                QueryWrapper<SysEnumTypeDef> etquery
                        = new QueryWrapper<SysEnumTypeDef>();
                etquery.lambda().eq(SysEnumTypeDef::getTypeId, item.getCollectTypeId());

                SysEnumTypeDef def = sysEnumTypeDefMapper.selectOne(etquery);

                collectionVo.setCollectionName(def.getTypeName());

                //1.2 获取类型来源和key
                QueryWrapper<SysInterfaceTypeDef> iquery
                        = new QueryWrapper<SysInterfaceTypeDef>();
                iquery.lambda().eq(SysInterfaceTypeDef::getInterfaceTypeId, item.getInterfaceTypeId());
                SysInterfaceTypeDef idef = sysInterfaceTypeDefMapper.selectOne(iquery);

                collectionVo.setSrc(idef.getInterfaceSourceName());

                result.add(collectionVo);

            }else if(item.getDataType() == 2){ //analog

                //1.1 获取类型名称
                QueryWrapper<SysAnalogTypeDef> atquery
                        = new QueryWrapper<SysAnalogTypeDef>();
                atquery.lambda().eq(SysAnalogTypeDef::getTypeId, item.getCollectTypeId());

                SysAnalogTypeDef def = sysAnalogTypeDefMapper.selectOne(atquery);

                collectionVo.setCollectionName(def.getTypeName());

                //1.2 获取类型来源和key
                QueryWrapper<SysInterfaceTypeDef> iquery
                        = new QueryWrapper<SysInterfaceTypeDef>();
                iquery.lambda().eq(SysInterfaceTypeDef::getInterfaceTypeId, item.getInterfaceTypeId());
                SysInterfaceTypeDef idef = sysInterfaceTypeDefMapper.selectOne(iquery);

                collectionVo.setSrc(idef.getInterfaceSourceName());

                result.add(collectionVo);

            }
        }

        res.setCode(R.SUCCESS);
        res.setData(result);
        return res;
    }


    @RequestMapping(value = "/devdata/getvisible", method = RequestMethod.POST)
    @ResponseBody
    public R getVisible(String typeid){

        QueryWrapper<SysKeyVisible> query = new QueryWrapper<>();
        query.lambda().eq(SysKeyVisible::getTypeId, typeid);

        List<SysKeyVisible> list = sysKeyVisibleMapper.selectList(query);

        R<List<SysKeyVisible>> res = new R<>();

        res.setCode(R.SUCCESS);
        res.setData(list);
        return res;
    }

    @RequestMapping(value = "/devdata/updatevisible", method = RequestMethod.POST)
    @ResponseBody
    public R updateVisible(List<SysKeyVisible> list){

        for(SysKeyVisible item : list){
            sysKeyVisibleMapper.updateById(item);
        }

        R<String> res = new R<>();
        res.setCode(R.SUCCESS);
        return res;
    }


    @RequestMapping(value = "/devdata/listcollections", method = RequestMethod.POST)
    @ResponseBody
    public R listCollections(List<CollectionVo> collectionVoList) {

        List<String> dKeys = new ArrayList<>();
        List<String> eKeys = new ArrayList<>();
        List<String> aKeys = new ArrayList<>();

        List<String> dres = new ArrayList<>();
        List<String> eres = new ArrayList<>();
        List<String> ares = new ArrayList<>();

        for(CollectionVo vo : collectionVoList){
            if(vo.getDataType() == 0){
                dKeys.add(vo.getKeyid());
            }else if(vo.getDataType() == 1){
                eKeys.add(vo.getKeyid());
            }else if(vo.getDataType() == 2){
                aKeys.add(vo.getKeyid());
            }
        }

        dres = digitalRedisUtils.gets(dKeys);
        eres = enumRedisUtils.gets(eKeys);
        ares = analogRedisUtils.gets(aKeys);


        List<Map<String, String>> result = new ArrayList<>();

        for(int i = 0; i < dKeys.size(); i++){
            Map<String, String> map = new HashMap<>();
            map.put(dKeys.get(i), dres.get(i));
            result.add(map);
        }

        for(int i = 0; i < eKeys.size(); i++){
            Map<String, String> map = new HashMap<>();
            map.put(eKeys.get(i), eres.get(i));
            result.add(map);
        }

        for(int i = 0; i < aKeys.size(); i++){
            Map<String, String> map = new HashMap<>();
            map.put(aKeys.get(i), ares.get(i));
            result.add(map);
        }

        R<Object> res = new R<>();
        res.setCode(R.SUCCESS);
        res.setData(result);
        return res;
    }
}