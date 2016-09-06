package pers.flower.utils.pdf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * 
 * @Description   : pdf工具类
 * @Author        : flower_ho@126.com 
 * @Creation Date : 2014年12月15日 下午8:00:58
 */
@SuppressWarnings("all")
public class PdfUtil {
		
	/**
	 * 
	 *  @Description    : 返回模板文件中要求的 filed name
	 *  @Method_Name    : getContractTemplateFileFieldNames
	 *  @param pdfFile
	 *  @return
	 *  @return         : Set<String>
	 *  @Creation Date  : 2014年12月16日 下午2:05:10 
	 *  @Author         : flower_ho@126.com
	 */
	public static Set<String> getContractTemplateFileFieldNames(File pdfFile) {
		return getContractTemplateFileFieldNames(pdfFile.getAbsolutePath());
	}
	
	/**
	 * 
	 *  @Description    : 返回模板文件中要求的 filed name
	 *  @Method_Name    : getContractTemplateFileFieldNames
	 *  @param pdfFileName
	 *  @return
	 *  @return         : Set<String>
	 *  @Creation Date  : 2014年12月16日 下午2:10:36 
	 *  @Author         : flower_ho@126.com
	 */
	public static Set<String> getContractTemplateFileFieldNames(String pdfFileName) {
		Set<String> fieldNames = new TreeSet<String>();
		PdfReader reader = null;
		try {
			reader = new PdfReader(pdfFileName);
			Set<String> keys = reader.getAcroFields().getFields().keySet();
			for(String key: keys){
				int lastIndexOf = key.lastIndexOf(".");
				int lastIndexOf2 = key.lastIndexOf("[");
				fieldNames.add(key.substring(lastIndexOf != -1 ? lastIndexOf + 1 : 0, lastIndexOf2 != -1 ? lastIndexOf2 : key.length()));
			}
		} catch (IOException e) {
			e.getMessage();
		} finally {
			if(reader != null){
				reader.close();
			}
		}
		
		return fieldNames;
	}
	
	/**
	 * 
	 *  @Description    : 用模板和模板中field和field value 组成的Map对象 创建合同文件
	 *  @Method_Name    : createContractFile
	 *  @param fieldValueMap fieldName 和 fieldValue 的Map
	 *  @param file 合同模板文件名(全路径名)
	 *  @param contractFileName 所要创建的合同文件名(全路径名)
	 *  @return         : void
	 *  @Creation Date  : 2014年12月17日 下午2:51:56 
	 *  @Author         : flower_ho@126.com
	 */
	public static void createContractFile(Map<String, String> fieldValueMap, byte[] file, String contractFileName) {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(contractFileName);
			makePdf2OutputStream(fieldValueMap, file, fos);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			e.getMessage();
		}
	}
	
	/**
	 * 
	 *  @Description    : 用模板和模板中field和field value 组成的Map对象 创建合同文件
	 *  @Method_Name    : createContractFile
	 *  @param fieldValueMap fieldName 和 fieldValue 的Map
	 *  @param file 合同模板文件名(全路径名)
	 *  @param contractFileName 所要创建的合同文件名(全路径名)
	 *  @return         : void
	 *  @Creation Date  : 2014年12月17日 下午2:55:05 
	 *  @Author         : flower_ho@126.com
	 */
	private static void makePdf2OutputStream(Map<String, String> fieldValueMap,byte[] file, OutputStream os) {
		PdfReader reader = null;
		PdfStamper stamper = null;
		BaseFont base = null;
		
		try {
			reader = new PdfReader(file);
			stamper = new PdfStamper(reader, os);
			
			stamper.setFormFlattening(true);
			base = BaseFont.createFont( "STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED );
			AcroFields acroFields = stamper.getAcroFields();
			for(String key : acroFields.getFields().keySet()){
				acroFields.setFieldProperty( key, "textfont", base, null );
				acroFields.setFieldProperty( key, "textsize", new Float(9), null );
			}
				
			if(fieldValueMap != null){
				for(String fieldName: fieldValueMap.keySet()){
					acroFields.setField(fieldName, fieldValueMap.get(fieldName));
				}
			}
		} catch (FileNotFoundException e) {
			e.getMessage();
		} catch (DocumentException e) {
			e.getMessage();
		} catch (IOException e) {
			e.getMessage();
		} finally {
			if(stamper != null){
				try {
					stamper.close();
				} catch (DocumentException e) {
					e.getMessage();
				} catch (IOException e) {
					e.getMessage();
				}
			}
			
			if(reader != null){
				reader.close();
			}
		}
	}
	
	/**
	 * 
	 *  @Description    : 读取文件数组
	 *  @Method_Name    : fileBuff
	 *  @param filePath
	 *  @return
	 *  @throws IOException
	 *  @return         : byte[]
	 *  @Creation Date  : 2015年1月27日 下午5:26:49 
	 *  @Author         : flower_ho@126.com
	 */
    public static byte[] fileBuff(String filePath) throws IOException {  
        File file = new File(filePath);  
        long fileSize = file.length();  
        if (fileSize > Integer.MAX_VALUE) {  
            //System.out.println("file too big...");  
            return null;  
        }  
        FileInputStream fi = new FileInputStream(file);  
        byte[] file_buff = new byte[(int) fileSize];  
        int offset = 0;  
        int numRead = 0;  
        while (offset < file_buff.length && (numRead = fi.read(file_buff, offset, file_buff.length - offset)) >= 0) {  
            offset += numRead;  
        }  
        // 确保所有数据均被读取  
        if (offset != file_buff.length) {  
        throw new IOException("Could not completely read file "  
                    + file.getName());  
        }  
        fi.close();  
        return file_buff;  
    }
    
    /**
     * 
     *  @Description    : 合并pdf
     *  @Method_Name    : mergePdfFiles
     *  @param files
     *  @param savepath
     *  @return         : void
     *  @Creation Date  : 2015年3月26日 上午11:08:11 
     *  @Author         : flower_ho@126.com
     */
    public static void mergePdfFiles(String[] files, String savepath)  
    {  
    	Document document = null;
        try{  
            document = new Document(new PdfReader(files[0]).getPageSize(1));  
            PdfCopy copy = new PdfCopy(document, new FileOutputStream(savepath));  
            document.open();  
            for(int i=0; i<files.length; i++)  
            {  
                PdfReader reader = new PdfReader(files[i]);  
                int n = reader.getNumberOfPages();  
                for(int j=1; j<=n; j++)  
                {  
                    document.newPage();   
                    PdfImportedPage page = copy.getImportedPage(reader, j);  
                    copy.addPage(page);  
                }  
            }  
            document.close();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } catch(DocumentException e) {  
            e.printStackTrace();  
        }finally{  
            //关闭PDF文档流，OutputStream文件输出流也将在PDF文档流关闭方法内部关闭  
            if(document!=null){  
            	document.close();  
            }  
        }   
    }
}
