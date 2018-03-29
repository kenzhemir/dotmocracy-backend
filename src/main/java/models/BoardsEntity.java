package models;

import javax.persistence.*;

/**
 * Created by Assylkhanov Aslan on 30.03.2018.03.2018=
 */

@Entity
@Table(name = "boards", schema = "sql12229390", catalog = "")
public class BoardsEntity {
    private long id;
    private long owner;
    private String topic;
    private String category;

    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "owner")
    public long getOwner() {
        return owner;
    }

    public void setOwner(long owner) {
        this.owner = owner;
    }

    @Basic
    @Column(name = "topic")
    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    @Basic
    @Column(name = "category")
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BoardsEntity that = (BoardsEntity) o;

        if (id != that.id) return false;
        if (owner != that.owner) return false;
        if (topic != null ? !topic.equals(that.topic) : that.topic != null) return false;
        if (category != null ? !category.equals(that.category) : that.category != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (owner ^ (owner >>> 32));
        result = 31 * result + (topic != null ? topic.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        return result;
    }
}
