package es.iesnervion.mcasado.todolists.DB;

import androidx.room.TypeConverter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class Converters {

    //Not needed for now...
/*    @TypeConverter
    public static LocalDateTime LongToLocalDateTime (Long value){
        return value == null ? null : LocalDateTime.ofInstant(Instant.ofEpochMilli(value),ZoneId.systemDefault());
    }

    @TypeConverter
    public static Long LocalDateTimeToLong (LocalDateTime dateTime){
        Long value;
        value = dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        return value;
    }*/

    @TypeConverter
    public static int fromPriority (Priority priority){
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
    public static Priority fromInt (int value){
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

    @TypeConverter
    public static LocalDate LocalDatefromLong (long value){
        LocalDate date;
        date = Instant.ofEpochMilli(value).atZone(ZoneId.systemDefault()).toLocalDate();
        return date;
    }

    @TypeConverter
    public static long LongFromLocalDate (LocalDate date){
        long value;
        value = date.atStartOfDay(ZoneId.systemDefault()).toEpochSecond();
        return value;
    }

    @TypeConverter
    public static LocalTime LocalTimefromString (String stringTime){
        LocalTime time;
        time = LocalTime.parse(stringTime);
        return time;
    }

    @TypeConverter
    public static String StringFromLocalTime (LocalTime time){
        String stringTime;
        stringTime = time.toString();
        return stringTime;
    }

}
