package vijayakumar.vysakh.wishlistapp.util;

import java.util.Comparator;

import vijayakumar.vysakh.wishlistapp.data.WishListBean;

public class DirNameComparator implements Comparator<WishListBean> {
	
	@Override
	public int compare(WishListBean arg0, WishListBean arg1) {
		
		return arg0.getDirctrName().compareTo(arg1.getDirctrName());
	}

}
