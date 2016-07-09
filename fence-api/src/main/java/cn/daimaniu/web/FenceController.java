package cn.daimaniu.web;

import cn.daimaniu.model.Fence;
import cn.daimaniu.service.FenceService;
import cn.daimaniu.util.ApplicationConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Author :chenqisheng
 * Date   :2016-07-09 19:45.
 */
@Controller
@RequestMapping("/fences")
public class FenceController {
    @Autowired
    FenceService fenceService;

    @RequestMapping(method = RequestMethod.GET)
    public String index(Model model) {
        return "addFence";
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public
    @ResponseBody
    String addFence(HttpServletRequest request, @ModelAttribute Fence fence) {
        fenceService.add(fence);
        return ApplicationConstants.RESPONSE_SUCCESS;
    }
}
