package com.casco.devdata.controller;


import com.casco.devdata.common.dto.R;
import com.casco.devdata.controller.vo.DetailVo;
import com.casco.devdata.controller.vo.DevVo;
import com.casco.devdata.entity.SysAnalogQuantityDef;
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
import java.util.stream.Collectors;

@RestController
@Slf4j
@CrossOrigin("*")
public class DetailDataController {

    @Autowired
    private SysRelateCollectionDefMapper sysRelateCollectionDefMapper;
    @Autowired
    private SysAnalogQuantityDefMapper sysAnalogQuantityDefMapper;
    @Autowired
    private DigitalRedisUtils digitalRedisUtils;
    @Autowired
    private EnumRedisUtils enumRedisUtils;
    @Autowired
    private AnalogRedisUtils analogRedisUtils;

    @RequestMapping(value = "/devdata/detail", method = RequestMethod.POST)
    @ResponseBody
    public R listDetails(@RequestBody DetailVo detailVo){

        Map<String, Object> result = new HashMap();

        List<Map> datalist = sysRelateCollectionDefMapper.listCollections(detailVo.getDevId(), detailVo.getDevName(), detailVo.getTypes(),
                detailVo.getStart(), detailVo.getLimit());

        int count = sysRelateCollectionDefMapper.countCollections(detailVo.getDevId(), detailVo.getDevName(), detailVo.getTypes(),
                detailVo.getStart(), detailVo.getLimit());


        List<String> params = new ArrayList<>();
        for(Map vo:datalist){
            params.add(vo.get("DEV_ID").toString());
        }

        params.stream().distinct().collect(Collectors.toList());
        List<Map> list = sysRelateCollectionDefMapper.listEnumAttr(params);
        List<SysAnalogQuantityDef> unitList = sysAnalogQuantityDefMapper.listAnalogUnit(params);

        for(int i = 0; i < datalist.size(); i++){

            String collectionId = datalist.get(i).get("COLLECT_TYPE_ID").toString();

            if((int)datalist.get(i).get("DATA_TYPE") == 0){
                datalist.get(i).put("value", digitalRedisUtils.get(datalist.get(i).get("KEY_ID").toString()));
            }else if((int)datalist.get(i).get("DATA_TYPE") == 4){
                String value = enumRedisUtils.get(datalist.get(i).get("KEY_ID").toString());
                datalist.get(i).put("value", changeEnumvalue(list, collectionId, value));
            }else if((int)datalist.get(i).get("DATA_TYPE") == 1){
                String value = analogRedisUtils.get(datalist.get(i).get("KEY_ID").toString());
                datalist.get(i).put("value", changeAnalogValue(unitList, collectionId, value));
            }
        }

        result.put("total", count);
        result.put("data", datalist);

        R<Object> res = new R<>();

        res.setCode(R.SUCCESS);
        res.setData(result);
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
