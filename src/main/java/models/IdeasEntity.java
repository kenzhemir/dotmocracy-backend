package models;

import javax.persistence.*;

/**
 * Created by Assylkhanov Aslan on 30.03.2018.03.2018=
 */

@Entity
@Table(name = "ideas", schema = "sql12229390", catalog = "")
public class IdeasEntity {
    private long id;
    private String name;
    private long boardsId;

    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "boards_id")
    public long getBoardsId() {
        return boardsId;
    }

    public void setBoardsId(long boardsId) {
        this.boardsId = boardsId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IdeasEntity entity = (IdeasEntity) o;

        if (id != entity.id) return false;
        if (boardsId != entity.boardsId) return false;
        if (name != null ? !name.equals(entity.name) : entity.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (int) (boardsId ^ (boardsId >>> 32));
        return result;
    }
}
