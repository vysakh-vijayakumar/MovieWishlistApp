package vijayakumar.vysakh.wishlistapp.data;
// java bean
public class WishListBean implements Comparable<WishListBean>{
	private String movieName;
	private String dirctrName;
	private String prodcrName;
	private String review;
	private int rating;
	
	public WishListBean() {				// no-arg constructor
		// TODO Auto-generated constructor stub
	}
	
	public WishListBean(String movieName, String dirctrName, String prodcrName, String review, int rating) {	// paramtrzd constructor..
		super();
		this.movieName = movieName;
		this.dirctrName = dirctrName;
		this.prodcrName = prodcrName;
		this.review = review;
		this.rating = rating;
	}
	// getters and setters

	public String getMovieName() {
		return movieName;
	}
	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}
	public String getDirctrName() {
		return dirctrName;
	}
	public void setDirctrName(String dirctrName) {
		this.dirctrName = dirctrName;
	}
	public String getProdcrName() {
		return prodcrName;
	}
	public void setProdcrName(String prodcrName) {
		this.prodcrName = prodcrName;
	}
	public String getReview() {
		return review;
	}
	public void setReview(String review) {
		this.review = review;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if(obj instanceof WishListBean) {
			WishListBean w = (WishListBean)obj;
			if(this.movieName.equals(w.movieName) && this.dirctrName.equals(w.dirctrName) && this.prodcrName.equals(w.prodcrName) && 
					this.review.equals(w.review) && this.rating == w.rating)
				return true;
			else
				return false;
		}
		else
			return false;
	}
	@Override
	public int hashCode() {
		
		return (movieName+dirctrName+prodcrName+review+rating).hashCode();
	}
	@Override
	public String toString() {
		return "WishListBean [movieName=" + movieName + ", dirctrName=" + dirctrName + ", prodcrName=" + prodcrName
				+ ", review=" + review + ", rating=" + rating + "]";
	}

	@Override
	public int compareTo(WishListBean arg0) {
		return this.movieName.compareTo(arg0.movieName);
	}
	
}
