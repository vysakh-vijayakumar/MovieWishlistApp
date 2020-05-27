package vijayakumar.vysakh.wishlistapp.ui;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import vijayakumar.vysakh.wishlistapp.data.Constants;
import vijayakumar.vysakh.wishlistapp.data.WishListBean;
import vijayakumar.vysakh.wishlistapp.services.WishListModel;
import vijayakumar.vysakh.wishlistapp.util.Logger;
import vijayakumar.vysakh.wishlistapp.util.WishListUtil;



public class MoviesMenu {

public static void getMenu(String wishName) {

		int ch2 = 0, ch3 = 0,ch4 = 0,ch5 = 0;
		String movieName,dirctrName,prodcrName,review,movieKey;
		int rating;
		Logger l = Logger.getInstance();
		l.log("MoviesMenu -> getMenu() ");
		Scanner s1 = new Scanner(System.in);
		Scanner s2 = new Scanner(System.in);
		WishListModel model = new WishListModel();
////////		MOVIE MENU		
		while(ch2 != 6) {
			System.out.println("******MOVIE MENU******");													// Movie Menu
			System.out.println("Wishlist Name : "+wishName);
			System.out.println("# Press 1 : Add a Movie");
			System.out.println("# Press 2 : Edit a Movie");
			System.out.println("# Press 3 : Remove a Movie");
			System.out.println("# Press 4 : List the Movies");
			System.out.println("# Press 5 : Search Movies");
			System.out.println("# Press 6 : Go Back");
			
			//ch2 = s1.nextInt();
			do {
				System.out.println("\nEnter your choice : ");
			    while (!s1.hasNextInt()) {
			    	l.log("MoviesMenu -> getMenu() ch2 validation failed..");
			        System.out.println("Invalid Choice..! Enter a number from 1 to 6..");
			        s1.next(); // this is important!
			    }
			    ch2 = s1.nextInt();
			} while (ch2 <= 0);
			l.log("MoviesMenu -> getMenu() ch2 = "+ch2);
			
			switch (ch2) {
//			ADD A MOVIE
			case 1:																							// Movie Menu -> Case 1
				l.log("MoviesMenu -> getMenu() inside case 1");
				System.out.println("Enter Movie Name : ");
				movieName = s2.nextLine();
				// Movie name validation...
				while(!WishListUtil.validateMovieNames(movieName)) {	
					l.log("MoviesMenu -> getMenu() movie name validation failed");
					System.out.println("/nMovie name must have only Letters and Digits (Eg: Dil Wale)");
					System.out.println("Enter a new movie name : ");
					movieName = s2.nextLine();
				}
				// Business validation
				String result1 = model.movieNameExist(movieName, wishName);
				while(result1.equals(Constants.SUCCESS)) {
					System.out.println("\nMovie Name Already Exists..!! Try Another Movie Name");
					System.out.println("Enter a new movie name : ");
					movieName = s2.nextLine();
					result1 = model.movieNameExist(movieName, wishName);
				}
				
				System.out.println("Enter Director Name : ");
				dirctrName = s2.nextLine();
				// director name validation
				while(!WishListUtil.validateMovieNames(dirctrName)) {		
					System.out.println("/Director name must have only Letters and Digits ");
					System.out.println("Enter a new director name : ");
					dirctrName = s2.nextLine();
				}
	
				System.out.println("Enter Producer Name : ");
				prodcrName = s2.nextLine();
				//	producer name validation
				while(!WishListUtil.validateMovieNames(prodcrName)) {		
					System.out.println("/nProducer name must have only Letters and Digits ");
					System.out.println("Enter a new producer name : ");
					prodcrName = s2.nextLine();
				}
			//	System.out.println("Enter Your Rating (enter value 1-100 : 1-Low 50-Avg 100-High) : ");
				do {
					System.out.println("Enter Your Rating (enter value 1-100 : 1-Low 50-Avg 100-High) : ");
				    while (!s1.hasNextInt()) {
				        System.out.println("Invalid rating..! Enter a number from 1 - 100");
				        s1.next(); 
				    }
				    rating = s1.nextInt();
				} while (rating <= 0);
				
				// rating validation
				while(!WishListUtil.validateRating(rating)) {
					System.out.println("The rating must be between number 1 to 100");
					do {
						System.out.println("Enter your rating : ");
					    while (!s1.hasNextInt()) {
					        System.out.println("Invalid rating..! Enter a number from 1 - 100");
					        s1.next(); 
					    }
					    rating = s1.nextInt();
					} while (rating <= 0);
				}
				
				System.out.println("Enter Your Review : ");
				review = s2.nextLine();
				//	review validation
				while(!WishListUtil.validateMovieNames(review)) {		
					System.out.println("/nReview must have only Letters,Digits and full stop");
					System.out.println("Enter your review : ");
					review = s2.nextLine();
				}
				
			    //	writing data to file..
				WishListBean bean = new WishListBean(movieName, dirctrName, prodcrName, review, rating);
				String result2 = model.addMovie(bean, wishName);
				if(result2.equals(Constants.SUCCESS)) {
					System.out.println("The Movie Added Sucessfully...!");
				}
				else {
					System.out.println("Process failed..!! \n"+result2);
				}
				break;
//			MOVIE EDIT MENU
			case 2:																						//	Movie Menu -> Case 2
				while(ch3 != 3) {

					System.out.println("******EDIT MOVIE INFO******");
					System.out.println("List Of Movies : ");
					List<WishListBean> movieList = model.listMovies(wishName);
					for (WishListBean movieBean : movieList) {
						System.out.println("Movie Name : "+movieBean.getMovieName()+" | Director Name : "+movieBean.getDirctrName()+
								" | Producer Name : "+movieBean.getProdcrName()+" | Rating : "+movieBean.getRating());
						System.out.println("Review : "+movieBean.getReview());
						System.out.println("_____________________________________________________________________________________");
					}
					
					System.out.println("#Press 1 : To remove a movie info ");
					System.out.println("#Press 2 : To update a movie info ");
					System.out.println("#Press 3 : Go Back");
					//System.out.println("Enter your choice : ");
					// choice validation..
					do {											
						System.out.println("\nEnter your choice : ");
					    while (!s1.hasNextInt()) {
					        System.out.println("Invalid Choice..! Enter a number from 1 to 3..");
					        s1.next(); // this is important!
					    }
					    ch3 = s1.nextInt();
					} while (ch3 <= 0);
					
					switch(ch3) {
					case 1:
							System.out.println("Enter the movie name to remove : ");
							movieName = s2.nextLine();
							String result4 = model.deleteMovie(movieName, wishName);
							if(result4.equals(Constants.SUCCESS)) {
								System.out.println("The Movie Removed Sucessfully...!");
							}
							else {
								System.out.println("Process failed..!! \n"+result4);
							}
						break;
					case 2:
							while(ch4 != 6) {
								System.out.println("Enter the name of movie to update : ");
								movieKey = s2.nextLine();
								// movie name validation...
								while(!WishListUtil.validateMovieNames(movieKey)) {		
									System.out.println("\nMovie name doesn't exist");
									System.out.println("Enter a new movie name : ");
									movieKey = s2.nextLine();
								}
								// business validation
								String result3 = model.movieNameExist(movieKey, wishName);
								while(!(result3.equals(Constants.SUCCESS))) {
									System.out.println("\nMovie Name doesn't exists..!! Enter a valid name..!");
									System.out.println("Enter movie name : ");
									movieKey = s2.nextLine();
									result3 = model.movieNameExist(movieKey, wishName);
								}
								
								
								System.out.println("which info you want to update ? ");
								
								System.out.println("# Press 1 : Movie Name");
								System.out.println("# Press 2 : Director Name");
								System.out.println("# Press 3 : Producer Name");
								System.out.println("# Press 4 : Rating");
								System.out.println("# Press 5 : Review");
								System.out.println("# Press 6 : Go Back");
								System.out.println("Enter your choice : ");
								// choice validation
								do {
									System.out.println("\nEnter your choice : ");
								    while (!s1.hasNextInt()) {
								        System.out.println("Invalid Choice..! Enter a number from 1 to 6..");
								        s1.next(); 
								    }
								    ch4 = s1.nextInt();
								} while (ch4 <= 0);
								
								switch(ch4) {
								case 1:
									System.out.println("Enter new movie name : ");
									movieName = s2.nextLine();
									// movie name validation...
									while(!WishListUtil.validateMovieNames(movieName)) {		
										System.out.println("/nMovie name must have only Letters and Digits (Eg: Dil Wale)");
										System.out.println("Enter a new movie name : ");
										movieName = s2.nextLine();
									}
									// updating movie name..
									model.updatingInfo(wishName, movieKey, Constants.MNAME, movieName);
									ch4 = 6;
									break;
								case 2:
									System.out.println("Enter new director name : ");
									dirctrName = s2.nextLine();
									// director name validation
									while(!WishListUtil.validateMovieNames(dirctrName)) {		
										System.out.println("/Director name must have only Letters and Digits ");
										System.out.println("Enter a new director name : ");
										dirctrName = s2.nextLine();
									}
									//	updating director name..
									model.updatingInfo(wishName, movieKey, Constants.DIRECTOR, dirctrName);
									ch4 = 6;
									break;
								case 3:
									System.out.println("Enter new producer name : ");
									prodcrName = s2.nextLine();
									//	producer name validation
									while(!WishListUtil.validateMovieNames(prodcrName)) {		
										System.out.println("/nProducer name must have only Letters and Digits ");
										System.out.println("Enter a new producer name : ");
										prodcrName = s2.nextLine();
									}
									//	updating producer name..
									model.updatingInfo(wishName, movieKey, Constants.PRODUCER, prodcrName);
									ch4 = 6;
									break;
								case 4:
									System.out.println("Enter new rating (enter value 1-100 : 1-Low 50-Avg 100-High) : ");
									rating = s1.nextInt();
									// rating validation
									while(!WishListUtil.validateRating(rating)) {
										System.out.println("/nThe rating must be between number 1 to 100");
										System.out.println("Enter a new rating : ");
										rating = s1.nextInt();
									}
									// updating rating..
									model.updatingInfo(wishName, movieKey, Constants.RATING, String.valueOf(rating));
									ch4 = 6;
									break;
								case 5:
									System.out.println("Enter new review : ");
									review = s2.nextLine();
									//	review validation
									while(!WishListUtil.validateMovieNames(review)) {		
										System.out.println("/nReview must have only Letters,Digits and full stop");
										System.out.println("Enter your review : ");
										review = s2.nextLine();
									}
									// updating review..
									model.updatingInfo(wishName, movieKey, Constants.REVIEW, review);
									ch4 = 6;
									break;
								}
							}
						break;
					case 3:																					
						System.out.println("Going back to previous menu..");
						break;
					}
					
			}
				break;
// 			REMOVE MOVIE
			case 3:																							//	Movie menu case -> 3
				System.out.println("Enter the movie name to remove : ");
				movieName = s2.nextLine();
				String result4 = model.deleteMovie(movieName, wishName);
				if(result4.equals(Constants.SUCCESS)) {
					System.out.println("The Movie deleted Sucessfully...!");
				}
				else {
					System.out.println("Process failed..!! \n"+result4);
				}
				break;
//			LIST MOVIE				
			case 4:																							//	Movie menu case -> 4
				while(ch5 != 6) {
					// Listing Menu...
					System.out.println("\n******LIST MENU******");
					System.out.println("#Press 1 : List Movies by Movies names ");
					System.out.println("#Press 2 : List Movies by Director names ");
					System.out.println("#Press 3 : List Movies by Producer names ");
					System.out.println("#Press 4 : List Movies by Ratings");
					System.out.println("#Press 5 : List Movies by Reviews");
					System.out.println("#Press 6 : Go Back");
					System.out.println("Enter your choice : ");
					// choice validation
					do {
						System.out.println("\nEnter your choice : ");
					    while (!s1.hasNextInt()) {
					        System.out.println("Invalid Choice..! Enter a number from 1 to 6..");
					        s1.next(); // this is important!
					    }
					    ch5 = s1.nextInt();
					} while (ch5 <= 0);
					
					switch(ch5) {
						case 1:
							ListSorted.showList(model.sortListMovieName(wishName));
							break;
							
						case 2:
							ListSorted.showList(model.sortListDirctrName(wishName));
							break;
						
						case 3:
							ListSorted.showList(model.sortListProdcrName(wishName));
							break;
							
						case 4:
							ListSorted.showList(model.sortListRating(wishName));
							break;
							
						case 5:
							ListSorted.showList(model.sortListReview(wishName));
							break;
						default:
							System.out.println("Invalid input..!! Give valid option..!!");
							break;	
					}
				}
				break;
//			SEARCH MOVIE				
			case 5:																							//	Movie menu case -> 5
				System.out.println("****** SEARCH MENU ******\n");
				System.out.println("Enter search word : \n");
				String searchWord = s2.nextLine();
				
				List<WishListBean> movieNameSerch = model.searchInMovieName(wishName, searchWord);
				List<WishListBean> dirctrNameSerch = model.searchInDirctrName(wishName, searchWord);
				List<WishListBean> prdcrNameSerch = model.searchInProdcrName(wishName, searchWord);
				List<WishListBean> reviewSerch = model.searchInReview(wishName, searchWord);
				List<WishListBean> ratingSerch = model.searchInRating(wishName, searchWord);
				Set<WishListBean> set = model.uniqueSearch(wishName, searchWord);
				
				System.out.println("#Total number of occurances : "+(movieNameSerch.size() + dirctrNameSerch.size() + prdcrNameSerch.size() 
					+ reviewSerch.size() + ratingSerch.size()));
				System.out.println("#Total matches found : ");
				for (WishListBean movieBean : set) {
					System.out.println("_____________________________________________________________________________________\n");
					System.out.println("Movie Name : "+movieBean.getMovieName()+" | Director Name : "+movieBean.getDirctrName()+
							" | Producer Name : "+movieBean.getProdcrName()+" | Rating : "+movieBean.getRating());
					System.out.println("Review : "+movieBean.getReview());
					System.out.println("_____________________________________________________________________________________");
				}
				
				System.out.println("#Number of occurances in movie name : "+movieNameSerch.size());
				System.out.println("#matches found : ");
				ListSorted.showList(movieNameSerch);
				System.out.println("#Number of occurences in director name : "+dirctrNameSerch.size());
				System.out.println("#matches found : ");
				ListSorted.showList(dirctrNameSerch);
				System.out.println("#Number of occurences in producer name : "+prdcrNameSerch.size());
				System.out.println("#matches found : ");
				ListSorted.showList(prdcrNameSerch);
				System.out.println("#Number of occurences in review : "+reviewSerch.size());
				System.out.println("#matches found : ");
				ListSorted.showList(reviewSerch);
				System.out.println("#Number of occurences in rating : "+ratingSerch.size());
				System.out.println("#matches found : ");
				ListSorted.showList(ratingSerch);
				
				break;
//			GO BACK				
			case 6:																							//  Movie menu case -> 6
				System.out.println("Going back to Main menu.. !!");
				break; 
			default:
				System.out.println("Invalid input..!! Give valid option..!!");
				break;
				
			}
		}
	
	
	
}
	
}
