package com.wuzx.springboot;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class MailService {
    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    private JavaMailSender mailSender;

    /**
     * Description: 发送普通邮件
     *
     * @param toEmail
     * @return BaseCommonResult
     * @author 吴子贤 2020-11-24 16:23
     */
    public CommonResult sendSimpleEmail(ToEmail toEmail) {
        try {
            //创建简单邮件消息
            SimpleMailMessage message = new SimpleMailMessage();
            //谁发的
            message.setFrom(from);
            //谁要接收
            message.setTo(toEmail.getTos());
            //邮件标题
            message.setSubject(toEmail.getSubject());
            //邮件内容
            message.setText(toEmail.getContent());

            mailSender.send(message);
            log.info("发送普通邮件,成功！");
            return Result.success();
        } catch (MailException e) {
            log.error("MailManage-sendSimpleEmail,发送普通邮件,发生异常, toEmail:{}", toEmail, e);
            return Result.fail("发送普通邮件失败");
        }
    }

    /**
     * Description: 发送 HTML 邮件
     *
     * @param toEmail
     * @return BaseCommonResult
     * @author 吴子贤 2020-11-24 16:27
     */
    public CommonResult sendHtmlEmail(ToEmail toEmail) {
        try {
            //创建一个MINE消息
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper minehelper = new MimeMessageHelper(message, true);
            //谁发
            minehelper.setFrom(from);
            //谁要接收
            minehelper.setTo(toEmail.getTos());
            //邮件主题
            minehelper.setSubject(toEmail.getSubject());
            //邮件内容   true 表示带有附件或html
            minehelper.setText(toEmail.getContent(), true);

            mailSender.send(message);
            log.info("发送HTML邮件,成功！");
            return Result.success();
        } catch (MailException e) {
            log.error("MailManage-sendHtmlEmail,发生异常, toEmail:{}", toEmail, e);
            return Result.fail("发送HTML邮件失败");
        } catch (MessagingException e) {
            log.error("MailManage-sendHtmlEmail,发生异常, toEmail:{}", toEmail, e);
            return Result.fail("发送HTML邮件失败");
        }
    }

    /**
     * Description: 发送带附件的邮件
     *
     * @param toEmail
     * @param filePath 文件路径
     * @return BaseCommonResult
     * @author 吴子贤 2020-11-24 16:37
     */
    public CommonResult sendAffixEmailByFilePath(ToEmail toEmail, String filePath) {
        try {
            //创建一个MINE消息
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            //谁发
            helper.setFrom(from);
            //谁接收
            helper.setTo(toEmail.getTos());
            //邮件主题
            helper.setSubject(toEmail.getSubject());
            //邮件内容   true 表示带有附件或html
            helper.setText(toEmail.getContent(), true);

            //添加附件
            FileSystemResource file = new FileSystemResource(new File(filePath));
            String filename = file.getFilename();
            helper.addAttachment(filename, file);

            mailSender.send(message);

            log.info("发送带附件邮件,成功！");
            return Result.success();
        } catch (MailException e) {
            log.error("MailManage-sendAffixEmailByFilePath,发送带附件邮件,发生异常, toEmail:{}", toEmail, e);
            return Result.fail("发送带附件邮件失败");
        } catch (MessagingException e) {
            log.error("MailManage-sendAffixEmailByFilePath,发送带附件邮件,发生异常, toEmail:{}", toEmail, e);
            return Result.fail("发送带附件邮件失败");
        }
    }


    /**
     * Description: MultipartFile 转  File
     *
     * @param multiFile
     * @return java.io.File
     * @author 吴子贤 2020-11-24 16:40
     */
    private File MultipartFileToFile(MultipartFile multiFile) {
        // 获取文件名
        String fileName = multiFile.getOriginalFilename();
        // 获取文件后缀
        String prefix = fileName.substring(fileName.lastIndexOf("."));
        // 若需要防止生成的临时文件重复,可以在文件名后添加随机码

        try {
            File file = File.createTempFile(fileName, prefix);
            multiFile.transferTo(file);
            return file;
        } catch (Exception e) {
            log.error("MailManage-MultipartFileToFile,MultipartFile 转  File,发生异常", e);
            //throw new OperationException(BaseApiCode.FAIL,"MultipartFile 转  File,发生异常");
        }
        return null;
    }

    /**
     * Description: 发送带附件的邮件
     *
     * @param toEmail
     * @param multipartFile
     * @return BaseCommonResult
     * @author 吴子贤 2020-11-24 16:37
     */
    public CommonResult sendAffixEmail(ToEmail toEmail, MultipartFile multipartFile) {
        try {
            //创建一个MINE消息
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            //谁发
            helper.setFrom(from);
            //谁接收
            helper.setTo(toEmail.getTos());
            //邮件主题
            helper.setSubject(toEmail.getSubject());
            //邮件内容   true 表示带有附件或html
            helper.setText(toEmail.getContent(), true);

            //添加附件
            File multipartFileToFile = MultipartFileToFile(multipartFile);
            FileSystemResource file = new FileSystemResource(multipartFileToFile);
            String filename = file.getFilename();
            helper.addAttachment(filename, file);

            mailSender.send(message);

            log.info("发送带附件邮件,成功！");
            return Result.success();
        } catch (MailException e) {
            log.error("MailManage-sendAffixEmail,发送带附件邮件,发生异常, toEmail:{}", toEmail, e);
            return Result.fail("发送带附件邮件失败");
        } catch (MessagingException e) {
            log.error("MailManage-sendAffixEmail,发送带附件邮件,发生异常, toEmail:{}", toEmail, e);
            return Result.fail("发送带附件邮件失败");
        }
    }


    /**
     * Description: 发送带静态文件的邮件
     *
     * @param toEmail
     * @param resIdList         静态资源id 列表
     * @param multipartFileList 静态资源 列表
     * @return com.pingan.paic.ip.support.core.dto.result.BaseCommonResult
     * @author 吴子贤 2020-11-24 16:50
     */
    public CommonResult sendStaticEmail(ToEmail toEmail, List<String> resIdList, List<MultipartFile> multipartFileList) {
        try {
            MimeMessage message = mailSender.createMimeMessage();

            //true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(toEmail.getTos());
            helper.setSubject(toEmail.getSubject());
            helper.setText(toEmail.getContent(), true);

            for (int i = 0; i < multipartFileList.size(); i++) {
                //蒋 multpartfile 转为file
                File multipartFileToFile = MultipartFileToFile(multipartFileList.get(i));
                FileSystemResource res = new FileSystemResource(multipartFileToFile);
                //添加内联资源，一个id对应一个资源，最终通过id来找到该资源
                helper.addInline(resIdList.get(i), res);
            }
            mailSender.send(message);

            log.info("发送带静态文件的邮件,成功！");
            return Result.success();
        } catch (MessagingException e) {
            log.error("MailManage-sendStaticEmail,发送带静态文件的邮件,发生异常, toEmail:{},resIdList:{}", toEmail, JSON.toJSONString(resIdList), e);
            return Result.fail("发送带静态文件的邮件");
        }
    }


    /**
     * Description: 发送带静态文件的邮件
     *
     * @param toEmail
     * @param rscIdMap k-v  k:静态资源id，  v: 静态资源路径
     * @return com.pingan.paic.ip.support.core.dto.result.BaseCommonResult
     * @author 吴子贤 2020-11-24 16:50
     */
    public CommonResult sendStaticEmailByFilePath(ToEmail toEmail, Map<String, String> rscIdMap) {
        try {
            MimeMessage message = mailSender.createMimeMessage();

            //true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(toEmail.getTos());
            helper.setSubject(toEmail.getSubject());
            helper.setText(toEmail.getContent(), true);

            for (Map.Entry<String, String> entry : rscIdMap.entrySet()) {
                FileSystemResource file = new FileSystemResource(new File(entry.getValue()));
                helper.addInline(entry.getKey(), file);
            }
            mailSender.send(message);

            log.info("发送带静态文件的邮件,成功！");
            return Result.success();
        } catch (MessagingException e) {
            log.error("MailManage-sendStaticEmail,发送带静态文件的邮件,发生异常, toEmail:{},rscIdMap:{}", toEmail, JSON.toJSONString(rscIdMap), e);
            return Result.fail("发送带静态文件的邮件");
        }
    }
}