package me.tutttuwi.dbbrowze.controller.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import lombok.extern.slf4j.Slf4j;
import me.tutttuwi.dbbrowze.constant.WebConst;
import me.tutttuwi.dbbrowze.controller.AbstractUiController;
import me.tutttuwi.dbbrowze.request.LoginFormRequest;
import me.tutttuwi.dbbrowze.service.AppMenuService;
import me.tutttuwi.dbbrowze.session.CommonSession;

@Slf4j
@Controller
@RequestMapping("")
@SessionAttributes("scopedTarget.commonSession")
public class AppMenuUiController extends AbstractUiController {

    @Autowired
    AppMenuService service;

    @Autowired
    CommonSession commonSession;

    @ModelAttribute
    CommonSession createSession() {
        return commonSession;
    }

    /**
     * ルートコントローラ
     */
    @GetMapping(value = "/")
    public String index(@ModelAttribute LoginFormRequest form) throws Throwable {
        return "menu";
    }

    /**
     * メニューコントローラ.
     *
     * @param userDetails AccountUserDetails
     * @return HTML Path String
     * @throws Throwable Any Exception
     */
    @GetMapping(value = "/menu")
    public String menu() throws Throwable {
        return "menu";
    }

    @Override
    public String getFunctionName() {
        return WebConst.FNC_MENU;
    }
}
