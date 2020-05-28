// MOVIE WISHLIST APP UI....
package vijayakumar.vysakh.wishlistapp.ui;

import java.io.File;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ScheduledExecutorService;

import com.itextpdf.text.log.SysoLogger;

import vijayakumar.vysakh.wishlistapp.data.Constants;
import vijayakumar.vysakh.wishlistapp.services.WishListModel;
import vijayakumar.vysakh.wishlistapp.util.CreateDirctry;
import vijayakumar.vysakh.wishlistapp.util.EmailValidator;
import vijayakumar.vysakh.wishlistapp.util.Logger;
import vijayakumar.vysakh.wishlistapp.util.ScheduledMailExecutor;
import vijayakumar.vysakh.wishlistapp.util.WishListUtil;
import vijayakumar.vysakh.wishlistapp.util.WishlistMailerUtil;
import vijayakumar.vysakh.wishlistapp.util.WishlistToPDF;
import vijayakumar.vysakh.wishlistapp.util.WishlistsToExcel;

public class StartApp {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			System.out.println("jfgsdjfh");
			int ch1 = 0;
			String wishName,searchWord,recepientEmail;
			CreateDirctry.create();
			Logger logger = Logger.getInstance();
			logger.emptyLog();
			logger.SetLogtoConsole(false);
			logger.log("StartApp -> main() starting...");	
			
			ScheduledMailExecutor.executeMail(); 										// Executing scheduled 	mail executor..
			
			Scanner s1 = new Scanner(System.in);
			Scanner s2 = new Scanner(System.in);
			WishListModel model = new WishListModel();
			///	 MAIN MENU	
			while(ch1 != 8) {	
				System.out.println("****** MAIN MENU ******");
				System.out.println("# Press 1 : To Create MovieWishList");
				System.out.println("# Press 2 : To Load MovieWishList");
				System.out.println("# Press 3 : To Search");
				System.out.println("# Press 4 : To List");
				System.out.println("# Press 5 : To Generate Excel Sheet");
				System.out.println("# Press 6 : To Generate PDF");
				System.out.println("# Press 7 : To Send Wishlist to Mail");
				System.out.println("# Press 8 : To Exit");
				// choice validation
				do {
					System.out.println("\nEnter your choice : ");
				    while (!s1.hasNextInt()) {
				        System.out.println("Invalid choice..! Enter a number from 1 to 5..");
				        s1.next(); 
				    }
				    ch1 = s1.nextInt();
				} while (ch1 <= 0);
				logger.log("StartApp -> main() ch1 = "+ch1);
				
				switch(ch1) {
//				CREATING WISHLIST
				case 1: 																		//	Main menu case -> 1
					///		CREATING WISHLIST MENU
					System.out.println("******CREATING WISHLIST MENU******");
					System.out.println("Enter the Wishlist name : ");
					wishName = s2.nextLine();
					
					while(!WishListUtil.validateName(wishName)) {		// name validation..
						logger.log("StartApp -> main() wishName invalid..");
						System.out.println("Wishlist name must be single word, Starts with a letter and alphanumeric only (Eg: Abc2) ");
						System.out.println("Enter another Wishlist name : ");
						wishName = s2.nextLine();
					}
					// business validation...
					
					if(model.wishListExist(wishName)) {
						logger.log("StartApp -> main() wishname business validation failed..");
						System.out.println("The name already exist..!! ");
						System.out.println("Enter a new name : ");
					}
					else {
					///		MOVIE MENU 
						logger.log("StartApp -> main() wishname = "+wishName);
						MoviesMenu.getMenu(wishName);
						logger.log("StartApp -> main() calling MoviesMenu.getMenu(wishName) wishName = "+wishName);
					}
					break;
//				LOAD WISHLIST			
				case 2:																				// Main menu case -> 2
						System.out.println("******LOAD WISHLIST MENU******");
						File[] f = model.getWishlistNames(Constants.PATH);
						for (File file : f) {
							if(file.exists() && file.isFile() && file.getName().endsWith(".txt")) {
								System.out.println(file.getName().replace(".txt", ""));
							}
						}
						
						System.out.println("Enter the Wishlist name : ");
						wishName = s2.nextLine();
						while(!WishListUtil.validateName(wishName)) {		// name validation..
							logger.log("StartApp -> main() wishName validation failed..");
							System.out.println("Wishlist name must be single word, Starts with a letter and alphanumeric only (Eg: Abc2) ");
							System.out.println("Enter another Wishlist name : ");
							wishName = s2.nextLine();
						}
						// business validation...
						
						while(!model.wishListExist(wishName)) {
							logger.log("StartApp -> main() wishName business validation failed..");
							System.out.println("The wishlist doesn;t exist.. ");
							System.out.println("Enter a new name : ");
							wishName = s2.nextLine();
						}
						// calling movie menu..
						logger.log("StartApp -> main() calling MoviesMenu.getMenu(wishName) wishName = "+wishName);
						MoviesMenu.getMenu(wishName);
					break;
//				SEARCH MENU
				case 3:																				// Main menu case -> 3
					System.out.println("******SEARCH WISHLIST MENU******\n");
					System.out.println("Enter search word : \n");
					searchWord = s2.nextLine();
					logger.log("StrarApp -> main() in case 3 searchWord = "+searchWord);
					System.out.println("\t\t\tSearch Result In Each Wishlist : ");
					logger.log("StrarApp -> main() in case 3 calling WishlistSearch.getOccurences(searchWord)");
					WishlistSearch.getOccurences(searchWord);
					break;
//				LIST MENU
				case 4:																				//	Main menu case -> 4
					logger.log("StrarApp -> main() in case 4 ");
					System.out.println("******LIST WISHLIST MENU******");
					File[] f2 = model.getWishlistNames(Constants.PATH);
					for (File file : f2) {
						if(file.exists() && file.isFile() && file.getName().endsWith(".txt")) {
							System.out.println(file.getName().replace(".txt", ""));
						}
					}
					break;
//				EXCEL SHEET	
				case 5:
					if(WishlistsToExcel.generateExcel())
						System.out.println("Excel Sheet with wishlist data generated Successfully...!");
					else
						System.out.println("The Excel Sheet Generation failed..!");
					break;
//				PDF	
				case 6:																				// Main menu case -> 6
					if(WishlistToPDF.generatePDF())
						System.out.println("PDF file with wishlist data generated Successfully...?!");
					else
						System.out.println("The PDF file generation failed..!");
					break;
//				SEND MAIL	
				case 7:																				// Main menu case -> 7
					System.out.println("Enter email address :");
					recepientEmail = s2.nextLine();
					EmailValidator validate = new EmailValidator();					// email validation using regex
					while(!validate.validateEmail(recepientEmail)) {
						System.out.println("Invalid email address..!");
						System.out.println("Enter a valid email :");
						recepientEmail = s2.nextLine();
					}
					
					if(WishlistMailerUtil.sendMail(recepientEmail))
						System.out.println("Wishlist mailed to the recepient Successfully..!");
					else
						System.out.println("Wishlist mailing Failed..!");
					break;
//				GO BACK		
				case 8:																				//	Main menu case -> 8
					System.out.println("******GOOD BYE, COME BACK SOON******");
					break;
				default:
					System.out.println("Invalid input..!! Give valid option..!!");
					break;
	
				}
			}
			
		}
		catch(Throwable t) {
			Logger.getInstance().log("StrarApp -> main() exception happen "+t.getMessage());
			System.out.println("Oops..! Something went wrong.. Contact development team..");
			t.printStackTrace();
		}
	}

}
