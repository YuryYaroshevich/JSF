package com.epam.ta.bean;

import java.util.Locale;

import org.apache.struts.actions.DispatchAction;

import com.epam.ta.database.dao.NewsDAO;
import com.epam.ta.exception.TATechnicalException;
import com.epam.ta.model.News;

public final class NewsActionBean extends DispatchAction {
	private NewsDAO newsDAO;
	private NewsViewBean newsView;
	private LocaleBean localeBean;


	// I return this variable when user click cancel button
	private String previousPage;

	// I use one JSP for add and edit news. This attribute helps to choose
	// correct title.
	private String addOrEditTitle;
	private static final String ADD_TITLE_KEY = "title.add.news";
	private static final String EDIT_TITLE_KEY = "title.edit.news";

	// returning values of action methods
	private static final String NEWS_LIST_PAGE = "newsList";
	private static final String VIEW_NEWS_PAGE = "viewNews";
	private static final String ADD_NEWS_PAGE = "addNews";
	private static final String EDIT_NEWS_PAGE = "editNews";

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

	public NewsActionBean(NewsDAO newsDAO, NewsViewBean newsView)
			throws TATechnicalException {
		setNewsDAO(newsDAO);
		setNewsView(newsView);
		newsView.setNewsList(newsDAO.getNewsList());
		setPreviousPage(NEWS_LIST_PAGE);
	}

	public void setNewsDAO(NewsDAO newsDAO) {
		this.newsDAO = newsDAO;
	}

	public NewsViewBean getNewsView() {
		return newsView;
	}

	public void setNewsView(NewsViewBean newsView) {
		this.newsView = newsView;
	}
	
	public LocaleBean getLocaleBean() {
		return localeBean;
	}

	public void setLocaleBean(LocaleBean localeBean) {
		this.localeBean = localeBean;
	}

	public String getPreviousPage() {
		return previousPage;
	}

	public void setPreviousPage(String previousPage) {
		this.previousPage = previousPage;
	}

	public String getAddOrEditTitle() {
		return addOrEditTitle;
	}

	public void setAddOrEditTitle(String addOrEditTitle) {
		this.addOrEditTitle = addOrEditTitle;
	}

	public String newsList() throws TATechnicalException {
		newsView.setNewsList(newsDAO.getNewsList());
		setPreviousPage(NEWS_LIST_PAGE);
		return NEWS_LIST_PAGE;
	}


	// gets form for creating news
	public String addNews() {
		newsView.resetNews();
		setAddOrEditTitle(ADD_TITLE_KEY);
		return ADD_NEWS_PAGE;
	}

	// saves created news in database
	public String saveNews() throws TATechnicalException {
		News newsMessage = newsView.getNews();
		long newsId = newsDAO.addNews(newsMessage);
		newsMessage.setNewsId(newsId);
		newsView.setNewsList(newsDAO.getNewsList());
		return VIEW_NEWS_PAGE;
	}

	public String viewNews(int newsId) throws TATechnicalException {
		News newsMessage = newsDAO.fetchNewsById(newsId);
		newsView.setNews(newsMessage);
		setPreviousPage(VIEW_NEWS_PAGE);
		return VIEW_NEWS_PAGE;
	}

	public String editNews(int newsId) throws TATechnicalException {
		News newsMessage = newsDAO.fetchNewsById(newsId);
		newsView.setNews(newsMessage);
		setAddOrEditTitle(EDIT_TITLE_KEY);
		return EDIT_NEWS_PAGE;
	}

	// saves edited news in database
	public String updateNews() throws TATechnicalException {
		newsDAO.updateNews(newsView.getNews());
		return VIEW_NEWS_PAGE;
	}

	// on view news page
	public String deleteNews() throws TATechnicalException {
		newsDAO.deleteNews(newsView.getNewsId());
		newsView.setNewsList(newsDAO.getNewsList());
		return NEWS_LIST_PAGE;
	}

	// on list news page
	public String deleteNewsGroup() throws TATechnicalException {
		String[] selectedNews = newsView.getSelectedNews();
		if (selectedNews != null) {
			newsDAO.deleteNewsGroup(selectedNews);
			newsView.setNewsList(newsDAO.getNewsList());
		}
		return NEWS_LIST_PAGE;
	}

	public String changeLocale(String language) {
		localeBean.setLocale(new Locale(language));
		return null;
	}

	public String cancel() {
		return getPreviousPage();
	}
}
