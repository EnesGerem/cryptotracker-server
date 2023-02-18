package com.enes.cryptotracker.general.parameter;

import com.enes.cryptotracker.general.parameter.entity.Parameter;
import com.enes.cryptotracker.general.parameter.entity.enums.ParameterKey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ParameterService {
    private final ParameterRepository parameterRepository;

    @Transactional(readOnly = true)
    public String retrieveParameterValueByKey(ParameterKey key) {
        Parameter parameter = parameterRepository.findParameterByKey(key).orElseThrow(()->new IllegalArgumentException("Parameter is not exist"));
        return parameter.getValue();
    }

    @Transactional(readOnly = true)
    public List<String> retrieveParameterValueListByKey(ParameterKey key) {
        String value = retrieveParameterValueByKey(key);
        return Arrays.asList(value.split(","));
    }
}
