package com.esvaga.testenv.interfaces.repositories;

import java.util.List;

public interface IInterventionRepository<T, K> {

    List<T> getAll();

   // T GetByKey (K key);


}
