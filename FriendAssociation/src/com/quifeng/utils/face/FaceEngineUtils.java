package com.quifeng.utils.face;

import com.arcsoft.face.*;
import com.arcsoft.face.enums.DetectMode;
import com.arcsoft.face.enums.DetectOrient;
import com.arcsoft.face.enums.ErrorInfo;
import com.arcsoft.face.enums.ImageFormat;
import com.arcsoft.face.toolkit.ImageInfo;

import java.awt.image.BufferedImage;
import java.io.File;

import java.util.ArrayList;
import java.util.List;

import static com.arcsoft.face.toolkit.ImageFactory.getRGBData;

/**
 * @Desc 对虹膜人脸的使用
 * @author 语录
 */
public class FaceEngineUtils {

	private static FaceEngine faceEngine = null;
	private static ActiveFileInfo activeFileInfo = null;
	private static int errorCode = 0;

	static {
		into();
	}

	// 先进行加载必要的东西
	public static int into() {
		// 从官网获取
		String appId = "CiU9hZbg7LgqomhwuRUieyteLPnCJUkarUmUqodokzns";
		String sdkKey = "FeLvhUeS1DTfvaqXNs5CnWfi4pJo16dKym26rvTgJLeX";
//		faceEngine = new FaceEngine("H:\\HBSI_JAVA\\MyCode\\ArcSoft_ArcFace_Java_Windows_x64_V3.0\\libs\\WIN64");
        faceEngine = new FaceEngine("C:\\Users\\Administrator\\Desktop\\ArcSoft_ArcFace_Java_Windows_x64_V3.0\\libs\\WIN64");
//        faceEngine = new FaceEngine("D:\\java\\java_study\\idea\\ArcSoft_ArcFace_Java_Windows_x64_V3.0\\libs\\WIN64");
		int errorCode = faceEngine.activeOnline(appId, sdkKey);
		if (errorCode != ErrorInfo.MOK.getValue() && errorCode != ErrorInfo.MERR_ASF_ALREADY_ACTIVATED.getValue()) {
			System.out.println("引擎激活失败");
			return -1;
		}

		activeFileInfo = new ActiveFileInfo();
		errorCode = faceEngine.getActiveFileInfo(activeFileInfo);
		if (errorCode != ErrorInfo.MOK.getValue() && errorCode != ErrorInfo.MERR_ASF_ALREADY_ACTIVATED.getValue()) {
			System.out.println("获取激活文件信息失败");
			return -1;
		}
		// 引擎配置
		EngineConfiguration engineConfiguration = new EngineConfiguration();
		engineConfiguration.setDetectMode(DetectMode.ASF_DETECT_MODE_IMAGE);
		engineConfiguration.setDetectFaceOrientPriority(DetectOrient.ASF_OP_ALL_OUT);
		engineConfiguration.setDetectFaceMaxNum(10);
		engineConfiguration.setDetectFaceScaleVal(16);
		// 功能配置
		FunctionConfiguration functionConfiguration = new FunctionConfiguration();
		functionConfiguration.setSupportAge(true);
		functionConfiguration.setSupportFace3dAngle(true);
		functionConfiguration.setSupportFaceDetect(true);
		functionConfiguration.setSupportFaceRecognition(true);
		functionConfiguration.setSupportGender(true);
		functionConfiguration.setSupportLiveness(true);
		functionConfiguration.setSupportIRLiveness(true);
		engineConfiguration.setFunctionConfiguration(functionConfiguration);
		// 初始化引擎
		errorCode = faceEngine.init(engineConfiguration);

		if (errorCode != ErrorInfo.MOK.getValue()) {
			System.out.println("初始化引擎失败");
			return -1;
		}
		return 200;
	}

	/**
	 * @Deec 获取 ImageInfo 这里是要经行活体检测 和获取人脸是用的
	 * @param image
	 * @return
	 */
	public static ImageInfo getGrayData1(BufferedImage image) {
		ImageInfo imageInfo = bufferedImage2GrayImageInfo(image);
		return imageInfo;

	}

	public static ImageInfo bufferedImage2GrayImageInfo(BufferedImage image) {
		ImageInfo imageInfo = new ImageInfo();
		int width = image.getWidth();
		int height = image.getHeight();
		width &= -4;
		height &= -4;
		imageInfo.setWidth(width);
		imageInfo.setHeight(height);
		int[] rgb = image.getRGB(0, 0, width, height, (int[]) null, 0, width);
		byte[] bytes = rgbToGray(rgb, width, height);
		imageInfo.setImageFormat(ImageFormat.CP_PAF_GRAY);
		imageInfo.setImageData(bytes);
		return imageInfo;
	}

	private static byte[] rgbToGray(int[] argb, int width, int height) {
		int yIndex = 0;
		int index = 0;
		byte[] gray = new byte[width * height];

		for (int j = 0; j < height; ++j) {
			for (int i = 0; i < width; ++i) {
				int R = (argb[index] & 16711680) >> 16;
				int G = (argb[index] & '\uff00') >> 8;
				int B = argb[index] & 255;
				int Y = (66 * R + 129 * G + 25 * B + 128 >> 8) + 16;
				gray[yIndex++] = (byte) (Y < 0 ? 0 : (Y > 255 ? 255 : Y));
				++index;
			}
		}

		return gray;
	}

	/**
	 * @Desc 对人脸的对比 说实话真tm好用
	 * @return
	 */
	public static double face(BufferedImage image1, BufferedImage image2) {
		// 获取
		ImageInfo imageInfo1 = getGrayData1(image1);
		ImageInfo imageInfo2 = getGrayData1(image2);
		List<FaceInfo> faceInfoList1 = new ArrayList<FaceInfo>();
		List<FaceInfo> faceInfoList2 = new ArrayList<FaceInfo>();
		FaceFeature faceFeature = new FaceFeature();
		FaceFeature faceFeature2 = new FaceFeature();
		faceFeature = getFaceT(faceFeature, imageInfo1, faceInfoList1);
		faceFeature2 = getFaceT(faceFeature2, imageInfo2, faceInfoList2);
		// 特征比对
		FaceFeature targetFaceFeature = new FaceFeature();
		targetFaceFeature.setFeatureData(faceFeature.getFeatureData());
		FaceFeature sourceFaceFeature = new FaceFeature();
		sourceFaceFeature.setFeatureData(faceFeature2.getFeatureData());
		FaceSimilar faceSimilar = new FaceSimilar();
		errorCode = faceEngine.compareFaceFeature(targetFaceFeature, sourceFaceFeature, faceSimilar);
		return faceSimilar.getScore();
	}

	public static void main(String[] args) {
		ImageInfo imageInfo1 = getRGBData(new File("D:\\face\\0.jpg"));
		ImageInfo imageInfo2 = getRGBData(new File("D:\\face\\0.jpg"));
		List<FaceInfo> faceInfoList1 = new ArrayList<FaceInfo>();
		List<FaceInfo> faceInfoList2 = new ArrayList<FaceInfo>();
		FaceFeature faceFeature = new FaceFeature();
		FaceFeature faceFeature2 = new FaceFeature();
		faceFeature = getFaceT(faceFeature, imageInfo1, faceInfoList1);
		faceFeature2 = getFaceT(faceFeature2, imageInfo2, faceInfoList2);
		// 特征比对
		FaceFeature targetFaceFeature = new FaceFeature();
		targetFaceFeature.setFeatureData(faceFeature.getFeatureData());
		FaceFeature sourceFaceFeature = new FaceFeature();
		sourceFaceFeature.setFeatureData(faceFeature2.getFeatureData());
		FaceSimilar faceSimilar = new FaceSimilar();
		errorCode = faceEngine.compareFaceFeature(targetFaceFeature, sourceFaceFeature, faceSimilar);

		System.out.println(faceSimilar.getScore());
	}

	/**
	 * @Desc 特性的获取 爱了
	 * @param faceFeature
	 * @return
	 */
	public static FaceFeature getFaceT(FaceFeature faceFeature, ImageInfo imageInfo, List<FaceInfo> faceInfoList) {
		errorCode = faceEngine.detectFaces(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(),
				imageInfo.getImageFormat(), faceInfoList);
		// 特征提取
		faceFeature = new FaceFeature();
		errorCode = faceEngine.extractFaceFeature(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(),
				imageInfo.getImageFormat(), faceInfoList.get(0), faceFeature);

		return faceFeature;
	}

	/**
	 * @Desc 活体检测
	 * @param imageInfo
	 * @param faceInfoList
	 * @return
	 */
	public static double isPreson(ImageInfo imageInfo, List<FaceInfo> faceInfoList) {
		// 设置活体测试
		errorCode = faceEngine.setLivenessParam(0.5f, 0.7f);
		// 人脸属性检测
		FunctionConfiguration configuration = new FunctionConfiguration();
		configuration.setSupportLiveness(true);
		errorCode = faceEngine.process(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(),
				imageInfo.getImageFormat(), faceInfoList, configuration);
		// 活体检测
		List<LivenessInfo> livenessInfoList = new ArrayList<LivenessInfo>();
		if (livenessInfoList.size() == 0) {
			return 0;
		}
		errorCode = faceEngine.getLiveness(livenessInfoList);
		return livenessInfoList.get(0).getLiveness();
	}

	public static double isPreson(BufferedImage image1) {
		ImageInfo imageInfo1 = getGrayData1(image1);
		List<FaceInfo> faceInfoList1 = new ArrayList<FaceInfo>();
		FaceFeature faceFeature = new FaceFeature();
		getFaceT(faceFeature, imageInfo1, faceInfoList1);
		return isPreson(imageInfo1, faceInfoList1);
	}

}
