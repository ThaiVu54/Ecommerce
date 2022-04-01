package com.example.thuongmai.controller;

import com.example.thuongmai.Service.origin.IOriginService;
import com.example.thuongmai.model.origin.Origin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/origins")
public class OriginController {
    @Autowired
    private IOriginService originService;

    @GetMapping
    public ResponseEntity<Iterable<Origin>> findAll() {
        return new ResponseEntity<>(originService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<Origin>> findPage(@PageableDefault(value = 4) Pageable pageable) {
        return new ResponseEntity<>(originService.findAllPage(pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<Origin> originOptional = originService.findById(id);
        if (!originOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(originOptional.get(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Origin> updateById(@PathVariable Long id, @RequestBody Origin origin) {
        Optional<Origin> originOptional = originService.findById(id);
        if (!originOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        originOptional.get().setName(origin.getName());
        return new ResponseEntity<>(originService.save(originOptional.get()), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        originService.deleteById(id);
        Optional<Origin> origin = originService.findById(id);

        if (!origin.isPresent()) {
            return new ResponseEntity<>("Xóa thành công", HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>("Xóa không thành công", HttpStatus.NO_CONTENT);
    }
}
