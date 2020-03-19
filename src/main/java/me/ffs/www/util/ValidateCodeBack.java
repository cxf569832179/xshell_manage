package me.ffs.www.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;

public class ValidateCodeBack {

	  // 图片的宽度。  
    private int width = 160;
    // 图片的高度。  
    private int height = 40;
    // 验证码字符个数  
    private Integer result = null;
    // 验证码  
    private String code = null;
    // 验证码图片Buffer  
    private BufferedImage image=null;
    
	public  String convertNum2HZ(Integer num){
		switch (num) {
			case 0:
				return "零";
			case 1:
				return "壹";
			case 2:
				return "贰";
			case 3:
				return "叁";
			case 4:
				return "肆";
			case 5:
				return "伍";
			case 6:
				return "陆";
			case 7:
				return "柒";
			case 8:
				return "捌";
			case 9:
				return "玖";
				default: return "零";
		}
	}

	public Color getRandColor(int fc, int bc) {//给定范围获得随机颜色
		Random random = new Random();
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}
	
	 public  ValidateCodeBack() {  
	        this.createCode();  
	    }  
	  
    /** 
     *  
     * @param width 图片宽 
     * @param height 图片高 
     */  
    public  ValidateCodeBack(int width,int height) {  
        this.width=width;  
        this.height=height;  
        this.createCode();  
    }  
    
	public void createCode() {  
		 StringBuffer randomCode = new StringBuffer();  
		 StringBuffer randomResult = new StringBuffer();  
		// 在内存中创建图象
			int width = 60, height = 20;
			 image = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			// 获取图形上下文
			Graphics g = image.getGraphics();
			//生成随机类
			Random random = new Random();
			// 设定背景色
			g.setColor(getRandColor(200, 250));
			g.fillRect(0, 0, width, height);
			//设定字体
//				Font font = new Font("Times New Roman", Font.PLAIN, 18);
			Font font=new Font("宋体",Font.PLAIN,12);
			g.setFont(font);

			//画边框
			//g.setColor(new Color());
			//g.drawRect(0,0,width-1,height-1);
			// 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到
			g.setColor(getRandColor(160, 200));
			for (int i = 0; i < 155; i++) {
				int x = random.nextInt(width);
				int y = random.nextInt(height);
				int xl = random.nextInt(12);
				int yl = random.nextInt(12);
				g.drawLine(x, y, x + xl, y + yl);
			}
			// 取随机产生的认证码(4位数字)

			Integer fNum = random.nextInt(10);
			Integer sNum = random.nextInt(10);
			Integer symNum = random.nextInt(10);
			String fStr;
			String sStr;
			String symbol;
			Integer results=0;
			if(symNum%2 == 0){//加法
				results = fNum + sNum;
				symbol = "加";
			}else {//乘法
				results = fNum * sNum;
				symbol = "乘";
			}

		  	fStr = random.nextInt(10)%2 == 0 ?  convertNum2HZ(fNum) : fNum+"";
			sStr = random.nextInt(10)%2 == 0 ?  convertNum2HZ(sNum) : sNum+"";

			// 将认证码显示到图象中
			g.setColor(new Color(20 + random.nextInt(110), 20 + random
					.nextInt(110), 20 + random.nextInt(110)));// 调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
			g.drawString(fStr,6,16);

			 randomCode.append(fStr);
			 
			g.setColor(new Color(20 + random.nextInt(110), 20 + random
				.nextInt(110), 20 + random.nextInt(110)));// 调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
			g.drawString(symbol, 12  + 6, 16);

			 randomCode.append(symbol);
			 
			g.setColor(new Color(20 + random.nextInt(110), 20 + random
				.nextInt(110), 20 + random.nextInt(110)));// 调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
			g.drawString(sStr, 26 + 6, 16);

			 randomCode.append(sStr);
			 
		    g.setColor(new Color(20 + random.nextInt(110), 20 + random
		            .nextInt(110), 20 + random.nextInt(110)));// 调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
		    g.drawString("=", 38 + 6, 16);
	
		    randomCode.append("=");
		    
		    g.setColor(new Color(20 + random.nextInt(110), 20 + random
		            .nextInt(110), 20 + random.nextInt(110)));// 调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
		    g.drawString("?", 46 + 6, 16);
		    
		    randomCode.append("?");
		    randomResult.append(results);
		    
		    code =randomCode.toString();
		    result =Integer.parseInt(randomResult.toString());
		    
//				// 将认证码存入SESSION
//				session.setAttribute("backend_key", result+"");
		
    }
		
	 public void write(String path) throws IOException {
	        OutputStream sos = new FileOutputStream(path);
	            this.write(sos);
	    }
	    
	    public void write(OutputStream sos) throws IOException {
	            ImageIO.write(image, "png", sos);
	            sos.close();
	    }
	    public BufferedImage getBuffImg() {
	        return image;
	    }
	    
	    public String getCode() {  
	        return code;  
	    }
	    public Integer getResult() {  
	    	return result;  
	    }
	
}
