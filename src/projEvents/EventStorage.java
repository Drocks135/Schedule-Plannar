package projEvents;

import java.util.HashMap;
import java.time.LocalDate;
import java.util.LinkedList;

public class EventStorage {
    HashMap<String, LinkedList<Events>> EventMap;

    private static EventStorage storage;

    private EventStorage(){
       EventMap = new HashMap<>();
    }

    public static EventStorage getInstance(){
        if (storage == null)
            storage = new EventStorage();
        return storage;
    }

    public void addEvent(Events event){
        LocalDate date = event.getDue();

        String key = Integer.toString(date.getDayOfMonth()) + date.getMonthValue() + date.getYear();
        LinkedList<Events> list;
        if(EventMap.containsKey(key)){
            list = EventMap.get(key);
            list.add(event);
        } else {
            list = CreateList(event);
            EventMap.put(key, list);
        }
    }

    public LinkedList<Events> GetListOfDay(LocalDate date){
        String key = Integer.toString(date.getDayOfMonth()) + date.getMonthValue() + date.getYear();
        return EventMap.get(key);
    }

    private LinkedList<Events> CreateList(Events event){
        LinkedList<Events> list = new LinkedList<>();
        list.add(event);
        return list;
    }

}
