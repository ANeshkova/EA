package bg.an.englishacademy.service.impl;

import bg.an.englishacademy.model.entity.*;
import bg.an.englishacademy.model.service.LessonLogServiceModel;
import bg.an.englishacademy.repository.LessonLogRepository;
import bg.an.englishacademy.service.LessonLogService;
import bg.an.englishacademy.service.LessonService;
import bg.an.englishacademy.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LessonLogServiceImpl implements LessonLogService {

    private final LessonLogRepository lessonLogRepository;
    private final LessonService lessonService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public LessonLogServiceImpl(LessonLogRepository lessonLogRepository, LessonService lessonService,
                                UserService userService, ModelMapper modelMapper) {
        this.lessonLogRepository = lessonLogRepository;
        this.lessonService = lessonService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public void createLog(String action, Long lessonId) {
        LessonEntity lessonEntity = this.modelMapper
                .map(this.lessonService.findLessonById(lessonId), LessonEntity.class);

        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        String username = authentication.getName();

        UserEntity userEntity = this.modelMapper
                .map(this.userService.findUserByUsername(username), UserEntity.class);

        LessonLogEntity lessonLogEntity = new LessonLogEntity()
                .setUserEntity(userEntity)
                .setLessonEntity(lessonEntity)
                .setAction(action)
                .setDateTime(LocalDateTime.now());

        lessonLogRepository.save(lessonLogEntity);
    }
}
