package vijayakumar.vysakh.wishlistapp.ui;

import java.util.List;

import vijayakumar.vysakh.wishlistapp.data.WishListBean;
import vijayakumar.vysakh.wishlistapp.util.Logger;

public class ListSorted {

		public static void showList(List<WishListBean> list) {
			Logger.getInstance().log("ListSorted -> showList() list = "+list);
			for (WishListBean movieBean : list) {
				System.out.println("_____________________________________________________________________________________\n");
				System.out.println("Movie Name : "+movieBean.getMovieName()+" | Director Name : "+movieBean.getDirctrName()+
						" | Producer Name : "+movieBean.getProdcrName()+" | Rating : "+movieBean.getRating());
				System.out.println("Review : "+movieBean.getReview());
				System.out.println("_____________________________________________________________________________________");
			}
		}
		
		public static void listSearchResult() {
			
		}
}
