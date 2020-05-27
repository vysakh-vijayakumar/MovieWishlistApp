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
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

import vijayakumar.vysakh.wishlistapp.data.Constants;
import vijayakumar.vysakh.wishlistapp.data.WishListBean;
import vijayakumar.vysakh.wishlistapp.services.WishListModel;

public class WishlistToPDF {

	public static boolean generatePDF() {
		File file = new File(Constants.PATH);
		File[] f = file.listFiles();
//		OutputStream fileOut = null; 
		CreateDirctry.createPDFFolder();
		WishListModel model = new WishListModel();
		com.itextpdf.text.Document document = new com.itextpdf.text.Document();
		PdfWriter writer = null;
				
		try {
			 PdfWriter.getInstance(document, new FileOutputStream("pdf/MovieWishlistsPdf.pdf"));
//			writer = new PdfWriter("pdf/MovieWishlistsPdf.pdf"); 
//			PdfDocument pdf = new PdfDocument(writer); 
			 document.open();
			 for(File fi : f) {
					if(fi.exists() && fi.isFile() && fi.getName().endsWith(".txt")) {
						Chunk underline = new Chunk(fi.getName().replaceAll(".txt", ""));
						underline.setUnderline(0.1f, -2f); //0.1 thick, -2 y-location
						document.add(underline);
//						Paragraph paragraph = new Paragraph(fi.getName().replaceAll(".txt", ""));
//						document.add(paragraph);
//						float [] pointColumnWidths = {300F, 300F};       
//						Table table = new Table(pointColumnWidths);   
						PdfPTable table = new PdfPTable(new float[] { 2, 1, 2 });
						table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
						table.addCell("Movie Name");
						table.addCell("Details");
						table.setHeaderRows(1);
						PdfPCell[] cells = table.getRow(0).getCells();
						for (int j = 0; j < cells.length; j++) {
							cells[j].setBackgroundColor(BaseColor.GRAY);
						}
						List<WishListBean> wishlistData = model.listMovies(fi.getName().replaceAll(".txt", ""));
						for(WishListBean data : wishlistData) {
							
//							Paragraph para = new Paragraph(data.getMovieName());
//							Cell c1 = new Cell();
//							c1.add(para);
//							c1.setTextAlignment(TextAlignment.LEFT);
//							table.addCell(c1);
//							table.addCell(c1);
							table.addCell(data.getMovieName());
							
/*							com.itextpdf.text.List list =  new com.itextpdf.text.List();
							list.add(new ListItem("Director Name: "+data.getDirctrName()));
							list.add(new ListItem("Producer Name: "+data.getProdcrName())); 
							list.add(new ListItem("Rating: "+data.getRating()));
							list.add(new ListItem("Review: "+data.getReview()));
							
//							Cell c2 = new Cell();
//							c2.add(list);
//							c2.setTextAlignment(TextAlignment.LEFT); 
							PdfPCell cell = new PdfPCell();
					        cell.addElement(list);
					        
							table.addCell(cell);				*/
							
						}
						document.add(table);
						
					}
//					document.add(new AreaBreak());
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
