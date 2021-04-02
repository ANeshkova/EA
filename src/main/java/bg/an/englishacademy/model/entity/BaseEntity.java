package bg.an.englishacademy.model.entity;

import javax.persistence.*;

@MappedSuperclass
public abstract class BaseEntity {

    private long id;

    protected BaseEntity() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false, updatable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
