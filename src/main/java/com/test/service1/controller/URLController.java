package com.test.service1.controller;

import com.alibaba.fastjson.JSON;
import com.test.service1.info.ConvertURLInfo;
import com.test.service1.service.URLServ;
import com.test.service1.util.MD5ConvertUtil;
import com.test.service1.util.UUIDGenerator;
import com.test.service1.vo.ConvertURLVO;
import com.test.service1.service.URLServ;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
@Api(value = "/", description = "url service ")
@RestController
public class URLController {

    @Autowired
    private URLServ urlServ;

    @ApiOperation(value = "based on param's url created shorten url", notes = "", response = String.class, tags={ "convertURL", })

    //这里是显示你可能返回的http状态，以及原因。比如404 not found, 303 see other
    @ApiResponses(value = {
            @ApiResponse(code = 405, message = "Invalid input", response = String.class) })

    /**
     * jsonStr 包括 id,urltr,comment,
     */
    @RequestMapping(value="/getShortenURL",method= RequestMethod.POST)
    public String convertURL(@RequestBody @ApiParam(value = "json paramter inlcude id ,urlStr , comment ,shortenURL", required = true)   String jsonStr) {

        ConvertURLVO vo = JSON.parseObject(jsonStr, ConvertURLVO.class);

        String key = "GM";                 //自定义生成MD5加密字符串前的混合KEY
        String[] chars = new String[]{          //要使用生成URL的字符
                "a","b","c","d","e","f","g","h",
                "i","j","k","l","m","n","o","p",
                "q","r","s","t","u","v","w","x",
                "y","z","0","1","2","3","4","5",
                "6","7","8","9","A","B","C","D",
                "E","F","G","H","I","J","K","L",
                "M","N","O","P","Q","R","S","T",
                "U","V","W","X","Y","Z"
        };

        String hex = MD5ConvertUtil.MD5Encode(key + vo.getUrlStr());
        int hexLen = hex.length();
        int subHexLen = hexLen / 8;
        String[] ShortStr = new String[4];

        for (int i = 0; i < subHexLen; i++) {
            String outChars = "";
            int j = i + 1;
            String subHex = hex.substring(i * 8, j * 8);
            long idx = Long.valueOf("3FFFFFFF", 16) & Long.valueOf(subHex, 16);

            for (int k = 0; k < 6; k++) {
                int index = (int) (Long.valueOf("0000003D", 16) & idx);
                outChars += chars[index];
                idx = idx >> 5;
            }
            ShortStr[i] = outChars;
        }
        String voId = null;
        String shortUrl = "http://tiny.amazon.com/"+ShortStr[0].toString();
        ConvertURLInfo info = new ConvertURLInfo();
        voId = vo.getId();
        info.setId(voId);
        info.setComment(vo.getComment());
        info.setUrlStr(vo.getUrlStr());
        info.setShrotenURL(shortUrl);
        int index=-1;
        if(vo.getId()!=null&&!"".equals(vo.getId())){
            index = urlServ.update(info);
        }else{
            voId = UUIDGenerator.getUUID();
            info.setId(voId);
            vo.setId(voId);
            index = urlServ.insert(info);
        }
//        index = 1;
        if(index>0){
            System.out.println(" Opreating Successfully!");
//            vo.setId(voId);
            vo.setShortenURL(shortUrl);
        }
            return JSON.toJSONString(vo);
        }

    }
