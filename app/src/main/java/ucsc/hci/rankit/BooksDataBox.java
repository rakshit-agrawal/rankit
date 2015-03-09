package ucsc.hci.rankit;

/**
 * Created by rakshit on 3/8/15.
 */
public final class BooksDataBox {

    public Integer item_id;
    public String name;
    public String isbn;
    public String cover_art;
    public String publish_year;
    public Integer author;
    public Integer category;
    public Integer publisher;
    public String amazon_url;
    public String wikipedia_url;
    public String description;

    public BooksDataBox(){

        this.item_id = 0;
        this.name = "";
        this.isbn = "";
        this.cover_art = "";
        this.publish_year = "";
        this.author = 0;
        this.amazon_url = "";
        this.wikipedia_url = "";
        this.category = 0;
        this.publisher = 0;
        this.description = "";


    }




}
