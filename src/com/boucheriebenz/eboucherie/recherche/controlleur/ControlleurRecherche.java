package com.boucheriebenz.eboucherie.recherche.controlleur;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.boucheriebenz.eboucherie.recherche.dao.ArticleDao;
import com.boucheriebenz.eboucherie.recherche.dao.ArticlePhotoDao;
import com.boucheriebenz.eboucherie.recherche.dao.PhotoDao;
import com.boucheriebenz.eboucherie.recherche.model.Article;
import com.boucheriebenz.eboucherie.recherche.model.Photo;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
/**
 * Servlet implementation class ControlleurRecherche
 */
public class ControlleurRecherche extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ControlleurRecherche() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//on recupere le ou les mots-cles saisis 
		String mots = request.getParameter("recherche");
		mots=mots.toLowerCase();
		
		//on transforme cette chaine en array
		String [] motsCles=mots.split(" ");
		

        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        response.setHeader("Cache-control", "no-cache, no-store");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "-1");
 
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setHeader("Access-Control-Max-Age", "86400");
        
        List<Article> lArticlesDoublon=new ArrayList<Article>();
		for (int i = 0; i < motsCles.length; i++) {
			lArticlesDoublon.addAll(ArticleDao.getArticlesByLibelle(motsCles[i])) ;
			
		}
		
		//supprimmer les doublons 
		ArrayList<Article> lArticles = new ArrayList<Article>();
		for (Article arr1 : lArticlesDoublon) {
            boolean equal=false;
            for (Article arr2 : lArticles) {
                if (arr1.getLibelle().equals(arr2.getLibelle())) {
                    equal = true;
                    break;
                }
            }
            if (equal == false) {
                lArticles.add(arr1);
            }
        }
		

		
		Gson gson = new Gson(); 
		JsonObject myObj = new JsonObject();
		
		JsonElement articleObj = gson.toJsonTree(lArticles);
		
		
		 if(lArticles.size()==0){
	            myObj.addProperty("success", false);
	        }
	        else {
	            myObj.addProperty("success", true);
	        }
	        myObj.add("larticles", articleObj);
	        out.println(myObj.toString());
	 
	        out.close();
		
		

	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		doPost(req, resp);
	}

}