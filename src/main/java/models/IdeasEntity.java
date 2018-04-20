package models;

import javax.persistence.*;

/**
 * Created by Assylkhanov Aslan on 20.04.2018.04.2018=
 */

@Entity
@Table(name = "ideas", schema = "sql12229390", catalog = "")
public class IdeasEntity {
    private long id;
    private String name;
    private Long boardsId;
    private String description;

    @Id
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
    public Long getBoardsId() {
        return boardsId;
    }

    public void setBoardsId(Long boardsId) {
        this.boardsId = boardsId;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IdeasEntity that = (IdeasEntity) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (boardsId != null ? !boardsId.equals(that.boardsId) : that.boardsId != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (boardsId != null ? boardsId.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
