package com.epam.ta.bean;

import static com.epam.ta.appconstant.TAConstant.DEFAULT_DATE_FORMAT;
import static com.epam.ta.resource.PropertyGetter.getProperty;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.epam.ta.model.News;
import com.epam.ta.model.comparison.NewsByDateComparator;

public final class NewsViewBean implements Serializable {
	private static final long serialVersionUID = 4795803945923209098L;

	private List<News> newsList;

	private News news;

	private String[] selectedNews;

	private static final Comparator<News> newsByDateComparator;

	static {
		SimpleDateFormat defaultDateFormat = new SimpleDateFormat(
				getProperty(DEFAULT_DATE_FORMAT));
		newsByDateComparator = new NewsByDateComparator(defaultDateFormat);
	}

	public NewsViewBean() {
		news = new News();
	}

	public void reset() {
		selectedNews = null;
	}

	public List<News> getNewsList() {
		Collections.sort(newsList, newsByDateComparator);
		return newsList;
	}

	public List<News> getNewsList(Comparator<News> newsComparator) {
		Collections.sort(newsList, newsComparator);
		return newsList;
	}

	// nList is all news from table
	public void setNewsList(List<News> list) {
		this.newsList = list;
	}

	public News getNews() {
		return news;
	}

	public void setNews(News newsMessage) {
		this.news = newsMessage;
	}

	public void resetNews() {
		setNews(new News());
	}

	public String[] getSelectedNews() {
		return selectedNews;
	}

	public void setSelectedNews(String[] selectedNews) {
		this.selectedNews = selectedNews;
	}

	// getters and setters for newsMessage
	public String getTitle() {
		return news.getTitle();
	}

	public String getBrief() {
		return news.getBrief();
	}

	public String getContent() {
		return news.getContent();
	}

	public String getDateOfPublishing() {
		return news.getDateOfPublishing();
	}

	public long getNewsId() {
		return news.getNewsId();
	}

	public void setNewsId(long newsId) {
		news.setNewsId(newsId);
	}

	public void setTitle(String title) {
		news.setTitle(title);
	}

	public void setBrief(String brief) {
		news.setBrief(brief);
	}

	public void setContent(String content) {
		news.setContent(content);
	}

	public void setDateOfPublishing(String dateOfPublishing) {
		news.setDateOfPublishing(dateOfPublishing);
	}
}
