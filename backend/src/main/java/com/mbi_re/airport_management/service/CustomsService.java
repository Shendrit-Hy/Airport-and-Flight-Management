package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.dto.CustomsDTO;
import com.mbi_re.airport_management.model.Customs;
import com.mbi_re.airport_management.repository.CustomsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomsService {

    @Autowired
    private CustomsRepository repo;

    public CustomsDTO create(CustomsDTO dto) {
        Customs c = new Customs();
        c.setOfficerName(dto.getOfficerName());
        c.setItemChecked(dto.getItemChecked());
        c.setDate(dto.getDate());
        repo.save(c);
        dto.setId(c.getId());
        return dto;
    }
}
