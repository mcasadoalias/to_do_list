package es.iesnervion.mcasado.todolists.DB;

import androidx.room.TypeConverter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class Converters {
    @TypeConverter
    public static LocalDateTime LongToLocalDateTime (Long value){
        //TODO Change now for proper conversion
        return value == null ? null : LocalDateTime.ofInstant(Instant.ofEpochMilli(value),ZoneId.systemDefault());
    }

    @TypeConverter
    public static Long LocalDateTimeToLong (LocalDateTime dateTime){
        Long value = 3475345L;
        //TODO Check conversion
        value = dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        return value;
    }

    @TypeConverter
    public static int PriorityToInt (Priority priority){
        int value;
        switch (priority){
            case HIGH:
                value=2;
                break;
            case NORMAL:
                value=1;
                break;
            case LOW:
                value=0;
                break;
            case UNDEFINED:
            default:
                value = -1;
                break;
        }
        return value;
    }

    @TypeConverter
    public static Priority IntToPriority (int value){
        Priority priority;
        switch (value){
            case 2:
                priority=Priority.HIGH;
                break;
            case 1:
                priority = Priority.NORMAL;
                break;
            case 0:
                priority = Priority.LOW;
                break;
            case -1:
            default :
                priority = Priority.UNDEFINED;
                break;
        }
        return priority;
    }

}
