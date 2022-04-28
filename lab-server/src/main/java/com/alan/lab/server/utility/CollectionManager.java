package com.alan.lab.server.utility;

import com.alan.lab.common.data.Person;
import com.alan.lab.common.exceptions.NotMinException;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class CollectionManager {
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


    public void clear() {
        ids.clear();
        passwordIds.clear();
        mainData.clear();
    }
    public boolean update(Person person) {
        removeByID(person.getId());
        return add(person);
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public Integer getSize() {
        return mainData.size();
    }

    public Class getType() {
        return mainData.getClass();
    }

    public PriorityQueue<Person> getMainData() {
        return mainData;
    }

    public Long getNewID() {
        while (ids.contains(iterId)) {
            iterId++;
        }
        return iterId;
    }

    public boolean addMin(Person person) throws NotMinException {
        if (getMinHeight() > person.getHeight()) {
         return    add(person);
        } else {
            throw new NotMinException();
        }

    }

    public boolean add(Person person) {
        if (!isContains(person.getPassportID())) {
            person.setId(getNewID());
            ids.add(person.getId());
            passwordIds.add(person.getPassportID());
            mainData.add(person);
            return true;
        }
        return false;

    }

    public boolean removeByID(Long id) {
        if (mainData.stream().anyMatch(x -> x.getId().equals(id))) {
            Person person = mainData.stream().filter(x -> x.getId().equals(id)).findAny().get();
            passwordIds.remove(person.getPassportID());
            mainData.remove(person);
            removeId(id);
            return true;
        }
        return false;
    }

    public Double averageHeight() {
        OptionalDouble average = mainData.stream()
                .map(Person::getHeight)
                .mapToDouble(value -> value)
                .average();
        return average.orElse(0D);
    }

    public void removeId(Long id) {
        ids.remove(id);
    }

    public boolean isHaveId(Long id) {
        return ids.contains(id);
    }

    public boolean isEmpty() {
        return mainData.isEmpty();
    }

    public boolean isContains(String passportID) {
        return passwordIds.contains(passportID);
    }

    public Person poll() {
        return mainData.poll();
    }

    public Float getMinHeight() {
        Optional<Float> optionHeight = mainData.stream().map(Person::getHeight).min(Float::compare);
        return optionHeight.orElse(0F);

    }

    public List<Person> descending() {
        return this.mainData.stream().sorted().collect(Collectors.toList());
    }

    public List<Person> filterGreaterThanHeight(Float height) {
        return this.mainData.stream().filter(person -> person.getHeight() > height).collect(Collectors.toList());
    }
    @Override
    public String toString() {
        return mainData.toString();
    }
}
