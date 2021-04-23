package com.wrvpark.apiserver.service;

import com.wrvpark.apiserver.dto.requests.DeleteDTO;
import com.wrvpark.apiserver.dto.EventDTO;
import com.wrvpark.apiserver.dto.requests.NewEventDTO;
import com.wrvpark.apiserver.util.ResultEntity;

import java.util.List;

/**
 * @author Isabel Ke
 * Original date:2020-02-07
 *
 * Description:event service interface
 */
public interface EventService {
    /*
    get all events
     */
   ResultEntity<List<EventDTO>> getAllEvents(String message);

    /*
    add a new event
     */
    ResultEntity<List<EventDTO>> createEvent(NewEventDTO eventDTO);

    /*
       delete an event by its id
    */
    ResultEntity<List<EventDTO>> deleteEventById(DeleteDTO eventId, boolean isAdmin);

    /*
    find all events under the specific sub-category
     */
    ResultEntity<List<EventDTO>> getEventsBySubCategory(String locId,String descId);

    /*
    update an evetn by its id
     */
    ResultEntity<List<EventDTO>> updateEventById(NewEventDTO eventDTO, boolean isAdmin);

    /*
    get an event by its id
     */
    ResultEntity<EventDTO> getEventByEventId(String eventId);
   //search event by event's name
    ResultEntity<List<EventDTO>> getEventByName(String eventName);
  //search event by the creator id
    ResultEntity<List<EventDTO>> getEventByUser(String userId);
}
