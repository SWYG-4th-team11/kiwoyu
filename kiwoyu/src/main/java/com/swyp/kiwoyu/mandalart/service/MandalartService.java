package com.swyp.kiwoyu.mandalart.service;
import com.swyp.kiwoyu.goal.domain.Goal;
import com.swyp.kiwoyu.goal.dto.UpdateGoalResponseDto;
import com.swyp.kiwoyu.mandalart.domain.Mandalart;
import com.swyp.kiwoyu.mandalart.dto.UpdateMandalartRequestDto;
import com.swyp.kiwoyu.mandalart.repository.MandalartRepository;
import com.swyp.kiwoyu.mandalart.repository.MandalartRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class MandalartService {

    @Autowired
    private MandalartRepository mandalartRepository;
    private MandalartRepositoryImpl mandalartRepositoryImpl;

    public List<Mandalart> getAllMandalarts() {
        return mandalartRepository.findAll();
    }

    public Optional<Mandalart> getMandalartById(Long id) {
        return mandalartRepository.findById(id);
    }

    public Mandalart createOrUpdateMandalart(Mandalart mandalart) {
        return mandalartRepository.save(mandalart);
    }

    public Mandalart createOrUpdateMandalartWithUserId(Mandalart mandalart, Long userId) {
        return mandalartRepository.createOrUpdateMandalartWithUserId(mandalart,userId);
    }
    public void deleteMandalart(Long id) {
        mandalartRepository.deleteById(id);
    }

    public UpdateMandalartRequestDto updateMandalartCategory(UpdateMandalartRequestDto dto){
        Optional<Mandalart> existingMandalart = getMandalartById(dto.getId());
        if (existingMandalart.isPresent()) {
            Mandalart m = existingMandalart.get();
            m.setCategory(dto.getCategory());

            return new UpdateMandalartRequestDto(mandalartRepository.save(m));
        } else {
            return null;
        }
    }
}