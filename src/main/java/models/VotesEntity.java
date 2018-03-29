package models;

import javax.persistence.*;

@Entity
@Table(name = "votes", schema = "sql12229390", catalog = "")
public class VotesEntity {
    private long id;
    private long value;
    private long usersId;
    private long boardsId;
    private long optionsId;

    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "value")
    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    @Basic
    @Column(name = "users_id")
    public long getUsersId() {
        return usersId;
    }

    public void setUsersId(long usersId) {
        this.usersId = usersId;
    }

    @Basic
    @Column(name = "boards_id")
    public long getBoardsId() {
        return boardsId;
    }

    public void setBoardsId(long boardsId) {
        this.boardsId = boardsId;
    }

    @Basic
    @Column(name = "options_id")
    public long getOptionsId() {
        return optionsId;
    }

    public void setOptionsId(long optionsId) {
        this.optionsId = optionsId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VotesEntity that = (VotesEntity) o;

        if (id != that.id) return false;
        if (value != that.value) return false;
        if (usersId != that.usersId) return false;
        if (boardsId != that.boardsId) return false;
        if (optionsId != that.optionsId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (value ^ (value >>> 32));
        result = 31 * result + (int) (usersId ^ (usersId >>> 32));
        result = 31 * result + (int) (boardsId ^ (boardsId >>> 32));
        result = 31 * result + (int) (optionsId ^ (optionsId >>> 32));
        return result;
    }
}
