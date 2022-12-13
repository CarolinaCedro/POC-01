package io.github.CarolinaCedro.POC01.domain.entities;

import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;

import java.util.ArrayList;
import java.util.List;

public class CustomerGroupSequenceProvider implements DefaultGroupSequenceProvider<Customer> {

    @Override
    public List<Class<?>> getValidationGroups(Customer customer) {
        List<Class<?>> groups = new ArrayList<>();
        groups.add(Customer.class);

        if (isPeopleSelected(customer)){
            groups.add(customer.getPjOrPf().getGroup());
        }

        return  groups;
    }

    protected boolean isPeopleSelected(Customer customer) {
        return customer != null && customer.getPjOrPf() != null;
    }
}
