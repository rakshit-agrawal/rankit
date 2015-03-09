package ucsc.hci.rankit;

/**
 * Created by rakshit on 3/8/15.
 */
public final class MovieDataBox {

    public Integer item_id;
    public String name;
    public String doi;
    public String cover_art;
    public Integer director;
    public String imdb_url;
    public String wikipedia_url;
    public Integer genre;
    public Integer lead_actor_male;
    public Integer lead_actor_female;
    public String description;

    public MovieDataBox(){

        this.item_id = 0;
        this.name = "";
        this.doi = "";
        this.cover_art = "";
        this.director = 0;
        this.imdb_url = "";
        this.wikipedia_url = "";
        this.genre = 0;
        this.lead_actor_male = 0;
        this.lead_actor_female = 0;
        this.description = "";


    }




}
