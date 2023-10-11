package com.realnet.Bookmarks.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realnet.Bookmarks.Entity.Bookmarks;
import com.realnet.Bookmarks.Repository.BookmarksRepository;

@Service
public class BookmarksService {
	@Autowired
	private BookmarksRepository Repository;

	public Bookmarks Savedata(Bookmarks data) {
		return Repository.save(data);
	}

	public List<Bookmarks> getdetails() {
		return (List<Bookmarks>) Repository.findAll();
	}

	public Bookmarks getdetailsbyId(Integer id) {
		return Repository.findById(id).get();
	}

	public void delete_by_id(Integer id) {
		Repository.deleteById(id);
	}

	public Bookmarks update(Bookmarks data, Integer id) {
		Bookmarks old = Repository.findById(id).get();
		old.setBookmark_firstletter(data.getBookmark_firstletter());
		old.setBookmark_link(data.getBookmark_link());
		final Bookmarks test = Repository.save(old);
		return test;
	}
}
