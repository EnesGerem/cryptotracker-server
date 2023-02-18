package com.enes.cryptotracker.parameter;

import com.enes.cryptotracker.general.AbstractUnitTest;
import com.enes.cryptotracker.general.TestEntityBuilder;
import com.enes.cryptotracker.general.parameter.ParameterRepository;
import com.enes.cryptotracker.general.parameter.ParameterService;
import com.enes.cryptotracker.general.parameter.entity.Parameter;
import com.enes.cryptotracker.general.parameter.entity.enums.ParameterKey;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParameterServiceTest extends AbstractUnitTest {

    @InjectMocks
    private ParameterService parameterService;

    @Mock
    private ParameterRepository parameterRepository;

    @Test
    public void testRetrieveParameterValueByKey() {
        Parameter parameter = new Parameter(ParameterKey.UNSUPPORTED_CURRENCIES, TestEntityBuilder.generateRandomString());

        Mockito.when(parameterRepository.findParameterByKey(ParameterKey.UNSUPPORTED_CURRENCIES)).thenReturn(Optional.of(parameter));

        String parameterValueByKey = parameterService.retrieveParameterValueByKey(ParameterKey.UNSUPPORTED_CURRENCIES);

        Mockito.verify(parameterRepository).findParameterByKey(parameter.getKey());
        assertEquals(parameter.getValue(), parameterValueByKey);
    }

    @Test
    public void testRetrieveParameterValueListByKey() {
        Parameter parameter = new Parameter(ParameterKey.UNSUPPORTED_CURRENCIES, TestEntityBuilder.generateRandomString()+","+TestEntityBuilder.generateRandomString());

        Mockito.when(parameterRepository.findParameterByKey(ParameterKey.UNSUPPORTED_CURRENCIES)).thenReturn(Optional.of(parameter));

        List<String> parameterValueListByKey = parameterService.retrieveParameterValueListByKey(ParameterKey.UNSUPPORTED_CURRENCIES);

        Mockito.verify(parameterRepository).findParameterByKey(ParameterKey.UNSUPPORTED_CURRENCIES);
        assertEquals(2, parameterValueListByKey.size());
        List<String> expectedValues = Arrays.asList(parameter.getValue().split(","));
        assertEquals(expectedValues.get(0), parameterValueListByKey.get(0));
        assertEquals(expectedValues.get(1), parameterValueListByKey.get(1));
    }
}
