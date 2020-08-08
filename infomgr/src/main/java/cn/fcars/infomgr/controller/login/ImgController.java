package cn.fcars.infomgr.controller.login;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

@Controller
public class ImgController {
    public static final String SessionSecurityCode = "imageCode";

    @RequestMapping(value = "/generate", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public void generate(HttpServletRequest req, HttpServletResponse response){
            ByteArrayOutputStream output = new ByteArrayOutputStream();
        String code = drawImg(output);
        // 将四位数字的验证码保存到Session中。
        HttpSession session = req.getSession();
        session.setAttribute(SessionSecurityCode, code);
        //下面权限控制这种写法涉及到过滤器的相关配置,暂时报错,先按照上面的代码理解
//        Subject currentUser = SecurityUtils.getSubject();
//        Session session = currentUser.getSession();
//        session.setAttribute(SessionSecurityCode, code);
        try {
            // 将图像输出到Servlet输出流中
            ServletOutputStream out = response.getOutputStream();
            output.writeTo(out);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String drawImg(ByteArrayOutputStream output){
        String code = "";
        for(int i=0; i<4; i++){
            code += randomChar();
        }
        int width = 70;
        int height = 25;
        BufferedImage bi = new BufferedImage(width,height,BufferedImage.TYPE_3BYTE_BGR);
        Font font = new Font("Times New Roman",Font.PLAIN,20);
        Graphics2D g = bi.createGraphics();
        g.setFont(font);
        Random r = new Random();
        //Color color = new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256));
        Color color = new Color(128,0,128);
        g.setColor(color);
        g.setBackground(new Color(226,226,240));
        g.clearRect(0, 0, width, height);
        FontRenderContext context = g.getFontRenderContext();
        Rectangle2D bounds = font.getStringBounds(code, context);
        double x = (width - bounds.getWidth()) / 2;
        double y = (height - bounds.getHeight()) / 2;
        double ascent = bounds.getY();
        double baseY = y - ascent;
        g.drawString(code, (int)x, (int)baseY);

        //干扰线
        for(int i=0;i<15;i++){
            g.setColor(new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256)));
            //画线
            g.drawLine(r.nextInt(60), r.nextInt(30), r.nextInt(60), r.nextInt(30));
        }
        g.dispose();
        try {
            ImageIO.write(bi, "jpg", output);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return code;
    }

    private char randomChar(){
        Random r = new Random();
        String str = "ABCDEFGHJKLMNPRSTUVWXYZ0123456789";
        return str.charAt(r.nextInt(str.length()));
    }
}
