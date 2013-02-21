package com.boucheriebenz.eboucherie.recherche.controlleur;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.boucheriebenz.eboucherie.recherche.dao.ArticlePhotoDao;
import com.boucheriebenz.eboucherie.recherche.dao.PhotoDao;
import com.boucheriebenz.eboucherie.recherche.model.ArticlePhotoId;
import com.boucheriebenz.eboucherie.recherche.model.Photo;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Servlet implementation class PhotoControlleur
 */
public class PhotoControlleur extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PhotoControlleur() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		String idArticle = request.getParameter("idArticle");
		ArticlePhotoId articlePhotoId=ArticlePhotoDao.findByIdArticle(Integer.parseInt(idArticle));
		Photo photo=null;
		
		if (null==articlePhotoId){
			photo=null;
		}else{
			
			int idPhoto=articlePhotoId.getPhoto();
			photo=PhotoDao.findById(idPhoto);
		}
		
		Gson gson = new Gson(); 
		JsonObject myObj = new JsonObject();
		
		JsonElement photoObj = gson.toJsonTree(photo);
		
		
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		if(null==photo){
	            myObj.addProperty("success", false);
	        }
	    else {
	            myObj.addProperty("success", true);
	    }
	        myObj.add("photo", photoObj);
	        out.println(myObj.toString());
	 
	        out.close();
        
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}
