package bg.an.englishacademy.model.service;

public abstract class BaseServiceModel {

    private long id;

    public BaseServiceModel() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
