package test;

import com.boucheriebenz.eboucherie.recherche.dao.ArticleDao;
import com.boucheriebenz.eboucherie.recherche.dao.ArticlePhotoDao;
import com.boucheriebenz.eboucherie.recherche.dao.ClientDao;
import com.boucheriebenz.eboucherie.recherche.dao.PhotoDao;
import com.boucheriebenz.eboucherie.recherche.model.ArticlePhotoId;


public class TestMain {

	
	public static void main(String[] args) {
		
//		ArticleDao.getArticlesByLibelle("Article1");
//		ArticleDao.getListArticles("b");
//		System.out.println(ArticlePhotoDao.findById(1).getPhoto());
//		System.out.println(PhotoDao.findById(3).getLibelle());
//		System.out.println(ClientDao.getClientByName("a").get(1).getPrenom());
		System.out.println(ClientDao.getClientByID(321654).getPrenom());
		System.out.println(ArticleDao.findById(1).getLibelle());

	}
	


}
