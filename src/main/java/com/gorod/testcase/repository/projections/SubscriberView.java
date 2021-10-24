package com.gorod.testcase.repository.projections;

import java.util.Set;

public interface SubscriberView {
    Long getId();
    String getFio();
    String getAccount();

    Set<ServiceWithoutChildren> getServices();
}
