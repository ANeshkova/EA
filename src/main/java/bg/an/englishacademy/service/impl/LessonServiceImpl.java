package bg.an.englishacademy.service.impl;

import bg.an.englishacademy.model.entity.LessonEntity;
import bg.an.englishacademy.model.service.LessonServiceModel;
import bg.an.englishacademy.repository.LessonRepository;
import bg.an.englishacademy.service.LessonService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;
    private final ModelMapper modelMapper;

    public LessonServiceImpl(LessonRepository lessonRepository, ModelMapper modelMapper) {
        this.lessonRepository = lessonRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public LessonServiceModel addLesson(LessonServiceModel lessonServiceModel) {
        LessonEntity lessonEntity = this.modelMapper.map(lessonServiceModel, LessonEntity.class);
        lessonEntity.setAddedOn(LocalDate.now());

        this.lessonRepository.save(lessonEntity);
        return lessonServiceModel;
    }

    @Override
    public boolean lessonTitleExists(String title) {
        return this.lessonRepository.findByTitle(title).isPresent();
    }

    @Override
    public List<LessonServiceModel> findAllLessons() {
        return this.lessonRepository.findAll()
                .stream().map(l -> this.modelMapper.map(l, LessonServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public LessonServiceModel findLessonById(Long id) {
        LessonEntity lessonEntity = this.lessonRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Lesson with id " + id + " not found."));

        return this.modelMapper.map(lessonEntity, LessonServiceModel.class);
    }

    @Override
    public LessonServiceModel editLesson(Long id, LessonServiceModel lessonServiceModel) {
        LessonEntity lessonEntity = this.lessonRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Lesson with id " + id + " not found."));

        lessonEntity.setTitle(lessonServiceModel.getTitle());
        lessonEntity.setDescription(lessonServiceModel.getDescription());
        lessonEntity.setVideoUrl(lessonServiceModel.getVideoUrl());

        return this.modelMapper.map(this.lessonRepository.saveAndFlush(lessonEntity), LessonServiceModel.class);
    }

    @Override
    public void deleteLesson(Long id) {
        this.lessonRepository.deleteById(id);
    }
}
