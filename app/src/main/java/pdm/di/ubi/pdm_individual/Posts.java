package pdm.di.ubi.pdm_individual;

/**
 * Created by saraiva on 18-11-2017.
 */

public class Posts {

    String Slugpk, Content, Title, Coordinates, Img, Date, Excerpt;
    int Id;


    public Posts() {
    }


    public Posts(String slugpk, String content, String title, String coordinates, String img, String date, String excerpt, int id) {
        Slugpk = slugpk;
        Content = content;
        Title = title;
        Coordinates = coordinates;
        Img = img;
        Date = date;
        Excerpt = excerpt;
        Id = id;
    }


    public String getSlugpk() {
        return Slugpk;
    }

    public void setSlugpk(String slugpk) {
        Slugpk = slugpk;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getCoordinates() {
        return Coordinates;
    }

    public void setCoordinates(String coordinates) {
        Coordinates = coordinates;
    }

    public String getImg() {
        return Img;
    }

    public void setImg(String img) {
        Img = img;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getExcerpt() {
        return Excerpt;
    }

    public void setExcerpt(String excerpt) {
        Excerpt = excerpt;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }


    @Override
    public String toString() {
        return "Posts{" +
                "Slugpk='" + Slugpk + '\'' +
                ", Content='" + Content + '\'' +
                ", Title='" + Title + '\'' +
                ", Coordinates='" + Coordinates + '\'' +
                ", Img='" + Img + '\'' +
                ", Date='" + Date + '\'' +
                ", Excerpt='" + Excerpt + '\'' +
                ", Id=" + Id +
                '}';
    }
}


