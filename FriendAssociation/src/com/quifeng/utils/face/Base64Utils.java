package com.quifeng.utils.face;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import org.bytedeco.javacpp.opencv_core.Rect;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


public class Base64Utils {
	
    private static final String OPENCV_DLL_PATH ="D:\\java\\opencv\\opencv\\build\\java\\x64\\opencv_java3412.dll";

    static {
    	//加载 opencv_java401.dll
    	 System.load(OPENCV_DLL_PATH);
    }
	
    
    
 
    /**
     * @Desc 路径图片转为base64
     * @param imagePath
     * @return
     * @throws IOException
     */
    public static Mat imagePath2Mat(String imagePath) throws IOException {
       
        BufferedImage image = ImageIO.read(new FileInputStream(imagePath));
        Mat matImage = Base64Utils.BufImg2Mat(image, BufferedImage.TYPE_3BYTE_BGR, CvType.CV_8UC3);// CvType.CV_8UC3
        return matImage;
    }
    

   /**
    * @Desc base64 转成  Rect
    */
   public static Rect base642Rect(String base64) throws IOException {
       BASE64Decoder decoder = new BASE64Decoder();
       byte[] origin = decoder.decodeBuffer(base64);
       InputStream in = new ByteArrayInputStream(origin); // 灏哹浣滀负杈撳叆娴侊紱
       BufferedImage image = ImageIO.read(in);
       return new Rect(0,0,image.getWidth(),image.getHeight());
   }
   
   
 
   /**
    * @Desc 将BufferedImage 转为 Rect
    * @param image
    * @return
    * @throws IOException
    */
  public static Rect BufferedImage2Rect(BufferedImage image) throws IOException {
      return new Rect(0,0,image.getWidth(),image.getHeight());
  }
   
 
   

  /**
   * @Desc BufferedImage转换成Mat
   * @param original
   * @param imgType
   * @param matType
   * @return
   */
  public static Mat BufImg2Mat(BufferedImage original, int imgType, int matType) {
	  System.load(OPENCV_DLL_PATH);
	  if (original == null) {
        throw new IllegalArgumentException("original == null");
    
      }

    
      if (original.getType() != imgType) {

          // Create a buffered image
          BufferedImage image = new BufferedImage(original.getWidth(), original.getHeight(), imgType);

          // Draw the image onto the new buffer
          Graphics2D g = image.createGraphics();
          System.out.println(1);
          try {
              g.setComposite(AlphaComposite.Src);
              g.drawImage(original, 0, 0, null);
          } finally {
              g.dispose();
          }
      }

      byte[] pixels = ((DataBufferByte) original.getRaster().getDataBuffer()).getData();
      Mat mat = Mat.eye(original.getHeight(), original.getWidth(), matType);
      mat.put(0, 0, pixels);
      return mat;
  }
    
  
  /**
   * @Desc 将base64 转为 BufferedImage
   * @param base64
   * @return
   * @throws IOException
   */
   public static BufferedImage base642BufferedImage(String base64) throws IOException {
	   System.load(OPENCV_DLL_PATH);
	   BASE64Decoder decoder = new BASE64Decoder();
       byte[] origin = decoder.decodeBuffer(base64);
       ByteArrayInputStream in = new ByteArrayInputStream(origin); // 灏哹浣滀负杈撳叆娴侊紱
       return ImageIO.read(in);
   }

	
	
	 

	
   /**
    * 图片转base64字符串
    * @param imgFile 图片路径
    * @return
    */
   public static String imageToBase64Str(String imgFile) {
       InputStream inputStream = null;
       byte[] data = null;
       try {
           inputStream = new FileInputStream(imgFile);
           data = new byte[inputStream.available()];
           inputStream.read(data);
           inputStream.close();
       } catch (IOException e) {
           e.printStackTrace();
       }
       // 加密
       BASE64Encoder encoder = new BASE64Encoder();
       return encoder.encode(data);
   }

	
	/**
	 * @Desc 将图片转为base64
	 * @param imgStr
	 * @param imgFilePath
	 * @return
	 */
	  public static boolean GenerateImage(String imgStr,String imgFilePath){   
	        if (imgStr == null)
	            return false;
	        BASE64Decoder decoder = new BASE64Decoder();
	        try 
	        {
	           
	            byte[] b = decoder.decodeBuffer(imgStr);
	            for(int i=0;i<b.length;++i)
	            {
	                if(b[i]<0)
	                {
	                    b[i]+=256;
	                }
	            }
	            OutputStream out = new FileOutputStream(imgFilePath);    
	            out.write(b);
	            out.flush();
	            out.close();
	            return true;
	        } 
	        catch (Exception e) 
	        {
	            return false;
	        }
	    }

	
	
	
}
