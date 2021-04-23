package com.wrvpark.apiserver.controllers;


import com.wrvpark.apiserver.dto.requests.DeleteDTO;
import com.wrvpark.apiserver.dto.EventDTO;
import com.wrvpark.apiserver.dto.requests.NewEventDTO;
import com.wrvpark.apiserver.service.EventService;
import com.wrvpark.apiserver.util.ConstantUtil;
import com.wrvpark.apiserver.util.ResultEntity;
import com.wrvpark.apiserver.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;


/**
 * @author Isabel Ke
 * Original date:2020-02-07
 *
 * Description:event controller
 */
@CrossOrigin(origins = "*",maxAge = 3600)
@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventService eventService;

    /**
     * get all events
     * @return a list of events or null if no data
     */
    @GetMapping("")
    public ResultEntity<List<EventDTO>> findAllEvents()
    {
        return eventService.getAllEvents(null);
    }

    /**
     * add a new event
     * @param eventDTO the new event that will be added
     * @return a list of events after adding
     */
    @PostMapping("")
    public ResultEntity<List<EventDTO>> createEvent(@RequestBody NewEventDTO eventDTO)
    {
        return eventService.createEvent(eventDTO);
    }

    /**
     * delete en event by event id
     * @param
     * @return a list of events after deleting
     */
    @PutMapping("/delete")
    public ResultEntity<List<EventDTO>> deleteEventById(@RequestBody DeleteDTO dto, Principal principal,
                                                        Authentication authentication)
    {
        dto.setModifierId(principal.getName());
       boolean isAdmin= SecurityUtil.hasRole(authentication, ConstantUtil.ROLE_ADMIN);
       return  eventService.deleteEventById(dto,isAdmin);
    }

    /**
     * find all events under this sub-category
     * @param locId id of the location sub-category
     * @param  descId id of the description sub-category
     * @return a list of events that under this sub-category or null if not events matches
     */
    @GetMapping("/search")
    public ResultEntity<List<EventDTO>> getEventsBySubCategory(@RequestParam String locId,
                                                               @RequestParam String descId)
    {
        return  eventService.getEventsBySubCategory(locId,descId);
    }

    /**
     * get an event by its event id
     * @param eventId id of the event
     * @return an event object
     */
    @GetMapping("/search/{eventId}")
    public ResultEntity<EventDTO> getEventsByEventId(@PathVariable String eventId)
    {
        return  eventService.getEventByEventId(eventId);
    }

    /**
     * search event by event name
     * @param eventName event name
     * @return a list of matched events, or empty data
     */
    @GetMapping("/searchName/{eventName}")
    public ResultEntity<List<EventDTO>> getEventsByName(@PathVariable String eventName)
    {
        return  eventService.getEventByName(eventName);
    }

    /**
     * search event by user id
     * @param userId the id of the event creator
     * @return a list of matched events, or empty data with message
     */
    @GetMapping("/searchUser/{userId}")
    public ResultEntity<List<EventDTO>> getEventsByUser(@PathVariable String userId)
    {
        return  eventService.getEventByUser(userId);
    }

    /**
     * update an event
     * @param eventDTO event that will be updated
     * @return a list of events after updating
     */
    @PutMapping("")
    public ResultEntity<List<EventDTO>> updateEventById(@RequestBody NewEventDTO eventDTO,
                                                        Authentication authentication
                                                        )
    {
        boolean isAdmin= SecurityUtil.hasRole(authentication, ConstantUtil.ROLE_ADMIN);
        return  eventService.updateEventById(eventDTO,isAdmin);
    }
}
