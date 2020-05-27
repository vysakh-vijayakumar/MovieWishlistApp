package vijayakumar.vysakh.wishlistapp.util;

import java.io.File;

import vijayakumar.vysakh.wishlistapp.data.Constants;

public class CreateDirctry {
	public static void create() {
		//Logger.getInstance().log("CreateDirctry -> create()");
		new File(Constants.PATH+"/log").mkdirs();
	}
	
	public static void createExcelFolder() {
		new File(Constants.PATH+"/excel").mkdir();
	}
	
	public static void createPDFFolder() {
		new File(Constants.PATH+"/pdf").mkdir();
	}
}

	