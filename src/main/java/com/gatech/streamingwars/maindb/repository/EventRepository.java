package com.gatech.streamingwars.maindb.repository;

import com.gatech.streamingwars.maindb.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event,Long> {
    public Event findEventByName(String eventName);
}
