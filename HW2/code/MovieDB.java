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
    	// HINT: MovieDBGenre 클래스를 정렬된 상태로 유지하기 위한 
    	// MyLinkedList 타입의 멤버 변수를 초기화 한다.
    }

    public void insert(MovieDBItem item) {
    	Node<Genre> currGenre = this.genreList.head;
    	while (currGenre.getNext() != null) {
    		int compare = currGenre.getItem().getName().compareTo(item.getGenre());
    		if (compare < 0) {
    			break;
			}
    		if (compare == 0) {
    			currGenre.getItem().movieList.add(item.getTitle());
			}
    		currGenre.setNext(new Node<>(new Genre(item.getGenre()), currGenre.getNext()));
    		currGenre.getNext().getItem().movieList.add(item.getTitle());
		}
        // Insert the given item to the MovieDB.

    	// Printing functionality is provided for the sake of debugging.
        // This code should be removed before submitting your work.
        System.err.printf("[trace] MovieDB: INSERT [%s] [%s]\n", item.getGenre(), item.getTitle());
    }

    public void delete(MovieDBItem item) {
        for (Genre genre: this.genreList) {
        	if (genre.getName().equals(item.getGenre())) {
        		genre.movieList.remove(item.getTitle());
			}
		}
    	
    	// Printing functionality is provided for the sake of debugging.
        // This code should be removed before submitting your work.
        System.err.printf("[trace] MovieDB: DELETE [%s] [%s]\n", item.getGenre(), item.getTitle());
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
		MyLinkedList<MovieDBItem> results = new MyLinkedList<>();
		for (Genre genre: this.genreList) {
			for (String movie: genre.movieList) {
				results.add(new MovieDBItem(genre.getName(), movie));
			}
		}
        // Search the given term from the MovieDatabase.
        // You should return a linked list of QueryResult.
        // The print command is handled at PrintCmd class.

    	// Printing movie items is the responsibility of PrintCmd class. 
    	// So you must not use System.out in this method to achieve specs of the assignment.

    	// Printing functionality is provided for the sake of debugging.
        // This code should be removed before submitting your work.
        System.err.printf("[trace] MovieDB: ITEMS\n");
    	return results;
    }
}

class Genre {
	public MovieList movieList;
	private String name;

	public Genre() {
		this.name = null;
		movieList = new MovieList();
	}

	public Genre(String name) {
		this.name = name;
		movieList = new MovieList();
	}

	public final String getName() {
		return name;
	}

	public final void setName(String name) {
		this.name = name;
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
		while (head.getNext() != null) {
			if (head.getNext().getItem().equals(title)) {
				head.setNext(head.getNext().getNext());
			}
		}
	}
}