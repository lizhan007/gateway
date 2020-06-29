package com.casco.operationportal.controller;

import com.casco.operationportal.common.controller.BaseController;
import com.casco.operationportal.common.dto.R;
import com.casco.operationportal.utils.NumberUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.*;

@RestController
@RequestMapping("/operationportal/SysFile")
@Slf4j
public class SysFileController extends BaseController {

    /**
     * 上传文件
     *
     * @param file
     * @param type 0：jar文件   1：线路专业配置文件   2：jar配置文件
     * @return
     */
    @RequestMapping("/uploadFile")
    public R<String> uploadFile(@NotNull @RequestParam("file") MultipartFile file,
                                @NotNull String type) throws IOException {

        log.info("************收到文件上传请求");

        if (file.isEmpty()) {
            log.info("文件为空");
        }

        String filePath = System.getProperty("user.dir") + "/operationPortalFiles";
        switch (type){
            case "0":
                filePath+="/jarFlies/";
                break;

            case "1":
                filePath+="/lineFlies/";
                break;

            case "2":
                filePath+="/confFlies/";
                break;
        }
        filePath = filePath + NumberUtil.getTimeStamp() + "/";

        String fileName = filePath + file.getOriginalFilename();

        File newFile = new File(fileName);
        if(!newFile.getParentFile().exists()){
            newFile.getParentFile().mkdirs();
        }

        OutputStream os = new FileOutputStream(newFile);
        InputStream in = file.getInputStream();
        IOUtils.copy(in, os);
        os.flush(); //关闭流
        in.close();
        os.close();

        log.info("************文件保存成功，文件名：" + fileName);

        R<String> r = new R<>();
        r.setCode(R.SUCCESS);
        r.setData(fileName);
        return r;
    }

    /**
     * 下载文件
     * @param fileName
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/downLoadFile/{fileName}")
    public ResponseEntity<byte[]> download(@PathVariable String fileName) throws IOException {
        File file = new File(fileName);
        byte[] body = null;
        InputStream is = new FileInputStream(file);
        body = new byte[is.available()];
        is.read(body);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attchement;filename=" + file.getName());
        HttpStatus statusCode = HttpStatus.OK;
        ResponseEntity<byte[]> entity = new ResponseEntity<>(body, headers, statusCode);
        return entity;
    }

    /**
     * 读取文件
     * @param fileName
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/readFile")
    public String readFile(@NotNull @RequestParam("fileName") String fileName) throws IOException {


        String context = "";
        BufferedReader br=null;
        try {
            br = new BufferedReader(new FileReader(fileName));
            String line = "";
            while ((line = br.readLine()) != null) {
                context += line + "\r\n";
            }
        }catch (Exception e) {
            log.error("文件读取异常" + e.toString());
            e.printStackTrace();
            throw e;
        }finally{
            if(br!=null){
                try {
                    br.close();
                } catch (IOException e) {
                    log.error(e.toString());
                    e.printStackTrace();
                    throw e;
                }
            }
        }

        return context;
    }
}