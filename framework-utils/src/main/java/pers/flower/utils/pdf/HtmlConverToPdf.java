package pers.flower.utils.pdf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import javax.servlet.http.HttpServletRequest;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;
import com.lowagie.text.pdf.BaseFont;

/**
 * 
 * @Description   : Html转Pdf工具类
 * @Author        : flower_ho@126.com 
 * @Creation Date : 2014年12月15日 下午9:00:58
 */
@SuppressWarnings("all")
public class HtmlConverToPdf {
	
	/**
     * 
     *  @Description    : html转pdf
     *  @Method_Name    : HtmlConvertToPdf
     *  @param inputFile  输入html文件
     *  @param outputFile 输出pdf文件
     *  @return         : void
     *  @Creation Date  : 2015年8月20日 下午2:59:59 
     *  @Author         : flower_ho@126.com
     */
	public static void HtmlConvertToPdf(String inputFile,String outputFile ,String localTemplateFilePath){
		try {
	        String url = new File(inputFile).toURI().toURL().toString();
	        OutputStream os = new FileOutputStream(outputFile);     
	        ITextRenderer renderer = new ITextRenderer();     
	        renderer.setDocument(url);     
	        // 解决中文支持问题     
	        ITextFontResolver fontResolver = renderer.getFontResolver();
	        fontResolver.addFont(localTemplateFilePath + "template/fonts/simsun.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);     
	        // 解决图片的相对路径问题 
	        String imgPath = "file:"+ localTemplateFilePath +"template/images/";
	        renderer.getSharedContext().setBaseURL(imgPath);
	        
	        renderer.layout();     
	        renderer.createPDF(os);     
	             
	        os.close();  
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (com.lowagie.text.DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}     
	}
}
