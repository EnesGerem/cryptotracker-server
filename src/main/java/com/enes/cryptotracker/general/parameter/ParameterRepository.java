package com.enes.cryptotracker.general.parameter;

import com.enes.cryptotracker.general.parameter.entity.Parameter;
import com.enes.cryptotracker.general.parameter.entity.enums.ParameterKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParameterRepository extends JpaRepository<Parameter, Long> {
    Optional<Parameter> findParameterByKey(ParameterKey key);
}
