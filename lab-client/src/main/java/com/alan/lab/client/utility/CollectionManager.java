package com.alan.lab.client.utility;

import com.alan.lab.client.data.Person;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.PriorityQueue;

public class CollectionManager {
    private static final int MAX = 49;
    private static final int MIN = 6;
    private PriorityQueue<Person> mainData = new PriorityQueue<>();
    private final LocalDate creationDate = LocalDate.now();
    private HashSet<Long> ids = new HashSet<>();
    private HashSet<String> passwordIds = new HashSet<>();
    private Long iterId = 0L;

    public void initialiseData(PriorityQueue<Person> people) {
        this.mainData = people;
        for (Person person : mainData) {
            ids.add(person.getId());
            passwordIds.add(person.getPassportID());
        }
    }

    public PriorityQueue<Person> getMainData() {
        return mainData;
    }

    public void clear() {
        ids.clear();
        passwordIds.clear();
        mainData.clear();
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public int add(Person person) {

        if (person.getPassportID().length() >= MAX || person.getPassportID().length() <= MIN) {
            return 1;
        }
        if (passwordIds.contains(person.getPassportID())) {
            return 2;
        }
        while (ids.contains(iterId)) {
            iterId++;
        }
        person.setId(iterId);
        ids.add(person.getId());
        passwordIds.add(person.getPassportID());
        mainData.add(person);
        return 0;
    }

    public void delete(Person person) {
        ids.remove(person.getId());
        passwordIds.remove(person.getPassportID());
        mainData.remove(person);
    }

    public Long getMinId() {
        Optional<Long> optionHeight = mainData.stream().map(Person::getId).min(Long::compare);
        return optionHeight.orElse(0L);

    }
}
