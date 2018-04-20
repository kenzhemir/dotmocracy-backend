package models;

import javax.persistence.*;

@Entity
@Table(name = "logs", schema = "sql12229390", catalog = "")
public class LogsEntity {

    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
    private String whattime;
    private String who;
    private String what;
    private String agent;

    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "whattime")
    public String getWhattime() {
        return whattime;
    }

    public void setWhattime(String whattime) {
        this.whattime = whattime;
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
    @Column(name = "agent")
    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LogsEntity that = (LogsEntity) o;

        if (id != that.id) return false;
        if (whattime != null ? !whattime.equals(that.whattime) : that.whattime != null) return false;
        if (who != null ? !who.equals(that.who) : that.who != null) return false;
        if (what != null ? !what.equals(that.what) : that.what != null) return false;
        if (agent != null ? !agent.equals(that.agent) : that.agent != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (whattime != null ? whattime.hashCode() : 0);
        result = 31 * result + (who != null ? who.hashCode() : 0);
        result = 31 * result + (what != null ? what.hashCode() : 0);
        result = 31 * result + (agent != null ? agent.hashCode() : 0);
        return result;
    }
}
