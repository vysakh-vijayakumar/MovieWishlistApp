package vijayakumar.vysakh.wishlistapp.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import vijayakumar.vysakh.wishlistapp.data.Constants;
import vijayakumar.vysakh.wishlistapp.data.WishListBean;
import vijayakumar.vysakh.wishlistapp.services.WishListModel;  

public class WishlistsToExcel {

	public static boolean generateExcel() {
		
		File file = new File(Constants.PATH);
		File[] f = file.listFiles();
		HSSFWorkbook wb = new HSSFWorkbook();
		OutputStream fileOut = null;
		WishListModel model = new WishListModel();
		CreateDirctry.createExcelFolder();
		try {
			fileOut = new FileOutputStream("excel/MovieWishlists.xls");
			
			for(File fi : f) {
				if(fi.exists() && fi.isFile() && fi.getName().endsWith(".txt")) {
					HSSFSheet sheet1 = wb.createSheet(fi.getName().replaceAll(".txt", "")); 
					HSSFRow header = sheet1.createRow(0);	
					header.createCell(0).setCellValue("Movie Name");
					header.createCell(1).setCellValue("Director Name");
					header.createCell(2).setCellValue("Producer Name");
					header.createCell(3).setCellValue("Rating");
					header.createCell(4).setCellValue("Review");
					
					List<WishListBean> wishlistData = model.listMovies(fi.getName().replaceAll(".txt", ""));
					int index = 1;
					
					for(WishListBean bean : wishlistData) {
						HSSFRow row = sheet1.createRow(index);
						row.createCell(0).setCellValue(bean.getMovieName());
						row.createCell(1).setCellValue(bean.getDirctrName());
						row.createCell(2).setCellValue(bean.getProdcrName());
						row.createCell(3).setCellValue(bean.getRating());
						row.createCell(4).setCellValue(bean.getReview());
						index++;
					}
				}

			}
			wb.write(fileOut);
			return true;
		}
		catch(Exception e) {
			Logger.getInstance().log("WishlistToExcle -> generateExcel() Exception happen.."+e.getMessage());
			return false;
		}
		finally {
			try {
				wb.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if(fileOut != null)
				try {
					fileOut.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
}
