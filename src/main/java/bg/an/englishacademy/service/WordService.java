package bg.an.englishacademy.service;

import bg.an.englishacademy.model.service.WordServiceModel;

public interface WordService {

    WordServiceModel addWord(WordServiceModel wordServiceModel);

    boolean englishWordExistsInCategory(String englishWord, String categoryName);
}
