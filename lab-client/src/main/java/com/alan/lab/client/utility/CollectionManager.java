package com.alan.lab.client.utility;

import com.alan.lab.client.data.Person;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.PriorityQueue;

public class CollectionManager {
    private PriorityQueue<Person> mainData = new PriorityQueue<>();
    private final LocalDate creationDate = LocalDate.now();
    private HashSet<Long> ids =new HashSet<>();
    private HashSet<String> passwordIds = new HashSet<>();

    public void initialiseData(PriorityQueue<Person> people) {
        this.mainData = people;
        for (Person person:mainData) {
            ids.add(person.getId());
            passwordIds.add(person.getPassportID());
        }
    }


    public LocalDate getCreationDate() {
        return creationDate;
    }

    public int add(Person person) {
        if(ids.contains(person.getId())) {
            return 1;
        } if(passwordIds.contains(person.getPassportID())) {
            return 2;
        }
        ids.add(person.getId());
        passwordIds.add(person.getPassportID());
        mainData.add(person);
        return 0;
    }

    public Long getMinId() {
        Optional<Long> optionHeight = mainData.stream().map(Person::getId).min(Long::compare);
        return optionHeight.orElse(0L);

    }
}
