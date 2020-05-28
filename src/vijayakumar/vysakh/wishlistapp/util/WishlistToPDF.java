package vijayakumar.vysakh.wishlistapp.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
//import java.io.OutputStream;
import java.util.List;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Phrase;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

import vijayakumar.vysakh.wishlistapp.data.Constants;
import vijayakumar.vysakh.wishlistapp.data.WishListBean;
import vijayakumar.vysakh.wishlistapp.services.WishListModel;

public class WishlistToPDF {

	public static boolean generatePDF() {
		File file = new File(Constants.PATH);
		File[] f = file.listFiles();
		CreateDirctry.createPDFFolder();
		WishListModel model = new WishListModel();
		com.itextpdf.text.Document document = new com.itextpdf.text.Document();
		Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);
		PdfWriter writer = null;
				
		try {
			 PdfWriter.getInstance(document, new FileOutputStream("pdf/MovieWishlistsPdf.pdf"));
			 document.open();
			 for(File fi : f) {
					if(fi.exists() && fi.isFile() && fi.getName().endsWith(".txt")) {
					 
						PdfPTable table = new PdfPTable(new float[] { 2, 2 });
						table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
						table.addCell(new Phrase("WISHLIST NAME :", boldFont));
						table.addCell(fi.getName().replaceAll(".txt", "").toUpperCase());
						table.addCell(new Phrase("MOVIE NAME", boldFont));
						table.addCell(new Phrase("DETAILS", boldFont));
						table.setHeaderRows(1);
						
						List<WishListBean> wishlistData = model.listMovies(fi.getName().replaceAll(".txt", ""));
						for(WishListBean data : wishlistData) {
							
							table.addCell(data.getMovieName());
							
							com.itextpdf.text.List list =  new com.itextpdf.text.List();
							list.add(new ListItem("Director Name: "+data.getDirctrName()));
							list.add(new ListItem("Producer Name: "+data.getProdcrName())); 
							list.add(new ListItem("Rating: "+data.getRating()));
							list.add(new ListItem("Review: "+data.getReview()));
							
							PdfPCell cell = new PdfPCell();
					        cell.addElement(list);
					        
							table.addCell(cell);				
							
						}
						document.add(table);
						
					}
					document.newPage();
			 }
			 document.close();
			 return true;
			 
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
			return false;
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
}
