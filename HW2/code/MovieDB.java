import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Genre, Title 을 관리하는 영화 데이터베이스.
 * 
 * MyLinkedList 를 사용해 각각 Genre와 Title에 따라 내부적으로 정렬된 상태를  
 * 유지하는 데이터베이스이다. 
 */
public class MovieDB {
	private MyLinkedList<Genre> genreList;

    public MovieDB() {
        genreList = new MyLinkedList<>();
    }

    public void insert(MovieDBItem item) {
		// Insert the given item to the MovieDB.
    	Node<Genre> currGenre = this.genreList.head;
    	int compare = 0;
    	while (currGenre.getNext() != null) {
    		try {
				compare = currGenre.getItem().getName().compareTo(item.getGenre());
			} catch(Exception e) {
				currGenre = currGenre.getNext();
    			continue;
			}
    		if (compare < 0) {
    			break;
			}
    		if (compare == 0) {
    			currGenre.getItem().movieList.add(item.getTitle());
			}
    		currGenre = currGenre.getNext();
		}
		currGenre.setNext(new Node<>(new Genre(item.getGenre()), currGenre.getNext()));
		currGenre.getNext().getItem().movieList.add(item.getTitle());
    }

    public void delete(MovieDBItem item) {
        for (Genre genre: this.genreList) {
        	if (genre.getName().equals(item.getGenre())) {
        		genre.movieList.remove(item.getTitle());
			}
		}
    }

    public MyLinkedList<MovieDBItem> search(String term) {
    	MyLinkedList<MovieDBItem> results = new MyLinkedList<>();
		for (Genre genre: this.genreList) {
			for (String movie: genre.movieList) {
				if (movie.contains(term)) {
					results.add(new MovieDBItem(genre.getName(), movie));
				}
			}
		}
    	return results;
    }
    
    public MyLinkedList<MovieDBItem> items() {
		// Search the given term from the MovieDatabase.
		MyLinkedList<MovieDBItem> results = new MyLinkedList<>();
		for (Genre genre: this.genreList) {
			for (String movie: genre.movieList) {
				results.add(new MovieDBItem(genre.getName(), movie));
			}
		}
    	return results;
    }
}

class Genre {
	public MovieList movieList;
	private final String name;

	public Genre(String name) {
		this.name = name;
		movieList = new MovieList();
	}

	public final String getName() {
		return name;
	}
}

class MovieList extends MyLinkedList<String> {
	@Override
	public void add(String item) {
		Node<String> temp = this.head;
		while (temp.getNext() != null) {
			int compare = temp.getNext().getItem().compareTo(item);
			if (compare < 0) break;
			if (compare == 0) return;
			temp = temp.getNext();
		}
		temp.setNext(new Node<>(item, temp.getNext()));
	}

	public void remove(String title) {
		Node<String> temp = this.head;
		while (temp.getNext() != null) {
			if (temp.getNext().getItem().equals(title)) {
				temp.setNext(temp.getNext().getNext());
			}
		}
	}
}