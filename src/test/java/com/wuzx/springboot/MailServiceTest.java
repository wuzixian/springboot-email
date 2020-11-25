package com.wuzx.springboot;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

/**
 * Description:
 *
 * @author 吴子贤 2020-11-24 15:58
 * @version 1.0
 * @company com.ping.paic.ip
 * @email WUZIXIAN603@pingan.com.cn
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles(value = "dev")
@Slf4j
public class MailServiceTest {

    @Autowired
    private MailService mailService;

    private static final String[] tos = {"xxx@qq.com"};

    /**
     * 测试发送普通邮件
     */
    @Test
    public void sendSimpleEmail() {
        ToEmail toEmail = new ToEmail();
        toEmail.setTos(tos);
        toEmail.setSubject("普通邮件测试");
        toEmail.setContent("我是一个普普通通的内容");
        CommonResult result = mailService.sendSimpleEmail(toEmail);
        log.info("结果：{}", result);
    }

    /**
     * 测试发送html邮件
     */
    @Test
    public void testHtml() throws Exception {
        ToEmail toEmail = new ToEmail();
        toEmail.setTos(tos);
        toEmail.setSubject("Html邮件");

        String content = "<html>\n" +
                "<body>\n" +
                "    <h1>这是Html格式邮件!,不信你看邮件，我字体比一般字体还要大</h1>\n" +
                "</body>\n" +
                "</html>";
        toEmail.setContent(content);

        CommonResult result = mailService.sendHtmlEmail(toEmail);
        log.info("结果：{}", result);
    }

    /**
     * 测试发送带附件的邮件
     *
     * @throws FileNotFoundException
     */
    @Test
    public void sendAttachmentMessage() throws FileNotFoundException {
        ToEmail toEmail = new ToEmail();
        toEmail.setTos(tos);
        toEmail.setSubject("发送带附件的邮件");
        toEmail.setContent("我在测试发送带附件的邮件");

        File file = ResourceUtils.getFile("C:\\Users\\Administrator\\Desktop\\xxx.pdf");
        String filePath = file.getAbsolutePath();
        CommonResult result = mailService.sendAffixEmailByFilePath(toEmail, filePath);
        log.info("结果：{}", result);
    }

    /**
     * 发送带静态文件的邮件
     *
     * @throws FileNotFoundException
     */
    @Test
    public void sendStaticEmailByFilePath() throws FileNotFoundException {
        ToEmail toEmail = new ToEmail();
        toEmail.setTos(tos);
        toEmail.setSubject("发送带静态文件的邮件");

        String htmlStr = "<html><body>测试：图片1 <br> <img src=\'cid:pic1\'/> <br>图片2 <br> <img src=\'cid:pic2\'/></body></html>";
        toEmail.setContent(htmlStr);

        Map<String, String> rscIdMap = new HashMap<>(2);
        rscIdMap.put("pic1", ResourceUtils.getFile("C:\\Users\\Administrator\\Pictures\\一护.jpg").getAbsolutePath());
        rscIdMap.put("pic2", ResourceUtils.getFile("C:\\Users\\Administrator\\Pictures\\一护.jpg").getAbsolutePath());
        CommonResult result = mailService.sendStaticEmailByFilePath(toEmail, rscIdMap);
        log.info("结果：{}", result);
    }

}