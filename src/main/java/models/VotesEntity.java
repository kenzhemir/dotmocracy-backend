package models;

import javax.persistence.*;

/**
 * Created by Assylkhanov Aslan on 20.04.2018.04.2018=
 */

@Entity
@Table(name = "votes", schema = "sql12229390", catalog = "")
public class VotesEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    private Long value;
    private Long usersId;
    private Long optionsId;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "value")
    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    @Basic
    @Column(name = "users_id")
    public Long getUsersId() {
        return usersId;
    }

    public void setUsersId(Long usersId) {
        this.usersId = usersId;
    }

    @Basic
    @Column(name = "options_id")
    public Long getOptionsId() {
        return optionsId;
    }

    public void setOptionsId(Long optionsId) {
        this.optionsId = optionsId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VotesEntity that = (VotesEntity) o;

        if (id != that.id) return false;
        if (value != null ? !value.equals(that.value) : that.value != null) return false;
        if (usersId != null ? !usersId.equals(that.usersId) : that.usersId != null) return false;
        if (optionsId != null ? !optionsId.equals(that.optionsId) : that.optionsId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (usersId != null ? usersId.hashCode() : 0);
        result = 31 * result + (optionsId != null ? optionsId.hashCode() : 0);
        return result;
    }
}
