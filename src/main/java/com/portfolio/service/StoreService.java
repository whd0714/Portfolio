package com.portfolio.service;

import com.portfolio.domain.Store;
import com.portfolio.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    @PostConstruct
    public void initData() throws IOException {
        if(storeRepository.count() == 0){
            Resource resource = new ClassPathResource("store.csv");
            List<Store> stores = Files.readAllLines(resource.getFile().toPath(), StandardCharsets.UTF_8).stream()
                    .map(line -> {
                        String[] split = line.split(",");
                        return Store.builder().storeName(split[0]).build();
                    }).collect(Collectors.toList());
            storeRepository.saveAll(stores);
        }
    }

}
