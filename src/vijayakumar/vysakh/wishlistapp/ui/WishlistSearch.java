package vijayakumar.vysakh.wishlistapp.ui;

import java.io.File;
import java.util.Set;

import vijayakumar.vysakh.wishlistapp.data.Constants;
import vijayakumar.vysakh.wishlistapp.data.WishListBean;
import vijayakumar.vysakh.wishlistapp.services.WishListModel;
import vijayakumar.vysakh.wishlistapp.util.Logger;

public class WishlistSearch {
	
	public static void getOccurences(String searchWord) {
		Logger.getInstance().log("WishlistSearch -> getOccurences() searchWord = "+searchWord);
		File f = new File(Constants.PATH);
		File[] file = f.listFiles();
		WishListModel model = new WishListModel();
		for (File fi : file) {
			if(fi.exists() && fi.isFile() && fi.getName().endsWith(".txt")) {
				Set<WishListBean> set = model.uniqueSearch(fi.getName().replaceAll(".txt", ""), searchWord);
				if(set.size() > 0) {
					System.out.println("====================================================================================\n");
					System.out.println("# Wishlist Name : "+fi.getName().replaceAll(".txt", ""));
					System.out.println("# Number of occurences : "+set.size());
					System.out.println("# Results found : ");
					
					for (WishListBean movieBean : set) {
						System.out.println("_____________________________________________________________________________________\n");
						System.out.println("Movie Name : "+movieBean.getMovieName()+" | Director Name : "+movieBean.getDirctrName()+
								" | Producer Name : "+movieBean.getProdcrName()+" | Rating : "+movieBean.getRating());
						System.out.println("Review : "+movieBean.getReview());
						System.out.println("_____________________________________________________________________________________");
					}
					System.out.println("====================================================================================");
				}
			}
		}
	}
}
