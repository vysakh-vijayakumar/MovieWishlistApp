package vijayakumar.vysakh.wishlistapp.util;

public class WeeklyMailThread implements Runnable {				// for running wishlist mailer...using thread

	@Override
	public void run() {
		// calling WishlistMailerUtil --> sendMail
		String toMailID = "vysakh.v@hotmail.com";
		WishlistMailerUtil.sendMail(toMailID);
		
	}

}
