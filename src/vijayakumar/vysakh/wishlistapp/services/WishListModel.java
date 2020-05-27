package vijayakumar.vysakh.wishlistapp.services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import vijayakumar.vysakh.wishlistapp.data.Constants;
import vijayakumar.vysakh.wishlistapp.data.WishListBean;
import vijayakumar.vysakh.wishlistapp.util.DirNameComparator;
import vijayakumar.vysakh.wishlistapp.util.Logger;
import vijayakumar.vysakh.wishlistapp.util.PrdcrNameComparator;
import vijayakumar.vysakh.wishlistapp.util.RatingComparator;
import vijayakumar.vysakh.wishlistapp.util.ReviewComparator;

public class WishListModel {
	
	public boolean wishListExist(String wishName) {			// checking whether wish list name exist already..
		Logger.getInstance().log("WishListModel -> wishListExist() wishName = "+wishName);
		File f = new File(wishName+".txt");
		if(f.exists())
			return true;
		else
			return false;
	}
	public String addMovie(WishListBean bean, String wishName) {	// to write movie details into the file..
		Logger.getInstance().log("WishListModel -> addMovie() WishListBean = "+bean+" wishName = "+wishName);
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(wishName+".txt",true));
			bw.write(bean.getMovieName()+":"+bean.getDirctrName()+":"+bean.getProdcrName()+":"+bean.getRating()+":"+bean.getReview());
			bw.newLine();
			return Constants.SUCCESS;
		}
		catch(IOException e){
			Logger.getInstance().log("WishListModel -> addMovie() Exception occured "+e.getMessage()+"\n"+e.getStackTrace());
			return "Oops..Something went wrong..!!"+e.getMessage();	
		}
		finally {
			if(bw != null)
				try {
					bw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
	
	public String movieNameExist(String movieName, String wishlistName) {		//  To check the whether the movie name is unique..
		Logger.getInstance().log("WishListModel -> movieNameExist() movieName = "+movieName+" wishlistName = "+wishlistName);
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(wishlistName+".txt"));
			String line;
			
			while((line = br.readLine()) != null) {
				String[] movieLines = line.split(":");
				if(movieLines[0].equals(movieName))
					return Constants.SUCCESS;
			}
			return Constants.FAILURE;
			
		}
		catch(IOException e) {
			Logger.getInstance().log("WishListModel -> movieNameExist() Exception occured "+e.getMessage()+"\n"+e.getStackTrace());
			return "Oops..Something went wrong..!!"+e.getMessage();
		}
		finally {
			if(br != null)
				try {
					br.close();
				} catch (IOException e) {
					Logger.getInstance().log("WishListModel -> movieNameExist() Exception occured "+e.getMessage()+"\n"+e.getStackTrace());
				}
		}
		
	}
	
	public List<WishListBean> listMovies(String wishlistName){				// list the movie info from file
		Logger l = Logger.getInstance();
		l.log("WishListModel -> listMovies() wishlistName = "+wishlistName);
		BufferedReader br = null;
			try {
				File file = new File(wishlistName+".txt");
				List<WishListBean> movieList = new ArrayList<WishListBean>();
				if(file.length() != 0) {
					WishListBean bean;
					br = new BufferedReader(new FileReader(wishlistName+".txt"));
					String line;
					
					while((line = br.readLine()) != null) {
						String[] str = line.split(":");
						if(str.length != 0) {
							bean = new WishListBean(str[0],str[1],str[2],str[4],Integer.parseInt(str[3]));
							movieList.add(bean);
						}
					}
				}
				return movieList;
			}
			catch(IOException e){
				l.log("WishListModel -> listMovies() Exception occured "+e.getMessage()+"\n"+e.getStackTrace());
				return null;
			}
			catch (NumberFormatException e) {
				l.log("WishListModel -> listMovies() Exception occured "+e.getMessage()+"\n"+e.getStackTrace());
				return null;
			}
			finally {
				if(br != null) {
					try {
						br.close();
					} 
					catch (IOException e) {
						l.log("WishListModel -> listMovies() Exception occured "+e.getMessage()+"\n"+e.getStackTrace());
					}
				}
			}
			
	}
	
	public boolean updatingInfo(String listName, String movieName, int field, String newValue){ // updating movie info..
		
		Logger.getInstance().log("WishListModel -> updatingInfo( int field) listName ="+listName+" movieName = "+movieName+" field = "+field+" newValue = "+newValue);
		List<WishListBean> movies = listMovies(listName);
		
		for (WishListBean data : movies) {
				if(data.getMovieName().equals(movieName)) {
					if(field == Constants.MNAME)
						data.setMovieName(newValue);
					if(field == Constants.DIRECTOR)
						data.setDirctrName(newValue);
					if(field == Constants.PRODUCER)
						data.setProdcrName(newValue);
					if(field == Constants.RATING)
						data.setRating(Integer.parseInt(newValue));
					if(field == Constants.REVIEW)
						data.setReview(newValue);
				}	
		}
		overwriteFile(movies, listName);
		return true;
	}
	
	public void overwriteFile(List<WishListBean> list, String listname) {					//	overwrite file..
		
		Logger.getInstance().log("WishListModel -> overwriteFile() list =" +list+"listname = "+listname);
		File f = new File(listname+".txt");			//	delete old movielist file
		if(f.exists())
			f.delete();
			
		for (WishListBean data : list) {		// writing new file using each data beans from list..
			addMovie(data, listname);			// calling add movie method..
		}

	}
	
	public String deleteMovie(String movieName, String movieWishlistName) {									// delete  movie info...
		
			Logger.getInstance().log("WishListModel -> deleteMovie() movieName = "+movieName+" movieWishlistName = "+movieWishlistName);
			List<WishListBean> movieListCurrent = listMovies(movieWishlistName);// list all data in wishlist
			List<WishListBean> movieListUpdated = new ArrayList<WishListBean>();
			for (WishListBean bean : movieListCurrent) {
				if(!bean.getMovieName().equals(movieName))
					movieListUpdated.add(bean);				// add all movies that is not equal to movie name.
			}
			overwriteFile(movieListUpdated, movieWishlistName);		// over writing wishlist file with update list
			return Constants.SUCCESS;

	}
	
	public List<WishListBean> sortListMovieName(String wishlistName){		// sorting list based on Movie name..
		Logger.getInstance().log("WishListModel -> sortListMovieName() wishlistName = "+wishlistName);
		List<WishListBean> list = listMovies(wishlistName);
		Collections.sort(list);
		return list;
	}
	
	public List<WishListBean> sortListDirctrName(String wishlistName){		//	sorting list based on Director name..
		Logger.getInstance().log("WishListModel -> sortListDirctrName() wishlistName = "+wishlistName);
		List<WishListBean> list = listMovies(wishlistName);
		DirNameComparator directr = new DirNameComparator();
		Collections.sort(list, directr);
		return list;
	}
	
	public List<WishListBean> sortListProdcrName(String wishlistName){		// sorting list based on Producer name..
		Logger.getInstance().log("WishListModel -> sortListProdcrName() wishlistName = "+wishlistName);
		List<WishListBean> list = listMovies(wishlistName);
		PrdcrNameComparator prdcr = new PrdcrNameComparator();
		Collections.sort(list, prdcr);
		return list;
	}
	
	public List<WishListBean> sortListRating(String wishlistName){			// sorting list based on rating..
		Logger.getInstance().log("WishListModel -> sortListRating() wishlistName = "+wishlistName);
		List<WishListBean> list = listMovies(wishlistName);
		RatingComparator rating = new RatingComparator();
		Collections.sort(list, rating);
		return list;
	}
	
	public List<WishListBean> sortListReview(String wishlistName){			// sorting list based on review..
		Logger.getInstance().log("WishListModel -> sortListReview() wishlistName = "+wishlistName);
		List<WishListBean> list = listMovies(wishlistName);
		ReviewComparator rev = new ReviewComparator();
		Collections.sort(list, rev);
		return list;
	}
	
	public File[] getWishlistNames(String filesPath) {
		Logger.getInstance().log("WishListModel -> getWishlistNames() filesPath = "+filesPath);
		File f = new File(filesPath);
		File[] files = f.listFiles();
		return files;
	}
	
	// Searching...
	public List<WishListBean> searchInMovieName(String wishlistName, String srchString){		// search inside movie name..
		Logger.getInstance().log("WishListModel -> searchInMovieName() wishlistName = "+wishlistName+" srchString = " +srchString);
		List<WishListBean> srcList = listMovies(wishlistName);
		List<WishListBean> movieNameSrchResult = new ArrayList<WishListBean>();
		for (WishListBean data : srcList) {
			if(data.getMovieName().contains(srchString))
				movieNameSrchResult.add(data);
		}
		return movieNameSrchResult;	
	}
	
	public List<WishListBean> searchInDirctrName(String wishlistName, String srchString){		// search inside director name..
		Logger.getInstance().log("WishListModel ->searchInDirctrName() wishlistName  = "+wishlistName+" srchString = "+srchString);
		List<WishListBean> srcList = listMovies(wishlistName);
		List<WishListBean> dirctrNameSrchResult = new ArrayList<WishListBean>();
		for (WishListBean data : srcList) {
			if(data.getDirctrName().contains(srchString))
				dirctrNameSrchResult.add(data);
		}
		return dirctrNameSrchResult;	
	}
	
	public List<WishListBean> searchInProdcrName(String wishlistName, String srchString){		// search inside producer name..
		Logger.getInstance().log("WishListModel -> searchInProdcrName() wishlistName = "+wishlistName+" srchString = "+srchString);
		List<WishListBean> srcList = listMovies(wishlistName);
		List<WishListBean> prodcNameSrchResult = new ArrayList<WishListBean>();
		for (WishListBean data : srcList) {
			if(data.getProdcrName().contains(srchString))
				prodcNameSrchResult.add(data);
		}
		return prodcNameSrchResult;	
	}
	
	public List<WishListBean> searchInReview(String wishlistName, String srchString){		// search inside review..
		Logger.getInstance().log("WishListModel -> searchInReview() wishlistName = "+wishlistName+" srchString = "+srchString);
		List<WishListBean> srcList = listMovies(wishlistName); 
		List<WishListBean> reviewSrchResult = new ArrayList<WishListBean>();
		for (WishListBean data : srcList) {
			if(data.getReview().contains(srchString))
				reviewSrchResult.add(data);
		}
		return reviewSrchResult;	
	}
	
	public List<WishListBean> searchInRating(String wishlistName, String srchString){		// search inside rating..
		Logger.getInstance().log("WishListModel -> searchInRating() wishlistName = "+wishlistName+" srchString = "+srchString);
		List<WishListBean> srcList = listMovies(wishlistName);
		List<WishListBean> ratingSrchResult = new ArrayList<WishListBean>();
		for (WishListBean data : srcList) {
			if(String.valueOf(data.getRating()).contains(srchString))
				ratingSrchResult.add(data);
		}
		return ratingSrchResult;	
	}
	
	public Set<WishListBean> uniqueSearch(String wishlistName, String srchString){			// search in every field info and return unique results..
		Logger.getInstance().log("WishListModel -> uniqueSearch() wishlistName = "+wishlistName+" srchString = "+srchString);
		List<WishListBean> movieNameSerch = searchInMovieName(wishlistName, srchString);
		List<WishListBean> dirctrNameSerch = searchInDirctrName(wishlistName, srchString);
		List<WishListBean> prdcrNameSerch = searchInProdcrName(wishlistName, srchString);
		List<WishListBean> reviewSerch = searchInReview(wishlistName, srchString);
		List<WishListBean> ratingSerch = searchInRating(wishlistName, srchString);
		Set<WishListBean> combined = new HashSet<WishListBean>(movieNameSerch);
		combined.addAll(dirctrNameSerch);
		combined.addAll(prdcrNameSerch);
		combined.addAll(reviewSerch);
		combined.addAll(ratingSerch);
		return combined;
	}
	
	public List <String> wishlistSearch(String searchWord) {										// WishList Search...
		Logger.getInstance().log("WishListModel -> wishlistSearch() searchWord = "+searchWord); 
		List<String> wishlists = new ArrayList<String>();
		File f = new File(Constants.PATH);
		File[] file = f.listFiles();
		for (File fi : file) {
			if(fi.exists() && fi.isFile() && fi.getName().endsWith(".txt") && fi.getName().replaceAll(".txt", "").contains(searchWord))
				wishlists.add(fi.getName().replaceAll(".txt", ""));
		}
		return wishlists;
	}
}


















