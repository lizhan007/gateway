package com.casco.devdata.controller;


import com.casco.devdata.common.dto.R;
import com.casco.devdata.controller.vo.CollectionVo;
import com.casco.devdata.controller.vo.DevVo;
import com.casco.devdata.entity.SysAnalogQuantityDef;
import com.casco.devdata.entity.SysRelateCollectionDef;
import com.casco.devdata.mapper.SysAnalogQuantityDefMapper;
import com.casco.devdata.mapper.SysRelateCollectionDefMapper;
import com.casco.devdata.redis.AnalogRedisUtils;
import com.casco.devdata.redis.DigitalRedisUtils;
import com.casco.devdata.redis.EnumRedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@CrossOrigin("*")
public class GroupDataController {

    @Autowired
    private SysRelateCollectionDefMapper sysRelateCollectionDefMapper;
    @Autowired
    private DigitalRedisUtils digitalRedisUtils;
    @Autowired
    private EnumRedisUtils enumRedisUtils;
    @Autowired
    private AnalogRedisUtils analogRedisUtils;
    @Autowired
    private SysAnalogQuantityDefMapper sysAnalogQuantityDefMapper;

    @RequestMapping(value = "/devdata/listgroupdevattr", method = RequestMethod.POST)
    @ResponseBody
    public R listGroupDevAttr(String devid){
        List list = sysRelateCollectionDefMapper.listGroupDevAttr(devid);

        R<List<Map>> res = new R<>();

        res.setCode(R.SUCCESS);
        res.setData(list);
        return res;
    }

    @RequestMapping(value = "/devdata/listgroupdevscollects", method = RequestMethod.POST)
    @ResponseBody
    public R listGroupDevsCollects(@RequestBody  List<DevVo> devIdList){

        List<String> params = new ArrayList<>();
        for(DevVo vo:devIdList){
            params.add(vo.getDevId());
        }

        List<Map> sysRelateCollectionDefList = sysRelateCollectionDefMapper.listGroupCollections(params);
        List<Map> list = sysRelateCollectionDefMapper.listEnumAttr(params);
        List<SysAnalogQuantityDef> unitList = sysAnalogQuantityDefMapper.listAnalogUnit(params);

        List<String> dKeys = new ArrayList<>();
        List<String> eKeys = new ArrayList<>();
        List<String> aKeys = new ArrayList<>();

        List<String> dres = new ArrayList<>();
        List<String> eres = new ArrayList<>();
        List<String> ares = new ArrayList<>();

        for(Map vo : sysRelateCollectionDefList){
            if((int)vo.get("DATA_TYPE") == 0){
                dKeys.add(vo.get("KEY_ID").toString());
            }else if((int)vo.get("DATA_TYPE") == 4){
                eKeys.add(vo.get("KEY_ID").toString());
            }else if((int)vo.get("DATA_TYPE") == 1){
                aKeys.add(vo.get("KEY_ID").toString());
            }
        }

        dres = digitalRedisUtils.gets(dKeys);
        eres = enumRedisUtils.gets(eKeys);
        ares = analogRedisUtils.gets(aKeys);

        List<Map<String, String>> result = new ArrayList<>();

        if(dres == null || dres.size() == 0){
            for(int i=0;i<dKeys.size();i++){
                dres.add("");
            }
        }

        if(eres == null || eres.size() == 0){
            for(int i=0;i<eKeys.size();i++){
                eres.add("");
            }
        }

        if(ares == null || ares.size() == 0){
            for(int i=0;i<aKeys.size();i++){
                ares.add("");
            }
        }

        Map<String, List> map = new HashMap<>();

        for(Map vo : sysRelateCollectionDefList){

            String DevId    = vo.get("DEV_ID").toString();
            String KeyId    = vo.get("KEY_ID").toString();
            String CollectionId = vo.get("COLLECT_TYPE_ID").toString();
            int DataType    = (int)vo.get("DATA_TYPE");

            if(!map.containsKey(DevId)){
                map.put(DevId, new ArrayList());
            }

            if(DataType == 0){
                for(int i = 0; i < dKeys.size(); i++){
                    if(KeyId.equals(dKeys.get(i))){
                        map.get(DevId).add(dres.get(i));
                        //map.get(DevId).add(KeyId);
                        break;
                    }
                }
            }else if(DataType == 4){
                for(int i = 0; i < eKeys.size(); i++){
                    if(KeyId.equals(eKeys.get(i))){
                        //map.get(DevId).add(KeyId);
                        map.get(DevId).add(changeEnumvalue(list, String.valueOf(CollectionId), eres.get(i)));
                        break;
                    }
                }
            }else if(DataType == 1){
                for(int i = 0; i < aKeys.size(); i++){
                    if(KeyId.equals(aKeys.get(i))){
                        //map.get(DevId).add(KeyId);
                        //map.get(vo.getDevId()).add(ares.get(i));
                        map.get(DevId).add(changeAnalogValue(unitList, aKeys.get(i), ares.get(i)));
                        break;
                    }
                }
            }
        }//for(CollectionVo vo : collectionVoList){


        R<Object> res = new R<>();
        res.setCode(R.SUCCESS);
        res.setData(map);
        return res;
    }

    private String changeEnumvalue(final List<Map> enumMapList, String CollectId, String value){

        if(null == value){
            return null;
        }

        for(int i = 0; i < enumMapList.size(); i++){

            if(CollectId.equals(enumMapList.get(i).get("COLLECT_TYPE_ID").toString())
                    && value.equals(enumMapList.get(i).get("ENUM_VALUE").toString())){

                return (String) enumMapList.get(i).get("ENUM_MEAN");
            }
        }

        return null;
    }

    private String changeAnalogValue(final  List<SysAnalogQuantityDef> list, String keyid, String value){
        for(SysAnalogQuantityDef item: list){
            if(item.getKeyId().equals(keyid) && item.getUnit() != null && item.getUnit().length() > 0){
                if(value != null && value.length() > 0){
                    return value + " " + item.getUnit();
                }
            }
        }

        return value;
    }

}
