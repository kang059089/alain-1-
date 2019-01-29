package com.bigcloud.alain.web.rest;

import com.bigcloud.alain.domain.User;
import com.bigcloud.alain.repository.UserRepository;
import com.bigcloud.alain.service.UserService;
import com.bigcloud.alain.service.dto.UserDTO;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class FileUploadResource {

    private final Logger log = LoggerFactory.getLogger(FileUploadResource.class);

    private final UserService userService;


    public FileUploadResource(UserService userService) {
        this.userService = userService;
    }

    /**
     * 上传头像
     * @param request
     * @param file // 上传的图片文件
     * @param login // 用户登录名
     * @return 返回头像地址、文件名称、生成的文件名的map集合
     */
    @PostMapping("/upLoad/{login}")
    @Timed
    public Map<String, String> avatarUpLoad(HttpServletRequest request, @RequestParam("avatar") MultipartFile file, @PathVariable String login) {
        log.debug("上传头像图片的参数: {}, 上传头像图片的用户：{}", file, login);
        // 文件保存路径
        String realPath = request.getSession().getServletContext().getRealPath("/");
        String path = realPath + "/content/avatars/" + login;
        /**
         * 可能会出现重复文件，所以我们要对文件进行一个重命名的操作
         * 截取文件的原始名称，然后将扩展名和文件名分开，使用：时间戳-文件名.扩展名的格式保存
         */
        // 获取文件名称
        String fileName = file.getOriginalFilename();
        // 获取扩展名
        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".") + 1);
        // 获取文件名
        String name = fileName.substring(0, fileName.lastIndexOf("."));
        // 生成最终保存的文件名,格式为: 时间戳-文件名.扩展名
        String id = String.valueOf(new Date().getTime());
        String saveFileName = id + "-" + name + "." + fileExtensionName;
        // 保存到数据库里的头像地址
        String savePath = "/content/avatars/" + login + "/" + saveFileName;
        /**
         * 上传操作：可能upload目录不存在，所以先判断一下如果不存在，那么新建这个目录
         */
        File fileDir = new File(path);
        if (!fileDir.exists()){
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }
        /**
         * 上传
         */
        File targetFile = new File(path, saveFileName);
        try {
            file.transferTo(targetFile);
            UserDTO userDTO = userService.findByLogin(login);
            userDTO.setImageUrl(savePath);
            userService.updateUser(userDTO);
        } catch (IOException e) {
            e.printStackTrace();
        }
        /**
         * 返回值，这三个对象是ng-zorro那边需要的
         */
        Map<String, String> result = new HashMap<>();
        result.put("imageUrl", String.format("http://localhost:8080" + savePath, saveFileName));
        result.put("uid", id);
        result.put("name", fileName);
        return result;
    }
}
