import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TimetableImpl implements Timetable {

    public Map<Pair<String, String>, Integer> getData() {
        return data;
    }

    private final Map<Pair<String, String>, Integer> data;

    public TimetableImpl() {
        this.data = new HashMap<>();
    }

    public TimetableImpl(Map<Pair<String, String>, Integer> data) {
        this.data = new HashMap<>(data);
    }

    @Override
    public Timetable addHour(String activity, String day) {
        Map<Pair<String, String>, Integer> newData = new HashMap<>(data);
        Pair<String, String> key = new Pair<>(activity, day);
        newData.put(key, newData.getOrDefault(key, 0) + 1);
        return new TimetableImpl(newData);
    }

    @Override
    public Set<String> activities() {
        Set<String> activities = new HashSet<>();
        for (Pair<String, String> key : data.keySet()) {
            activities.add(key.get1());
        }
        return activities;
    }

    @Override
    public Set<String> days() {
        Set<String> days = new HashSet<>();
        for (Pair<String, String> key : data.keySet()) {
            days.add(key.get2());
        }
        return days;
    }

    @Override
    public int getSingleData(String activity, String day) {
        return data.getOrDefault(new Pair<>(activity, day), 0);
    }

    @Override
    public int sums(Set<String> activities, Set<String> days) {
        int sum = 0;
        for (Pair<String, String> key : data.keySet()) {
            if (activities.contains(key.get1()) && days.contains(key.get2())) {
                sum += data.get(key);
            }
        }
        return sum;
    }
}
