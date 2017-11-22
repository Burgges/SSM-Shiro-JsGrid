package com.hand.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.hand.annotation.OperationLog;
import com.hand.dto.JsGridResult;
import com.hand.dto.LoginDto;
import com.hand.dto.MessageDto;
import com.hand.model.User;
import com.hand.service.UserService;
import com.hand.util.AccessUtil;
import com.hand.util.MessageUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;

/**
 * Created by huiyu.chen on 2017/7/6.
 *
 */

@Controller
public class UserController {

    private static final Logger LOGGER = Logger.getLogger(UserController.class);
    @Resource
    private UserService userService;

    @Resource
    private DefaultKaptcha captchaProducer;

    private AccessUtil accessUtil = new AccessUtil();

    private MessageUtil<User> messageUtil = new MessageUtil<>();

    /**
     * Add user
     * @param user new user
     * @return return info
     */
    @OperationLog("Add user")
    @RequestMapping(value = "/api/users", method = RequestMethod.POST)
    @ResponseBody
    public MessageDto<User> save(User user,
                    HttpServletRequest request) throws Exception {
        MessageDto<User> messageDto;
        Boolean flag = accessUtil.isAccessFlag(request);
        if (!flag) {
            return messageUtil.setMessageDto(500,"No access", user);
        }
        LOGGER.info("insert: /users");
        HttpSession session = request.getSession();
        Integer uId = (Integer)(session.getAttribute("UID") == null ? 0: session.getAttribute("UID"));
        user.setCreatedBy(uId);
        user.setLastUpdatedBy(uId);
        messageDto = userService.save(user);
        return messageDto;
    }

    /**
     * Update user by user id
     * @param params updateUser
     * @param userId primary key
     * @return return info
     * @throws Exception error
     */
    @OperationLog("Update user")
    @RequestMapping(value = "/api/users/{userId}", method = RequestMethod.POST)
    @ResponseBody
    public MessageDto<User> update(@PathVariable Integer userId,
                      @RequestParam Map<String, Object> params,
                        HttpServletRequest request) throws Exception {
        LOGGER.info("update: /users/{userId}");

        String userName = params.get("userName")+ "";
        String userSex = params.get("userSex") + "";
        String userMail = params.get("userMail") + "";
        User user = new User();
        user.setUserMail(userMail);
        user.setUserName(userName);
        user.setUserSex(userSex);
        HttpSession session = request.getSession();
        Integer uId = (Integer)(session.getAttribute("UID") == null ? 0: session.getAttribute("UID"));
        user.setLastUpdatedBy(uId);
        return userService.update(user, userId);
    }

    /**
     * Delete user by user id
     * @param userId primary key
     * @return return info
     */
    @OperationLog("Delete user")
    @RequestMapping(value = "/api/users/{userId}", method = RequestMethod.DELETE)
    @ResponseBody
    public int delete(@PathVariable Integer userId) {
        LOGGER.info("delete: /users/{userId}");
        userService.delete(userId);
        return 1;
    }

    /**
     * Select user by user name
     * @param userName search condition of user name
     * @return return info
     * @throws Exception error
     */
    @OperationLog("Select user by user name")
    @RequestMapping(value = "/api/users/{userName}", method = RequestMethod.GET)
    @ResponseBody
    public User findOneByName(@PathVariable String userName) throws Exception {
        LOGGER.info("findOneByName: /users/{userName}");
        return userService.findOneByName(userName);
    }

    /**
     * Query all user
     * @param params search condition
     * @return return info
     */
    @OperationLog("Query all users")
    @RequestMapping(value = "/api/users", method = RequestMethod.GET)
    @ResponseBody
    public List<User> queryList(@RequestParam Map<String, Object> params) {
        LOGGER.info("queryList: /users");
        List<User> userList = userService.findList(params);
        JsGridResult<User> jsGridResult = new JsGridResult<>();
        jsGridResult.setData(userList);
        jsGridResult.setItemsCount(Integer.toString(userList.size()));
        return userList;
    }

    /**
     * Into user index page
     * @return return info
     */
    @RequestMapping(value = "/api/users/index", method = RequestMethod.GET)
    public String toUserIndex() {
        LOGGER.info("toUserIndex: /users/index");
        return "userIndex";
    }

    /**
     * Into login page
     * @return return info
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String toLogin() {
        LOGGER.info("toLogin");
        return "login";
    }

    /**
     * create captcha
     */
    @RequestMapping(value = "/getCaptcha", method = RequestMethod.GET)
    public void createCaptcha(HttpServletRequest request,
                              HttpServletResponse response) {
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");
        ServletOutputStream out = null;
        try {
            String capText = captchaProducer.createText();
            HttpSession session = request.getSession();
            session.setAttribute("captchaCode",capText);
            BufferedImage bi = captchaProducer.createImage(capText);
            LOGGER.info("captcha: {}" + bi);
            out = response.getOutputStream();
            ImageIO.write(bi, "jpg", out);
            out.flush();
        } catch (Exception e) {
            LOGGER.info("create captcha fail: {}", e);
        } finally {
            if(out != null){
                try {
                    out.close();
                }catch (Exception e){
                    LOGGER.info("captcha output close fail: {}",e);
                }
            }
        }
    }

    /**
     * user login
     * @param request request
     * @param loginDto login user
     * @return return info
     * @throws Exception error
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public MessageDto<User> login(HttpServletRequest request,
                                  LoginDto loginDto) throws Exception {
        MessageDto<User> messageDto = new MessageDto<>();
        String result = userService.login(loginDto, request);
        Integer code = 500;
        if ("login success".equals(result)) {
            result = "userIndex";
            code = 200;
        }
        messageDto.setCode(code);
        messageDto.setMessage(result);
        return messageDto;
    }

    /**
     * log out
     * @param request request
     * @return result
     */
    @OperationLog("User log out")
    @RequestMapping(value = "/api/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if(StringUtils.isEmpty(session.getAttribute("userName")+"")){
            return "login";
        }
        session.removeAttribute("userName");
        session.removeAttribute("UID");
        return "login";
    }

    /**
     * Select user by user id
     * @param userId search condition of user id
     * @return return info
     * @throws Exception error
     */
    @OperationLog("Select user by user id")
    @RequestMapping(value = "/api/{userId}/users", method = RequestMethod.GET)
    @ResponseBody
    public User findOneById(@PathVariable Integer userId) throws Exception {
        LOGGER.info("findOneById: /{userId}/users");
        return userService.findOneById(userId);
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ResponseBody
    public Object test () {
        return "{\"data\":[{\"taskId\":46,\"taskName\":\"task2\",\"driveAssessStatus\":0},{\"taskId\":47,\"taskName\":\"   blankTask  \",\"driveAssessStatus\":0},{\"taskId\":53,\"taskName\":\"NotModifyModeTask\",\"driveAssessStatus\":0},{\"taskId\":54,\"taskName\":\"CarNameTask\",\"driveAssessStatus\":0},{\"taskId\":55,\"taskName\":\"manyCarTask\",\"driveAssessStatus\":0},{\"taskId\":65,\"taskName\":\"task1\",\"driveAssessStatus\":0},{\"taskId\":67,\"taskName\":\"cancelDeleteTask\",\"driveAssessStatus\":0},{\"taskId\":71,\"taskName\":\"哈哈车号测试\",\"driveAssessStatus\":0},{\"taskId\":72,\"taskName\":\"chineseCarNum\",\"driveAssessStatus\":0},{\"taskId\":83,\"taskName\":\"啊哈\",\"driveAssessStatus\":0},{\"taskId\":93,\"taskName\":\"testTask\",\"driveAssessStatus\":0},{\"taskId\":97,\"taskName\":\"Ctest03\",\"driveAssessStatus\":0}],\"code\":0,\"msg\":\"ok\"}";
    }

    @RequestMapping(value = "/testInfo", method = RequestMethod.GET)
    @ResponseBody
    public Object test2 () {
        return "{\"data\":{\"taskId\":97,\"taskName\":\"Ctest03\",\"driveAssessStatus\":0,\"assess\":[{\"oneLevelId\":8,\"name\":\"座椅\",\"dataType\":1,\"paperWork\":1,\"gradeOne\":\"20\",\"gradeTwo\":\"40\",\"gradeThree\":\"60\",\"gradeFour\":\"80\",\"gradeFive\":\"100\",\"carInfos\":[{\"carNumber\":\"11\",\"projectNum\":\"#projectNum2\",\"vehicleModel\":\"#vehicleModel2\",\"deploy\":\"#vehicleDeploy2\",\"engine\":\"#vehicleEngine2\",\"trans\":\"#vehicleTrans2\",\"score\":-100},{\"carNumber\":\"22\",\"projectNum\":\"#projectNum3\",\"vehicleModel\":\"#vehicleModel3\",\"deploy\":\"#vehicleDeploy3\",\"engine\":\"#vehicleEngine3\",\"trans\":\"#vegicleTrans3\",\"score\":-100}],\"twoLevels\":[{\"twoLevelId\":20,\"name\":\"zyqTest01\",\"dataType\":1,\"paperWork\":1,\"gradeOne\":\"1\",\"gradeTwo\":\"2\",\"gradeThree\":\"3\",\"gradeFour\":\"4\",\"gradeFive\":\"5\",\"carInfos\":[{\"carNumber\":\"11\",\"projectNum\":\"#projectNum2\",\"vehicleModel\":\"#vehicleModel2\",\"deploy\":\"#vehicleDeploy2\",\"engine\":\"#vehicleEngine2\",\"trans\":\"#vehicleTrans2\",\"score\":-100},{\"carNumber\":\"22\",\"projectNum\":\"#projectNum3\",\"vehicleModel\":\"#vehicleModel3\",\"deploy\":\"#vehicleDeploy3\",\"engine\":\"#vehicleEngine3\",\"trans\":\"#vegicleTrans3\",\"score\":-100}]},{\"twoLevelId\":21,\"name\":\"zyqTest02\",\"dataType\":0,\"paperWork\":0,\"gradeOne\":\"1\",\"gradeTwo\":\"2\",\"gradeThree\":\"3\",\"gradeFour\":\"4\",\"gradeFive\":\"5\",\"carInfos\":[{\"carNumber\":\"11\",\"projectNum\":\"#projectNum2\",\"vehicleModel\":\"#vehicleModel2\",\"deploy\":\"#vehicleDeploy2\",\"engine\":\"#vehicleEngine2\",\"trans\":\"#vehicleTrans2\",\"score\":-100},{\"carNumber\":\"22\",\"projectNum\":\"#projectNum3\",\"vehicleModel\":\"#vehicleModel3\",\"deploy\":\"#vehicleDeploy3\",\"engine\":\"#vehicleEngine3\",\"trans\":\"#vegicleTrans3\",\"score\":-100}]},{\"twoLevelId\":22,\"name\":\"zyqTest03\",\"dataType\":0,\"paperWork\":0,\"gradeOne\":\"1\",\"gradeTwo\":\"2\",\"gradeThree\":\"3\",\"gradeFour\":\"4\",\"gradeFive\":\"5\",\"carInfos\":[{\"carNumber\":\"11\",\"projectNum\":\"#projectNum2\",\"vehicleModel\":\"#vehicleModel2\",\"deploy\":\"#vehicleDeploy2\",\"engine\":\"#vehicleEngine2\",\"trans\":\"#vehicleTrans2\",\"score\":-100},{\"carNumber\":\"22\",\"projectNum\":\"#projectNum3\",\"vehicleModel\":\"#vehicleModel3\",\"deploy\":\"#vehicleDeploy3\",\"engine\":\"#vehicleEngine3\",\"trans\":\"#vegicleTrans3\",\"score\":-100}]}]},{\"oneLevelId\":133,\"name\":\"车轮\",\"dataType\":0,\"paperWork\":1,\"gradeOne\":\"1\",\"gradeTwo\":\"2\",\"gradeThree\":\"3\",\"gradeFour\":\"4\",\"gradeFive\":\"5\",\"carInfos\":[{\"carNumber\":\"11\",\"projectNum\":\"#projectNum2\",\"vehicleModel\":\"#vehicleModel2\",\"deploy\":\"#vehicleDeploy2\",\"engine\":\"#vehicleEngine2\",\"trans\":\"#vehicleTrans2\",\"score\":-100},{\"carNumber\":\"22\",\"projectNum\":\"#projectNum3\",\"vehicleModel\":\"#vehicleModel3\",\"deploy\":\"#vehicleDeploy3\",\"engine\":\"#vehicleEngine3\",\"trans\":\"#vegicleTrans3\",\"score\":-100}]},{\"oneLevelId\":137,\"name\":\"橡胶件\",\"dataType\":1,\"paperWork\":0,\"gradeOne\":\"1\",\"gradeTwo\":\"2\",\"gradeThree\":\"3\",\"gradeFour\":\"4\",\"gradeFive\":\"5\",\"carInfos\":[{\"carNumber\":\"11\",\"projectNum\":\"#projectNum2\",\"vehicleModel\":\"#vehicleModel2\",\"deploy\":\"#vehicleDeploy2\",\"engine\":\"#vehicleEngine2\",\"trans\":\"#vehicleTrans2\",\"score\":-100},{\"carNumber\":\"22\",\"projectNum\":\"#projectNum3\",\"vehicleModel\":\"#vehicleModel3\",\"deploy\":\"#vehicleDeploy3\",\"engine\":\"#vehicleEngine3\",\"trans\":\"#vegicleTrans3\",\"score\":-100}],\"twoLevels\":[{\"twoLevelId\":138,\"name\":\"传动皮带\",\"dataType\":1,\"paperWork\":1,\"gradeOne\":\"1\",\"gradeTwo\":\"2\",\"gradeThree\":\"3\",\"gradeFour\":\"4\",\"gradeFive\":\"5\",\"carInfos\":[{\"carNumber\":\"11\",\"projectNum\":\"#projectNum2\",\"vehicleModel\":\"#vehicleModel2\",\"deploy\":\"#vehicleDeploy2\",\"engine\":\"#vehicleEngine2\",\"trans\":\"#vehicleTrans2\",\"score\":-100},{\"carNumber\":\"22\",\"projectNum\":\"#projectNum3\",\"vehicleModel\":\"#vehicleModel3\",\"deploy\":\"#vehicleDeploy3\",\"engine\":\"#vehicleEngine3\",\"trans\":\"#vegicleTrans3\",\"score\":-100}]},{\"twoLevelId\":139,\"name\":\"保护套\",\"dataType\":0,\"paperWork\":0,\"gradeOne\":\"￥\",\"gradeTwo\":\"2\",\"gradeThree\":\"3\",\"gradeFour\":\"4\",\"gradeFive\":\"5\",\"carInfos\":[{\"carNumber\":\"11\",\"projectNum\":\"#projectNum2\",\"vehicleModel\":\"#vehicleModel2\",\"deploy\":\"#vehicleDeploy2\",\"engine\":\"#vehicleEngine2\",\"trans\":\"#vehicleTrans2\",\"score\":-100},{\"carNumber\":\"22\",\"projectNum\":\"#projectNum3\",\"vehicleModel\":\"#vehicleModel3\",\"deploy\":\"#vehicleDeploy3\",\"engine\":\"#vehicleEngine3\",\"trans\":\"#vegicleTrans3\",\"score\":-100}]}]},{\"oneLevelId\":140,\"name\":\"外观\",\"dataType\":0,\"paperWork\":0,\"gradeOne\":\"1\",\"gradeTwo\":\"2\",\"gradeThree\":\"3\",\"gradeFour\":\"4\",\"gradeFive\":\"5\",\"carInfos\":[{\"carNumber\":\"11\",\"projectNum\":\"#projectNum2\",\"vehicleModel\":\"#vehicleModel2\",\"deploy\":\"#vehicleDeploy2\",\"engine\":\"#vehicleEngine2\",\"trans\":\"#vehicleTrans2\",\"score\":-100},{\"carNumber\":\"22\",\"projectNum\":\"#projectNum3\",\"vehicleModel\":\"#vehicleModel3\",\"deploy\":\"#vehicleDeploy3\",\"engine\":\"#vehicleEngine3\",\"trans\":\"#vegicleTrans3\",\"score\":-100}],\"twoLevels\":[{\"twoLevelId\":141,\"name\":\"刮痕\",\"dataType\":0,\"paperWork\":0,\"gradeOne\":\"1\",\"gradeTwo\":\"2\",\"gradeThree\":\"3\",\"gradeFour\":\"4\",\"gradeFive\":\"5\",\"carInfos\":[{\"carNumber\":\"11\",\"projectNum\":\"#projectNum2\",\"vehicleModel\":\"#vehicleModel2\",\"deploy\":\"#vehicleDeploy2\",\"engine\":\"#vehicleEngine2\",\"trans\":\"#vehicleTrans2\",\"score\":-100},{\"carNumber\":\"22\",\"projectNum\":\"#projectNum3\",\"vehicleModel\":\"#vehicleModel3\",\"deploy\":\"#vehicleDeploy3\",\"engine\":\"#vehicleEngine3\",\"trans\":\"#vegicleTrans3\",\"score\":-100}]},{\"twoLevelId\":142,\"name\":\"凹陷\",\"dataType\":0,\"paperWork\":0,\"gradeOne\":\"1\",\"gradeTwo\":\"2\",\"gradeThree\":\"3\",\"gradeFour\":\"4\",\"gradeFive\":\"5\",\"carInfos\":[{\"carNumber\":\"11\",\"projectNum\":\"#projectNum2\",\"vehicleModel\":\"#vehicleModel2\",\"deploy\":\"#vehicleDeploy2\",\"engine\":\"#vehicleEngine2\",\"trans\":\"#vehicleTrans2\",\"score\":-100},{\"carNumber\":\"22\",\"projectNum\":\"#projectNum3\",\"vehicleModel\":\"#vehicleModel3\",\"deploy\":\"#vehicleDeploy3\",\"engine\":\"#vehicleEngine3\",\"trans\":\"#vegicleTrans3\",\"score\":-100}]}]},{\"oneLevelId\":205,\"name\":\"A\",\"dataType\":0,\"paperWork\":0,\"gradeOne\":\"1\",\"gradeTwo\":\"2\",\"gradeThree\":\"3\",\"gradeFour\":\"4\",\"gradeFive\":\"5\",\"comments\":\"一级评估项A\",\"carInfos\":[{\"carNumber\":\"11\",\"projectNum\":\"#projectNum2\",\"vehicleModel\":\"#vehicleModel2\",\"deploy\":\"#vehicleDeploy2\",\"engine\":\"#vehicleEngine2\",\"trans\":\"#vehicleTrans2\",\"score\":-100},{\"carNumber\":\"22\",\"projectNum\":\"#projectNum3\",\"vehicleModel\":\"#vehicleModel3\",\"deploy\":\"#vehicleDeploy3\",\"engine\":\"#vehicleEngine3\",\"trans\":\"#vegicleTrans3\",\"score\":-100}],\"twoLevels\":[{\"twoLevelId\":213,\"name\":\"a1\",\"dataType\":0,\"paperWork\":0,\"gradeOne\":\"1\",\"gradeTwo\":\"2\",\"gradeThree\":\"3\",\"gradeFour\":\"4\",\"gradeFive\":\"5\",\"comments\":\"二级评估项a1\",\"carInfos\":[{\"carNumber\":\"11\",\"projectNum\":\"#projectNum2\",\"vehicleModel\":\"#vehicleModel2\",\"deploy\":\"#vehicleDeploy2\",\"engine\":\"#vehicleEngine2\",\"trans\":\"#vehicleTrans2\",\"score\":-100},{\"carNumber\":\"22\",\"projectNum\":\"#projectNum3\",\"vehicleModel\":\"#vehicleModel3\",\"deploy\":\"#vehicleDeploy3\",\"engine\":\"#vehicleEngine3\",\"trans\":\"#vegicleTrans3\",\"score\":-100}]},{\"twoLevelId\":214,\"name\":\"a2\",\"dataType\":0,\"paperWork\":0,\"gradeOne\":\"1\",\"gradeTwo\":\"2\",\"gradeThree\":\"3\",\"gradeFour\":\"4\",\"gradeFive\":\"5\",\"comments\":\"二级评估项a2\",\"carInfos\":[{\"carNumber\":\"11\",\"projectNum\":\"#projectNum2\",\"vehicleModel\":\"#vehicleModel2\",\"deploy\":\"#vehicleDeploy2\",\"engine\":\"#vehicleEngine2\",\"trans\":\"#vehicleTrans2\",\"score\":-100},{\"carNumber\":\"22\",\"projectNum\":\"#projectNum3\",\"vehicleModel\":\"#vehicleModel3\",\"deploy\":\"#vehicleDeploy3\",\"engine\":\"#vehicleEngine3\",\"trans\":\"#vegicleTrans3\",\"score\":-100}]},{\"twoLevelId\":215,\"name\":\"a3\",\"dataType\":0,\"paperWork\":0,\"gradeOne\":\"1\",\"gradeTwo\":\"2\",\"gradeThree\":\"3\",\"gradeFour\":\"4\",\"gradeFive\":\"5\",\"comments\":\"二级评估项a3\",\"carInfos\":[{\"carNumber\":\"11\",\"projectNum\":\"#projectNum2\",\"vehicleModel\":\"#vehicleModel2\",\"deploy\":\"#vehicleDeploy2\",\"engine\":\"#vehicleEngine2\",\"trans\":\"#vehicleTrans2\",\"score\":-100},{\"carNumber\":\"22\",\"projectNum\":\"#projectNum3\",\"vehicleModel\":\"#vehicleModel3\",\"deploy\":\"#vehicleDeploy3\",\"engine\":\"#vehicleEngine3\",\"trans\":\"#vegicleTrans3\",\"score\":-100}]},{\"twoLevelId\":216,\"name\":\"a4\",\"dataType\":0,\"paperWork\":0,\"gradeOne\":\"1\",\"gradeTwo\":\"2\",\"gradeThree\":\"3\",\"gradeFour\":\"4\",\"gradeFive\":\"5\",\"comments\":\"二级评估项a4\",\"carInfos\":[{\"carNumber\":\"11\",\"projectNum\":\"#projectNum2\",\"vehicleModel\":\"#vehicleModel2\",\"deploy\":\"#vehicleDeploy2\",\"engine\":\"#vehicleEngine2\",\"trans\":\"#vehicleTrans2\",\"score\":-100},{\"carNumber\":\"22\",\"projectNum\":\"#projectNum3\",\"vehicleModel\":\"#vehicleModel3\",\"deploy\":\"#vehicleDeploy3\",\"engine\":\"#vehicleEngine3\",\"trans\":\"#vegicleTrans3\",\"score\":-100}]}]}]},\"code\":0,\"msg\":\"ok\"}";
    }

    @RequestMapping(value = "/testInfo2", method = RequestMethod.POST)
    @ResponseBody
    public Object submitTest (String username, String password) {
        LOGGER.info(username + "===-====" + password);
        return "success";
    }
}
