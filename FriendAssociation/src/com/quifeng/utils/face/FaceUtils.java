package com.quifeng.utils.face;

import java.util.Arrays;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

public class FaceUtils {

	static CascadeClassifier faceDetector;
	static int i = 0;

	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
//	        faceDetector = new CascadeClassifier("C:\\Users\\Administrator\\Desktop\\opencv\\sources\\data\\haarcascades\\haarcascade_frontalface_alt.xml");
		faceDetector = new CascadeClassifier(
				"E:\\opencv\\sources\\data\\haarcascades\\haarcascade_frontalface_alt.xml");
	}

	/**
	 * 裁剪人脸
	 * 
	 * @param imagePath
	 * @param outFile
	 * @param posX
	 * @param posY
	 * @param width
	 * @param height
	 */
	public static void imageCut(String imagePath, String outFile, int posX, int posY, int width, int height) {
		Mat image = Imgcodecs.imread(imagePath);
		Rect rect = new Rect(posX, posY, width, height);
		Mat sub = image.submat(rect); // Mat sub = new Mat(image,rect);
		Mat mat = new Mat();
		Size size = new Size(width, height);
		Imgproc.resize(sub, mat, size);
		Imgcodecs.imwrite(outFile, mat);
		System.out.println(String.format("鍥剧墖瑁佸垏鎴愬姛锛岃鍒囧悗鍥剧墖鏂囦欢涓猴細 %s", outFile));

	}

	/**
	 * 灰化人脸
	 * 
	 * @param img
	 * @return
	 */
	public static Mat conv_Mat(String img) {
		Mat image0 = Imgcodecs.imread(img);
		Mat image1 = new Mat();
		Imgproc.cvtColor(image0, image1, Imgproc.COLOR_BGR2GRAY);
		MatOfRect faceDetections = new MatOfRect();
		faceDetector.detectMultiScale(image1, faceDetections);
		for (Rect rect : faceDetections.toArray()) {
			Mat face = new Mat(image1, rect);
			return face;
		}
		return null;
	}

	/**
	 * 人脸比对
	 * 
	 * @param img_1
	 * @param img_2
	 * @return
	 */
	public static double compare_image(String img_1, String img_2) {
		Mat mat_1 = conv_Mat(img_1);
		Mat mat_2 = conv_Mat(img_2);
		Mat hist_1 = new Mat();
		Mat hist_2 = new Mat();
		MatOfFloat ranges = new MatOfFloat(0f, 256f);

		MatOfInt histSize = new MatOfInt(1000);

		Imgproc.calcHist(Arrays.asList(mat_1), new MatOfInt(0), new Mat(), hist_1, histSize, ranges);
		Imgproc.calcHist(Arrays.asList(mat_2), new MatOfInt(0), new Mat(), hist_2, histSize, ranges);

		double res = Imgproc.compareHist(hist_1, hist_2, Imgproc.CV_COMP_CORREL);
		return res;
	}

	public static double compare_image(Mat mat_1, Mat mat_2) {
		Mat hist_1 = new Mat();
		Mat hist_2 = new Mat();
		MatOfFloat ranges = new MatOfFloat(0f, 256f);
		MatOfInt histSize = new MatOfInt(1000);
		Imgproc.calcHist(Arrays.asList(mat_1), new MatOfInt(0), new Mat(), hist_1, histSize, ranges);
		Imgproc.calcHist(Arrays.asList(mat_2), new MatOfInt(0), new Mat(), hist_2, histSize, ranges);
		double res = Imgproc.compareHist(hist_1, hist_2, Imgproc.CV_COMP_CORREL);
		return res;
	}

	/**
	 * 灰化人脸Mat
	 * 
	 * @param img
	 * @return
	 */
	public static Mat conv_Mat(Mat img) {
		Mat image1 = new Mat();

		Imgproc.cvtColor(img, image1, Imgproc.COLOR_BGR2GRAY);

		MatOfRect faceDetections = new MatOfRect();
		faceDetector.detectMultiScale(image1, faceDetections);

		for (Rect rect : faceDetections.toArray()) {
			Mat face = new Mat(image1, rect);
			return face;
		}
		return null;
	}

}
