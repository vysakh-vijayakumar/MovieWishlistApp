package vijayakumar.vysakh.wishlistapp.util;


public class WishListUtil {
	public static boolean validateName(String name) {		// Wishlist name validation..
		Logger.getInstance().log("WishLisyUtil -> validateName() name ="+name);
		if(name == null)
			return false;
		if(name.trim().contentEquals(""))
			return false;
		if(!Character.isLetter(name.charAt(0)))
			return false;
		if(name.split(" ").length > 1)
			return false;
			
		for(int i = 1; i < name.length(); i++)	{
			char cr = name.charAt(i);
			if(!(Character.isLetter(cr) || Character.isDigit(cr)))
				return false;
		}
		return true;				
	}
	
	public static boolean validateMovieNames(String name) {		// Movie name, producer name, directr name, review validation..
		Logger l = Logger.getInstance();
		l.log("WishLisyUtil -> validateMovieNames() name = "+name);
		if(name == null)
			return false;
		if(name.trim().contentEquals(""))
			return false;
		if(!(Character.isLetter(name.charAt(0)) || Character.isDigit(name.charAt(0))))
			return false;	
		for(int i = 1; i < name.length(); i++)	{
			char cr = name.charAt(i);
			if(!(Character.isLetter(cr) || Character.isDigit(cr) || Character.isWhitespace(cr) || String.valueOf(cr).equals(String.valueOf('.'))))
				return false;
		}
		return true;		
	}
	
	public static boolean validateRating(int rating) {
		Logger.getInstance().log("WishLisyUtil -> validateRating() rating = "+rating);
		if(rating > 0 && rating <= 100)
			return true;
		else
			return false;
	}
	
}
