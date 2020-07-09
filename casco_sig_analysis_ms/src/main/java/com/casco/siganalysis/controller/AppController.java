package com.casco.siganalysis.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.casco.siganalysis.common.dto.R;
import com.casco.siganalysis.controller.vo.ListAlarmVo;
import com.casco.siganalysis.entity.SysAlarmTable;
import com.casco.siganalysis.entity.SysCombineRelated;
import com.casco.siganalysis.entity.SysCombineScene;
import com.casco.siganalysis.mapper.SysAlarmTableMapper;
import com.casco.siganalysis.mapper.SysCombineRelatedMapper;
import com.casco.siganalysis.mapper.SysCombineSceneMapper;
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
public class AppController {

    @Autowired
    private SysCombineSceneMapper sysCombineSceneMapper;

    @Autowired
    private SysAlarmTableMapper sysAlarmTableMapper;

    @Autowired
    private SysCombineRelatedMapper sysCombineRelatedMapper;

    @RequestMapping(value = "/siganalysis/listscene", method = RequestMethod.POST)
    @ResponseBody
    public R listScene() {

        List<Map> list = new ArrayList<>();

        //获取场景列表
        QueryWrapper<SysCombineScene> query = new QueryWrapper<>();
        List<SysCombineScene> mList = sysCombineSceneMapper.selectList(query);

        //获取场景列表对应的KEY中文
        List<Map> sumList = sysCombineSceneMapper.listSceneSummurySet();

        for(SysCombineScene scene:mList){

            Map<String, Object> data = new HashMap();
            data.put("scene_name", scene.getArmSceneName());
            data.put("line_name", scene.getLineName());
            data.put("arm_code_set", scene.getArmCodeSet());
            data.put("train_name", scene.getTrainName());
            data.put("scene_id", scene.getArmSceneId());

            List<String> tmp = new ArrayList<>();

            for(Map map: sumList){
                if(scene.getArmSummarySet().contains(map.get("KEY_ID").toString())){
                    tmp.add(map.get("KEY_ID") +"#" + map.get("RES_TYPE_NAME"));
                }
            }
            data.put("summary_set", tmp);
            list.add(data);
        }

        R<Object> result = new R<>();
        result.setCode(R.SUCCESS);
        result.setData(list);

        return result;
    }

    @RequestMapping(value = "/siganalysis/listalarm", method = RequestMethod.POST)
    @ResponseBody
    public R getAlarm(@RequestBody ListAlarmVo listAlarmVo){

        QueryWrapper<SysAlarmTable> query = new QueryWrapper<>();
        query.lambda().in(SysAlarmTable::getArmCode, listAlarmVo.getAlarmCode())
            .ge(SysAlarmTable::getArmHappenTime, listAlarmVo.getStart()).lt(SysAlarmTable::getArmHappenTime, listAlarmVo.getEnd());

        List<SysAlarmTable> mList = sysAlarmTableMapper.selectList(query);

        R<Object> result = new R<>();
        result.setCode(R.SUCCESS);
        result.setData(mList);

        return result;
    }

    @RequestMapping(value = "/siganalysis/listimages", method = RequestMethod.POST)
    @ResponseBody
    public R getSceneImages(@RequestParam("sceneId") String sceneId){

        QueryWrapper<SysCombineRelated> query = new QueryWrapper<>();
        query.lambda().eq(SysCombineRelated::getArmSceneId, sceneId);

        List<SysCombineRelated> list  = sysCombineRelatedMapper.selectList(query);

        R<Object> result = new R<>();
        result.setCode(R.SUCCESS);
        result.setData(list);
        return result;
    }
}
