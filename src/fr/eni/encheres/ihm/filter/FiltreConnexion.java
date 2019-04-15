package fr.eni.encheres.ihm.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import fr.eni.encheres.bll.EnchereManager;
import fr.eni.encheres.bo.Utilisateur;

@WebFilter(urlPatterns= {"/*"})
public class FiltreConnexion implements Filter {

	static List<String> excludedUrls = new ArrayList<>();

	static {

		//excludedUrls.add("/CreationCompte");
		excludedUrls.add("/TrocEncheresV1/CreationCompte");
		excludedUrls.add("/TrocEncheresV1/css/monCss.css");
		excludedUrls.add("TrocEncheresV1/css/monCss.css");
		excludedUrls.add("/css/monCSS.css");
		excludedUrls.add("css/monCSS.css");

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();
		Cookie[] cookies = req.getCookies();

		String path = ((HttpServletRequest) request).getRequestURI();
		//System.out.println(path);
		//si on est dans une URL où on peut passer sans être connecté
		if (excludedUrls.contains(path)) {
			chain.doFilter(request, response); 
		} else {
			//si on est connecté
			if (session.getAttribute("user") != null) {	
				chain.doFilter(request, response);
			}
			else {
				//check les cookies
				Cookie connexOne = null;
				Cookie connexTwo = null;

				boolean foundOne = false;
				boolean foundTwo = false;
				if (cookies != null) {
					for (Cookie ck:cookies) {

						//si on a un cookie du nom qu'on veut
						if ("connexOne".equals(ck.getName())) {
							connexOne = ck;
							connexOne.setMaxAge(999999999);
							foundOne = true;


						}
						else if ("connexTwo".equals(ck.getName())) {
							connexTwo = ck;
							connexTwo.setMaxAge(999999999);
							foundTwo = true;

						}

						//si on a trouvé nos cookies, stop loop
						if (foundOne && foundTwo) {

							EnchereManager manager = EnchereManager.getInstance();
							Utilisateur userVide = new Utilisateur();
							//userVide.setPseudo(connexOne.getValue());
							//userVide.setMotDePasse(connexTwo.getValue());
							Utilisateur retourConnect = manager.connectUser(connexOne.getValue(), connexTwo.getValue());

							//si on s'est connecté
							if(retourConnect != null) {

								req.getSession().setAttribute("user", retourConnect);

							}

							break;

						}

					}
				}

				request.getRequestDispatcher("Connexion").forward(request, response);
				/*
				//si on a une session valide
				if (session.getAttribute("user") != null) {	
					chain.doFilter(request, response);
				}
				else {	
					request.getRequestDispatcher("Connexion").forward(request, response);

				}
				*/
			}


		}

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
