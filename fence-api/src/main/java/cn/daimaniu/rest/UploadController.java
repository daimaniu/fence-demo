package cn.daimaniu.rest;

import cn.daimaniu.model.UploadLog;
import cn.daimaniu.service.FenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Author :chenqisheng
 * Date   :2016-07-09 19:45.
 */
@Controller
@RequestMapping("/v1/uploads")
public class UploadController {

    @Autowired
    FenceService fenceService;

    /**
     * 上报位置
     *
     * @param uploadLog
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public
    @ResponseBody
    void uploadLocation(@ModelAttribute UploadLog uploadLog) {
        fenceService.upload(uploadLog);
    }
}
