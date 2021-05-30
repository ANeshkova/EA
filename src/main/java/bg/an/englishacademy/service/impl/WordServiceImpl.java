package bg.an.englishacademy.service.impl;

import bg.an.englishacademy.model.entity.WordEntity;
import bg.an.englishacademy.model.service.WordServiceModel;
import bg.an.englishacademy.repository.WordRepository;
import bg.an.englishacademy.service.WordService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WordServiceImpl implements WordService {

    private final WordRepository wordRepository;
    private final ModelMapper modelMapper;

    public WordServiceImpl(WordRepository wordRepository, ModelMapper modelMapper) {
        this.wordRepository = wordRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public WordServiceModel addWord(WordServiceModel wordServiceModel) {
        this.wordRepository.save(modelMapper.map(wordServiceModel, WordEntity.class));
        return wordServiceModel;
    }

    @Override
    public boolean englishWordExistsInCategory(String englishWord, String categoryName) {
        return this.wordRepository.findByEnglishAndCategory_Name(englishWord, categoryName).isPresent();
    }

    @Override
    public List<WordServiceModel> findAllWords() {
        return this.wordRepository.findAll()
                .stream().map(w -> this.modelMapper.map(w, WordServiceModel.class))
                .collect(Collectors.toList());
    }
}
