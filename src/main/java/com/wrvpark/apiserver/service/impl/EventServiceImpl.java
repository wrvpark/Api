package com.wrvpark.apiserver.service.impl;

import com.wrvpark.apiserver.domain.*;
import com.wrvpark.apiserver.dto.requests.DeleteDTO;
import com.wrvpark.apiserver.dto.EventDTO;
import com.wrvpark.apiserver.dto.requests.NewEventDTO;
import com.wrvpark.apiserver.repository.EventRepository;
import com.wrvpark.apiserver.repository.NonParkDocumentLogRepository;
import com.wrvpark.apiserver.repository.SubCategoryRepository;
import com.wrvpark.apiserver.repository.UserRepository;
import com.wrvpark.apiserver.service.EventService;
import com.wrvpark.apiserver.util.ResultEntity;
import com.wrvpark.apiserver.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;


/**
 * @author Isabel Ke
 * @author Vahid Haghighat
 * Original date:2020-02-07
 *
 * Description:event service class that handles all the event logic
 */
@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private SubCategoryRepository subCategoryRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NonParkDocumentLogRepository nonParkDocumentLogRepository;

    /**
     * find all events
     */
    @Override
    public ResultEntity<List<EventDTO>> getAllEvents(String message) {
        List<Event> events=eventRepository.findAll(Sort.by(Sort.Direction.DESC, "createTime"));
        //check if there is data
        if(events.isEmpty())
        {
            return ResultEntity.successWithOutData("No events");
        }
        List<EventDTO> eventDTOS=convertEvents(events);

        return ResultEntity.successWithData(eventDTOS,message);
    }


    /**
     * add a new event
     */
    @Override
    public ResultEntity<List<EventDTO>> createEvent(NewEventDTO eventDTO) {
        //save this event in the database
        Event event=generateEvent(eventDTO);
        eventRepository.save(event);
        return getAllEvents("New event has been added!");
    }


    /**
     * delete an event
     * @param dto the event that will be deleted
     * @param isAdmin
     * @return a list of events after deleting
     */
    @Override
    public ResultEntity<List<EventDTO>> deleteEventById(DeleteDTO dto, boolean isAdmin) {
        Event event=eventRepository.findById(dto.getItemId()).get();
        //if the current user is either admin or the original creator, the event should not be deleted
        if(!dto.getModifierId().equals(event.getCreator().getId()) && !isAdmin)
        {
            return ResultEntity.failed("Only the creator or admin can delete it");
        }
        eventRepository.deleteById(dto.getItemId());
        //if this is admin deletes it, then add the entry to the log

        if(isAdmin)
        {
            User modifier=userRepository.findById(dto.getModifierId()).get();
            NonParkDocumentLog log=new NonParkDocumentLog(modifier,dto.getReason(),dto.getDescription(),
                    "DELETE",new Date(),dto.getItemId(),
                    event.getDescSubcategory().getCategory()
            );
            nonParkDocumentLogRepository.save(log);
        }
        return getAllEvents("The Event has been deleted");
    }

    /**
     * find all events under this sub-category
     * @param locId id of the location sub-category
     * @param descId id of the description sub-category
     * @return a list of events that under this sub-category or null if not events matches
     */
    @Override
    public ResultEntity<List<EventDTO>> getEventsBySubCategory(String locId,String descId) {
        List<Event> events;
        if (locId.isEmpty() && descId.isEmpty())
            events = eventRepository.findAll(Sort.by(Sort.Direction.DESC, "createTime"));
        else if(!locId.isEmpty() && descId.isEmpty())
        {
            events = eventRepository.findByLocationSubcategory(new Subcategory(locId));
        }
        else if(locId.isEmpty() && !descId.isEmpty())
        {
            events=eventRepository.findByDescSubcategory(new Subcategory(descId));
        }
        else
        {
            events=eventRepository.findByLocationSubcategoryAndDescSubcategory(new Subcategory(locId), new Subcategory(descId));

        }

        //check if there are any matched events
        if(events.size() > 0)
        {
            List<EventDTO> eventDTOS=convertEvents(events);
            return ResultEntity.successWithData(eventDTOS,"Find matched events!");
        }
       else
        {
            return ResultEntity.failed("No events under this sub-category!");
        }
    }

    /**
     * update an event
     * @param eventDTO event that will be updated
     * @param isAdmin
     * @return a list of events after updating
     */
    @Override
    public ResultEntity<List<EventDTO>> updateEventById(NewEventDTO eventDTO, boolean isAdmin) {
        Event event=eventRepository.findById(eventDTO.getId()).get();
        //if the current user is either admin or the original creator, the event should not be deleted
        if(!eventDTO.getUserId().equals(event.getCreator().getId()) && !isAdmin)
        {
            return ResultEntity.failed("Only the creator or admin can update it");
        }
       // update the event object
        Event updatedEvent=generateEvent(eventDTO);
        //update the event in the database
        eventRepository.save(updatedEvent);
        return getAllEvents("Updated the event");
    }

    /**
     * get an event by its event id
     * @param eventId id of the event
     * @return an event object
     */
    @Override
    public ResultEntity<EventDTO> getEventByEventId(String eventId) {
        if (eventRepository.findById(eventId).isPresent()) {
            Event event = eventRepository.findById(eventId).get();
            return ResultEntity.successWithData(new EventDTO(event), "Event found");
        } else {
            return ResultEntity.successWithOutData("Event not found");
        }
    }

    /**
     * get event by name
     * @param eventName event name
     * @return a list of matched events, or empty data with a message
     */
    @Override
    public ResultEntity<List<EventDTO>> getEventByName(String eventName) {
        if(eventName.isEmpty())
        {
            getAllEvents(null);
        }
        else
        {
            //create the query condition
            Specification<Event> specification = new Specification<Event>() {
                @Override
                public Predicate toPredicate(Root<Event> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                    Path<Object> title = root.get("title");
                    Predicate p = cb.like(title.as(String.class), "%" + eventName + "%");
                    return p;
                }

            };
            //sort by create time
            //conditional query
            Sort sort=Sort.by(Sort.Direction.DESC,"createTime");
            List<Event> events=eventRepository.findAll(specification,sort);
            if(events.size()==0)
            {
                return ResultEntity.failed("No events match");
            }
            else
            {
                List<EventDTO> eventDTOS=convertEvents(events);
                return ResultEntity.successWithData(eventDTOS,"Found matched events");
            }
        }
        return null;
    }

    /**
     * search events by user
     * @param userId of this user
     * @return a list of events that under this sub-category or null if not events matches
     */
    @Override
    public ResultEntity<List<EventDTO>> getEventByUser(String userId) {
        if(userId.isEmpty())
        {
            return ResultEntity.failed("user id cannot be empty");
        }
       List<Event> events=eventRepository.findByCreator(new User(userId));
        if(events.size()==0)
        {
            return ResultEntity.successWithOutData("Event not found");
        }

        List<EventDTO> eventDTOS=convertEvents(events);
        return ResultEntity.successWithData(eventDTOS,"Found matched events");
    }

    /**
     * generate an event from a eventDTO
     */
    private Event generateEvent(NewEventDTO eventDTO)
    {
        Event event;
        if(eventDTO.getId()!=null)
        {//get the event from the database
         event =eventRepository.findById(eventDTO.getId()).get();
        }
        //create a new event object
        else
        {
            event=new Event();
        }
        //set the title
        String title=eventDTO.getTitle();
        if(title!=null && !title.equals(""))
        {
            event.setTitle(title);
        }
        //set the description
        String desc=eventDTO.getDescription();
        if(desc!=null && !desc.equals(""))
        {
            event.setDescription(desc);
        }
        //set the description
        String detail=eventDTO.getDetails();
        if(detail!=null && !detail.equals(""))
        {
            event.setDetails(detail);
        }
        //add the current time
        event.setCreateTime(new Date());
        //set the start time
        String start=eventDTO.getStartTime();
        if(start!=null)
        {
            event.setStartTime(TimeUtil.convertStringToDate(start));
        }
        //set the end time
        String end=eventDTO.getEndTime();
        if(end!=null)
        {
            event.setEndTime(TimeUtil.convertStringToDate(end));
        }
        //set the file
        if(eventDTO.getFileName()!=null)
        {
            event.setFileName(eventDTO.getFileName());
        }
        //set the creator

        if(eventDTO.getUserId()!=null)
        {
            try {
                User user = userRepository.findById(eventDTO.getUserId()).get();
               event.setCreator(user);
            }catch (NoSuchElementException e)
            {
                ResultEntity.failed("This user does not exist!");
            }
        }
        //set the location sub-category
        if(eventDTO.getLocSubId()!=null && !eventDTO.getLocSubId().equals("")) {
            Subcategory locationSub = subCategoryRepository.findById(eventDTO.getLocSubId()).get();
        event.setLocationSubcategory(locationSub);
        }
        //set the description subcategory
        if(eventDTO.getDescSubId()!=null && !eventDTO.getDescSubId().equals("")) {
            Subcategory descSub = subCategoryRepository.findById(eventDTO.getDescSubId()).get();
            event.setDescSubcategory(descSub);
        }
        //check if the event is recurring
        if(eventDTO.isRecurring())
        {
            event.setRecurring(new EventRecuring(eventDTO));
        }
        return event;
    }

    /**
     * generate a eventDTOS from a list of events
     */
    private List<EventDTO> convertEvents( List<Event> events)
    {
        List<EventDTO> eventDTOS=new ArrayList<>();
        for (Event event : events) {
            eventDTOS.add(new EventDTO(event));
        }
        return eventDTOS;
    }
}
