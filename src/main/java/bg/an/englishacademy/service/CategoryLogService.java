package bg.an.englishacademy.service;

import bg.an.englishacademy.model.service.CategoryLogServiceModel;

import java.util.List;

public interface CategoryLogService {

    void createLog(String action, String categoryName);

    List<CategoryLogServiceModel> findAllLogs();

    void deleteLogs();
}
