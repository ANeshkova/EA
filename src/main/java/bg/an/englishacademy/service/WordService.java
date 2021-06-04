package bg.an.englishacademy.service;

import bg.an.englishacademy.model.service.WordServiceModel;

import java.util.List;

public interface WordService {

    WordServiceModel addWord(WordServiceModel wordServiceModel);

    boolean englishWordExistsInCategory(String englishWord, String categoryName);

    List<WordServiceModel> findAllWords();

    WordServiceModel findWordById(Long id);

    WordServiceModel editWord(Long id, WordServiceModel wordServiceModel);
}
