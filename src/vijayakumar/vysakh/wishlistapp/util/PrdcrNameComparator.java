package vijayakumar.vysakh.wishlistapp.util;

import java.util.Comparator;

import vijayakumar.vysakh.wishlistapp.data.WishListBean;

public class PrdcrNameComparator implements Comparator<WishListBean> {

	@Override
	public int compare(WishListBean arg0, WishListBean arg1) {

		return arg0.getProdcrName().compareTo(arg0.getProdcrName());
	}
	

}
