package com.epam.ta.bean;

import java.util.Locale;

import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import org.apache.struts.actions.DispatchAction;

import com.epam.ta.database.dao.INewsDAO;
import com.epam.ta.exception.TATechnicalException;
import com.epam.ta.model.News;

public final class NewsActionBean extends DispatchAction {
	private INewsDAO newsDAO;
	private NewsViewBean newsView;

	// returning values of action methods
	private static final String GO_TO_NEWS_LIST = "newsList";
	private static final String GO_TO_VIEW_NEWS = "viewNews";
	private static final String GO_TO_ADD_NEWS = "addNews";
	private static final String GO_TO_EDIT_NEWS = "editNews";

	/*
	 * private static final String ATTR_PATH_WRAPPER = "pathWrapper"; private
	 * static final String ATTR_LANGUAGE = "language"; private static final
	 * String ATTR_PREVIOUS_PATH = "previousPath";
	 */

	// I use one JSP for adding and editing news, so these constants for making
	// appropriate title in adding and editing pages
	/*
	 * private static final String ADD_TITLE_PART = "add"; private static final
	 * String EDIT_TITLE_PART = "edit"; private static final String TITLE_PART =
	 * "titlePart";
	 */

	public void setNewsDAO(INewsDAO newsDAO) {
		this.newsDAO = newsDAO;
	}

	public NewsViewBean getNewsView() {
		return newsView;
	}

	public void setNewsView(NewsViewBean newsView) {
		this.newsView = newsView;
	}

	public String newsList() throws TATechnicalException {
		newsView.setNewsList(newsDAO.getNewsList());
		System.out.println(GO_TO_NEWS_LIST);
		return GO_TO_NEWS_LIST;// forwardTo(mapping.findForward(FORWARD_NEWS_LIST),
								// request);
	}

	/*
	 * private static ActionForward forwardTo(ActionForward whereWeGo,
	 * HttpServletRequest request) { HttpSession session =
	 * request.getSession(true); // prepare request wrapper RequestWrapper
	 * requestWrapper = prepareRequestWrapper(session);
	 * requestWrapper.appendPath(whereWeGo.getPath()); // put request wrapper
	 * and path of the next page in session
	 * session.setAttribute(ATTR_PATH_WRAPPER, requestWrapper);
	 * session.setAttribute(ATTR_PREVIOUS_PATH, whereWeGo.getPath()); return
	 * whereWeGo; }
	 */

	// gets form for creating news
	public String addNews() {
		newsView.resetNews();
		System.out.println(GO_TO_ADD_NEWS);
		/*
		 * HttpSession session = request.getSession(true); RequestWrapper
		 * requestWrapper = prepareRequestWrapper(session); ActionForward
		 * whereWeGo = mapping.findForward(FORWARD_ADD_NEWS);
		 * requestWrapper.appendPath(mapping.findForward(FORWARD_NEWS_LIST)
		 * .getPath()); session.setAttribute(ATTR_PATH_WRAPPER, requestWrapper);
		 * session.setAttribute(ATTR_PREVIOUS_PATH, whereWeGo.getPath());
		 * session.setAttribute(TITLE_PART, ADD_TITLE_PART);
		 */
		return GO_TO_ADD_NEWS;
	}

	// saves created news in database
	public String saveNews() throws TATechnicalException {
		/*
		 * ActionMessages errors = newsForm.validate(mapping, request); if
		 * (!errors.isEmpty()) { saveErrors(request, errors); return
		 * mapping.findForward(FORWARD_ADD_NEWS); } resetToken(request);
		 */
		News newsMessage = newsView.getNews();
		long newsId = newsDAO.addNews(newsMessage);
		newsMessage.setNewsId(newsId);
		newsView.setNewsList(newsDAO.getNewsList());
		// return forwardTo(mapping.findForward(FORWARD_VIEW_NEWS), request);
		return GO_TO_VIEW_NEWS;
		// return mapping.findForward(FORWARD_VIEW_NEWS);
	}

	public String viewNews(int newsId) throws TATechnicalException {
		News newsMessage = newsDAO.fetchNewsById(newsId);
		System.out.println(GO_TO_VIEW_NEWS);
		newsView.setNews(newsMessage);
		/*
		 * saveToken(request);// creates token in user's session before
		 * submitting // form HttpSession session = request.getSession(true);
		 * RequestWrapper requestWrapper = prepareRequestWrapper(session);
		 * ActionForward whereWeGo = mapping.findForward(FORWARD_VIEW_NEWS);
		 * requestWrapper.appendPathAndNewsId(whereWeGo.getPath(),
		 * newsMessage.getNewsId()); session.setAttribute(ATTR_PATH_WRAPPER,
		 * requestWrapper); session.setAttribute(ATTR_PREVIOUS_PATH,
		 * whereWeGo.getPath());
		 */
		return GO_TO_VIEW_NEWS;
	}

	public String editNews(int newsId) throws TATechnicalException {
		News newsMessage = newsDAO.fetchNewsById(newsId);
		newsView.setNews(newsMessage);
		/*
		 * saveToken(request);// creates token in user's session before
		 * submitting // form HttpSession session = request.getSession(true);
		 * ActionForward whereWeGo = mapping.findForward(FORWARD_EDIT_NEWS);
		 * session.setAttribute(ATTR_PREVIOUS_PATH, whereWeGo.getPath());
		 * session.setAttribute(TITLE_PART, EDIT_TITLE_PART);
		 */
		return GO_TO_EDIT_NEWS;
	}

	// saves edited news in database
	public String updateNews() throws TATechnicalException {
		/*
		 * ActionMessages errors = newsForm.validate(mapping, request); if
		 * (!errors.isEmpty()) { saveErrors(request, errors); return
		 * mapping.findForward(FORWARD_EDIT_NEWS); } resetToken(request);
		 */
		newsDAO.updateNews(newsView.getNews());
		// return forwardTo(mapping.findForward(FORWARD_VIEW_NEWS), request);

		return GO_TO_VIEW_NEWS;
	}

	// on view news page
	public String deleteNews() throws TATechnicalException {
		// ActionForward whereWeGo = mapping.findForward(FORWARD_NEWS_LIST);
		// if (isTokenValid(request, true)) {

		newsDAO.deleteNews(newsView.getNewsId());
		newsView.setNewsList(newsDAO.getNewsList());
		/*
		 * HttpSession session = request.getSession(true);
		 * session.setAttribute(ATTR_PREVIOUS_PATH, whereWeGo.getPath());
		 */

		return GO_TO_NEWS_LIST;
	}

	// on list news page
	public String deleteNewsGroup() throws TATechnicalException {
		// ActionForward whereWeGo = mapping.findForward(FORWARD_NEWS_LIST);
		// if (isTokenValid(request)) {
		String[] selectedNews = newsView.getSelectedNews();
		if (selectedNews != null) {
			newsDAO.deleteNewsGroup(selectedNews);
			newsView.setNewsList(newsDAO.getNewsList());
		}
		// resetToken(request);
		/*
		 * saveToken(request); HttpSession session = request.getSession(true);
		 * session.setAttribute(ATTR_PREVIOUS_PATH, whereWeGo.getPath());
		 */
		// }
		return GO_TO_NEWS_LIST;
	}

	public String changeLocale(String language) {
		UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
		viewRoot.setLocale(new Locale(language));
		/*
		 * HttpSession session = request.getSession(true); String language =
		 * (String) request.getParameter(ATTR_LANGUAGE);
		 * session.setAttribute(Globals.LOCALE_KEY, new Locale(language));
		 * String previousPath = (String)
		 * session.getAttribute(ATTR_PREVIOUS_PATH);
		 */
		// return new ActionForward(previousPath);

		// the same view is redisplayed
		return null;
	}

	public String cancel() {
		/*
		 * HttpSession session = request.getSession(true); RequestWrapper
		 * requestWrapper = (RequestWrapper) session
		 * .getAttribute(ATTR_PATH_WRAPPER); String previousPath =
		 * requestWrapper.getRequest();
		 */
		return "previousPath";
	}

	/*
	 * private static RequestWrapper prepareRequestWrapper(HttpSession session)
	 * { RequestWrapper requestWrapper = (RequestWrapper) session
	 * .getAttribute(ATTR_PATH_WRAPPER); if (requestWrapper == null) { return
	 * new RequestWrapper(); } else { requestWrapper.resetWrapper(); return
	 * requestWrapper; } }
	 */
}
