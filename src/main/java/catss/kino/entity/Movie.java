package catss.kino.entity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "movies")
public class Movie {

    // Autogenerated ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    //The relation between the movie and reservation classes.
    @OneToMany(mappedBy = "movie", fetch = FetchType.EAGER)
    List<Showing> showings;

    // Entity class for movie with attributes fitting all the information gotten from the Json
    private String title;
    private String year;
    private String rated;
    private String released;
    private String runtime;
    private String genre;
    private String director;
    private String writer;
    private String actors;

    @Column(length = 2000)
    private String plot;
    @Column(length = 2000)
    private String plotDK;
    private String poster;

    private String metascore;
    private String imdbRating;
    private String imdbVotes;

    @Column(unique = true)
    private String imdbID;
    private String website;
    private String response;


    public void addShowing(Showing showing) {
        if (showings == null){
            showings = new ArrayList<>();
        }
        showings.add(showing);
    }
}