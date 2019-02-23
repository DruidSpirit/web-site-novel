package com.druidelf.novelmain.common.utils;

import com.druidelf.novelmain.common.config.GeetestConfig;
import com.druidelf.novelmain.common.utils.sdk.GeetestLib;
import com.druidelf.novelmain.request.userParam.GeeTestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

public class GeeTestValidatorUtil {
    /**
     * 极验二次验证
     *
     * @param geeTestParam
     * @return
     */
    public Boolean secondaryValidation( GeeTestParam geeTestParam ) {
        GeetestLib gtSdk = new GeetestLib(GeetestConfig.getGeetest_id(), GeetestConfig.getGeetest_key(),
                GeetestConfig.isnewfailback());
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//        String challenge = request.getParameter(GeetestLib.fn_geetest_challenge);
//        String validate = request.getParameter(GeetestLib.fn_geetest_validate);
//        String seccode = request.getParameter(GeetestLib.fn_geetest_seccode);

        //从session中获取gt-server状态
        Object test = request.getSession().getAttribute(gtSdk.gtServerStatusSessionKey);
        int gt_server_status_code = (Integer) request.getSession().getAttribute(gtSdk.gtServerStatusSessionKey);

        //从session中获取userid
        //String userid = (String)request.getSession().getAttribute("userid");

        //自定义参数,可选择添加
        HashMap<String, String> param = new HashMap<String, String>();
//        param.put("user_id", userid); //网站用户id
//        param.put("client_type", "web"); //web:电脑上的浏览器；h5:手机上的浏览器，包括移动应用内完全内置的web_view；native：通过原生SDK植入APP应用的方式
//        param.put("ip_address", "127.0.0.1"); //传输用户请求验证时所携带的IP

        int gtResult = 0;

        if (gt_server_status_code == 1) {
            //gt-server正常，向gt-server进行二次验证

            gtResult = gtSdk.enhencedValidateRequest(geeTestParam.getChallenge(), geeTestParam.getValidate(), geeTestParam.getSecCode(), param);
            //  System.out.println(gtResult);
        } else {
            // gt-server非正常情况下，进行failback模式验证

            // System.out.println("failback:use your own server captcha validate");
            gtResult = gtSdk.failbackValidateRequest(geeTestParam.getChallenge(), geeTestParam.getValidate(), geeTestParam.getSecCode());
            // System.out.println(gtResult);
        }


        if (gtResult == 1) {
            // 验证成功
            return true;
        }
        return false;
    }
}
