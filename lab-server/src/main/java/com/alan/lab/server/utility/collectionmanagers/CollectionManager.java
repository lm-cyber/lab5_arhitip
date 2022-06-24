package com.alan.lab.server.utility.collectionmanagers;

import com.alan.lab.common.data.Person;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class CollectionManager {
    private PriorityBlockingQueue<Person> mainData;
    private final LocalDate creationDate = LocalDate.now();
    private Set<Long> ids = ConcurrentHashMap.newKeySet();
    private Set<String> passwordIds = ConcurrentHashMap.newKeySet();
    private AtomicLong iterId = new AtomicLong(0L); // хотя для 64 бит машин пофиг

    public void initialiseData(PriorityBlockingQueue<Person> people) {
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
        if (removeByID(person.getId(), person.getOwnerID())) {
            addWithoutChecks(person);
            return true;
        }
        return false;
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

    public PriorityBlockingQueue<Person> getMainData() {
        return mainData;
    }

    public AtomicLong getNewID() {
        while (ids.contains(iterId.get())) {
            iterId.incrementAndGet();
        }
        return iterId;
    }

    public boolean addMin(Person person) {
        if (getMinHeight() > person.getHeight()) {
            return add(person);
        }
        return false;

    }

    public boolean add(Person person) {
        if (!isContainsPasswordIds(person.getPassportID())) {
            person.setId(getNewID().get());
            addWithoutChecks(person);
            return true;
        }
        return false;

    }
    private void addWithoutChecks(Person person) {
        ids.add(person.getId());
        passwordIds.add(person.getPassportID());
        mainData.add(person);
    }

    public boolean removeByID(Long id, Long userID) {
        Optional<Person> person = mainData.stream().filter(x -> x.getId().equals(id)).findAny();
        if (person.isPresent()) {
            if (person.get().getOwnerID().equals(userID)) {
                remove(person.get());
                return true;
            }
        }
        return false;
    }
    private void remove(Person person) {
        passwordIds.remove(person.getPassportID());
        ids.remove(person.getId());
        mainData.remove(person);
    }

    public Double averageHeight() {
        OptionalDouble average = mainData.stream()
                .map(Person::getHeight)
                .mapToDouble(value -> value)
                .average();
        return average.orElse(0D);
    }


    public boolean isContainsId(Long id) {
        return ids.contains(id);
    }

    public boolean isEmpty() {
        return mainData.isEmpty();
    }

    public boolean isContainsPasswordIds(String passportID) {
        return passwordIds.contains(passportID);
    }

    public boolean checkOwner(Long personID, Long userID) {
        Optional<Person> person = mainData.stream().filter(x -> x.getId().equals(personID)).findAny();
        return person.filter(value -> Objects.equals(value.getOwnerID(), userID)).isPresent();
    }

    public  Person peek() {
        return mainData.peek();
    }
    public Person poll(Long userID) {
        if (!Objects.equals(mainData.peek().getOwnerID(), userID)) {
            return null;
        }
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
