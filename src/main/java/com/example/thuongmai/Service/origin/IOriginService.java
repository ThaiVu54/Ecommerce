package com.example.thuongmai.Service.origin;

import com.example.thuongmai.Service.IGeneralService;
import com.example.thuongmai.model.origin.Origin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IOriginService extends IGeneralService<Origin> {
    Page<Origin> findAllPage(Pageable pageable);
}
