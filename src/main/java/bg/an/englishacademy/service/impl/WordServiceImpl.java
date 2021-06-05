package bg.an.englishacademy.service.impl;

import bg.an.englishacademy.model.entity.CategoryEntity;
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

    @Override
    public WordServiceModel findWordById(Long id) {
        WordEntity wordEntity = this.wordRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Word with id " + id + " not found"));
        return this.modelMapper.map(wordEntity, WordServiceModel.class);
    }

    @Override
    public WordServiceModel editWord(Long id, WordServiceModel wordServiceModel) {
        WordEntity wordEntity = this.wordRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Word with id" + id + " not found"));

        wordEntity.setEnglish(wordServiceModel.getEnglish());
        wordEntity.setBulgarian(wordServiceModel.getBulgarian());
        wordEntity.setCategory(this.modelMapper.map(wordServiceModel.getCategory(), CategoryEntity.class));

        return this.modelMapper.map(this.wordRepository.saveAndFlush(wordEntity), WordServiceModel.class);
    }

    @Override
    public void deleteWord(Long id) {
        this.wordRepository.deleteById(id);
    }
}
