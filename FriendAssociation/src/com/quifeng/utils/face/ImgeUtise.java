package com.quifeng.utils.face;

import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;


import java.io.File;
import java.util.UUID;



public class ImgeUtise {
	
    private static final String OPENCV_DLL_PATH = "C:\\Users\\Administrator\\Desktop\\opencv\\build\\java\\x64\\opencv_java3412.dll";
//    private static final String OPENCV_XML_PATH = "D:\\java\\opencv\\opencv\\sources\\data\\haarcascades\\haarcascade_frontalface_alt.xml";
    private static final String OPENCV_XML_PATH  = "C:\\Users\\Administrator\\Desktop\\opencv\\sources\\data\\haarcascades\\haarcascade_frontalface_alt.xml";

	
    /**
     * @param imageFilePath 待处理图片路径
     * @param destDir       提前人脸后居中裁剪后的图片存储目录
     * @return 居中裁剪的图片路径
     */
	public static String[] detectFaceImage(String imageFilePath, String destDir) {

        File dir = new File(destDir);
        System.load(OPENCV_DLL_PATH);
        CascadeClassifier faceDetector = new CascadeClassifier(OPENCV_XML_PATH);
        if (faceDetector.empty()) {
            return null;
        }
        File imgFile = new File(imageFilePath);
        Mat image = Imgcodecs.imread(imgFile.getPath());
        int srcWidth = image.width();
        int srcHeight = image.height();
        MatOfRect faceDetections = new MatOfRect();
        faceDetector.detectMultiScale(image, faceDetections);
        Rect[] rectFace = faceDetections.toArray();
        String[] targetFiles = new String[rectFace.length];
        for (int i = 0; i < rectFace.length; i++) {
            Rect rect = rectFace[i];
            Rect centerCropBox = estimateCenterCropBox(srcWidth, srcHeight, rect);
            String imgpath = UUID.randomUUID().toString();
            targetFiles[i] = destDir + File.separator + imgpath + getImageType(imgFile);
            cutCenterImage(imgFile.getPath(), targetFiles[i], centerCropBox);
        }
        return targetFiles;
    }
	
	
	public static String[] detectFaceImage(Mat image, String destDir) {

        System.load(OPENCV_DLL_PATH);
        CascadeClassifier faceDetector = new CascadeClassifier(OPENCV_XML_PATH);
        if (faceDetector.empty()) {
            return null;
        }
        int srcWidth = image.width();
        int srcHeight = image.height();
        MatOfRect faceDetections = new MatOfRect();
        faceDetector.detectMultiScale(image, faceDetections);
        Rect[] rectFace = faceDetections.toArray();
        String[] targetFiles = new String[rectFace.length];
        for (int i = 0; i < rectFace.length; i++) {
            Rect rect = rectFace[i];
            Rect centerCropBox = estimateCenterCropBox(srcWidth, srcHeight, rect);
            String imgpath = UUID.randomUUID().toString();
            targetFiles[i] = destDir + File.separator + imgpath + ".jpg";
            cutCenterImage(image, targetFiles[i], centerCropBox);
        }
        return targetFiles;
    }
	
	
	/**
	 * @Desc 是否有人脸
	 * @param mat
	 * @return
	 */
	public static boolean imgIsFace(Mat mat){
		 	System.load(OPENCV_DLL_PATH);
		 	CascadeClassifier faceDetector = new CascadeClassifier(OPENCV_XML_PATH);
	        MatOfRect faceDetections = new MatOfRect();
	        faceDetector.detectMultiScale(mat, faceDetections);
	        Rect[] rectFace = faceDetections.toArray();
	        if (rectFace.length>0) {
	        	return true;
			}
	        return false;
	}
	

	
	
	
	 /**
     * 获取文件后缀不带.
     * @param file 文件后缀名
     * @return
     */
	public static String getImageType(File file) {
        if (file != null && file.exists() && file.isFile()) {
            String fileName = file.getName();
            int index = fileName.lastIndexOf(".");
            if (index != -1 && index < fileName.length() - 1) {
                return fileName.substring(index);
            }
        }
        return null;
    }

	  /**
     * 计算居中裁剪框
     * 在原图边界范围内，以检测的人脸框为中心，向四周等比例放大，最大的裁剪框可保证人脸居中
     * @param srcWidth  原始图像宽度
     * @param srcHeight 原始图像高度
     * @param rect      人脸框位置
     * @return 居中裁剪框
     */
	public static Rect estimateCenterCropBox(int srcWidth, int srcHeight, Rect rect) {

        int w0 = rect.x;
        int w1 = srcWidth - rect.x - rect.width;
        int w2 = (srcHeight - rect.y - rect.height) * rect.width / rect.height;
        int w3 = rect.width * rect.y / rect.height;

        if (w0 < 0 || w1 < 0 || w2 < 0 || w3 < 0) {
            return null;
        }
        int ret = w0;
        if (ret > w1) {
            ret = w1;
        }
        if (ret > w2) {
            ret = w2;
        }
        if (ret > w3) {
            ret = w3;
        }

        return new Rect(rect.x - ret, rect.y - ret * rect.height / rect.width,
                rect.width + 2 * ret, rect.height + 2 * ret * rect.height / rect.width);
    }

	

	
	
	/**
     * @param oriImg  原始图像
     * @param outFile 裁剪的图像输出路径
     * @param rect    剪辑矩形区域
     */
	public static void cutCenterImage(String oriImg, String outFile, Rect rect) {
        System.out.println("cutCenterImage ...");
        Mat image = Imgcodecs.imread(oriImg);
        Mat sub = image.submat(rect);
        Mat mat = new Mat();
        Size size = new Size(rect.width, rect.height);
        Imgproc.resize(sub, mat, size);
        Imgcodecs.imwrite(outFile, mat);
    }
	
	public static void cutCenterImage(Mat image,String outFile, Rect rect){
		 Mat sub = image.submat(rect);
	        Mat mat = new Mat();
	        Size size = new Size(rect.width, rect.height);
	        Imgproc.resize(sub, mat, size);
	        Imgcodecs.imwrite(outFile, mat);
	}
	
	
	
}
