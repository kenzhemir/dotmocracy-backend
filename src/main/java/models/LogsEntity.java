package models;

import javax.persistence.*;

/**
 * Created by Assylkhanov Aslan on 20.04.2018.04.2018=
 */

@Entity
@Table(name = "logs", schema = "sql12229390", catalog = "")
public class LogsEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    private String when;
    private String who;
    private String what;
    private String from;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    public long getId() {
        return id;
    }

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "when")
    public String getWhen() {
        return when;
    }

    public void setWhen(String when) {
        this.when = when;
    }

    @Basic
    @Column(name = "who")
    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    @Basic
    @Column(name = "what")
    public String getWhat() {
        return what;
    }

    public void setWhat(String what) {
        this.what = what;
    }

    @Basic
    @Column(name = "from")
    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LogsEntity that = (LogsEntity) o;

        if (id != that.id) return false;
        if (when != null ? !when.equals(that.when) : that.when != null) return false;
        if (who != null ? !who.equals(that.who) : that.who != null) return false;
        if (what != null ? !what.equals(that.what) : that.what != null) return false;
        if (from != null ? !from.equals(that.from) : that.from != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (when != null ? when.hashCode() : 0);
        result = 31 * result + (who != null ? who.hashCode() : 0);
        result = 31 * result + (what != null ? what.hashCode() : 0);
        result = 31 * result + (from != null ? from.hashCode() : 0);
        return result;
    }
}
