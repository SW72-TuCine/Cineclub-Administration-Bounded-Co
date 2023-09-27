package com.tucine.cineclubadministration.Film.service.interf;

import com.tucine.cineclubadministration.Film.dto.normal.AwardDto;
import com.tucine.cineclubadministration.Film.dto.normal.CategoryDto;
import com.tucine.cineclubadministration.Film.dto.receive.AwardReceiveDto;
import com.tucine.cineclubadministration.Film.dto.receive.CategoryReceiveDto;

import java.util.List;

public interface AwardService {

    List<AwardDto> getAllAwards();
    AwardDto createAward(AwardReceiveDto awardReceiveDto);
    AwardDto modifyAward(Long awardId, AwardReceiveDto awardReceiveDto);
    public void deleteAward(Long awardId);
}
