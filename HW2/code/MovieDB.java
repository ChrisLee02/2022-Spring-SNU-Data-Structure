import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Genre, Title 을 관리하는 영화 데이터베이스.
 * 
 * MyLinkedList 를 사용해 각각 Genre와 Title에 따라 내부적으로 정렬된 상태를  
 * 유지하는 데이터베이스이다. 
 */
public class MovieDB {
	private MyLinkedList<Genre> genres;

    public MovieDB() {
        genres = new GenreList();
    }

    public void insert(MovieDBItem item) {
        // FIXME implement this
        // Insert the given item to the MovieDB.
		Genre tmp = new Genre(item.getGenre());
		if(genres.getNode(tmp)==null) {
			genres.addOrderly(tmp); //처음 나오는 장르의 영화면, 순서에 맞게 새 장르노드 삽입.
			tmp.addMovieOrderly(item.getTitle());
			//item값만 넘겨주면 되도록 node.java에서 짜놨음.
		} else {
			Genre MovieGenre = genres.getNode(tmp).getItem();
			//System.out.println("이미 장르 존재");
			MovieGenre.addMovieOrderly(item.getTitle()); // 존재성 여부는 addOrderly 함수에서 처리해준다.
		}

    	// Printing functionality is provided for the sake of debugging.
        // This code should be removed before submitting your work.
        //System.err.printf("[trace] MovieDB: INSERT [%s] [%s]\n", item.getGenre(), item.getTitle());
    }

    public void delete(MovieDBItem item) {
        // FIXME implement this
        // Remove the given item from the MovieDB.
		Genre tmp = new Genre(item.getGenre());
		if(genres.getNode(tmp)==null) {
			return;
		} else {
			Genre MovieGenre = genres.getNode(tmp).getItem();
			MovieGenre.deleteMovie(item.getTitle());
		}

    	// Printing functionality is provided for the sake of debugging.
        // This code should be removed before submitting your work.
        //System.err.printf("[trace] MovieDB: DELETE [%s] [%s]\n", item.getGenre(), item.getTitle());
    }

    public MyLinkedList<MovieDBItem> search(String term) {
        // FIXME implement this
        // Search the given term from the MovieDB.
        // You should return a linked list of MovieDBItem.
        // The search command is handled at SearchCmd class.
		MyLinkedList<MovieDBItem> results = new MyLinkedList<MovieDBItem>();
		for (Genre genre:genres) {
			genre.searchMovie(genre.getItem(), term, results);
		}
		//System.out.println(results.isEmpty());

    	// Printing search results is the responsibility of SearchCmd class. 
    	// So you must not use System.out in this method to achieve specs of the assignment.
    	
        // This tracing functionality is provided for the sake of debugging.
        // This code should be removed before submitting your work.
    	//System.err.printf("[trace] MovieDB: SEARCH [%s]\n", term);

        return results;
    }
    
    public MyLinkedList<MovieDBItem> items() {
        // FIXME implement this
        // Search the given term from the MovieDatabase.
        // You should return a linked list of QueryResult.
        // The print command is handled at PrintCmd class.

    	// Printing movie items is the responsibility of PrintCmd class. 
    	// So you must not use System.out in this method to achieve specs of the assignment.

    	// Printing functionality is provided for the sake of debugging.
        // This code should be removed before submitting your work.
		MyLinkedList<MovieDBItem> results = new MyLinkedList<MovieDBItem>();
		for (Genre genre:genres) {
			genre.searchMovie(genre.getItem(), "", results);
		}
		//System.out.println(results.isEmpty());
    	// FIXME remove this code and return an appropriate MyLinkedList<MovieDBItem> instance.
    	// This code is supplied for avoiding compilation error.   

    	return results;
    }
}

//장르 리스트의 각 요소들은 영화 리스트를 가리키도록 설계해야됨.
//MovieDBItem은 DB 내부 메소드에서 new로 생성하면 그만이고, 탐색 자체는 그냥 장르 하위의 string 리스트로 해도 충분함.


class GenreList extends MyLinkedList<Genre> implements ListInterface<Genre>{
	//생성자 아래로 구현해야됨.
	public GenreList() {
		super();
	}

	/*@Override
	public Iterator<String> iterator() {
		return new MyLinkedListIterator<String>(this);
	}*/

	@Override
	public boolean isEmpty() {
		return head.getNext() == null;
	}

	@Override
	public int size() {
		return numItems;
	}

	@Override
	public void add(Genre item) {
		Node<Genre> last = head;
		while (last.getNext() != null) {
			last = last.getNext();
		}
		last.insertNext(item);
		numItems += 1;
	}

	@Override
	public Genre first() {
		return head.getNext().getItem();
	}

	@Override
	public void removeAll() {
		head.setNext(null);
	}
}

class Genre extends Node<String> implements Comparable<Genre> {
	private MovieList genreMovieList;
	public Genre(String name) {
		super(name);
		genreMovieList = new MovieList();
	}
	
	@Override
	public int compareTo(Genre o) {
		return this.getItem().compareTo(o.getItem());
	}

	public void addMovieOrderly(String item) {
		genreMovieList.addOrderly(item);
	}
	public void deleteMovie(String item) {
		genreMovieList.removeItem(item);
	}
	public void searchMovie(String genre, String title,  MyLinkedList<MovieDBItem> result) {
		genreMovieList.searchItems(genre, title, result);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Genre other = (Genre) obj;
		if (this.getItem() == null) {
			if (other.getItem() != null)
				return false;
		} else if (!this.getItem().equals(other.getItem()))
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.getItem() == null) ? 0 : this.getItem().hashCode());
		return result;
	}

}

class MovieList extends MyLinkedList<String> implements ListInterface<String>{
	public MovieList() {
		super();
	}

	/*@Override
	public Iterator<String> iterator() {
		return new MyLinkedListIterator<String>(this);
	}*/

	@Override
	public boolean isEmpty() {
		return head.getNext() == null;
	}

	@Override
	public int size() {
		return numItems;
	}

	public void searchItems(String genre, String title, MyLinkedList<MovieDBItem> result) {
		Node<String> last = head.getNext();
		/*System.out.printf("%s은 %s를 포함?\n", title, last.getItem());
		System.out.println(last.getItem().contains(title));*/
		while (last != null) {
			if(last.getItem().contains(title)) {
				//System.out.printf("%s은 %s를 포함합니다~\n", title, last.getItem());
				result.add(new MovieDBItem(genre, last.getItem()));
			}
			last = last.getNext();
		}
	}

	@Override
	public void add(String item) {
		Node<String> last = head;
		while (last.getNext() != null) {
			last = last.getNext();
		}
		last.insertNext(item);
		numItems += 1;
	}

	@Override
	public String first() {
		return head.getNext().getItem();
	}

	@Override
	public void removeAll() {
		head.setNext(null);
	}
}