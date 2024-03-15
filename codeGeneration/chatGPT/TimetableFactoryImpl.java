import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;

public class TimetableFactoryImpl implements TimetableFactory {

    public Timetable empty() {
        return new TimetableImpl();
    }

    @Override
    public Timetable single(String activity, String day) {
        return new TimetableImpl().addHour(activity, day);
    }

    @Override
    public Timetable join(Timetable table1, Timetable table2) {
        TimetableImpl result = new TimetableImpl();

        // Uniamo le ore dalla prima tabella
        for (String activity : table1.activities()) {
            for (String day : table1.days()) {
                int hours = table1.getSingleData(activity, day);
                for (int i = 0; i < hours; i++) {
                    result = (TimetableImpl) result.addHour(activity, day);
                }
            }
        }

        // Uniamo le ore dalla seconda tabella
        for (String activity : table2.activities()) {
            for (String day : table2.days()) {
                int hours = table2.getSingleData(activity, day);
                for (int i = 0; i < hours; i++) {
                    result = (TimetableImpl) result.addHour(activity, day);
                }
            }
        }

        return result;
    }

    @Override
    public Timetable cut(Timetable table, BiFunction<String, String, Integer> bounds) {
        Map<Pair<String, String>, Integer> newData = new HashMap<>();
        Map<Pair<String, String>, Integer> oldData = ((TimetableImpl) table).getData();

        for (Map.Entry<Pair<String, String>, Integer> entry : oldData.entrySet()) {
            String activity = entry.getKey().get1();
            String day = entry.getKey().get2();
            int hours = entry.getValue();
            int maxHours = bounds.apply(activity, day);
            int newHours = Math.min(hours, maxHours);
            newData.put(new Pair<>(activity, day), newHours);
        }

        return new TimetableImpl(newData);
    }

}